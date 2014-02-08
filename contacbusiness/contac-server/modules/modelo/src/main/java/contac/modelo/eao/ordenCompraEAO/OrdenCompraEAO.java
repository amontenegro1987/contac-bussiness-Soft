package contac.modelo.eao.ordenCompraEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.entity.OrdenCompra;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: Alejandro Montenegro
 * Date: 02-06-14
 * Time: 11:14 PM
 */
public interface OrdenCompraEAO extends GenericEAO<OrdenCompra, Integer> {

     /**
     * Buscar Orden de compra por Numero de Orden de Compra
     *
     * @param noOrdenCompra, Numero de Orden de Compra
     * @return OrdenCompra
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public OrdenCompra findByNumero(long noOrdenCompra) throws GenericPersistenceEAOException,
            PersistenceClassNotFoundException;
    /**
     * Buscar Ordenes de Compra por rangos de fecha
     *
     * @param fechaDesde, Fecha desde
     * @param fechaHasta, Fecha hasta
     * @return List<OrdenCompra>
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public List<OrdenCompra> findByFechas(Date fechaDesde, Date fechaHasta) throws GenericPersistenceEAOException;

}