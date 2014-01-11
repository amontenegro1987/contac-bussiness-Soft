package contac.modelo.eao.almacenEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.Almacen;

import java.util.List;

/**
 * User: Eddy Montenegro
 * Date: 7/25/11
 * Time: 2:46 PM
 */
public class AlmacenEAOPersistence extends GenericPersistenceEAO<Almacen, Integer> implements AlmacenEAO{

    @Override
    public List<Almacen> findAlmacenesPorCompania(Integer idCompania) throws GenericPersistenceEAOException {

        //Init service
        initService();

        List<Almacen> almacenes = em.createQuery("Select a from Almacen a where a.compania.id = :idCompania and a.estatus = 1").
                setParameter("idCompania", idCompania).getResultList();

        return almacenes;
    }

    @Override
    public long obtenerNoConsecutivoFacturacion(Integer idAlmacen) throws GenericPersistenceEAOException {

        //Init service
        initService();

        return (Long)em.createQuery("Select a.consecutivo from Almacen a where a.id = :idAlmacen").
                setParameter("idAlmacen", idAlmacen).getSingleResult();
    }
}
