package contac.modelo.eao.productoEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Producto;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;
import org.apache.log4j.Logger;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Producto DAO Layer implementation JAVA SE
 *
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
                "codigo", "%".concat(codigo).concat("%")));
        //2. Agregando parametro de nonbre
        querySolver.add(new QueryFragment(((nombre != null) && (!nombre.equals(""))), "", " p.nombre like :nombre ",
                "nombre", "%".concat(nombre).concat("%")));
        //3. Agregando parametro de codigo de fabricante
        querySolver.add(new QueryFragment(((codigoFabricante != null) && (!codigoFabricante.equals(""))), "",
                " p.codigoFabricante like :codigoFabricante ", "codigoFabricante", "%".concat(codigoFabricante).concat("%")));

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        Query query = em.createQuery(ejbQuery);

        List<Producto> listProductos = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listProductos;
    }

    @Override
    public Producto findByCodigo(String codigo) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //Construct query
        String query = "select p from Producto p where p.codigo = ?1";

        //Creando listado de parametros
        List parametros = new ArrayList();
        parametros.add(codigo);

        List<Producto> productos = (List<Producto>)findByCriteria(query, parametros);

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
