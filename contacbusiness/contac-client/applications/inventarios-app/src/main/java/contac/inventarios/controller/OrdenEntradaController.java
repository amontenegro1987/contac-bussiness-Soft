/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.inventarios.controller;

import contac.internationalization.LanguageLocale;
import contac.modelo.entity.*;
import contac.servicio.facturacion.ManagerFacturacionServiceBusiness;
import contac.servicio.facturacion.ManagerFacturacionServiceBusinessException;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusinessException;
import contac.servicio.seguridad.ManagerSeguridadServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Administracion de ordenes de entrada controller
 * User: Eddy Montenegro
 * Date: 11-09-11
 * Time: 11:41 PM
 */
public class OrdenEntradaController extends InventarioBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(OrdenEntradaController.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private long noMovimiento;
    private Date fechaAlta;
    private Date fechaSolicitud;
    private String personaEntrega;
    private String descripcion;
    private Moneda moneda;
    private double montoTotal;
    private List<OrdenEntrada> ordenesEntrada;
    List<ArticuloEntrada> articulos;
    private OrdenEntrada ordenEntrada;
    private TiposEntrada tipoEntrada;
    private Almacen almacen;

    //*********************************************************************
    //GETTERS AND SETTERS
    //*********************************************************************

    public long getNoMovimiento() {
        return noMovimiento;
    }

    public void setNoMovimiento(long noMovimiento) {
        this.noMovimiento = noMovimiento;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getPersonaEntrega() {
        return personaEntrega;
    }

    public void setPersonaEntrega(String personaEntrega) {
        this.personaEntrega = personaEntrega;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public List<ArticuloEntrada> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ArticuloEntrada> articulos) {
        this.articulos = articulos;
    }

    public OrdenEntrada getOrdenEntrada() {
        return ordenEntrada;
    }

    public void setOrdenEntrada(OrdenEntrada ordenEntrada) {
        this.ordenEntrada = ordenEntrada;
    }

    public TiposEntrada getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(TiposEntrada tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<OrdenEntrada> getOrdenesEntrada() {
        return ordenesEntrada;
    }

    public void setOrdenesEntrada(List<OrdenEntrada> ordenesEntrada) {
        this.ordenesEntrada = ordenesEntrada;
    }

    //Init values
    public void init() {

        logger.debug("Iniciando carga datos controller");

        //Editar orden de entrada
        set_edit(false);

        setNoMovimiento(VALUE_INT_ZERO_DEFINED);
        setFechaAlta(new Date());
        setFechaSolicitud(new Date());
        setPersonaEntrega(VALUE_STRING_NOT_DEFINED);
        setDescripcion(VALUE_STRING_NOT_DEFINED);
        setMoneda(null);
        setMontoTotal(VALUE_INT_NOT_DEFINED);
        setArticulos(new ArrayList<ArticuloEntrada>());
        setTipoEntrada(null);
        setAlmacen(null);

        try {

            //Cargar listado de almacenes
            setAlmacenes(buscarAlmacenes());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    //Init Modificacion values
    public void initModificacion() {

        logger.debug("Iniciando carga datos para modificacion controller");

        //Init values
        init();

        //Editar orden de entrada
        set_edit(true);

        setNoMovimiento(ordenEntrada.getNoMovimiento());
        setFechaAlta(ordenEntrada.getFechaAlta());
        setFechaSolicitud(ordenEntrada.getFechaSolicitud());
        setPersonaEntrega(ordenEntrada.getPersonaEntrega());
        setDescripcion(ordenEntrada.getDescripcion());
        setMoneda(ordenEntrada.getMoneda());
        setMontoTotal(ordenEntrada.getMontoTotal().doubleValue());
        setAlmacen(ordenEntrada.getAlmacen());

        //<Tipo de entrada>
        for (TiposEntrada tipoEntrada : TiposEntrada.values()) {
            if (tipoEntrada.getValue() == (int) ordenEntrada.getTipoEntrada()) {
                setTipoEntrada(tipoEntrada);
            }
        }

        //<Articulos>
        List<ArticuloEntrada> articulosList = new ArrayList<ArticuloEntrada>();
        articulosList.addAll(ordenEntrada.getArticulos());

        //Ordenar listado de articulos
        Collections.sort(articulosList, TiposOrdenamientoArticulo.PorCodigo);

        setArticulos(articulosList);
    }

    //Init registros de ordenes de entrada inventario
    public void initRegistrosOrdenesEntradaInventario() throws Exception {

        //Setting almacenes registrados
        setAlmacenes(buscarAlmacenes());

        //Setting almacen del usuario
        setAlmacen(buscarAlmacenUsuario());
        if (getAlmacen() == null) {
            setAlmacen(getAlmacenes().get(0));
        }

        //Buscar Ordenes de Entrada con fecha actual del servidor
        Date fechaFacturacion = buscarFechaFacturacion();

       setOrdenesEntrada(buscarOrdenesEntrada(fechaFacturacion, fechaFacturacion, almacen.getId()));
    }

    //*************************************************************************************
    //ACTION EVENTS
    //*************************************************************************************

    /**
     * Crear encabezado de orden de entrada
     *
     * @throws Exception, Exception
     */
    public void crearOrdenEntrada() throws Exception {

        logger.debug("Crear orden de entrada de inventario.");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Creamos registro de ingreso
            OrdenEntrada ordenEntrada = mgrInventario.crearOrdenEntrada(getTipoEntrada().getValue(), getFechaAlta(),
                    null, getAlmacen().getId(), getPersonaEntrega(), getDescripcion(), getArticulos());

            //Setting orden de entrada
            this.ordenEntrada = ordenEntrada;

            //Init modificacion
            initModificacion();

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar orden de entrada inventario
     *
     * @throws Exception, Exception
     */
    public void modificarOrdenEntrada() throws Exception {

        logger.debug("Modificar orden de entrada de inventario.");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Modificar registro de orden de compra
            OrdenEntrada ordenEntrada = mgrInventario.modificarOrdenEntrada(getOrdenEntrada().getId(), TiposEntrada.ENTRADA_ORDINARIA.getValue(),
                    getFechaAlta(), null, getPersonaEntrega(), getDescripcion(), getArticulos());

            //Setting orden de entrada
            this.ordenEntrada = ordenEntrada;

            //Init Modificacion
            initModificacion();

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

/**
     * Validar Estado de Orden de Entrada a Imprimir
     *
     * @throws Exception, Exception
     */
    public void ordenEntradaImprimir() throws Exception {

        logger.debug("Validar Estado de Orden de Entrada");

        try {

            //Obtener manager de Inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Validar Estado de Orden de Entrada
            mgrInventario.validarImpresionOrdenEntrada(getOrdenEntrada().getId());

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }

    /**
     * Remover orden de entrda inventario
     *
     * @throws Exception, Exception
     */
    public void removerOrdenEntrada() throws Exception {

        logger.debug("Remover orden de entrada de inventario.");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Remover registro de orden de entrada
            mgrInventario.eliminarOrdenEntrada(ordenEntrada.getId());

            //Actualizar registro de ordenes de entrada
            getOrdenesEntrada().clear();
            //getOrdenesEntrada().addAll(buscarOrdenesEntrada());

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Anular orden de entrada inventario
     *
     * @throws Exception, Exception
     */
    public void anularOrdenEntrada() throws Exception {

        logger.debug("Anular orden de entrada de inventario.");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Anular registro de orden de entrada
            mgrInventario.anularOrdenEntrada(ordenEntrada.getId());

            //Actualizar registro de ordenes de entrada
            getOrdenesEntrada().clear();
            //getOrdenesEntrada().addAll(buscarOrdenesEntrada());

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Agregar articulo a orden de entrada
     *
     * @param producto, Producto
     * @param cantidad, int
     */
    public void agregarArticulo(Producto producto, int cantidad, double costoUND) throws Exception {

        logger.debug("Agregando articulo entrada.");

        try {

            //Crear articulo
            ArticuloEntrada articulo = null;

            //Buscar articulo en listado ingresado
            for (ArticuloEntrada entity : getArticulos()) {
                if (entity.getCodigo().equals(producto.getCodigo()))
                    articulo = entity;
            }

            if (articulo != null) {
                articulo.setCodigo(producto.getCodigo());
                articulo.setNombre(producto.getNombre());
                articulo.setCodigoFabricante(producto.getCodigoFabricante());
                articulo.setCosto(new BigDecimal(costoUND));
                articulo.setCantidad(cantidad);
                articulo.setCostoTotal(articulo.getCosto().multiply(new BigDecimal(cantidad)));
                articulo.setUnidadMedida(producto.getUnidadMedida().getNombre());
                articulo.setProducto(producto);
                articulo.setCreate(false);
                articulo.setUpdate(true);
            } else {
                articulo = new ArticuloEntrada();
                articulo.setCodigo(producto.getCodigo());
                articulo.setNombre(producto.getNombre());
                articulo.setCodigoFabricante(producto.getCodigoFabricante());
                articulo.setCosto(new BigDecimal(costoUND));
                articulo.setCantidad(cantidad);
                articulo.setCostoTotal(articulo.getCosto().multiply(new BigDecimal(cantidad)));
                articulo.setUnidadMedida(producto.getUnidadMedida().getNombre());
                articulo.setProducto(producto);
                articulo.setCreate(true);
                articulo.setUpdate(false);

                //Adding articulo
                getArticulos().add(articulo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Imprimir orden de entrada
     * @throws Exception, Exception
     */
    public void imprimirOrdenEntrada() throws Exception {
        
        logger.debug("Imprimir orden de entrada de inventario");

        try {

            //Nombre del reporte jasper orden de entrada
            String reportName = "orden_entrada_inventario.jrxml";

            //Parametros orden de entrada
            Map parameters = new HashMap();
            parameters.put("no_entrada", String.valueOf(ordenEntrada.getNoMovimiento()));
            parameters.put("almacen", ordenEntrada.getAlmacen().getDescripcion());
            parameters.put("fecha_alta", String.valueOf(ordenEntrada.getFechaAlta()));
            parameters.put("descripcion", String.valueOf(ordenEntrada.getDescripcion()));

            //<Tipo de entrada>
            for (TiposEntrada tipoEntrada : TiposEntrada.values()) {
                if (tipoEntrada.getValue() == (int) ordenEntrada.getTipoEntrada()) {
                    parameters.put("tipo_entrada", tipoEntrada.getNombre());
                }
            }

        } catch (Exception e) {

        }
    }

    /**
     * Obteniendo listado de ordenes de entrada inventario ordinaria
     *
     * @return List
     */
    private List<OrdenEntrada> buscarOrdenesEntrada(Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws Exception {

        logger.debug("Obteniendo listado de ordenes de entrada ordinaria...!");

        try {
            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            //Obtener manager inventario service
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Retornar listado de ordenes de ingreso ordinaria
            List<String> estados = new ArrayList<String>();
            estados.add(EstadosMovimiento.INGRESADO.getEstado());

            return mgrInventario.buscarOrdenesEntradaPorEstados(estados, fechaDesde, fechaHasta, idAlmacen);

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
    public void buscarOrdenesEntradasPorFechasAlmacen(Date fechaDesde, Date fechaHasta, Integer idAlmacen)
            throws Exception {
        try {

            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            //Obtener manager inventario service
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Retornar listado de ordenes de ingreso ordinaria
            List<String> estados = new ArrayList<String>();
            estados.add(EstadosMovimiento.INGRESADO.getEstado());

            //Buscar Orden Entrada
            List<OrdenEntrada> ordenEntradas = mgrInventario.buscarFacturasPorFechaAlmacen(estados, fechaDesde, fechaHasta, idAlmacen);
            getOrdenesEntrada().clear();
            getOrdenesEntrada().addAll(ordenEntradas);

        } catch (ManagerFacturacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}