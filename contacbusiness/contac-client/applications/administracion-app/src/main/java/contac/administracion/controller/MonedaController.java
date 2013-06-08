/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.administracion.controller;

import contac.internationalization.LanguageLocale;
import contac.modelo.entity.Moneda;
import contac.modelo.entity.Pais;
import contac.servicio.administracion.ManagerAdministracionServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: EMontenegro
 * Date: 02-12-12
 * Time: 09:58 PM
 */
public class MonedaController extends AdministracionBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(MonedaController.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //*********************************************
    //PROPERTIES BEAN FORM
    //*********************************************

    //Moneda properties
    private String nombre;
    private String nombreCorto;
    private String simbolo;
    private Pais pais;

    private Moneda moneda;
    private List<Moneda> monedas;

    //*********************************************
    //GETTERS AND SETTERS
    //*********************************************

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public List<Moneda> getMonedas() {
        return monedas;
    }

    public void setMonedas(List<Moneda> monedas) {
        this.monedas = monedas;
    }

    /**
     * Init values de registro de moneda
     */
    public void initValues() {

        logger.debug("Iniciando carga de datos controller");

        //Set Edit
        set_edit(false);

        //Setting properties values
        setNombre(VALUE_STRING_NOT_DEFINED);
        setNombreCorto(VALUE_STRING_NOT_DEFINED);
        setSimbolo(VALUE_STRING_NOT_DEFINED);
        setPais(null);
    }

    /**
     * Init values de modificacion de moneda
     */
    public void initModificacion() {

        logger.debug("Iniciando carga de datos modificacion controller");

        //Set Edit
        set_edit(true);

        //Setting properties values
        setNombre(this.moneda.getNombre());
        setNombreCorto(this.moneda.getNombreCorto());
        setSimbolo(this.moneda.getSimbolo());
        setPais(this.moneda.getPais());
    }

    /**
     * Init values de registro de moneda
     */
    public void initRegistrosMonedas() {
        setMonedas(new ArrayList<Moneda>());
    }

    //*****************************************************
    //Actions Events
    //*****************************************************

    /**
     * Crear nuevo registro de moneda
     * @throws Exception, Exception
     */
    public void crearMoneda() throws Exception {

    }

    /**
     * Modificar registro de moneda
     * @throws Exception, Exception
     */
    public void modificarMoneda() throws Exception {

    }

    /**
     * Actualizar registro de monedas
     * @throws Exception, Exception
     */
    public void actualizarRegistroMonedas() throws Exception {
        getMonedas().clear();
        getMonedas().addAll(buscarMonedas());
    }

    /**
     * Buscar listado de monedas registradas
     * @return List
     */
    private List<Moneda> buscarMonedas() throws Exception {

        logger.debug("Buscando listado de monedas");

        try {

            //Retornar listado de monedas
            return getMgrAdministracionService().buscarMonedas();

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
