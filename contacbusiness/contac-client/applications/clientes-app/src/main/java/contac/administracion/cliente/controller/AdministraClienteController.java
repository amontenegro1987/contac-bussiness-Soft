package contac.administracion.cliente.controller;

import contac.modelo.entity.*;
import contac.servicio.clientes.ManagerClientesServiceBusiness;
import contac.servicio.clientes.ManagerClientesServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: EMontenegro
 * Date: 02-05-12
 * Time: 10:07 PM
 */
public class AdministraClienteController extends ClienteBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(AdministraClienteController.class);

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private long codigo;
    private String nombreComercial;
    private String razonSocial;
    private String nit;
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
    private Cliente cliente;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    //Init values
    public void init() {

        logger.debug("Iniciar carga de datos");

        //Edit datas
        set_edit(false);

        setCodigo(VALUE_INT_ZERO_DEFINED);
        setNombreComercial(VALUE_STRING_NOT_DEFINED);
        setRazonSocial(VALUE_STRING_NOT_DEFINED);
        setNit(VALUE_STRING_NOT_DEFINED);
        setDescuento(new BigDecimal(0.00));
        setPlazoCredito(VALUE_INT_ZERO_DEFINED);
        setLimiteCredito(new BigDecimal(0.00));
        setActividadEconomica(null);
        setTipoPersona(TiposPersona.PERSONA_NATURAL);
        setPais(null);
        setEstado(VALUE_STRING_NOT_DEFINED);
        setCiudad(VALUE_STRING_NOT_DEFINED);
        setCodigoPostal(VALUE_STRING_NOT_DEFINED);
        setDireccion(VALUE_STRING_NOT_DEFINED);
        setEmail(VALUE_STRING_NOT_DEFINED);
        setWeb(VALUE_STRING_NOT_DEFINED);
        setTelefonoOficina(VALUE_STRING_NOT_DEFINED);
        setTelefonoMovil(VALUE_STRING_NOT_DEFINED);
        setFax(VALUE_STRING_NOT_DEFINED);
        setCliente(null);
    }

    //Init modification values
    public void initModificacion() {

        logger.debug("Iniciar modificacion datos");

        //Edit datas
        set_edit(true);

        //<Codigo>
        setCodigo(this.cliente.getCodigo());
        //<Nombre comercial>
        setNombreComercial(this.cliente.getNombreComercial());
        //<Razon social>
        setRazonSocial(this.cliente.getRazonSocial());
        //<Nit>
        setNit(this.cliente.getNit());
        //<Descuento>
        setDescuento(this.cliente.getDescuento());
        //<Plazo credito>
        setPlazoCredito(this.cliente.getPlazoCredito());
        //<Limite credito>
        setLimiteCredito(this.cliente.getLimiteCredito());
        //<Actividad economica>
        setActividadEconomica(this.cliente.getActividadEconomica());
        //<Tipos de persona>
        for (TiposPersona tipoPersona : TiposPersona.values()) {
            if (tipoPersona.getValue() == (int) this.cliente.getTipoPersona())
                setTipoPersona(tipoPersona);
        }
        //<Pais>
        setPais(this.cliente.getDireccion().getPais());
        //<Estado>
        setEstado(this.cliente.getDireccion().getEstado());
        //<Ciudad>
        setCiudad(this.cliente.getDireccion().getCiudad());
        //<Codigo postal>
        setCodigoPostal(this.cliente.getDireccion().getCodigoPostal());
        //<Direccion>
        setDireccion(this.cliente.getDireccion().getDireccion());
        //<Email>
        setEmail(this.cliente.getDireccion().getEmail());
        //<Web>
        setWeb(this.cliente.getDireccion().getWeb());
        //<Telefono oficina>
        setTelefonoOficina(this.cliente.getDireccion().getTelefono());
        //<Telefono movil>
        setTelefonoMovil(this.cliente.getDireccion().getCelular());
        //<Fax>
        setFax(this.cliente.getDireccion().getFax());
    }

    /**
     * Buscar cliente
     *
     * @throws Exception, Exception
     */
    public void buscarCliente() throws Exception {

        logger.info("Buscando cliente por su codigo nit");

        try {

            //Obtener manager de clientes
            ManagerClientesServiceBusiness mgrClientes = getMgrClientesService();

            //Buscar cliente por codigo
            this.cliente = mgrClientes.buscarClientePorCodigoNit(this.nit);

            //Init modificacion
            initModificacion();

        } catch (ManagerClientesServiceBusinessException e) {
            logger.error("CLIENTE NO ENCONTRADO PROCEDER CON EL REGISTRO");
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Retistra nuevo cliente
     *
     * @throws Exception, Exception
     */
    public void registrarCliente() throws Exception {

        logger.debug("Registrando nuevo cliente");

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

            //Obtener manager clientes
            ManagerClientesServiceBusiness mgrClientes = getMgrClientesService();

            //Registrar cliente
            mgrClientes.registrarCliente(this.razonSocial, this.nombreComercial, this.nit, getActividadEconomica().getId(),
                    direccion, null, this.plazoCredito, this.limiteCredito, this.descuento, (byte) this.tipoPersona.getValue());

            //Init registros
            init();

        } catch (ManagerClientesServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar cliente
     * @throws Exception, Exception
     */
    public void modificarCliente() throws Exception {

        logger.debug("Modificando cliente");

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

            //Obtener manager clientes
            ManagerClientesServiceBusiness mgrClientes = getMgrClientesService();

            //Modificar cliente
            mgrClientes.modificarCliente(this.cliente.getId(), this.razonSocial, this.nombreComercial, this.nit,
                    getActividadEconomica().getId(), direccion, null, this.plazoCredito, this.limiteCredito, this.descuento,
                    (byte) this.tipoPersona.getValue());

            //Init registros
            init();

        } catch (ManagerClientesServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Remover cliente
     * @throws Exception, Exception
     */
    public void removerCliente() throws Exception {

        logger.debug("Removiendo cliente");

        try {

            //Obtener manager clientes
            ManagerClientesServiceBusiness mgrClientes = getMgrClientesService();

            //Remover cliente
            mgrClientes.removerCliente(this.cliente.getId());

            //Init registros
            init();

        } catch (ManagerClientesServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
