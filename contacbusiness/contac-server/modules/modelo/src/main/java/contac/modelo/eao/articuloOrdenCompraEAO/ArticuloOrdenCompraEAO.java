/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloOrdenCompraEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloOrdenCompra;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: amontenegro
 * Date: 15/02/2014
 * Time: 12:28 PM
 */
public interface ArticuloOrdenCompraEAO extends GenericEAO<ArticuloOrdenCompra, Integer> {

    /**
     * Recodificar codigo de Articulo Orden de Compra
     *
     * @param idProducto,  Identificador de producto
     * @param codigoNuevo, Codigo nuevo a recodificar
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException;
}