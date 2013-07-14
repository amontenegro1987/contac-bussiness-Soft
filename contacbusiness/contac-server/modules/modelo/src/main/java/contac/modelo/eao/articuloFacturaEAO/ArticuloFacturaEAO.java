/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloFacturaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloFactura;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 02-16-12
 * Time: 11:06 AM
 */
public interface ArticuloFacturaEAO extends GenericEAO<ArticuloFactura, Integer> {

    /**
     * Recodificar producto en articulos de factura
     *
     * @param idProducto,  identificador de Producto
     * @param codigoNuevo, Codigo nuevo a recodificar
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException;
}
