/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.inventarios.controller;

import contac.internationalization.LanguageLocale;
import contac.modelo.entity.*;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.*;

/**
 * @author Alex
 *         Administración Levantamiento Inventario Físico controller
 *         Date: 25-05-2012
 */
public class OrdenLevantamientoController extends InventarioBaseController {

    //Apache Log4j
    private static final Logger logger = Logger.getLogger(OrdenLevantamientoController.class);

    //Message Resource Bundle
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
    private List<OrdenLevantamientoFisico> ordenesLevantamiento;
    private List<ArticuloLevantamientoFisico> articulos;
    private OrdenLevantamientoFisico ordenLevantamiento;
    private TipoActualizacionFisico tipoActualizacionFisico;
    private Almacen almacen;

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

    public long getNoMovimiento() {
        return noMovimiento;
    }

    public void setNoMovimiento(long noMovimiento) {
        this.noMovimiento = noMovimiento;
    }

    public OrdenLevantamientoFisico getOrdenLevantamiento() {
        return ordenLevantamiento;
    }

    public void setOrdenLevantamiento(OrdenLevantamientoFisico ordenLevantamiento) {
        this.ordenLevantamiento = ordenLevantamiento;
    }

    public List<ArticuloLevantamientoFisico> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ArticuloLevantamientoFisico> articulos) {
        this.articulos = articulos;
    }

    public List<OrdenLevantamientoFisico> getOrdenesLevantamiento() {
        return ordenesLevantamiento;
    }

    public void setOrdenesLevantamiento(List<OrdenLevantamientoFisico> ordenesLevantamiento) {
        this.ordenesLevantamiento = ordenesLevantamiento;
    }

    public TipoActualizacionFisico getTipoActualizacionFisico() {
        return tipoActualizacionFisico;
    }

    public void setTipoActualizacionFisico(TipoActualizacionFisico tipoActualizacionFisico) {
        this.tipoActualizacionFisico = tipoActualizacionFisico;
    }

    //init values
    public void init() {

        logger.debug("Iniciando carga de datos controller");

        //Editar orden de Entrada
        set_edit(false);

        setNoMovimiento(VALUE_INT_ZERO_DEFINED);
        setFechaAlta(new Date());
        setFechaSolicitud(new Date());
        setMoneda(null);
        setMontoTotal(VALUE_INT_NOT_DEFINED);
        setArticulos(new ArrayList<ArticuloLevantamientoFisico>());
        setTipoActualizacionFisico(null);
        setAlmacen(null);
        setDescripcion("");

        try {
            //Cargar listado de almacenes
            setAlmacenes(buscarAlmacenes());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        }
    }

    //Init modification values
    public void initModificacion() {

        logger.debug("Iniciando carga de datos para modificador controller");
        //Init values
        init();

        //Editar orden de entrada
        set_edit(true);

        setNoMovimiento(ordenLevantamiento.getNoMovimiento());
        setFechaAlta(ordenLevantamiento.getFechaAlta());
        setFechaSolicitud(ordenLevantamiento.getFechaSolicitud());
        setDescripcion(ordenLevantamiento.getDescripcion());
        setMoneda(ordenLevantamiento.getMoneda());
        setMontoTotal(ordenLevantamiento.getMontoTotal().doubleValue());
        setAlmacen(ordenLevantamiento.getAlmacen());

        //<Articulos>
        List<ArticuloLevantamientoFisico> articulosList = new ArrayList<ArticuloLevantamientoFisico>();
        articulosList.addAll(ordenLevantamiento.getArticulos());

        //<Tipo de entrada>
        for (TipoActualizacionFisico tipoActualizacion : TipoActualizacionFisico.values()) {
            if (tipoActualizacion.getValue() == ordenLevantamiento.getTipoActualizacion()) {
                setTipoActualizacionFisico(tipoActualizacion);
            }
        }

        //Ordenar listado de articulos
        Collections.sort(articulosList, TiposOrdenamientoArticulo.PorCodigo);

        setArticulos(articulosList);
    }

    //Init registros de ordenes de Levantamiento inventario fisico
    public void initRegistrosOrdenesEntradaInventario() throws Exception {
        setOrdenesLevantamiento(buscarOrdenesLevantamiento());
    }
    //*************************************************************************************
    //ACTION EVENTS
    //*************************************************************************************

    /**
     * Crear encabezado de orden de entrada
     *
     * @throws Exception, Exception
     */
    public void crearOrdenLevantamiento() throws Exception {

        logger.debug("Crear orden de Levantamiento de inventario Físico.");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Creamos registro de ingreso
            OrdenLevantamientoFisico ordenLevantamientoFisico = mgrInventario.crearOrdenLevantamientoFisico(getFechaAlta(),
                    null, getAlmacen().getId(), getDescripcion(), getArticulos());

            //Init orden de levantamiento fisico
            this.ordenLevantamiento = ordenLevantamientoFisico;

            //Init Modificacion
            initModificacion();

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar orden de Levantamiento Inventario
     *
     * @throws Exception, Exception
     */
    public void modificarOrdenLevantantamientoFisico() throws Exception {

        logger.debug("Modificar orden de Levantamiento Inventario Físico.");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Modificar registro
            OrdenLevantamientoFisico ordenLevantamientoFisico = mgrInventario.modificarOrdenLevantamientoFisico(
                    getOrdenLevantamiento().getId(), getFechaAlta(), getFechaSolicitud(), getDescripcion(), getArticulos());

            //Init orden de levantamiento fisico
            this.ordenLevantamiento = ordenLevantamientoFisico;

            //Init Modificacion
            initModificacion();

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Imprimir la Orden de Levantamiento de Inventario
     *
     * @throws Exception, Exception
     */

    public void ordenLevantamientoImprimir() throws Exception {

        logger.debug("Validar Estado de Orden de Levantamiento de Inventario");

        try {

            //Obtener manager de Inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Validar Estado de Orden de Levantamiento de Inventario
            mgrInventario.validarImpresionOrdenLevantamiento(getOrdenLevantamiento().getId());

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }

     /**
     * Buscar el Estado del Ajuste
     *
     * @throws Exception, Exception
     */

    public void estadoAjusteInventario() throws Exception {

        logger.debug("Buscar Estado del Ajuste");

        try {

            //Obtener manager de Inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Buscar estado del Ajuste
            mgrInventario.buscarEstadoAjuste(getOrdenLevantamiento().getId());

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

     /**
     * Buscar Ordenes de Levantamiento Inventario Físico por Fechas y Almacen
     * @param fechaDesde, Fecha desde donde inicia la busqueda
     * @param fechaHasta, Fecha donde termina la busqueda
     * @param idAlmacen, Almacen donde se hara el Levantamiento de Inventario
     * @throws Exception, Exception
     */
     public void buscarOrdenesLevantamientoFisico(Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws Exception {

        logger.debug("Buscando Registros de Levantamiento de Inventario por rangos de fecha");

        try {

            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            if (idAlmacen == null)
                idAlmacen = -1;

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Buscar ordenes de levantamiento de Inventario por rango de fechas
            List<OrdenLevantamientoFisico> ordenesLevantamiento = mgrInventario.buscarOrdenesLevantamientoFisicoPorFechas(fechaDesde, fechaHasta, idAlmacen);

            //Setting ordenes de Levantamiento de Inventario
            getOrdenesLevantamiento().clear();
            getOrdenesLevantamiento().addAll(ordenesLevantamiento);

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Remover orden de Levantamiento de Inventario Físico
     *
     * @throws Exception, Exception
     */
    public void removerOrdenLevantamientoFisico() throws Exception {

        logger.debug("Remover orden de Levantamiento Físico.");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Remover registro de orden de Levantamiento Fisico
            mgrInventario.eliminarOrdenLevantamientoFisico(ordenLevantamiento.getId());

            //Actualizar registro de Levantamiento Fisico
            getOrdenesLevantamiento().clear();
            getOrdenesLevantamiento().addAll(buscarOrdenesLevantamiento());

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Anular orden de Levantamiento de Inventario Fisico
     *
     * @throws Exception, Exception
     */
    public void anularOrdenLevantamientoFisico() throws Exception {

        logger.debug("Anular orden de Levantamiento Físico.");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Anular registro de orden de Levantamiento Fisico
            mgrInventario.anularOrdenLevantamientoFisico(ordenLevantamiento.getId());

            //Actualizar registro de Levantamiento Fisico
            getOrdenesLevantamiento().clear();
            getOrdenesLevantamiento().addAll(buscarOrdenesLevantamiento());

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Calcular ajuste levantamiento inventario
     *
     * @throws Exception, Exception
     */
    public void calcularAjusteLevantamientoInventario() throws Exception {

        logger.debug("Calculando ajuste levantamiento inventario fisico");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Calcular ajuste levantamiento inventario
            OrdenLevantamientoFisico ordenLevantamientoFisico = mgrInventario.calcularAjusteLevantamientoInventario(
                    getOrdenLevantamiento().getId());

            setOrdenLevantamiento(ordenLevantamientoFisico);

            //Actualizar registro de articulos
            getArticulos().clear();

            //Ordenar listado de articulos
            List<ArticuloLevantamientoFisico> articulosList = new ArrayList<ArticuloLevantamientoFisico>();
            articulosList.addAll(ordenLevantamientoFisico.getArticulos());
            Collections.sort(articulosList, TiposOrdenamientoArticulo.PorCodigo);

            getArticulos().addAll(articulosList);

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Aplicar ajuste levantamiento inventario
     *
     * @throws Exception, Exception
     */
    public void aplicarAjusteLevantamientoInventario() throws Exception {
        
        logger.debug("Aplicar ajuste levantamiento inventario");
        
        try {
            
            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();
            
            //Aplicar ajuste levantamiento inventario
            OrdenLevantamientoFisico ordenLevantamientoFisico = mgrInventario.aplicarAjusteLevantamientoInventario(
                    getOrdenLevantamiento().getId(), getTipoActualizacionFisico().getValue());
            
            setOrdenLevantamiento(ordenLevantamientoFisico);
               
        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Agregar articulo a orden de Levantamiento Físico
     *
     * @param producto, Producto
     * @param cantidad, int
     */
    public void agregarArticulo(Producto producto, int cantidad) throws Exception {

        logger.debug("Agregando Articulo Levantamiento Físico.");

        try {

            //Crear articulo
            ArticuloLevantamientoFisico articulo = null;

            //Buscar articulo en listado ingresado
            for (ArticuloLevantamientoFisico entity : getArticulos()) {
                if (entity.getCodigo().equals(producto.getCodigo()))
                    articulo = entity;
            }

            if (articulo != null) {
                articulo.setCodigo(producto.getCodigo());
                articulo.setNombre(producto.getNombre());
                articulo.setCodigoFabricante(producto.getCodigoFabricante());
                articulo.setModelo(producto.getModelo());
                articulo.setCosto(producto.getCostoPROM());
                articulo.setCantidad(cantidad);
                articulo.setCostoTotal(articulo.getCosto().multiply(new BigDecimal(cantidad)));
                articulo.setUnidadMedida(producto.getUnidadMedida().getNombre());
                articulo.setProducto(producto);
                articulo.setCreate(false);
                articulo.setUpdate(true);
            } else {
                articulo = new ArticuloLevantamientoFisico();
                articulo.setCodigo(producto.getCodigo());
                articulo.setNombre(producto.getNombre());
                articulo.setCodigoFabricante(producto.getCodigoFabricante());
                articulo.setModelo(producto.getModelo());
                articulo.setCosto(producto.getCostoUND());
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
     * Obteniendo listado de ordenes de Levantamiento de inventario
     *
     * @return List
     */
    private List<OrdenLevantamientoFisico> buscarOrdenesLevantamiento() throws Exception {

        logger.debug("Obteniendo listado de ordenes de levantamiento fisico...!");

        try {

            //Obtener manager inventario service
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Retornar listado de ordenes de ingreso ordinaria
            List<String> estados = new ArrayList<String>();
            estados.add(EstadosMovimiento.INGRESADO.getEstado());
            estados.add(EstadosMovimiento.CALCULADO.getEstado());

            return mgrInventario.buscarOrdenesLevantamientoFisicoPorEstados(estados);

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
