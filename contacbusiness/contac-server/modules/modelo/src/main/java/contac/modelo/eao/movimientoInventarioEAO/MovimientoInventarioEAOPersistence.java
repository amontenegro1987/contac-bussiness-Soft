package contac.modelo.eao.movimientoInventarioEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.MovimientoInventario;

import java.util.Date;
import java.util.List;

/**
 * Coctac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 12-20-11
 * Time: 11:40 AM
 */
public class MovimientoInventarioEAOPersistence extends GenericPersistenceEAO<MovimientoInventario, Integer> implements MovimientoInventarioEAO {

    @Override
    public List<MovimientoInventario> findByEstadoMovimiento(Integer idEstado, Integer idAlmacen, Date fechaHasta) throws GenericPersistenceEAOException {

        //Init Service
        initService();

        //Query JPA String
        String query = "Select m from MovimientoInventario m Where m.estado.id = :idEstado and m.almacen.id = :idAlmacen " +
                "and m.fechaAlta <= :fechaAlta";

        //Creating query JPA
        return em.createQuery(query).setParameter("idEstado", idEstado).setParameter("idAlmacen", idAlmacen).
                setParameter("fechaAlta", fechaHasta).getResultList();

    }

    @Override
    public List<MovimientoInventario> findByProducto(String codigoProducto, Integer idAlmacen, Integer idEstado, Date fechaHasta)
            throws GenericPersistenceEAOException {

        //Init service
        initService();

        //Query JPA String
        String query = "Select m from MovimientoInventario m Where m.producto.codigo = :codigoProducto and m.almacen.id = :" +
                "idAlmacen and m.estado.id = :idEstado and m.fechaAlta <= :fechaAlta";

        //Creating query JPA
        return em.createQuery(query).setParameter("codigoProducto", codigoProducto).setParameter("idAlmacen", idAlmacen).
                setParameter("idEstado", idEstado).setParameter("fechaAlta", fechaHasta).getResultList();
    }

    @Override
    public List<MovimientoInventario> findByProducto(String codigoProducto, Date fechaDesde, Date fechaHasta, int tipoAfectacion) throws GenericPersistenceEAOException {

        //Init service
        initService();

        //Query JPA String
        String query = "Select m from MovimientoInventario m Where m.fechaAlta >= :fechaDesde and m.fechaAlta <= :" +
                "fechaHasta and m.afectacion = :tipoAfectacion and m.producto.codigo = :codigoProducto";

        //Creating query JPA
        return em.createQuery(query).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).
                setParameter("tipoAfectacion", tipoAfectacion).setParameter("codigoProducto", codigoProducto).getResultList();
    }

    @Override
    public List<MovimientoInventario> findByProducto(String codigoProducto, Integer idAlmacen, Date fechaDesde, Date fechaHasta,
                                                     int tipoAfectacion) throws GenericPersistenceEAOException {

        //Init service
        initService();

        //Query JPA String
        String query = "Select m from MovimientoInventario m Where m.fechaAlta >= :fechaDesde and m.fechaAlta <= :" +
                "fechaHasta and m.almacen.id = :idAlmacen and m.afectacion = :tipoAfectacion and m.producto.codigo = :codigoProducto";

        //Creating query JPA
        return em.createQuery(query).setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).
                setParameter("idAlmacen", idAlmacen).setParameter("tipoAfectacion", (short)tipoAfectacion).
                setParameter("codigoProducto", codigoProducto).getResultList();
    }
}
