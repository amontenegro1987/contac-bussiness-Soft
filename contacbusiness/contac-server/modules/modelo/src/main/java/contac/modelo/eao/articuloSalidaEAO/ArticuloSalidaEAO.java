/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloSalidaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloSalida;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 01-04-12
 * Time: 11:36 PM
 */
public interface ArticuloSalidaEAO extends GenericEAO<ArticuloSalida, Integer> {

    /**
     * Recodificar Codigo Producto en Detalles Salida
     *
     * @param idProducto,  Identificador del Producto
     * @param codigoNuevo, Codigo Nuevo
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException;
}
