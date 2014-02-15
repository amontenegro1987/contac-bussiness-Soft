/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloOrdenCompraEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloOrdenCompra;
import contac.modelo.entity.ArticuloProforma;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: amontenegro
 * Date: 15/02/2014
 * Time: 12:30 PM
 */
public class ArticuloOrdenCompraEAOPersistence extends GenericPersistenceEAO<ArticuloOrdenCompra, Integer> implements ArticuloOrdenCompraEAO {

    @Override
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException {

        //Init service
        initService();

        em.createQuery("update ArticuloOrdenCompra a set a.codigo = :codigoNuevo where a.producto.id = :idProducto").
                setParameter("codigoNuevo", codigoNuevo).setParameter("idProducto", idProducto).executeUpdate();
    }
}
