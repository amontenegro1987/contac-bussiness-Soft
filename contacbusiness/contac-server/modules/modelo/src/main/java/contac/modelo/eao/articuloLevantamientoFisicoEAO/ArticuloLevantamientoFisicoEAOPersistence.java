/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.articuloLevantamientoFisicoEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.ArticuloLevantamientoFisico;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: emortiz
 * Date: 01-06-12
 * Time: 03:49 PM
 */
public class ArticuloLevantamientoFisicoEAOPersistence extends GenericPersistenceEAO<ArticuloLevantamientoFisico, Integer>
        implements ArticuloLevantamientoFisicoEAO {


    @Override
    public void recodificarProducto(Integer idProducto, String codigoNuevo) throws GenericPersistenceEAOException {

        //Init service
        initService();

        em.createQuery("update ArticuloLevantamientoFisico a set a.codigo = :codigoNuevo where a.producto.id = :idProducto").
                setParameter("codigoNuevo", codigoNuevo).setParameter("idProducto", idProducto).executeUpdate();
    }
}
