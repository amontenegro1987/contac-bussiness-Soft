/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.administracion.controller;

import contac.commons.app.BaseController;
import contac.modelo.entity.Banco;
import contac.modelo.entity.Moneda;
import contac.modelo.entity.Pais;
import contac.servicio.administracion.ManagerAdministracionServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: EMontenegro
 * Date: 02-13-12
 * Time: 10:45 PM
 */
public class AdministracionBaseController extends BaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(AdministracionBaseController.class);

    /**
     * Buscar listado de paises
     *
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
     * Retorna listado de bancos registrados
     * @return List
     */
    public List<Banco> getBancos() {
        try {
            return getMgrAdministracionService().buscarBancos();
        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Retorna listado de monedas
     * @return List
     */
    public List<Moneda> getMonedas() {
        try {
            return getMgrAdministracionService().buscarMonedas();
        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Retorna listado de monedas de referencia
     * @return List
     */
    public List<Moneda> getMonedasReferencia() {
        try {
            return getMgrAdministracionService().buscarMonedasReferencia();
        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }


}
