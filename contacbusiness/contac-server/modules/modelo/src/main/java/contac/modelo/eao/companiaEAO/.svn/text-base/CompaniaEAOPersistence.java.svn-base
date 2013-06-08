package contac.modelo.eao.companiaEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Compania;
import contac.modelo.entity.EstadosProducto;
import contac.modelo.entity.EstadosActivacion;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-28-2010
 * Time: 07:13:37 PM
 */
public class CompaniaEAOPersistence extends GenericPersistenceEAO<Compania, Integer> implements CompaniaEAO {

    @Override
    public Compania findByCodigoNIT(String nit) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        List<Compania> companias = em.createQuery("select c from Compania c where c.nit = :nit").setParameter("nit", nit).getResultList();

        //Evaluate type of result
        if ((companias == null) || (companias.size() < 1))
            throw new PersistenceClassNotFoundException("nit: " + nit);

        if (companias.size() > 1)
            throw new PersistenceClassNotFoundException("Se encontraron " + companias.size() + " con nit: " + nit);

        return companias.get(0);
    }

    @Override
    public List<Compania> findByActiva() throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        List<Compania> companias = em.createQuery("Select c From Compania c where c.estatus = :estadoActivo").
                setParameter("estadoActivo", EstadosActivacion.ACTIVO.getValue()).getResultList();

        if (companias.size() > 1)
            throw new PersistenceClassNotFoundException("Se encontraron " + companias.size() + " activas. Favor verificar que solo exista una compania activa.");

        if (companias.size() < 1)
            throw new PersistenceClassNotFoundException("No se encontro compania activa.");

        return companias;

    }

    @Override
    public void inactivaCompania() throws GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        em.createQuery("Update Compania c set c.estatus = :estadoInactivo WHERE c.estatus = :estadoActivo").
                setParameter("estadoInactivo", EstadosActivacion.INACTIVO.getValue()).
                setParameter("estadoActivo", EstadosActivacion.ACTIVO.getValue()).executeUpdate();

    }
}
