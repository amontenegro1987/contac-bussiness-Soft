/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloTrasladoEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloTraslado;

/**
 * Contac Business Software. All rigths reserved 2011.
 * User: EMontenegro
 * Date: 01-03-12
 * Time: 11:27 PM
 */
public interface ArticuloTrasladoEAO extends GenericEAO<ArticuloTraslado, Integer> {

    /**
     * Recodificar Producto
     *
     * @param idProducto,  Identificador de Producto
     * @param codigoNuevo, Codigo Nuevo
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException;
}
