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
 * User: emortiz
 * Date: 09-12-12
 * Time: 04:47 PM
 */
public class FacturaClienteController extends FacturacionBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(FacturaClienteController.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    private static final BigDecimal MONTO_MINIMO_RET_FUENTE = new BigDecimal("1000.00");
    private static final BigDecimal MONTO_MINIMO_RET_MUNICIPAL = new BigDecimal("1000.00");

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private long noFactura;
    private Date fechaAlta;
    private Almacen almacen;
    private TiposFactura tipoFactura;
    private Cliente cliente;
    private AgenteVentas agenteVentas;
    private BigDecimal tasaCambio;
    private Direccion direccionEntrega;
    private List<ArticuloFactura> articulos;
    private String nombreCliente;
    private boolean exonerada;
    private boolean retFuente;
    private boolean retMunicipal;
    private Factura factura;
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

    private long renglon = 90000; //Valor renglon para articulos nuevos dentro de la factura

    private List<Factura> facturas;

    //*********************************************************************
    //GETTERS AND SETTERS
    //*********************************************************************

    public long getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(long noFactura) {
        this.noFactura = noFactura;
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

    public TiposFactura getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(TiposFactura tipoFactura) {
        this.tipoFactura = tipoFactura;
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

    public List<ArticuloFactura> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ArticuloFactura> articulos) {
        this.articulos = articulos;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Proforma getProforma() {
        return proforma;
    }

    public void setProforma(Proforma proforma) {
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

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    //Init values
    public void init() {

        logger.debug("Iniciando carga de datos factura compra");

        //Editar factura compra
        set_edit(false);

        setNoFactura(VALUE_INT_NOT_DEFINED);
        setTipoFactura(null);
        setTasaCambio(null);
        setCliente(null);
        setNombreCliente(VALUE_STRING_NOT_DEFINED);
        setDireccionEntrega(null);
        setAgenteVentas(null);
        setArticulos(new ArrayList<ArticuloFactura>());
        setProforma(null);
        setExonerada(false);
        setRetFuente(false);
        setRetMunicipal(false);

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

            //Setting fecha alta facturacion
            setFechaAlta(buscarFechaFacturacion());

            //Setting tasa de cambio facturacion
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

        logger.debug("Iniciando modificacion de datos factura compra");

        //Iniciar datos controller
        init();

        //Editar factura compra
        set_edit(true);

        //Reiniciar valores de factura
        porcIVA = factura.getPorcIVA();
        porcDescuento = factura.getPorcDescuento();
        porcRetFuente = factura.getPorcRetFuente();
        porcRetMunicipal = factura.getPorcRetMunicipal();
        iva = factura.getMontoIVA();
        descuento = factura.getMontoDescuento();
        montoRetFuente = factura.getRetencionFuente();
        montoRetMunicipal = factura.getRetencionMunicipal();
        montoBruto = factura.getMontoBruto();
        montoAntesImpuesto = factura.getMontoBruto();
        montoTotal = factura.getMontoNeto();

        setNoFactura(factura.getNoDocumento());
        setFechaAlta(factura.getFechaAlta());
        setCliente(factura.getCliente());
        setNombreCliente(factura.getNombreCliente());
        setDireccionEntrega(factura.getDireccionEntrega());
        setAgenteVentas(factura.getAgenteVentas());
        setTasaCambio(factura.getTasaCambio());
        setProforma(factura.getProforma());
        setExonerada(factura.isExonerada());
        setRetFuente(factura.isRetencionF());
        setRetMunicipal(factura.isRetencionM());
        setProforma(factura.getProforma());

        //<Tipo de factura>
        for (TiposFactura tipoFactura : TiposFactura.values()) {
            if (tipoFactura.getValue() == (int) factura.getTipoFactura()) {
                setTipoFactura(tipoFactura);
            }
        }

        try {
            //<Articulos>
            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();
            List<ArticuloFactura> articulosList = mgrFacturacion.buscarArticulosFactura(factura.getId());

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
     * Init registros de factura
     *
     * @throws Exception, Exception
     */
    public void initRegistrosFactura() throws Exception {

        //Iniciar registro de facturas
        setFacturas(new ArrayList<Factura>());

        //Buscar registro de facturas con fecha actual del servidor
        Date fechaFacturacion = buscarFechaFacturacion();

        //Buscar facturas de clientes por fechas
        buscarFacturasClientesPorFechas(fechaFacturacion, fechaFacturacion);
    }

    //*************************************************************************************
    //ACTION EVENTS
    //*************************************************************************************

    /**
     * Crear factura de compra
     *
     * @throws Exception, Exception
     */
    public void crearFactura() throws Exception {

        logger.debug("Crear registro de factura.");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Creamos registro de factura
            Factura factura = mgrFacturacion.crearFactura(getNoFactura(), getTipoFactura().getValue(), getCliente().getId(),
                    getAlmacen().getId(), getAgenteVentas().getId(), getPorcDescuento(), getPorcIVA(), getPorcRetFuente(),
                    getPorcRetMunicipal(), getTasaCambio(), getNombreCliente(), getMoneda().getId(), getDireccionEntrega(),
                    getFechaAlta(), isExonerada(), isRetFuente(), isRetMunicipal(), null, getArticulos());

            //Guardar factura
            setFactura(factura);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar factura de compra
     *
     * @throws Exception, Exception
     */
    public void modificarFactura() throws Exception {

        logger.debug("Modificar registro de factura.");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Creamos registro de factura
            Factura factura = mgrFacturacion.modificarFactura(getFactura().getId(), getTasaCambio(), getDireccionEntrega(),
                    getPorcDescuento(), getPorcIVA(), getPorcRetFuente(), getPorcRetMunicipal(), getFechaAlta(),
                    isExonerada(), isRetFuente(), isRetMunicipal(), null, getArticulos());

            //Guardar factura;
            setFactura(factura);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Anular factura de compra
     *
     * @throws Exception, Exception
     */
    public void anularFactura() throws Exception {

        logger.debug("Anular registro de factura");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Anulamos registro de factura
            mgrFacturacion.anularFactura(getFactura().getId());

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }

    /**
     * Eliminar factura de compra
     *
     * @throws Exception, Exception
     */
    public void eliminarFactura() throws Exception {

        logger.debug("Eliminar factura de compra");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Eliminar factura de compra
            mgrFacturacion.eliminarFactura(getFactura().getId());

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Calcular total de la factura de compra
     *
     * @throws Exception, Exception
     */
    public void calcularTotalFactura() throws Exception {

        logger.debug("Calcular total de factura.");

        //Limpiar datos de la factura
        this.iva = new BigDecimal("0.00");
        this.descuento = new BigDecimal("0.00");
        this.montoRetFuente = new BigDecimal("0.00");
        this.montoRetMunicipal = new BigDecimal("0.00");
        this.montoBruto = new BigDecimal("0.00");
        this.montoAntesImpuesto = new BigDecimal("0.00");
        this.montoTotal = new BigDecimal("0.00");

        for (ArticuloFactura articulo : getArticulos()) {

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

            //Factura a cliente es exonerada
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


            //calculando datos de la factura
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
        for (ArticuloFactura articulo : getArticulos()) {
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


        //Calculando monto total factura
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

        logger.debug("Agregando articulo factura");

        try {

            //Crear articulo
            ArticuloFactura articulo = new ArticuloFactura();
            articulo.setProducto(producto);

            //Buscar articulo en listado ingresado
            boolean articulo_found = false;

            for (ArticuloFactura entity : getArticulos()) {
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
            //Calcular total de la factura
            //*********************************************************************
            calcularTotalFactura();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Editar fecha de la factura
     *
     * @throws Exception, Exception
     */
    public void editarDatosFactura() throws Exception {

        logger.debug("Editar datos de la factura");

        try {

            //Obtener manager de facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();
            mgrFacturacion.usuarioEditaDatosFactura();

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar listado de facturas a clientes
     *
     * @param fechaDesde, Fecha inicio de busqueda
     * @param fechaHasta, Fecha fin de busqueda
     * @throws Exception, Exception
     */
    public void buscarFacturasClientesPorFechas(Date fechaDesde, Date fechaHasta) throws Exception {
        try {

            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            //Obtener manager facturacion
            ManagerFacturacionServiceBusiness mgrFacturacion = getMgrFacturacionService();

            //Buscar facturas 
            List<Factura> facturas = mgrFacturacion.buscarFacturasPorFecha(fechaDesde, fechaHasta);
            getFacturas().clear();
            getFacturas().addAll(facturas);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}