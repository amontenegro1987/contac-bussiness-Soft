package contac.administracion.controller;

import contac.internationalization.LanguageLocale;
import contac.modelo.entity.*;
import contac.servicio.facturacion.ManagerFacturacionServiceBusiness;
import contac.servicio.facturacion.ManagerFacturacionServiceBusinessException;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusinessException;
import contac.servicio.proveedores.ManagerProveedoresServiceBusiness;
import contac.servicio.proveedores.ManagerProveedoresServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Copyright (c) 2012, Contac Business Software. All rights reserved.
 * User: Alejandro Montenegro
 * Date: 05/02/2014
 * Time: 10:56 PM
 */
public class OrdenCompraController extends FacturacionBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(OrdenCompraController.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/facturacion/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    private static final BigDecimal MONTO_MINIMO_RET_FUENTE = new BigDecimal("1000.00");
    private static final BigDecimal MONTO_MINIMO_RET_MUNICIPAL = new BigDecimal("1000.00");

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private long noOrdenCompra;
    private Date fechaAlta;
    private Date fechaRequerida;
    private Proveedor proveedor;
    private BigDecimal tasaCambio;
    private Direccion direccionEntrega;
    private List<ArticuloOrdenCompra> articulos;
    private String nombreProveedor;
    private boolean exonerada;
    private boolean retFuente;
    private boolean retMunicipal;
    private OrdenCompra ordenCompra;
    private Moneda moneda;
    private String descripcionCompra;
    private Integer numeroReferencia;

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

    private long renglon = 90000; //Valor renglon para articulos nuevos dentro de la Orden de Compra

    private List<OrdenCompra> ordenCompras;

    //*********************************************************************
    //GETTERS AND SETTERS
    //*********************************************************************

    public long getNoOrdenCompra() {
        return noOrdenCompra;
    }

    public void setNoOrdenCompra(long noOrdenCompra) {
        this.noOrdenCompra = noOrdenCompra;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaRequerida() {
        return fechaRequerida;
    }

    public void setFechaRequerida(Date fechaRequerida) {
        this.fechaRequerida = fechaRequerida;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<OrdenCompra> getOrdenCompras() {
        return ordenCompras;
    }

    public void setOrdenCompras(List<OrdenCompra> ordenCompras) {
        this.ordenCompras = ordenCompras;
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

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getDescripcionCompra() {
        return descripcionCompra;
    }

    public void setDescripcionCompra(String descripcionCompra) {
        this.descripcionCompra = descripcionCompra;
    }

    public Integer getNumeroReferencia() {
        return numeroReferencia;
    }

    public void setNumeroReferencia(Integer numeroReferencia) {
        this.numeroReferencia = numeroReferencia;
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

    public List<ArticuloOrdenCompra> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ArticuloOrdenCompra> articulos) {
        this.articulos = articulos;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
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

        logger.debug("Iniciando carga de datos controller");

        //Editar Orden de Compra
        set_edit(false);

        setNoOrdenCompra(VALUE_INT_NOT_DEFINED);
        setTasaCambio(null);
        setProveedor(null);
        setNombreProveedor(VALUE_STRING_NOT_DEFINED);
        setDescripcionCompra(VALUE_STRING_NOT_DEFINED);
        setNumeroReferencia(null);
        setDireccionEntrega(null);
        setArticulos(new ArrayList<ArticuloOrdenCompra>());
        setExonerada(false);
        setRetFuente(false);
        setRetMunicipal(false);

        //Reiniciar valores de Orden de Compra
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

            //Setting fecha alta OrdenCompra
            setFechaAlta(buscarFechaFacturacion());

            //Setting tasa de cambio Orden de Compra
            TasaCambio tasaCambioInicial = buscarTasaCambioInicial();

            if (tasaCambioInicial != null) {
                setTasaCambio(tasaCambioInicial.getTasaConversion());
                setMoneda(tasaCambioInicial.getMonedaConversion());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    //Init modificacion values
    public void initModificacion() {

        logger.debug("Iniciando modificacion de datos Orden de Compra");

        //Iniciar datos controller
        init();

        //Editar Orden de Compra
        set_edit(true);

        //Reiniciar valores de Orden de Compra
        porcIVA = ordenCompra.getPorcIVA();
        porcDescuento = ordenCompra.getPorcDescuento();
        porcRetFuente = ordenCompra.getPorcRetFuente();
        porcRetMunicipal = ordenCompra.getPorcRetMunicipal();
        iva = ordenCompra.getMontoIVA();
        descuento = ordenCompra.getMontoDescuento();
        montoRetFuente = ordenCompra.getRetencionFuente();
        montoRetMunicipal = ordenCompra.getRetencionMunicipal();
        montoBruto = ordenCompra.getMontoBruto();
        montoAntesImpuesto = ordenCompra.getMontoBruto();
        montoTotal = ordenCompra.getMontoNeto();

        setNoOrdenCompra(ordenCompra.getNoDocumento());
        setFechaAlta(ordenCompra.getFechaAlta());
        setFechaRequerida(ordenCompra.getFechaRequerida());
        setProveedor(ordenCompra.getProveedor());
        setNombreProveedor(ordenCompra.getNombreProveedor());
        setDescripcionCompra(ordenCompra.getDescripcionCompra());
        setNumeroReferencia(ordenCompra.getNumeroReferencia());
        setDireccionEntrega(ordenCompra.getDireccionEntrega());
        setTasaCambio(ordenCompra.getTasaCambio());
        setExonerada(ordenCompra.isExonerada());
        setRetFuente(ordenCompra.isRetencionF());
        setRetMunicipal(ordenCompra.isRetencionM());


        try {
            //<Articulos>
            //Obtener manager de proveedores
            ManagerProveedoresServiceBusiness mgrProveedores = getMgrProveedoresService();
            List<ArticuloOrdenCompra> articulosList = mgrProveedores.buscarArticulosOrdenCompra(ordenCompra.getId());

            //Ordenar listado de articulos
            Collections.sort(articulosList, TiposOrdenamientoArticulo.PorCodigo);
            setArticulos(articulosList);
        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }
    }

    //Init registros de ordenes de compra
    public void initRegistrosOrdenesCompra() throws Exception {

        //Buscar registro de orden de compra con fecha actual del servidor
        Date fechaFacturacion = buscarFechaFacturacion();

        setOrdenCompras(buscarOrdenesComprasPorFechas(fechaFacturacion, fechaFacturacion));
    }

    //*************************************************************************************
    //ACTION EVENTS
    //*************************************************************************************

    /**
     * Crear Orden de Compra
     *
     * @throws Exception, Exception
     */
    public void crearOrdenCompra() throws Exception {

        logger.debug("Crear registro de Orden de Compra.");

        try {

            //Obtener manager de Proveedores
            ManagerProveedoresServiceBusiness mgrProveedores = getMgrProveedoresService();

            //Creamos registro de Orden de Compra
            OrdenCompra ordenCompra = mgrProveedores.crearOrdenCompra(getNoOrdenCompra(),getProveedor().getId(),
                    getPorcDescuento(), getPorcIVA(), getPorcRetFuente(),
                    getPorcRetMunicipal(), getTasaCambio(), getNombreProveedor(), getMoneda().getId(), getDireccionEntrega(),
                    isExonerada(), isRetFuente(), isRetMunicipal(), getArticulos(),getFechaAlta(), getFechaRequerida(), getDescripcionCompra(), getNumeroReferencia());

            setOrdenCompra(ordenCompra);

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar Orden de Compra de cliente
     *
     * @throws Exception, Exception
     */
    public void modificarOrdenCompra() throws Exception {

        logger.debug("Modificar registro de Orden de Compra.");

        try {

            //Obtener manager de proveedores
            ManagerProveedoresServiceBusiness mgrProveedor = getMgrProveedoresService();

            //Creamos registro de Orden de Compra
            OrdenCompra ordenCompra = mgrProveedor.modificarOrdenCompra(getOrdenCompra().getId(), getTasaCambio(),
                    getPorcDescuento(), getPorcIVA(), getPorcRetFuente(), getPorcRetMunicipal(), getFechaAlta(),
                    isExonerada(), isRetFuente(), isRetMunicipal(), getArticulos(), getFechaRequerida(),
                    getDescripcionCompra(), getNumeroReferencia());

            //Guardar Orden de Compra;
            setOrdenCompras(ordenCompras);

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
     }

    /**
     * Anular Orden de Compra
     *
     * @throws Exception, Exception
     */
    public void anularOrdenCompra() throws Exception {

        logger.debug("Anular registro de Orden de Compra");
        try {

            //Obtener manager de Proveedores
            ManagerProveedoresServiceBusiness mgrProveedor = getMgrProveedoresService();

            //Anulamos Orden de Compra
            mgrProveedor.anularOrdenCompra(getOrdenCompra().getId());

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Eliminar Orden de Compra
     *
     * @throws Exception, Exception
     */
    public void eliminarOrdenCompra() throws Exception {

        logger.debug("Eliminar factura de compra");

        try {

            //Obtener manager de Proveedores
            ManagerProveedoresServiceBusiness mgrProveedor = getMgrProveedoresService();

            //Eliminar factura de compra
            mgrProveedor.eliminarOrdenCompra(getOrdenCompra().getId());

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Validar Estado de Orden de Compra a Imprimir
     *
     * @throws Exception, Exception
     */

    public void ordenCompraImprimir() throws Exception {

        logger.debug("Validar Estado de Orden de Compra");

        try {

            //Obtener manager de Inventario
            ManagerProveedoresServiceBusiness mgrProveedor = getMgrProveedoresService();

            //Validar Estado de Orden de Compra
            mgrProveedor.validarImpresionOrdenCompra(getOrdenCompra().getId());

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }

    /**
     * Aplicar orden de traslado
     *
     * @throws Exception, Exception
     */
    public void aplicarOrdenCompra() throws Exception {

        logger.debug("Aplicando Orden de Compra");

        try {

            //Obtener manager de Inventario
            ManagerProveedoresServiceBusiness mgrProveedor = getMgrProveedoresService();

            //Cambiar Estado de Orden de Traslado
            mgrProveedor.aplicarOrdenCompra(getOrdenCompra().getId());

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Calcular total de la Orden de Compra
     *
     * @throws Exception, Exception
     */
    public void calcularTotalOrdenCompra() throws Exception {

        logger.debug("Calcular total de Orden de Compra.");

        //Limpiar datos de la Proforma
        this.iva = new BigDecimal("0.00");
        this.descuento = new BigDecimal("0.00");
        this.montoRetFuente = new BigDecimal("0.00");
        this.montoRetMunicipal = new BigDecimal("0.00");
        this.montoBruto = new BigDecimal("0.00");
        this.montoAntesImpuesto = new BigDecimal("0.00");
        this.montoTotal = new BigDecimal("0.00");

        for (ArticuloOrdenCompra articulo : getArticulos()) {

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
        for (ArticuloOrdenCompra articulo : getArticulos()) {
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

        //Calculando monto total OrdenCompra
        this.montoTotal = this.montoTotal.add(this.montoAntesImpuesto.subtract(this.montoRetFuente).
                subtract(this.montoRetMunicipal).add(this.iva));
    }


    /**
     * Agregar articulo
     *
     * @param producto,      Producto asociado al articulo
     * @param renglon,       Renglon del producto agregar
     * @param cantidad,      Cantidad en Orden de Compra
     * @param precioBruto,   Precio bruto Orden de Compra
     * @param porcDescuento, Descuento del producto
     * @throws Exception, Exception
     */
    public void agregarArticulo(Producto producto, long renglon, long cantidad, BigDecimal precioBruto, BigDecimal porcDescuento)
            throws Exception {

        logger.debug("Agregando articulo Orden de Compra");

        try {

            //Crear articulo
            ArticuloOrdenCompra articulo = new ArticuloOrdenCompra();
            articulo.setProducto(producto);

            //Buscar articulo en listado ingresado
            boolean articulo_found = false;

            for (ArticuloOrdenCompra entity : getArticulos()) {
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
            calcularTotalOrdenCompra();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Editar fecha de la OrdendeCompra
     *
     * @throws Exception, Exception
     */
    public void editarDatosProforma() throws Exception {

       /* logger.debug("Editar datos de la Orden de Compra");

        try {

            //Obtener manager de facturacion
            ManagerProveedoresServiceBusiness mgrProveedor = getMgrProveedoresService();
            mgrProveedor.usuarioEditaDatosOrdenCompra();

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }*/
    }

    /**
     * Buscar listado de Ordenes de Compra a clientes
     *
     * @param fechaDesde, Fecha inicio de busqueda
     * @param fechaHasta, Fecha fin de busqueda
     * @throws Exception, Exception
     */
    /**
     * Obteniendo listado de ordenes de entrada inventario ordinaria
     *
     * @return List
     */
    public List<OrdenCompra> buscarOrdenesComprasPorFechas(Date fechaDesde, Date fechaHasta) throws Exception {

        logger.debug("Obteniendo listado de ordenes de Compra...!");

        try {
            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            //Obtener manager inventario service
            ManagerProveedoresServiceBusiness mgrProveedoresService = getMgrProveedoresService();

            return mgrProveedoresService.buscarOrdenesComprasPorFechasRegistro(fechaDesde, fechaHasta);

        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    public void buscarOrdenesComprasPorFechasRegistros(Date fechaDesde, Date fechaHasta)
            throws Exception {
        try {

            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            //Obtener manager inventario service
            ManagerProveedoresServiceBusiness mgrProveedoresService = getMgrProveedoresService();

            //Buscar Orden Compra
            List<OrdenCompra> ordenCompras = mgrProveedoresService.buscarOrdenesComprasPorFechasRegistro(fechaDesde, fechaHasta);
            getOrdenCompras().clear();
            getOrdenCompras().addAll(ordenCompras);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
