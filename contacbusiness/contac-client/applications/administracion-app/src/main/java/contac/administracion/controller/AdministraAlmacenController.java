package contac.administracion.controller;

import contac.commons.app.BaseController;
import contac.commons.form.label.JErrorLabel;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller para administrar registros de almacenes
 * User: emontenegro
 * Date: 7/13/11
 * Time: 11:54 PM
 */
public class AdministraAlmacenController extends BaseController {

    //Logger
    private static final Logger logger = Logger.getLogger(AdministraAlmacenController.class);

    //Message Resource Bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/administracion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private int codigo;
    private String descripcion;
    private String referencia;
    private TiposAlmacen tipoAlmacen;
    private String serie;
    private long consecutivo;
    private String noAutorizacionComercial;
    private int estatus;
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

    //Almacen registrado
    private Almacen almacen;

    //*************************************************************************************
    //INIT VALUES
    //*************************************************************************************
    public void init() {
        logger.debug("Iniciando ingreso");

        //<Codigo>
        setCodigo(0);
        //<Tipo almacen>
        setTipoAlmacen(null);
        //<Descripcion>
        setDescripcion("");
        //<Referencia>
        setReferencia("");
        //<Serie>
        setSerie("");
        //<No consecutivo>
        setConsecutivo(0);
        //<No autorizacion renta>
        setNoAutorizacionComercial("");
        //<Pais>
        setPais(null);
        //<Estado>
        setEstado("");
        //<Ciudad>
        setCiudad("");
        //<Codigo Postal>
        setCodigoPostal("");
        //<Direccion>
        setDireccion("");
        //<Email>
        setEmail("");
        //<Web>
        setWeb("");
        //<Telefono oficina>
        setTelefonoOficina("");
        //<Telefono movil>
        setTelefonoMovil("");
        //<Fax>
        setFax("");
    }

    public void initModificacion() {

        logger.debug("Iniciando modificacion de almacen.");

        //<Codigo>
        setCodigo(almacen.getCodigo());
        //<Tipo almacen>
        for (TiposAlmacen tipoAlmacen : TiposAlmacen.values()) {
            if (tipoAlmacen.getValue() == (int)almacen.getTipoAlmacen()){
                setTipoAlmacen(tipoAlmacen);
            }
        }
        //<Descripcion>
        setDescripcion(almacen.getDescripcion());
        //<Referencia>
        setReferencia(almacen.getReferencia());
        //<Serie>
        setSerie(Character.toString(almacen.getSerie()));
        //<No consecutivo>
        setConsecutivo(almacen.getConsecutivo());
        //<No autorizacion renta>
        setNoAutorizacionComercial(almacen.getNoAutorizacionComercial());
        //<Pais>
        setPais(almacen.getDireccion().getPais());
        //<Estado>
        setEstado(almacen.getDireccion().getEstado());
        //<Ciudad>
        setCiudad(almacen.getDireccion().getCiudad());
        //<Codigo postal>
        setCodigoPostal(almacen.getDireccion().getCodigoPostal());
        //<Direccion>
        setDireccion(almacen.getDireccion().getDireccion());
        //<Email>
        setEmail(almacen.getDireccion().getEmail());
        //<Web>
        setWeb(almacen.getDireccion().getWeb());
        //<Telefono oficina>
        setTelefonoOficina(almacen.getDireccion().getTelefono());
        //<Telefono movil>
        setTelefonoMovil(almacen.getDireccion().getCelular());
        //<Fax>
        setFax(almacen.getDireccion().getFax());

    }

    //*************************************************************************************
    //GETTERS AND SETTERS
    //*************************************************************************************

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public TiposAlmacen getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(TiposAlmacen tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getNoAutorizacionComercial() {
        return noAutorizacionComercial;
    }

    public void setNoAutorizacionComercial(String noAutorizacionComercial) {
        this.noAutorizacionComercial = noAutorizacionComercial;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
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

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    //*************************************************************************************
    //ACTIONS
    //*************************************************************************************

    /**
     * Registrar nuevo almacen
     * @throws Exception, Exception
     */
    public void registrarAlmacen() throws Exception {

        try {

            //Crear el almacen
            Almacen almacen = new Almacen();
            almacen.setCodigo(this.codigo);
            almacen.setSerie(this.serie.charAt(0));
            almacen.setTipoAlmacen((byte)this.tipoAlmacen.getValue());
            almacen.setTipoAlmacenDesc(this.tipoAlmacen.toString());
            almacen.setConsecutivo(this.consecutivo);
            almacen.setDescripcion(this.descripcion.toUpperCase());
            almacen.setReferencia(this.referencia.toUpperCase());
            almacen.setNoAutorizacionComercial(this.noAutorizacionComercial);
            almacen.setEstatus(EstadosActivacion.ACTIVO.getValue());
            almacen.setEstatusDesc(EstadosActivacion.ACTIVO.toString());

            Direccion direccion = new Direccion();
            direccion.setPais(this.pais);
            direccion.setEstado(this.estado.toUpperCase());
            direccion.setCiudad(this.ciudad.toUpperCase());
            direccion.setCodigoPostal(this.codigoPostal);
            direccion.setDireccion(this.direccion.toUpperCase());
            direccion.setEmail(this.email.toLowerCase());
            direccion.setWeb(this.web.toLowerCase());
            direccion.setTelefono(this.telefonoOficina);
            direccion.setCelular(this.telefonoMovil);
            direccion.setFax(this.fax);
            almacen.setDireccion(direccion);

            this.almacen = almacen;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar Almacen
     * @throws Exception, Exception
     */
    public void modificarAlmacen() throws Exception {

        try {

            almacen.setCodigo(this.codigo);
            almacen.setTipoAlmacen((byte)this.tipoAlmacen.getValue());
            almacen.setDescripcion(this.descripcion);
            almacen.setReferencia(this.referencia);
            almacen.setSerie(this.serie.charAt(0));
            almacen.setConsecutivo(this.consecutivo);
            almacen.setNoAutorizacionComercial(this.noAutorizacionComercial);
            almacen.getDireccion().setPais(this.pais);
            almacen.getDireccion().setEstado(this.estado);
            almacen.getDireccion().setCiudad(this.ciudad);
            almacen.getDireccion().setCodigoPostal(this.codigoPostal);
            almacen.getDireccion().setDireccion(this.direccion);
            almacen.getDireccion().setEmail(this.email);
            almacen.getDireccion().setWeb(this.web);
            almacen.getDireccion().setTelefono(this.telefonoOficina);
            almacen.getDireccion().setCelular(this.telefonoMovil);
            almacen.getDireccion().setFax(this.fax);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }

    //*************************************************************************************
    //GETTERS AND SETTERS
    //*************************************************************************************
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

    //*************************************************************************************
    //VALIDATIONS
    //*************************************************************************************


}
