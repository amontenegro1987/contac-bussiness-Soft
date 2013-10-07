/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.administracion.controller;

import contac.internationalization.LanguageLocale;
import contac.modelo.entity.Banco;
import contac.servicio.administracion.ManagerAdministracionServiceBusiness;
import contac.servicio.administracion.ManagerAdministracionServiceBusinessException;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Contac Business Software. All rights reserved 2012
 * User: emortiz
 * Date: 02-14-12
 * Time: 02:53 PM
 */
public class BancoController extends AdministracionBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(MonedaController.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //*********************************************
    //PROPERTIES BEAN FORM
    //*********************************************

    //Banco properties
    private String nombreComercial;
    private String razonSocial;

    private Banco banco;
    private List<Banco> bancos;

    //*********************************************
    //GETTERS AND SETTERS
    //*********************************************

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Banco> getBancos() {
        return bancos;
    }

    public void setBancos(List<Banco> bancos) {
        this.bancos = bancos;
    }

    /**
     * Init values registro de bancos
     */
    public void init() {

        logger.debug("Inicia carga de datos");

        //Edit datas
        set_edit(false);

        setNombreComercial(VALUE_STRING_NOT_DEFINED);
        setRazonSocial(VALUE_STRING_NOT_DEFINED);
        setBanco(null);
    }

    /**
     * Init values modificacion de bancos
     */
    public void initModificacion() {

        logger.debug("Inicia modificacion de datos");

        //Edit datas
        set_edit(true);

        setNombreComercial(this.banco.getNombreComercial());
        setRazonSocial(this.banco.getRazonSocial());
    }

    /**
     * Init registros de bancos
     */
    public void initRegistrosBancos() {
        setBancos(new ArrayList<Banco>());
    }

    //*****************************************************
    //Actions Events
    //*****************************************************

    /**
     * Crear banco
     * @throws Exception, Exception
     */
    public void registrarBanco() throws Exception {

        logger.debug("Creando registro de banco");

        try {

            //Obtener manager de administracion
            ManagerAdministracionServiceBusiness mgrAdministracion = getMgrAdministracionService();

            //Persistir registros
            mgrAdministracion.registrarBanco(getNombreComercial(), getRazonSocial());

            //Init registros
            init();

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar banco
     * @throws Exception, Exception
     */
    public void modicarBanco() throws Exception {

        logger.debug("Modificando registro de banco");

        try {

            //Obtener manager de administracion
            ManagerAdministracionServiceBusiness mgrAdministracion = getMgrAdministracionService();

            //Persistir registros
            mgrAdministracion.modificarBanco(this.banco.getId(), getNombreComercial(), getRazonSocial());

            //Init registros
            init();

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Remover registro de banco
     * @throws Exception, Exception
     */
    public void removerBanco() throws Exception {

        logger.debug("Removiendo registro de banco");

        try {

            //Obtener manager de administracion
            ManagerAdministracionServiceBusiness mgrAdministracion = getMgrAdministracionService();

            //Persistir registros
            mgrAdministracion.removerBanco(this.banco.getId());

            //Init registros
            init();

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Actualizar registro de bancos
     * @throws Exception, Exception
     */
    public void actualizarRegistrosBancos() throws Exception {
        getBancos().clear();
        getBancos().addAll(buscarBancos());
    }

    /**
     * Buscando listado de bancos
     * @return List
     * @throws Exception, Exception
     */
    private List<Banco> buscarBancos() throws Exception {

        logger.debug("Buscando listado de bancos");

        try {

            //Retornar listado de bancos
            return getMgrAdministracionService().buscarBancos();

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }
}
