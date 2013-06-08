package contac.administracion.controller;

import contac.commons.app.BaseController;
import contac.modelo.entity.*;
import contac.servicio.proveedores.ManagerProveedoresServiceBusiness;
import contac.servicio.proveedores.ManagerProveedoresServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Contac Business Inc. 2011 all rights reserved.
 * User: emontenegro
 * Date: 8/11/11
 * Time: 10:43 PM
 */
public class AdministraProveedorController extends ProveedorBaseController {

    //Apache log4j logger
    private static final Logger logger = Logger.getLogger(AdministraProveedorController.class);

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private long codigo;
    private String nombreComercial;
    private String razonSocial;
    private String nit;
    private String noCuenta;
    private BigDecimal descuento;
    private int plazoCredito;
    private BigDecimal limiteCredito;
    private ActividadEconomica actividadEconomica;
    private TiposPersona tipoPersona;
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
    private Proveedor proveedor;

    //*********************************************************************
    //GETTERS AND SETTERS
    //*********************************************************************

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

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

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public int getPlazoCredito() {
        return plazoCredito;
    }

    public void setPlazoCredito(int plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public ActividadEconomica getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(ActividadEconomica actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public TiposPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TiposPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    //Init values
    public void init() {

        //Editar proveedor
        set_edit(false);

        setTipoPersona(TiposPersona.PERSONA_NATURAL);
        setCodigo(0);
        setNombreComercial("");
        setRazonSocial("");
        setNit("");
        setNoCuenta("");
        setDescuento(new BigDecimal(0.00));
        setPlazoCredito(0);
        setLimiteCredito(new BigDecimal(0.00));
        setActividadEconomica(null);
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
        setProveedor(null);
    }

    //Init values modificacion
    public void initModificacion() {

        //Editar proveedor
        set_edit(true);

        //<Tipos persona>
        for (TiposPersona tipoPersona : TiposPersona.values()) {
            if (tipoPersona.getValue() == (int) this.proveedor.getTipoPersona())
                setTipoPersona(tipoPersona);
        }
        setCodigo(this.proveedor.getCodigo());
        setNombreComercial(this.proveedor.getNombreComercial());
        setRazonSocial(this.proveedor.getRazonSocial());
        setNit(this.proveedor.getNit());
        setNoCuenta(this.proveedor.getCuenta());
        setDescuento(this.proveedor.getDescuento());
        setPlazoCredito(this.proveedor.getPlazoCredito());
        setLimiteCredito(this.proveedor.getLimiteCredito());
        setActividadEconomica(this.proveedor.getActividadEconomica());
        setPais(this.proveedor.getDireccion().getPais());
        setEstado(this.proveedor.getDireccion().getEstado());
        setCiudad(this.proveedor.getDireccion().getCiudad());
        setCodigoPostal(this.proveedor.getDireccion().getCodigoPostal());
        setDireccion(this.proveedor.getDireccion().getDireccion());
        setEmail(this.proveedor.getDireccion().getEmail());
        setWeb(this.proveedor.getDireccion().getWeb());
        setTelefonoOficina(this.proveedor.getDireccion().getTelefono());
        setTelefonoMovil(this.proveedor.getDireccion().getCelular());
        setFax(this.proveedor.getDireccion().getFax());
    }

    /**
     * Registrar proveedor
     *
     * @throws Exception, Exception
     */
    public void registrarProveedor() throws Exception {

        logger.debug("Registrando proveedor");

        try {

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

            //Obtener manager proveedores
            ManagerProveedoresServiceBusiness mgrProveedor = getMgrProveedoresService();

            //Registrar proveedor
            mgrProveedor.registrarProveedor(this.codigo,
                    this.razonSocial,
                    this.nombreComercial,
                    this.nit,
                    this.noCuenta,
                    this.descuento,
                    this.plazoCredito,
                    this.limiteCredito,
                    (byte) this.tipoPersona.getValue(),
                    this.actividadEconomica,
                    direccion);

            //Init form
            this.init();

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar Proveedor
     *
     * @throws Exception, Exception
     */
    public void ModificarProveedor() throws Exception {

        logger.debug("Modificando proveedor");

        try {

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

            //Obtener manager proveedores
            ManagerProveedoresServiceBusiness mgrProveedor = getMgrProveedoresService();

            //Modificar proveedor
            mgrProveedor.modificarProveedor(proveedor.getId(),
                    this.codigo,
                    this.razonSocial,
                    this.nombreComercial,
                    this.nit,
                    this.noCuenta,
                    this.descuento,
                    this.plazoCredito,
                    this.limiteCredito,
                    (byte) this.tipoPersona.getValue(),
                    this.actividadEconomica,
                    direccion);

            //Init form
            this.init();

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar proveedor
     *
     * @throws Exception, Exception
     */
    public void buscarProveedor() throws Exception {

        logger.debug("Buscando proveedor por codigo");

        try {

            //Obtener manager proveedores
            ManagerProveedoresServiceBusiness mgrProveedor = getMgrProveedoresService();

            //Buscar proveedor por codigo
            this.proveedor = mgrProveedor.buscarProveedorPorCodigo(this.codigo);

            //Init modificacion
            this.initModificacion();

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error("PROVEEDOR NO ENCONTRADO PROCEDER CON EL REGISTRO!!!");
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Remover proveedor
     * @throws Exception, Exception
     */
    public void removerProveedor() throws Exception {

        logger.debug("Remover proveedor");

        try {

            //Obtener manager proveedores
            ManagerProveedoresServiceBusiness mgrProveedor = getMgrProveedoresService();

            //Remover proveedor
            mgrProveedor.removerProveedor(this.proveedor.getId());

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

}
