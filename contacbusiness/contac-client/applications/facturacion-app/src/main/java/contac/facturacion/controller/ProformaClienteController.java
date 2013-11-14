package contac.facturacion.controller;

import contac.internationalization.LanguageLocale;
import contac.modelo.entity.*;
import contac.servicio.facturacion.ManagerFacturacionServiceBusiness;
import contac.servicio.facturacion.ManagerFacturacionServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Copyright (c) 2012, Contac Business Software. All rights reserved.
 * User: Amontenegro
 * Date: 15-07-14
 * Time: 09:24 AM
 */
public class ProformaClienteController extends FacturacionBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(ProformaClienteController.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    private static final BigDecimal MONTO_MINIMO_RET_FUENTE = new BigDecimal("1000.00");
    private static final BigDecimal MONTO_MINIMO_RET_MUNICIPAL = new BigDecimal("1000.00");

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private long noProforma;
    private String correo;
    private Date fechaAlta;
    private Date fechaVencimiento;
    private Almacen almacen;
    private Cliente cliente;
    private AgenteVentas agenteVentas;
    private BigDecimal tasaCambio;
    private Direccion direccionEntrega;
    private List<ArticuloProforma> articulos;
    private String nombreCliente;
    private boolean exonerada;
    private boolean retFuente;
    private boolean retMunicipal;
    private Proforma proforma;
    private Moneda moneda;

    private BigDecimal porcIVA = new BigDecimal("0.00");
    private BigDecimal porcDescuento = new BigDecimal("0.00");
    private BigDecimal porcRetFuente = new BigDecimal("0.00");
    private BigDecimal porcRetMunicipal = new BigDecimal("0.00");
    private BigDecimal iva = new BigDecimal("0.00");
    private BigDecimal descuento = new BigDecimal("0.00");
    private BigDecimal montoRetFuente = new BigDecimal("0.00");
    private BigDecimal montoRetMunicipal = new BigDecimal("0.00");
    private BigDecimal montoBruto = new BigDecimal("0.00");
    private BigDecimal montoAntesImpuesto = new BigDecimal("0.00");
    private BigDecimal montoTotal = new BigDecimal("0.00");

    private long renglon = 90000; //Valor renglon para articulos nuevos dentro de la proforma

    private List<Proforma> proformas;

    //*********************************************************************
    //GETTERS AND SETTERS
    //*********************************************************************

    public long getNoProforma() {
        return noProforma;
    }

    public void setNoProforma(long noProforma) {
        this.noProforma = noProforma;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }


    public Date getFechaVencimiento(){
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento){
        this.fechaVencimiento = fechaVencimiento;
    }
    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public AgenteVentas getAgenteVentas() {
        return agenteVentas;
    }

    public void setAgenteVentas(AgenteVentas agenteVentas) {
        this.agenteVentas = agenteVentas;
    }

    public List<Proforma> getProformas() {
        return proformas;
    }

    public void setProformas(List<Proforma> proformas) {
        this.proformas = proformas;
    }

    public BigDecimal getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(BigDecimal tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    public Direccion getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(Direccion direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public boolean isExonerada() {
        return exonerada;
    }

    public void setExonerada(boolean exonerada) {
        this.exonerada = exonerada;
    }

    public boolean isRetMunicipal() {
        return retMunicipal;

    }

    public void setRetMunicipal(boolean retMunicipal) {
        if (montoAntesImpuesto.compareTo(MONTO_MINIMO_RET_MUNICIPAL) == 1 ||
                montoAntesImpuesto.compareTo(MONTO_MINIMO_RET_MUNICIPAL) == 0) {
            this.retMunicipal = retMunicipal;
        }
    }

    public boolean isRetFuente() {
        return retFuente;
    }

    public void setRetFuente(boolean retFuente) {
        if (montoAntesImpuesto.compareTo(MONTO_MINIMO_RET_FUENTE) == 1 ||
                montoAntesImpuesto.compareTo(MONTO_MINIMO_RET_FUENTE) == 0) {
            this.retFuente = retFuente;
        }
    }

    public List<ArticuloProforma> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ArticuloProforma> articulos) {
        this.articulos = articulos;
    }

    public Proforma getProforma() {
        return proforma;
    }

    public void setProformas(Proforma proforma) {
        this.proforma = proforma;
    }
    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getMontoRetFuente() {
        return montoRetFuente;
    }

    public void setMontoRetFuente(BigDecimal montoRetFuente) {
        this.montoRetFuente = montoRetFuente;
    }

    public BigDecimal getMontoRetMunicipal() {
        return montoRetMunicipal;
    }

    public void setMontoRetMunicipal(BigDecimal montoRetMunicipal) {
        this.montoRetMunicipal = montoRetMunicipal;
    }

    public BigDecimal getMontoBruto() {
        return montoBruto;
    }

    public void setMontoBruto(BigDecimal montoBruto) {
        this.montoBruto = montoBruto;
    }

    public BigDecimal getMontoAntesImpuesto() {
        return montoAntesImpuesto;
    }

    public void setMontoAntesImpuesto(BigDecimal montoAntesImpuesto) {
        this.montoAntesImpuesto = montoAntesImpuesto;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getPorcIVA() {
        return porcIVA;
    }

    public void setPorcIVA(BigDecimal porcIVA) {
        this.porcIVA = porcIVA;
    }

    public BigDecimal getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(BigDecimal porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    public BigDecimal getPorcRetFuente() {
        return porcRetFuente;
    }

    public void setPorcRetFuente(BigDecimal porcRetFuente) {
        this.porcRetFuente = porcRetFuente;
    }

    public BigDecimal getPorcRetMunicipal() {
        return porcRetMunicipal;
    }

    public void setPorcRetMunicipal(BigDecimal porcRetMunicipal) {
        this.porcRetMunicipal = porcRetMunicipal;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    //Init values
    public void init() {

        logger.debug("Iniciando carga de datos proforma cliente");

        //Editar Proforma
        set_edit(false);

        setNoProforma(VALUE_INT_NOT_DEFINED);
        setCorreo(null);
        setTasaCambio(null);
        setCliente(null);
        setNombreCliente(VALUE_STRING_NOT_DEFINED);
        setDireccionEntrega(null);
        setAgenteVentas(null);
        setArticulos(new ArrayList<ArticuloProforma>());
        setExonerada(false);
        setRetFuente(false);
        setRetMunicipal(false);
        setCorreo(VALUE_STRING_NOT_DEFINED);

        //Reiniciar valores de factura
        porcIVA = new BigDecimal("15.00");
        porcDescuento = new BigDecimal("0.00");
        porcRetFuente = new BigDecimal("0.00");
        porcRetMunicipal = new BigDecimal("0.00");
        iva = new BigDecimal("0.00");
        descuento = new BigDecimal("0.00");
        montoRetFuente = new BigDecimal("0.00");
        montoRetMunicipal = new BigDecimal("0.00");
        montoBruto = new BigDecimal("0.00");
        montoAntesImpuesto = new BigDecimal("0.00");
        montoTotal = new BigDecimal("0.00");

        try {
            //Setting fecha alta
            setFechaAlta(buscarFechaFacturacion());

            //Setting tasa de cambio
            TasaCambio tasaCambioInicial = buscarTasaCambioInicial();

            if (tasaCambioInicial != null) {
                setTasaCambio(tasaCambioInicial.getTasaConversion());
                setMoneda(tasaCambioInicial.getMonedaConversion());
            }

            //Setting almacenes registrados
            setAlmacenes(buscarAlmacenes());

            //Setting almacen del usuario
            setAlmacen(buscarAlmacenUsuario());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    //Init modificacion values
    public void initModificacion() {

        logger.debug("Iniciando modificacion de datos proforma cliente");

        //Iniciar datos controller
        init();

        //Editar proforma
        set_edit(true);

        //Reiniciar valores de Proforma
        porcIVA = proforma.getPorcIVA();
        porcDescuento = proforma.getPorcDescuento();
        porcRetFuente = proforma.getPorcRetFuente();
        porcRetMunicipal = proforma.getPorcRetMunicipal();
        iva = proforma.getMontoIVA();
        descuento = proforma.getMontoDescuento();
        montoRetFuente = proforma.getRetencionFuente();
        montoRetMunicipal = proforma.getRetencionMunicipal();
        montoBruto = proforma.getMontoBruto();
        montoAntesImpuesto = proforma.getMontoBruto();
        montoTotal = proforma.getMontoNeto();

        setNoProforma(proforma.getNoDocumento());
        setCorreo(proforma.getCorreo());
        setFechaAlta(proforma.getFechaAlta());
        setCliente(proforma.getCliente());
        setNombreCliente(proforma.getNombreCliente());
        setDireccionEntrega(proforma.getDireccionEntrega());
        setAgenteVentas(proforma.getAgenteVentas());
        setTasaCambio(proforma.getTasaCambio());
        setExonerada(proforma.isExonerada());
        setRetFuente(proforma.isRetencionF());
        setRetMunicipal(proforma.isRetencionM());
        setCorreo(proforma.getCorreo());

        try {
            //<Articulos>
            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();
            List<ArticuloProforma> articulosList = mgrFacturacion.buscarArticulosProforma(proforma.getId());

            //Ordenar listado de articulos
            Collections.sort(articulosList, TiposOrdenamientoArticulo.PorCodigo);
            setArticulos(articulosList);
        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Init registros de proforma
     *
     * @throws Exception, Exception
     */
    public void initRegistrosProformas() throws Exception {

        //Iniciar registro de Proformas
        setProformas(new ArrayList<Proforma>());

        //Setting almacenes registrados
        setAlmacenes(buscarAlmacenes());

        //Setting almacen del usuario
        setAlmacen(buscarAlmacenUsuario());

        //Buscar registro de Proformas con fecha actual del servidor
        Date fechaFacturacion = buscarFechaFacturacion();

        //Buscar Proformas de clientes por fechas
        buscarProformasPorFechas(fechaFacturacion, fechaFacturacion, almacen.getId());
    }

    //*************************************************************************************
    //ACTION EVENTS
    //*************************************************************************************

    /**
     * Crear Proforma
     *
     * @throws Exception, Exception
     */
    public void crearProforma() throws Exception {

        logger.debug("Crear registro de proforma.");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Creamos registro de proforma
            Proforma proforma = mgrFacturacion.crearProforma(getNoProforma(),getCliente().getId(),
                    getAlmacen().getId(), getAgenteVentas().getId(), getPorcDescuento(), getPorcIVA(), getPorcRetFuente(),
                    getPorcRetMunicipal(), getTasaCambio(), getNombreCliente(), getMoneda().getId(), getDireccionEntrega(),
                    getFechaAlta(), isExonerada(), isRetFuente(), isRetMunicipal(), getArticulos(), getFechaVencimiento(),
                    getCorreo());

            //Guardar Proforma
            setProformas(proforma);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar Proforma de cliente
     *
     * @throws Exception, Exception
     */
    public void modificarProforma() throws Exception {

        logger.debug("Modificar registro de proforma.");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Creamos registro de Prooforma
            Proforma proforma = mgrFacturacion.modificarProforma(getProforma().getId(), getTasaCambio(), getDireccionEntrega(),
                    getPorcDescuento(), getPorcIVA(), getPorcRetFuente(), getPorcRetMunicipal(), getFechaAlta(),
                    isExonerada(), isRetFuente(), isRetMunicipal(), getArticulos(), getCorreo());

            //Guardar proforma;
            setProformas(proforma);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }

    /**
     * Anular Proforma
     *
     * @throws Exception, Exception
     */
    public void anularProforma() throws Exception {

        logger.debug("Anular registro de Proforma");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Anulamos registro de Proforma
            mgrFacturacion.anularProforma(getProforma().getId());

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Eliminar Proforma
     *
     * @throws Exception, Exception
     */
    public void eliminarProforma() throws Exception {

        logger.debug("Eliminar Proforma");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Eliminar proforma
            mgrFacturacion.eliminarProforma(getProforma().getId());

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Calcular total de la Proforma
     *
     * @throws Exception, Exception
     */
    public void calcularTotalProforma() throws Exception {

        logger.debug("Calcular total de Proforma.");

        //Limpiar datos de la Proforma
        this.iva = new BigDecimal("0.00");
        this.descuento = new BigDecimal("0.00");
        this.montoRetFuente = new BigDecimal("0.00");
        this.montoRetMunicipal = new BigDecimal("0.00");
        this.montoBruto = new BigDecimal("0.00");
        this.montoAntesImpuesto = new BigDecimal("0.00");
        this.montoTotal = new BigDecimal("0.00");

        for (ArticuloProforma articulo : getArticulos()) {

            //Calcular descuento global
            if (this.porcDescuento.compareTo(new BigDecimal("0.00")) >= 0) {
                articulo.setPorcDescuento(this.porcDescuento);
                articulo.setDescuento(articulo.getPrecioBruto().multiply(new BigDecimal(articulo.getCantidad())).
                        multiply(this.porcDescuento.divide(new BigDecimal("100.00"))).setScale(2, BigDecimal.ROUND_HALF_UP));
                articulo.setPrecioAntesImpuesto(articulo.getPrecioBruto().subtract(articulo.getPrecioBruto().
                        multiply(articulo.getPorcDescuento().divide(new BigDecimal("100")))).multiply(new BigDecimal(articulo.getCantidad())).
                        setScale(2, BigDecimal.ROUND_CEILING));
            }

            //Articulo es exento
            if (articulo.isExento()) {
                articulo.setPorcIva(new BigDecimal(VALUE_INT_ZERO_DEFINED));
                articulo.setIva(new BigDecimal(VALUE_INT_ZERO_DEFINED));
            } else {
                articulo.setPorcIva(new BigDecimal("15.00"));
                articulo.setIva(articulo.getPrecioAntesImpuesto().multiply(articulo.getPorcIva().divide(new BigDecimal("100"))).
                        setScale(2, BigDecimal.ROUND_CEILING));
            }

            //Proforma a cliente es exonerada
            if (isExonerada()) {
                articulo.setPorcIva(new BigDecimal(VALUE_INT_ZERO_DEFINED));
                articulo.setIva(new BigDecimal(VALUE_INT_ZERO_DEFINED));
            } else {
                articulo.setPorcIva(this.porcIVA);
                articulo.setIva(articulo.getPrecioAntesImpuesto().multiply(articulo.getPorcIva().divide(new BigDecimal("100"))).
                        setScale(2, BigDecimal.ROUND_CEILING));
            }

            //Calcular precio neto
            articulo.setPrecioNeto(articulo.getPrecioAntesImpuesto().subtract(articulo.getRetencionFuente()).
                    subtract(articulo.getRetencionMunicipal()).add(articulo.getIva()).setScale(2, BigDecimal.ROUND_CEILING));

            //calculando datos de la proforma
            this.iva = this.iva.add(articulo.getIva());
            this.descuento = this.descuento.add(articulo.getDescuento());
            this.montoRetFuente = this.montoRetFuente.add(articulo.getRetencionFuente());
            this.montoRetMunicipal = this.montoRetMunicipal.add(articulo.getRetencionMunicipal());
            this.montoBruto = this.montoBruto.add(articulo.getPrecioBruto().multiply(new BigDecimal(articulo.getCantidad())));
            this.montoAntesImpuesto = this.montoAntesImpuesto.add(articulo.getPrecioAntesImpuesto());
        }

        //Evaluar retencion fuente
        if (isRetFuente() && (this.montoAntesImpuesto.compareTo(new BigDecimal("1000.00")) == 1 ||
                this.montoAntesImpuesto.compareTo(new BigDecimal("1000.00")) == 0)) {
            this.porcRetFuente = new BigDecimal("2.00");
            this.montoRetFuente = this.montoAntesImpuesto.multiply(porcRetFuente.divide(new BigDecimal("100.00"))).
                    setScale(2, BigDecimal.ROUND_CEILING);
        } else {
            setRetFuente(false);
            this.porcRetFuente = new BigDecimal("0.00");
        }

        //Evaluar retencion municipal
        if (isRetMunicipal() && (this.montoAntesImpuesto.compareTo(new BigDecimal("1000.00")) == 1 ||
                this.montoAntesImpuesto.compareTo(new BigDecimal("1000.00")) == 0)) {
            this.porcRetMunicipal = new BigDecimal("1.00");
            this.montoRetMunicipal = this.montoAntesImpuesto.multiply(porcRetMunicipal.divide(new BigDecimal("100.00"))).
                    setScale(2, BigDecimal.ROUND_CEILING);
        } else {
            setRetMunicipal(false);
            this.porcRetMunicipal = new BigDecimal("0.00");
        }

        //Calcular retencion en la fuente por articulo
        for (ArticuloProforma articulo : getArticulos()) {
            if (isRetFuente()) {

                //Retencion municipal
                if (isRetMunicipal()) {
                    articulo.setPorcRetencionFuente(new BigDecimal("2.00"));
                    articulo.setRetencionFuente(articulo.getPrecioAntesImpuesto().multiply(articulo.getPorcRetencionFuente().
                            divide(new BigDecimal("100.00"), 2, BigDecimal.ROUND_CEILING)));
                } else {
                    articulo.setPorcRetencionFuente(new BigDecimal("0.00"));
                    articulo.setRetencionFuente(new BigDecimal("0.00"));
                }

                //Retencion fuente
                if (isRetMunicipal()) {
                    articulo.setPorcRetencionMunicipal(new BigDecimal("1.00"));
                    articulo.setRetencionMunicipal(articulo.getPrecioAntesImpuesto().multiply(articulo.getPorcRetencionMunicipal().
                            divide(new BigDecimal("100.00"), 2, BigDecimal.ROUND_CEILING)));
                } else {
                    articulo.setRetencionMunicipal(new BigDecimal("0.00"));
                    articulo.setRetencionMunicipal(new BigDecimal("0.00"));
                }

            }
        }

        //Calculando monto total proforma
        this.montoTotal = this.montoTotal.add(this.montoAntesImpuesto.subtract(this.montoRetFuente).
                subtract(this.montoRetMunicipal).add(this.iva));
    }


    /**
     * Agregar articulo
     *
     * @param producto,      Producto asociado al articulo
     * @param renglon,       Renglon del producto agregar
     * @param cantidad,      Cantidad en factura
     * @param precioBruto,   Precio bruto factura
     * @param porcDescuento, Descuento del producto
     * @throws Exception, Exception
     */
    public void agregarArticulo(Producto producto, long renglon, long cantidad, BigDecimal precioBruto, BigDecimal porcDescuento)
            throws Exception {

        logger.debug("Agregando articulo Proforma");

        try {

            //Crear articulo
            ArticuloProforma articulo = new ArticuloProforma();
            articulo.setProducto(producto);

            //Buscar articulo en listado ingresado
            boolean articulo_found = false;

            for (ArticuloProforma entity : getArticulos()) {
                if (entity.getRenglon() == renglon) {
                    articulo = entity;
                    //Setting articulo found to true
                    articulo_found = true;
                }
            }

            //***********************************************************
            //Actualizando datos del articulo
            //***********************************************************
            articulo.setCodigo(producto.getCodigo());
            articulo.setNombre(producto.getNombre());
            articulo.setCodigoFabricante(producto.getCodigoFabricante());
            articulo.setCosto(producto.getCostoPROM().multiply(getTasaCambio()));
            articulo.setCantidad(cantidad);
            articulo.setCostoTotal(producto.getCostoPROM().multiply(getTasaCambio()).multiply(new BigDecimal(cantidad)));
            articulo.setPrecioBruto(precioBruto.setScale(2, BigDecimal.ROUND_HALF_EVEN));
            articulo.setPrecioPromocion(producto.getPrecioPROMOCION() != null);
            if (articulo.getRenglon() <= 0) {
                //Actualizar valor del renglon
                this.renglon = this.renglon + 1;
                articulo.setRenglon(this.renglon);
            }

            //*********************************************************************
            //Calcular descuento y precios de promocion
            //*********************************************************************
            if (articulo.isPrecioPromocion()) {
                articulo.setPorcDescuento(new BigDecimal("0.00"));
                articulo.setDescuento(new BigDecimal("0.00"));
            } else {
                articulo.setPorcDescuento(porcDescuento);
                articulo.setDescuento(articulo.getPrecioBruto().multiply(new BigDecimal(articulo.getCantidad())).
                        multiply(articulo.getPorcDescuento().divide(new BigDecimal("100"))).setScale(2, BigDecimal.ROUND_CEILING));
            }

            //*********************************************************************
            //Calcular precio antes de impuesto
            //*********************************************************************
            articulo.setPrecioAntesImpuesto(articulo.getPrecioBruto().subtract(articulo.getPrecioBruto().
                    multiply(articulo.getPorcDescuento().divide(new BigDecimal("100")))).multiply(new BigDecimal(articulo.getCantidad())).
                    setScale(2, BigDecimal.ROUND_CEILING));
            //*********************************************************************
            //Calcular porcentaje de retencion en la fuente
            //*********************************************************************
            articulo.setPorcRetencionFuente(new BigDecimal("0.00"));
            articulo.setRetencionFuente(articulo.getPrecioAntesImpuesto().multiply(articulo.getPorcRetencionFuente()).
                    setScale(2, BigDecimal.ROUND_CEILING));
            //*********************************************************************
            //Calcular porcentaje de retencion municipal
            //*********************************************************************
            articulo.setPorcRetencionMunicipal(new BigDecimal("0.00"));
            articulo.setRetencionMunicipal(articulo.getPrecioAntesImpuesto().multiply(articulo.getPorcRetencionMunicipal()).
                    setScale(2, BigDecimal.ROUND_CEILING));

            //*********************************************************************
            //Calcular productos exentos de pago de impuestos
            //*********************************************************************
            if (producto.isExento()) {
                articulo.setPorcIva(new BigDecimal("0.00"));
                articulo.setIva(new BigDecimal("0.00"));
            } else {
                articulo.setPorcIva(new BigDecimal("15.00"));
                articulo.setIva(articulo.getPrecioAntesImpuesto().multiply(new BigDecimal("15.00").
                        divide(new BigDecimal("100.00"))).setScale(2, BigDecimal.ROUND_CEILING));
            }

            //*********************************************************************
            //Calcular precio neto articulos
            //*********************************************************************
            articulo.setPrecioNeto(articulo.getPrecioAntesImpuesto().subtract(articulo.getRetencionFuente()).
                    subtract(articulo.getRetencionMunicipal()).add(articulo.getIva()).multiply(new BigDecimal(articulo.getCantidad())).
                    setScale(2, BigDecimal.ROUND_CEILING));
            articulo.setUnidadMedida(producto.getUnidadMedida().getNombre());


            //**********************************************************************
            //Setting valores del articulo para modificacion o creacion en el listado
            //**********************************************************************
            if (articulo_found) {
                articulo.setCreate(false);
                articulo.setUpdate(true);
            } else {
                articulo.setCreate(true);
                articulo.setUpdate(false);

                //Adding articulo
                getArticulos().add(articulo);
            }

            //*********************************************************************
            //Calcular total de la Proforma
            //*********************************************************************
            calcularTotalProforma();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Editar fecha de la Proforma
     *
     * @throws Exception, Exception
     */
    public void editarDatosProforma() throws Exception {

        logger.debug("Editar datos de la Proforma");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();
            mgrFacturacion.usuarioEditaDatosProforma();

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar listado de Proformas a clientes
     *
     * @param fechaDesde, Fecha inicio de busqueda
     * @param fechaHasta, Fecha fin de busqueda
     * @throws Exception, Exception
     */
    public void buscarProformasPorFechas(Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws Exception {
        try {

            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            //Obtener manager facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Buscar Proformas
            List<Proforma> proformas = mgrFacturacion.buscarProformasPorFecha(fechaDesde, fechaHasta, idAlmacen);
            getProformas().clear();
            getProformas().addAll(proformas);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
