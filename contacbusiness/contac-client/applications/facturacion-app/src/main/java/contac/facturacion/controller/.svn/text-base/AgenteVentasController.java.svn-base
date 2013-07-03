/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.facturacion.controller;

import contac.internationalization.LanguageLocale;
import contac.modelo.entity.AgenteVentas;
import contac.modelo.entity.Almacen;
import contac.servicio.facturacion.ManagerFacturacionServiceBusiness;
import contac.servicio.facturacion.ManagerFacturacionServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * User: emortiz
 * Date: 10-10-12
 * Time: 12:46 PM
 */
public class AgenteVentasController extends FacturacionBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(AgenteVentasController.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private long codigo;
    private String nombre;
    private Almacen almacen;
    private boolean activo;
    private AgenteVentas agenteVentas;
    private List<AgenteVentas> agenteVentasList;

    //*********************************************************************
    //GETTERS AND SETTERS
    //*********************************************************************

    public ResourceBundle getMessageBundle() {
        return messageBundle;
    }

    public void setMessageBundle(ResourceBundle messageBundle) {
        this.messageBundle = messageBundle;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public AgenteVentas getAgenteVentas() {
        return agenteVentas;
    }

    public void setAgenteVentas(AgenteVentas agenteVentas) {
        this.agenteVentas = agenteVentas;
    }

    public List<AgenteVentas> getAgenteVentasList() {
        return agenteVentasList;
    }

    public void setAgenteVentasList(List<AgenteVentas> agenteVentasList) {
        this.agenteVentasList = agenteVentasList;
    }

    //Init values
    public void init() {

        logger.debug("Iniciando carga de datos agentes de ventas");

        //Editar registro
        set_edit(false);

        setCodigo(VALUE_INT_NOT_DEFINED);
        setNombre(VALUE_STRING_NOT_DEFINED);
        setActivo(false);
        setAlmacen(null);
        setAgenteVentasList(new ArrayList<AgenteVentas>());

        try {

            //Setting almacenes registrados
            setAlmacenes(buscarAlmacenes());

            //Setting agentes de ventas
            List<AgenteVentas> agentesVentas = buscarAgentesVentas();
            if (agentesVentas != null)
                setAgenteVentasList(agentesVentas);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    //Init modificacion
    public void initModificacion() {

        logger.debug("Iniciando carga de datos para modificacion de agentes de ventas");

        //Editar registro
        set_edit(true);

        setCodigo(agenteVentas.getCodigo());
        setNombre(agenteVentas.getNombre());
        setActivo(agenteVentas.isActivo());
        setAlmacen(agenteVentas.getAlmacen());
    }

    /**
     * Registrar agente de ventas
     *
     * @throws Exception, Exception
     */
    public void registrarAgenteVentas() throws Exception {

        logger.debug("Registrando agente de ventas");

        try {

            //Getting manager facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Persistir agente de ventas
            AgenteVentas agenteVentas = mgrFacturacion.crearAgenteVentas(getCodigo(), getNombre(), getAlmacen().getId(),
                    isActivo());

            //Agregar agente de ventas al listado             ;
            getAgenteVentasList().add(agenteVentas);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar agente de ventas
     *
     * @throws Exception, Exception
     */
    public void modificarAgenteVentas() throws Exception {

        logger.debug("Modificando agente de ventas");

        try {

            //Getting manager facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Persistir agente de ventas
            AgenteVentas agenteVentas = mgrFacturacion.modificarAgenteVentas(getAgenteVentas().getId(), getNombre(),
                    getAlmacen().getId(), isActivo());

            //Agregar agente de ventas al listado
            getAgenteVentasList().clear();
            getAgenteVentasList().addAll(buscarAgentesVentas());

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
