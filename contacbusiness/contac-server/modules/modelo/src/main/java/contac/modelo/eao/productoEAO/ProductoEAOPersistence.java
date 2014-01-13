package contac.modelo.eao.productoEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Producto;
import contac.modelo.entity.ProductoExistencia;
import contac.modelo.entity.UnidadMedida;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Producto DAO Layer implementation JAVA SE
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-09-2010
 * Time: 11:21:39 PM
 */
public class ProductoEAOPersistence extends GenericPersistenceEAO<Producto, Integer> implements ProductoEAO {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(ProductoEAOPersistence.class);

    @Override
    public List<Producto> find(String codigo, String nombre, String codigoFabricante) throws PersistenceClassNotFoundException,
            GenericPersistenceEAOException {

        //Init service
        initService();

        String fromClause = "Select p from Producto p ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();
        //1. Agregando parametro de codigo
        querySolver.add(new QueryFragment(((codigo != null) && (!codigo.equals(""))), "", " p.codigo like :codigo ",
                "codigo", codigo.concat("%")));
        //2. Agregando parametro de nonbre
        querySolver.add(new QueryFragment(((nombre != null) && (!nombre.equals(""))), "", " p.nombre like :nombre ",
                "nombre", "%".concat(nombre).concat("%")));
        //3. Agregando parametro de codigo de fabricante
        querySolver.add(new QueryFragment(((codigoFabricante != null) && (!codigoFabricante.equals(""))), "",
                " p.codigoFabricante like :codigoFabricante ", "codigoFabricante", "%".concat(codigoFabricante).concat("%")));
        //3. Agregando estado de producto
        querySolver.add(new QueryFragment(true, "", " p.estado.id <> :estado", "estado", 6));

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        Query query = em.createQuery(ejbQuery);

        List<Producto> listProductos = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listProductos;
    }

    @Override
    public List<Producto> findByAlmacen(Integer idAlmacen) throws GenericPersistenceEAOException {

        //Init service
        initService();

        final Integer p_id_almacen = idAlmacen;

        final List<Producto> productos = new ArrayList<Producto>();

        Session session = em.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                StringBuffer query = new StringBuffer();
                query.append("select prod.N_ID, prod.C_CODIGO, prod.C_NOMBRE, prod.C_CODIGOFABRICANTE, ");
                query.append("sum(mov_inventario.N_CANTIDAD * mov_inventario.N_AFECTACION) as EXISTENCIA, sum(prod_existencia.N_CANTIDAD) as PROD_EXISTENCIA, ");
                query.append("unidadmed.C_NOMBRE, prod.N_COSTOFOB, prod.N_COSTOPROM, prod.N_COSTOUND, prod.N_PRECIOESTANDAR ");
                query.append("FROM INV_PRODUCTO as prod left join INV_MOV_INVENTARIO as mov_inventario ");
                query.append("on prod.N_ID = mov_inventario.N_ID_PRODUCTO ");
                query.append("left join INV_ESTADO_MOVIMIENTO as estado ");
                query.append("on mov_inventario.N_ID_ESTADO = estado.N_ID ");
                query.append("left join INV_ALMACEN as almacen_mov on mov_inventario.N_ID_ALMACEN = almacen_mov.N_ID ");
                query.append("left join INV_PRODUCTOEXISTENCIA as prod_existencia ");
                query.append("on prod.N_ID = prod_existencia.N_ID_PRODUCTO ");
                query.append("left join INV_ALMACEN as almacen_prod on prod_existencia.N_ID_ALMACEN = almacen_prod.N_ID ");
                query.append("inner join INV_UNIDADMEDIDA as unidadmed on prod.N_ID_UNIDADMEDIDA = unidadmed.N_ID ");
                query.append("inner join INV_LINEA as linea on prod.N_ID_LINEA = linea.N_ID ");
                query.append("inner join CMP_PROVEEDOR proveedor ON prod.N_ID_PROVEEDOR = proveedor.N_ID ");

                if (p_id_almacen > 0) {
                    query.append("where (almacen_mov.N_ID = ? OR almacen_mov.N_ID= ?) ");
                }

                query.append("group by prod.N_ID, prod.C_CODIGO, prod.C_NOMBRE, prod.C_CODIGOFABRICANTE ");
                query.append("HAVING EXISTENCIA > 0 ");
                query.append("order by prod.C_CODIGO ");

                PreparedStatement pstmt = connection.prepareStatement(query.toString());

                if (p_id_almacen > 0) {
                    pstmt.setInt(1, p_id_almacen);
                    pstmt.setInt(2, p_id_almacen);
                }

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getInt("N_ID"));
                    producto.setCodigo(rs.getString("C_CODIGO"));
                    producto.setNombre(rs.getString("C_NOMBRE"));
                    producto.setCodigoFabricante(rs.getString("C_CODIGOFABRICANTE"));
                    producto.setCostoFOB(rs.getBigDecimal("N_COSTOFOB"));
                    producto.setCostoPROM(rs.getBigDecimal("N_COSTOPROM"));
                    producto.setCostoUND(rs.getBigDecimal("N_COSTOUND"));
                    producto.setPrecioESTANDAR(rs.getBigDecimal("N_PRECIOESTANDAR"));

