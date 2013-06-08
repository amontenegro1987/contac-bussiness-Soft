package contac.administracion.controller;

import contac.commons.app.BaseController;
import contac.commons.form.label.JErrorLabel;
import contac.commons.form.label.JOptionErrorPane;
import contac.commons.form.navigation.Navigation;
import contac.modelo.entity.*;
import contac.servicio.administracion.ManagerAdministracionServiceBusiness;
import contac.servicio.administracion.ManagerAdministracionServiceBusinessException;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.renderer.ListCellContext;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 01-24-2011
 * Time: 10:17:22 PM
 */
public class AdministraCompaniaController extends BaseController {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(AdministraCompaniaController.class);

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private String nombreComercial;
    private String razonSocial;
    private String nit;
    private Date fechaConstitucion;
    private Date fechaAltaContribuyente;
    private String eslogan;
    private Pais pais;
    private String estado;
    private String ciudad;
    private String codigoPostal;
    private String direccion;
    private String email;
    private String web;
    private String telefonoOficina;
    private String telefonoMovil;
    private String fax;
    //private Direccion direccion;
    private Moneda moneda;
    private TiposPersona tipoPersona;
    private byte[] logotipo;

    //*************************************************************************************
    //PROPERTIES PARA EJECUCION
    //*************************************************************************************
    private List<Clasificador> clasificadores;
    private List<Almacen> almacenes;

    //<Entidad compania para editar>
    private Compania compania;
    private Almacen almacen;

    //*************************************************************************************
    //CONSTRUCTOR
    //*************************************************************************************
    public AdministraCompaniaController() {
        this.clasificadores = new ArrayList<Clasificador>();
        this.almacenes = new ArrayList<Almacen>();
    }

    //*************************************************************************************
    //TRANSACTIONAL METHODS
    //*************************************************************************************

    /**
     * Init values for new insert
     */
    public void init() {
        //<Tipos persona>
        setTipoPersona(TiposPersona.PERSONA_NATURAL);
        setNombreComercial("");
        setRazonSocial("");
        setNit("");
        setMoneda(null);
        setFechaConstitucion(null);
        setFechaAltaContribuyente(null);
        setEslogan("");
        setPais(null);
        setEstado("");
        setCiudad("");
        setCodigoPostal("");
        setDireccion("");
        setEmail("");
        setWeb("");
        setTelefonoOficina("");
        setTelefonoMovil("");
        setFax("");
        setLogotipo(null);
        setClasificadores(new ArrayList<Clasificador>());
        setAlmacenes(new ArrayList<Almacen>());
    }

    /**
     * Init values
     */
    public void initModificacion() {

        //<Tipos persona>
        for (TiposPersona tipoPersona : TiposPersona.values()) {
            if(tipoPersona.getValue() == (int)this.compania.getTipoPersona())
                setTipoPersona(tipoPersona);
        }

        //<Nombre comercial>
        setNombreComercial(this.compania.getNombreComercial());
        //<Razon social>
        setRazonSocial(this.compania.getRazonSocial());
        //<NIT>
        setNit(this.compania.getNit());
        //<Moneda referencia>
        setMoneda(this.compania.getMonedaReferencia());
        //<Fecha constitucion>
        setFechaConstitucion(this.compania.getFechaConstitucion());
        //<Fecha alta contribuyente>
        setFechaAltaContribuyente(this.compania.getFechaAltaContribuyente());
        //<Eslogan>
        setEslogan(this.compania.getEslogan());
        //<Pais>
        setPais(this.compania.getDireccion().getPais());
        //<Estado>
        setEstado(this.compania.getDireccion().getEstado());
        //<Ciudad>
        setCiudad(this.compania.getDireccion().getCiudad());
        //<Codigo postal>
        setCodigoPostal(this.compania.getDireccion().getCodigoPostal());
        //<Direccion>
        setDireccion(this.compania.getDireccion().getDireccion());
        //<Email>
        setEmail(this.compania.getDireccion().getEmail());
        //<Web>
        setWeb(this.compania.getDireccion().getWeb());
        //<Telefono oficina>
        setTelefonoOficina(this.compania.getDireccion().getTelefono());
        //<Telefono movil>
        setTelefonoMovil(this.compania.getDireccion().getCelular());
        //<Fax>
        setFax(this.compania.getDireccion().getFax());
        //<Logotipo>
        setLogotipo(this.compania.getLogotipo().getImage());
        //<Clasificadores>
        List<Clasificador> clasificadoresList = new ArrayList<Clasificador>();
        clasificadoresList.addAll(this.compania.getClasificaciones());
        setClasificadores(clasificadoresList);
        //<Almacenes>
        List<Almacen> almacenesList = new ArrayList<Almacen>();
        almacenesList.addAll(this.compania.getAlmacenes());
        setAlmacenes(almacenesList);
    }

