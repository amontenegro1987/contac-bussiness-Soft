package contac.modelo.eao.companiaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Compania;

import java.util.List;

/**
 * Interfaz de acceso a datos para manejar persistencia Entity
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-28-2010
 * Time: 07:13:26 PM
 */
public interface CompaniaEAO extends GenericEAO<Compania, Integer> {

    /**
     * Busca compania por codigo
     * @param nit, codigo nit de compania
     * @return Compania
     * @throws PersistenceClassNotFoundException, Exception
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException, Exception
     */
    public Compania findByCodigoNIT(String nit) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;

    /**
     * Busca una compania activa
     * @return Compania
     * @throws PersistenceClassNotFoundException, Exception
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException, Exception
     */
    public List<Compania> findByActiva() throws PersistenceClassNotFoundException, GenericPersistenceEAOException;

    /**
     * Inactiva todas las companias que se encuentran activas
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException, Exception
     */
    public void inactivaCompania() throws GenericPersistenceEAOException;
}
