/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloFacturaEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloFactura;

/**
 * Contac Business Software. All rights reserved 2012
 * User: emortiz
 * Date: 02-16-12
 * Time: 11:06 AM
 */
public class ArticuloFacturaEAOPersistence extends GenericPersistenceEAO<ArticuloFactura, Integer> implements ArticuloFacturaEAO {

    @Override
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException {

        //Init service
        initService();

        em.createQuery("update ArticuloFactura a set a.codigo = :codigoNuevo where a.producto.id = :idProducto").
                setParameter("codigoNuevo", codigoNuevo).setParameter("idProducto", idProducto).executeUpdate();
    }
}
