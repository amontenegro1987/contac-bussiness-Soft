/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.administracion.cliente.controller;

import contac.commons.app.BaseController;
import contac.modelo.entity.ActividadEconomica;
import contac.modelo.entity.Pais;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.List;


/**
 * Contac Business Software. All rights reserved 2012.
 * User: EMontenegro
 * Date: 02-05-12
 * Time: 10:08 PM
 */
public class ClienteBaseController extends BaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(ClienteBaseController.class);

    /**
     * Buscar listado de paises
     * @return List
     */
    public List<Pais> getPaises() {
        try {
            return getAplicacionBaseRemote().getPaises();
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Buscar listado de actividades economicas
     * @return List
     */
    public List<ActividadEconomica> getActividadesEconomicas() {
        try {
            return getAplicacionBaseRemote().getActividadesEconomicas();
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }
}
