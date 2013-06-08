package contac.modelo.eao.almacenEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.Almacen;

import java.util.List;

/**
 * User: Eddy Montenegro
 * Date: 7/25/11
 * Time: 2:45 PM
 */
public interface AlmacenEAO extends GenericEAO<Almacen, Integer> {

    /**
     * Busca almacenes por compania
     * @param idCompania, Integer
     * @return  List
     * @throws GenericPersistenceEAOException, Exception
     */
    public List<Almacen> findAlmacenesPorCompania(Integer idCompania) throws GenericPersistenceEAOException;

    /**
     * Obtener numero consecutivo de facturacion para almacen
     * @param idAlmacen, Integer
     * @return long
     * @throws GenericPersistenceEAOException, Exception
     */
    public long obtenerNoConsecutivoFacturacion(Integer idAlmacen) throws GenericPersistenceEAOException;
}
