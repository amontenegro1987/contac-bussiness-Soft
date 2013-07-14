/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.commons.app;

import contac.modelo.entity.TasaCambio;
import contac.servicio.facturacion.ManagerFacturacionServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * User: emortiz
 * Date: 10-03-12
 * Time: 11:03 AM
 */
public class BusquedaTasasCambioController extends BaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(BusquedaTasasCambioController.class);

    private List<TasaCambio> tasasCambio;

    public List<TasaCambio> getTasasCambio() {
        return tasasCambio;
    }

    public void setTasasCambio(List<TasaCambio> tasasCambio) {
        this.tasasCambio = tasasCambio;
    }

    /**
     * Default Constructor
     *
     * @throws Exception, Exception
     */
    public BusquedaTasasCambioController() throws Exception {
        buscarTasasCambio();
    }

    /**
     * Buscar tasas de cambio
     *
     * @throws Exception, Exception
     */
    public void buscarTasasCambio() throws Exception {

        logger.debug("Buscar tasas de cambio");

        try {

            if (this.tasasCambio == null)
                this.tasasCambio = new ArrayList<TasaCambio>();

            //Buscando tasas de cambio
            List<TasaCambio> tasasCambio = getMgrFacturacionService().buscarTasasCambioFacturacion();

            if (tasasCambio != null) {
                //Setting values
                getTasasCambio().clear();
                setTasasCambio(tasasCambio);
            }

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }
}
