package contac.modelo.eao.monedaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.Moneda;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hmurbina
 * Date: Oct 12, 2010
 * Time: 10:25:08 PM
 */
public interface MonedaEAO extends GenericEAO<Moneda, Integer> {

    /**
     *
     * @param nombre
     * @return List<Moneda>
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException
     */
    public List<Moneda> findByNombre(String nombre) throws GenericPersistenceEAOException;
}
