/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloProformaEAO;

import contac.modelo.eao.genericEAO.GenericEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloProforma;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 02-16-12
 * Time: 11:23 AM
 */
public interface ArticuloProformaEAO extends GenericEAO<ArticuloProforma, Integer> {

    /**
     * Recodificar codigo de Articulo Proforma
     *
     * @param idProducto,  Identificador de producto
     * @param codigoNuevo, Codigo nuevo a recodificar
     * @throws GenericPersistenceEAOException,
     *          Exception
     */
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException;
}
