/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloTrasladoEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloTraslado;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 01-03-12
 * Time: 11:28 PM
 */
public class ArticuloTrasladoEAOPersistence extends GenericPersistenceEAO<ArticuloTraslado, Integer> implements ArticuloTrasladoEAO {

    @Override
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException {

        //Init service
        initService();

        em.createQuery("update ArticuloTraslado a set a.codigo = :codigoNuevo where a.producto.id = :idProducto").
                setParameter("codigoNuevo", codigoNuevo).setParameter("idProducto", idProducto).executeUpdate();
    }
}
