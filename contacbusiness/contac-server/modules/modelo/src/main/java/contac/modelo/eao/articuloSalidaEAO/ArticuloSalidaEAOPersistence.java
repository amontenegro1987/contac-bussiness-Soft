/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloSalidaEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloSalida;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 01-04-12
 * Time: 11:36 PM
 */
public class ArticuloSalidaEAOPersistence extends GenericPersistenceEAO<ArticuloSalida, Integer> implements ArticuloSalidaEAO {

    @Override
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException {

        //Init service
        initService();

        em.createQuery("update ArticuloSalida a set a.codigo = :codigoNuevo where a.producto.id = :idProducto").
                setParameter("codigoNuevo", codigoNuevo).setParameter("idProducto", idProducto).executeUpdate();
    }
}
