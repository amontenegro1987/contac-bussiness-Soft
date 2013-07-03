package contac.modelo.eao.clasificadorEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Clasificador;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 09-09-2010
 * Time: 10:46:55 PM
 */
public interface ClasificadorEAO extends GenericEAO<Clasificador, Integer> {

    /**
     * Buscar clasificador por codigo cbs
     * @param cbs, codigo cbs
     * @return Clasificador
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException, Exception
     */
    public Clasificador buscarPorCodigoCbs(long cbs) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;

    /**
     * Buscar Clasificadores por codigo cbs y descripcion
     * @param cbs, Codigo cbs
     * @param descripcion, Descripcion de clasificador
     * @return List<Clasificador>
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<Clasificador> buscarPorCodigoCbsDescripcion(long cbs, String descripcion) throws GenericPersistenceEAOException;

    /**
     * Buscar Clasificador por codigo de clasificacion
     * @param clasificacion, codigo de clasificacion
     * @return List<Clasificador>
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException, Exception
     */
    public List<Clasificador> buscarPorClasificacion(long clasificacion) throws GenericPersistenceEAOException;

    /**
     * Buscar clasificadores por tipo de clasificador
     * @param tipoClasificador, tipo de clasificador 
     * @return List<Clasificador>
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<Clasificador> buscarPorTipoClasificador(int tipoClasificador) throws GenericPersistenceEAOException;
}
