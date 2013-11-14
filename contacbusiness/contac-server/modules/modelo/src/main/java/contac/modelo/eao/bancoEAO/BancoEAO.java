package contac.modelo.eao.bancoEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.Banco;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: hmurbina
 * Date: Oct 12, 2010
 * Time: 10:24:49 PM
 */
public interface BancoEAO extends GenericEAO<Banco, Integer> {

    /**
     * @param  nombreComercial
     * @return List<T>
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException
     */
    public List<Banco> findByNombreComercial(String nombreComercial) throws GenericPersistenceEAOException;

    /**
     *
     * @param razonSocial
     * @return List<T>
     * @throws contac.modelo.eao.genericEAO.GenericPersistenceEAOException
     */
    public List<Banco> findByRazonSocial(String razonSocial) throws GenericPersistenceEAOException;

}

