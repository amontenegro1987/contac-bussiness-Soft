/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.commons.app;

import contac.modelo.entity.AgenteVentas;
import contac.servicio.facturacion.ManagerFacturacionServiceBusinessException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * User: emortiz
 * Date: 10-03-12
 * Time: 11:01 AM
 */
public class BusquedaAgentesVentasController extends BaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(BusquedaAgentesVentasController.class);

    private List<AgenteVentas> agentesVentas;

    public List<AgenteVentas> getAgentesVentas() {
        return agentesVentas;
    }

    public void setAgentesVentas(List<AgenteVentas> agentesVentas) {
        this.agentesVentas = agentesVentas;
    }

    /**
     * Default constructor
     */
    public BusquedaAgentesVentasController() throws Exception {
        buscarAgentesVentas();
    }

    /**
     * Buscar listado de agentes de ventas
     *
     * @throws Exception, Exception
     */
    private void buscarAgentesVentas() throws Exception {

        logger.debug("Buscando agentes de ventas");

        try {

            if (this.agentesVentas == null)
                this.agentesVentas = new ArrayList<AgenteVentas>();

            //Buscando agentes de ventas
            List<AgenteVentas> agentesVentas = getMgrFacturacionService().buscarAgentesVentasUsuario();

            if (agentesVentas != null) {
                //Setting values
                getAgentesVentas().clear();
                setAgentesVentas(agentesVentas);
            }

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

}
