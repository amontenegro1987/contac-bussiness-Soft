package contac.modelo.eao.estadoMovimientoEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.EstadoMovimiento;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 11-09-11
 * Time: 10:29 PM
 */
public interface EstadoMovimientoEAO extends GenericEAO<EstadoMovimiento, Integer> {

    /**
     * Busca estado movimiento por alias
     * @param alias, Alias del estado
     * @return EstadoMovimiento
     * @throws PersistenceClassNotFoundException, Exception
     * @throws GenericPersistenceEAOException, Exception
     */
    public EstadoMovimiento findByAlias(String alias) throws PersistenceClassNotFoundException, GenericPersistenceEAOException;
}