                    UnidadMedida unidadMedida = new UnidadMedida();
                    unidadMedida.setNombre(rs.getString("C_NOMBRE"));
                    producto.setUnidadMedida(unidadMedida);

                    //Calcular existencia
                    long existencia = rs.getLong("EXISTENCIA");

                    if (rs.getLong("PROD_EXISTENCIA") > 0) {
                        existencia += rs.getLong("PROD_EXISTENCIA");
                    }

                    ProductoExistencia prodExistencia = new ProductoExistencia();
                    prodExistencia.setExistencia(existencia);

                    Set<ProductoExistencia> existencias = new HashSet<ProductoExistencia>();
                    existencias.add(prodExistencia);

                    producto.setExistencias(existencias);

                    //Agregando producto al listado
                    productos.add(producto);
                }

            }
        });

        return productos;

    }


    @Override
    public List<Producto> find(String codigoDesde, String codigoHasta, Integer idLinea, Integer idAlmacen,
                               Long codigoProveedor) throws GenericPersistenceEAOException {

        //Init service
        initService();

        final String p_codigo_desde = codigoDesde;
        final String p_codigo_hasta = codigoHasta;
        final Integer p_id_linea = idLinea;
        final Integer p_id_almacen = idAlmacen;
        final Long p_codigo_proveedor = codigoProveedor;

        final List<Producto> productos = new ArrayList<Producto>();

        Session session = em.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                StringBuffer query = new StringBuffer();
                query.append("select prod.N_ID, prod.C_CODIGO, prod.C_NOMBRE, prod.C_CODIGOFABRICANTE, ");
                query.append("sum(mov_inventario.N_CANTIDAD * mov_inventario.N_AFECTACION) as EXISTENCIA, sum(prod_existencia.N_CANTIDAD) as PROD_EXISTENCIA, ");
                query.append("unidadmed.C_NOMBRE, prod.N_COSTOFOB, prod.N_COSTOPROM, prod.N_COSTOUND, prod.N_PRECIOESTANDAR ");
                query.append("FROM INV_PRODUCTO as prod left join INV_MOV_INVENTARIO as mov_inventario ");
                query.append("on prod.N_ID = mov_inventario.N_ID_PRODUCTO ");
                query.append("left join INV_ESTADO_MOVIMIENTO as estado ");
                query.append("on mov_inventario.N_ID_ESTADO = estado.N_ID ");
                query.append("left join INV_ALMACEN as almacen_mov on mov_inventario.N_ID_ALMACEN = almacen_mov.N_ID ");
                query.append("left join INV_PRODUCTOEXISTENCIA as prod_existencia ");
                query.append("on prod.N_ID = prod_existencia.N_ID_PRODUCTO ");
                query.append("left join INV_ALMACEN as almacen_prod on prod_existencia.N_ID_ALMACEN = almacen_prod.N_ID ");
                query.append("inner join INV_UNIDADMEDIDA as unidadmed on prod.N_ID_UNIDADMEDIDA = unidadmed.N_ID ");
                query.append("inner join INV_LINEA as linea on prod.N_ID_LINEA = linea.N_ID ");
                query.append("inner join CMP_PROVEEDOR proveedor ON prod.N_ID_PROVEEDOR = proveedor.N_ID ");

                if (p_codigo_desde.trim().length() > 0) {
                    query.append("where prod.C_CODIGO >= ? and prod.N_ID_ESTADOPRODUCTO <> 6 ");
                } else {
                    query.append("where prod.C_CODIGO <> ? and prod.N_ID_ESTADOPRODUCTO <> 6 ");
                }

                if (p_codigo_hasta.trim().length() > 0) {
                    query.append("and prod.C_CODIGO <= ? ");
                } else {
                    query.append("and prod.C_CODIGO <> ? ");
                }

                if (p_id_linea > 0) {
                    query.append("and linea.N_ID = ? ");
                } else {
                    query.append("and linea.N_ID <> ? ");
                }

                if (p_codigo_proveedor > 0) {
                    query.append("and proveedor.N_CODIGO = ? ");
                } else {
                    query.append("and proveedor.N_CODIGO <> ? ");
                }

                if (p_id_almacen > 0) {
                    query.append("and (almacen_mov.N_ID = ? OR almacen_mov.N_ID= ?) ");
                }

                query.append("group by prod.N_ID, prod.C_CODIGO, prod.C_NOMBRE, prod.C_CODIGOFABRICANTE ");
                query.append("order by prod.C_CODIGO ");

                PreparedStatement pstmt = connection.prepareStatement(query.toString());
                pstmt.setString(1, p_codigo_desde);
                pstmt.setString(2, p_codigo_hasta);
                pstmt.setInt(3, p_id_linea);
                pstmt.setLong(4, p_codigo_proveedor);

                if (p_id_almacen > 0) {
                    pstmt.setInt(5, p_id_almacen);
                    pstmt.setInt(6, p_id_almacen);
                }

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getInt("N_ID"));
                    producto.setCodigo(rs.getString("C_CODIGO"));
                    producto.setNombre(rs.getString("C_NOMBRE"));
                    producto.setCodigoFabricante(rs.getString("C_CODIGOFABRICANTE"));
                    producto.setCostoFOB(rs.getBigDecimal("N_COSTOFOB"));
                    producto.setCostoPROM(rs.getBigDecimal("N_COSTOPROM"));
                    producto.setCostoUND(rs.getBigDecimal("N_COSTOUND"));
                    producto.setPrecioESTANDAR(rs.getBigDecimal("N_PRECIOESTANDAR"));

                    UnidadMedida unidadMedida = new UnidadMedida();
                    unidadMedida.setNombre(rs.getString("C_NOMBRE"));
                    producto.setUnidadMedida(unidadMedida);

                    //Calcular existencia
                    long existencia = rs.getLong("EXISTENCIA");

                    if (rs.getLong("PROD_EXISTENCIA") > 0) {
                        existencia += rs.getLong("PROD_EXISTENCIA");
                    }

                    ProductoExistencia prodExistencia = new ProductoExistencia();
                    prodExistencia.setExistencia(existencia);

                    Set<ProductoExistencia> existencias = new HashSet<ProductoExistencia>();
                    existencias.add(prodExistencia);

                    producto.setExistencias(existencias);

                    //Agregando producto al listado
                    productos.add(producto);
                }

            }
        });

        return productos;

    }

    @Override
    public Producto findByCodigo(String codigo) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //Construct query
        String query = "select p from Producto p where p.codigo = ?1 and p.estado.id <> 6";

        //Creando listado de parametros
        List parametros = new ArrayList();
        parametros.add(codigo);

        List<Producto> productos = (List<Producto>) findByCriteria(query, parametros);

        if (productos.size() <= 0)
            throw new PersistenceClassNotFoundException(codigo);

        if (productos.size() > 1)
            throw new PersistenceClassNotFoundException("productos encontrados: " + productos.size());

        return productos.get(0);
    }

    @Override
    public List<Producto> findByClasificador(Integer idClasificador) throws GenericPersistenceEAOException {

        String qry = "Select p From Producto p Where p.clasificador.id = ?1";

        logger.info("Query: " + qry);

        //Creando listado de parametros
        List parametros = new ArrayList();
        parametros.add(idClasificador);

        return (List<Producto>) findByCriteria(qry, parametros);
    }
}
