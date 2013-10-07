/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.administracion.controller;

import contac.internationalization.LanguageLocale;
import contac.modelo.entity.Banco;
import contac.modelo.entity.Moneda;
import contac.modelo.entity.TasaCambio;
import contac.modelo.entity.TiposTasaCambio;
import contac.servicio.administracion.ManagerAdministracionServiceBusiness;
import contac.servicio.administracion.ManagerAdministracionServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Contac Business Software. All rights reserved 2012
 * User: emortiz
 * Date: 02-14-12
 * Time: 02:56 PM
 */
public class TasasCambioController extends AdministracionBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(MonedaController.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //*********************************************************************
    //PROPERTIES BEAN FORM
    //*********************************************************************

    private TiposTasaCambio tipoTasaCambio;
    private BigDecimal tasaConversion;
    private Date fechaConversion;
    private Moneda monedaReferencia;
    private Moneda monedaConversion;
    private Banco banco;
    private boolean activaFacturacion;

    private TasaCambio tasaCambio;
    private List<TasaCambio> tasasCambio;

    //*********************************************************************
    //GETTERS AND SETTERS
    //*********************************************************************

    public TiposTasaCambio getTipoTasaCambio() {
        return tipoTasaCambio;
    }

    public void setTipoTasaCambio(TiposTasaCambio tipoTasaCambio) {
        this.tipoTasaCambio = tipoTasaCambio;
    }

    public BigDecimal getTasaConversion() {
        return tasaConversion;
    }

    public void setTasaConversion(BigDecimal tasaConversion) {
        this.tasaConversion = tasaConversion;
    }

    public Date getFechaConversion() {
        return fechaConversion;
    }

    public void setFechaConversion(Date fechaConversion) {
        this.fechaConversion = fechaConversion;
    }

    public Moneda getMonedaReferencia() {
        return monedaReferencia;
    }

    public void setMonedaReferencia(Moneda monedaReferencia) {
        this.monedaReferencia = monedaReferencia;
    }

    public Moneda getMonedaConversion() {
        return monedaConversion;
    }

    public void setMonedaConversion(Moneda monedaConversion) {
        this.monedaConversion = monedaConversion;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public TasaCambio getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(TasaCambio tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    public List<TasaCambio> getTasasCambio() {
        return tasasCambio;
    }

    public void setTasasCambio(List<TasaCambio> tasasCambio) {
        this.tasasCambio = tasasCambio;
    }

    public boolean isActivaFacturacion() {
        return activaFacturacion;
    }

    public void setActivaFacturacion(boolean activaFacturacion) {
        this.activaFacturacion = activaFacturacion;
    }

    //Init values
    public void init() {

        logger.debug("Iniciar carga datos controller");

        //Editar tasa de cambio
        set_edit(false);

        setTipoTasaCambio(null);
        setTasaConversion(new BigDecimal(0.0));
        setFechaConversion(new Date());
        setBanco(null);
        setMonedaReferencia(null);
        setMonedaConversion(null);
        setActivaFacturacion(false);

        setTasasCambio(new ArrayList<TasaCambio>());
        setTasaCambio(null);
    }

    //Init modificacion
    public void initModificacion() {

        logger.debug("Iniciando carga de datos para modificacion");

        //Editar tasa de cambio
        set_edit(true);

        //<Tipo almacen>
        for (TiposTasaCambio tipoTasaCambio : TiposTasaCambio.values()) {
            if (tipoTasaCambio.getValue() == (int)tasaCambio.getTipoTasaCambio()){
                setTipoTasaCambio(tipoTasaCambio);
            }
        }
        setTasaConversion(tasaCambio.getTasaConversion());
        setFechaConversion(tasaCambio.getFechaConversion());
        setBanco(tasaCambio.getBanco());
        setMonedaReferencia(tasaCambio.getMonedaReferencia());
        setMonedaConversion(tasaCambio.getMonedaConversion());
        setActivaFacturacion(tasaCambio.isActivaFacturacion());
    }

    //Init registros tasas cambio
    public void initRegistrosTasasCambio() {
        setTasasCambio(new ArrayList<TasaCambio>());
    }

    //*****************************************************
    //Actions Events
    //*****************************************************

    /**
     * Crear nueva tasa de cambio
     * @throws Exception, Exception
     */
    public void registrarTasaCambio() throws Exception {

        logger.debug("Creando registro de tasa de cambio");

        try {

            //Obtener manager de administracion
            ManagerAdministracionServiceBusiness mgrAdministracion = getMgrAdministracionService();

            //Persistir registros
            mgrAdministracion.registrarTasaCambio(getTipoTasaCambio().getValue(), getTasaConversion(), getFechaConversion(),
                    getMonedaReferencia().getId(), getMonedaConversion().getId(), getBanco().getId(), isActivaFacturacion());

            //Init registros
            init();

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar tasa de cambio
     * @throws Exception, Exception
     */
    public void modificarTasaCambio() throws Exception {

        logger.debug("Modificando registro de tasa de cambio");

        try {

            //Obtener manager de administracion
            ManagerAdministracionServiceBusiness mgrAdministracion = getMgrAdministracionService();

            //persistir registros
            mgrAdministracion.modificarTasaCambio(getTasaCambio().getId(), getTipoTasaCambio().getValue(), getTasaConversion(),
                    getFechaConversion(), getMonedaReferencia().getId(), getMonedaConversion().getId(), getBanco().getId(),
                    isActivaFacturacion());

            //Init registros
            init();

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Remover tasa de cambio
     * @throws Exception, Exception
     */
    public void removerTasaCambio() throws Exception {

        logger.debug("Remover registro de tasa de cambio");

        try {

            //Obtener manager de administracion
            ManagerAdministracionServiceBusiness mgrAdministracion = getMgrAdministracionService();

            //remover registro
            mgrAdministracion.removerTasaCambio(getTasaCambio().getId());

            //Init registros
            init();

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Actualizar registro de tasas de cambio
     * @throws Exception, Exception
     */
    public void actualizarRegistroTasasCambio() throws Exception {
        getTasasCambio().clear();
        getTasasCambio().addAll(buscarTasasCambio());
    }

    /**
     * Buscar registros de tasas de cambio
     * @return List
     * @throws Exception, Exception
     */
    private List<TasaCambio> buscarTasasCambio() throws Exception {

        logger.debug("Buscar listado de tasas de cambio");

        try {

            //Retornar listado de tasas de cambio
            return getMgrAdministracionService().buscarTasasCambio();

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

}
