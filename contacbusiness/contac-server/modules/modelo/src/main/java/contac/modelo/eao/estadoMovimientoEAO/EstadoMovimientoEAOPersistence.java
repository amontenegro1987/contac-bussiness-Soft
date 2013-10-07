package contac.modelo.eao.estadoMovimientoEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.EstadoMovimiento;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 11-09-11
 * Time: 10:29 PM
 */
public class EstadoMovimientoEAOPersistence extends GenericPersistenceEAO<EstadoMovimiento, Integer> implements EstadoMovimientoEAO {

    @Override
    public EstadoMovimiento findByAlias(String alias) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        EstadoMovimiento estado = (EstadoMovimiento)em.createQuery("Select e from EstadoMovimiento e where e.alias = :alias").
                setParameter("alias", alias).getSingleResult();

        if (estado == null)
            throw new PersistenceClassNotFoundException("alias: " + alias);

        return estado;

    }
}
