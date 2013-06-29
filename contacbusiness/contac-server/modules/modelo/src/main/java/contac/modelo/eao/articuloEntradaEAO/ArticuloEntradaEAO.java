/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloEntradaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloEntrada;

/**
 * Contac Business Software
 * User: EMontenegro
 * Date: 11-28-11
 * Time: 11:53 PM
 */
public interface ArticuloEntradaEAO extends GenericEAO<ArticuloEntrada, Integer> {

    /**
     * Recodificar Producto
     *
     * @param idProducto,  Codigo Identificador de Producto
     * @param codigoNuevo, Codigo Nuevo a recodificar
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException;
}