    /**
     * Registrar nueva compania
     * @throws Exception, Exception
     */
    public void registrarCompania() throws Exception {

        logger.debug("Registrando compania.");

        try {

            //Convertir clasificadores y almacenes
            Set<Clasificador> clasificadorSet = new HashSet<Clasificador>();
            clasificadorSet.addAll(clasificadores);

            Set<Almacen> almacenesSet = new HashSet<Almacen>();
            almacenesSet.addAll(almacenes);

            //Construir direccion
            Direccion direccion = new Direccion();
            direccion.setPais(this.pais);
            direccion.setEstado(this.estado.toUpperCase());
            direccion.setCiudad(this.ciudad.toUpperCase());
            direccion.setDireccion(this.direccion.toUpperCase());
            direccion.setCodigoPostal(this.codigoPostal);
            direccion.setTelefono(this.telefonoOficina);
            direccion.setCelular(this.telefonoMovil);
            direccion.setFax(this.fax);
            direccion.setEmail(this.email.toLowerCase());
            direccion.setWeb(this.web.toLowerCase());

            //Obtener manager de administracion
            ManagerAdministracionServiceBusiness mgr = getMgrAdministracionService();

            //Registrar compania
            mgr.registrarCompania(this.nit,
                    this.razonSocial.toUpperCase(),
                    this.nombreComercial.toUpperCase(),
                    this.fechaConstitucion,
                    this.fechaAltaContribuyente,
                    this.eslogan.toUpperCase(),
                    direccion,
                    clasificadorSet,
                    this.moneda,
                    almacenesSet,
                    (byte)this.tipoPersona.getValue(),
                    this.tipoPersona.toString(),
                    this.logotipo);

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar compania existente
     * @throws Exception, Exception
     */
    public void modificarCompania() throws Exception {

        logger.debug("Modificando compania");

        try {

             //Convertir clasificadores y almacenes
            Set<Clasificador> clasificadorSet = new HashSet<Clasificador>();
            clasificadorSet.addAll(clasificadores);

            Set<Almacen> almacenesSet = new HashSet<Almacen>();
            almacenesSet.addAll(almacenes);

            //Construir direccion
            Direccion direccion = new Direccion();
            direccion.setPais(this.pais);
            direccion.setEstado(this.estado.toUpperCase());
            direccion.setCiudad(this.ciudad.toUpperCase());
            direccion.setDireccion(this.direccion.toUpperCase());
            direccion.setCodigoPostal(this.codigoPostal);
            direccion.setTelefono(this.telefonoOficina);
            direccion.setCelular(this.telefonoMovil);
            direccion.setFax(this.fax);
            direccion.setEmail(this.email.toLowerCase());
            direccion.setWeb(this.web.toLowerCase());

            //Obtener manager de administracion
            ManagerAdministracionServiceBusiness mgr = getMgrAdministracionService();

            //Modificar datos compania
            mgr.modificarCompania(compania.getId(),
                    this.nit,
                    this.razonSocial.toUpperCase(),
                    this.nombreComercial.toUpperCase(),
                    this.fechaConstitucion,
                    this.fechaAltaContribuyente,
                    this.eslogan.toUpperCase(),
                    direccion,
                    clasificadorSet,
                    this.moneda,
                    almacenesSet,
                    (byte)this.tipoPersona.getValue(),
                    this.tipoPersona.toString(),
                    this.logotipo);

        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Obtener monedas
     * @return List<Moneda>
     */
    public List<Moneda> getMonedas() {
        try {
            return getAplicacionBaseRemote().getMonedas();
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            JOptionErrorPane.showMessageError(null, e.getMessage(),
                    e.getMessage());
        }
        return null;
    }

    /**
     * Obtener paises
     * @return List<Pais>
     */
    public List<Pais> getPaises() {
        try {
            return getAplicacionBaseRemote().getPaises();
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, new JErrorLabel(e.getMessage()));
        }
        return null;
    }

    /**
     * Obtener companias
     * @return List<Compania>
     */
    public List<Compania> getCompanias() {
        try {
            return getMgrAdministracionService().buscarCompanias();
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, new JErrorLabel(e.getMessage()));
        } catch (ManagerAdministracionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, new JErrorLabel(e.getMessage()));
        }
        return null;
    }

    //*********************************************************************
    //GETTERS AND SETTERS
    //*********************************************************************
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Date getFechaConstitucion() {
        return fechaConstitucion;
    }

    public void setFechaConstitucion(Date fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
    }

    public Date getFechaAltaContribuyente() {
        return fechaAltaContribuyente;
    }

    public void setFechaAltaContribuyente(Date fechaAltaContribuyente) {
        this.fechaAltaContribuyente = fechaAltaContribuyente;
    }

    public String getEslogan() {
        return eslogan;
    }

    public void setEslogan(String eslogan) {
        this.eslogan = eslogan;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public TiposPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TiposPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public List<Clasificador> getClasificadores() {
        return clasificadores;
    }

    public void setClasificadores(List<Clasificador> clasificadores) {
        this.clasificadores = clasificadores;
    }

    public List<Almacen> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(List<Almacen> almacenes) {
        this.almacenes = almacenes;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTelefonoOficina() {
        return telefonoOficina;
    }

    public void setTelefonoOficina(String telefonoOficina) {
        this.telefonoOficina = telefonoOficina;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Compania getCompania() {
        return compania;
    }

    public void setCompania(Compania compania) {
        this.compania = compania;
    }
}
