/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.inventarios.controller;

import contac.modelo.entity.*;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller ordenes de salida
 * User: EMontenegro
 * Date: 01-06-12
 * Time: 10:39 PM
 */
public class OrdenSalidaController extends InventarioBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(OrdenSalidaController.class);

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private long noMovimiento;
    private Date fechaAlta;
    private Date fechaSolicitud;
    private Almacen almacenSalida;
    private String personaAutoriza;
    private String descripcion;
    private List<ArticuloSalida> articulos;

    private OrdenSalida ordenSalida;
    private List<OrdenSalida> ordenesSalida;

    private Date fechaDesde;
    private Date fechaHasta;
    private Almacen almacen;

    //*************************************************************************************
    //GETTERS AND SETTERS
    //*************************************************************************************

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

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Almacen getAlmacenSalida() {
        return almacenSalida;
    }

    public void setAlmacenSalida(Almacen almacenSalida) {
        this.almacenSalida = almacenSalida;
    }

    public String getPersonaAutoriza() {
        return personaAutoriza;
    }

    public void setPersonaAutoriza(String personaAutoriza) {
        this.personaAutoriza = personaAutoriza;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ArticuloSalida> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ArticuloSalida> articulos) {
        this.articulos = articulos;
    }

    public OrdenSalida getOrdenSalida() {
        return ordenSalida;
    }

    public void setOrdenSalida(OrdenSalida ordenSalida) {
        this.ordenSalida = ordenSalida;
    }

    public List<OrdenSalida> getOrdenesSalida() {
        return ordenesSalida;
    }

    public void setOrdenesSalida(List<OrdenSalida> ordenesSalida) {
        this.ordenesSalida = ordenesSalida;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    //Init values
    public void init() {

        //Editar orden de salida FALSE
        set_edit(false);

        setNoMovimiento(VALUE_INT_NOT_DEFINED);
        setFechaAlta(new Date());
        setFechaSolicitud(new Date());
        setAlmacenSalida(null);
        setPersonaAutoriza(VALUE_STRING_NOT_DEFINED);
        setDescripcion(VALUE_STRING_NOT_DEFINED);
        setArticulos(new ArrayList<ArticuloSalida>());

        try {
            //Cargar listado de almacenes
            setAlmacenes(buscarAlmacenes());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    //Init modificacion values
    public void initModificacion() {

        //Init carga de datos
        init();

        //Editar orden de salida TRUE
        set_edit(true);

        setNoMovimiento(this.ordenSalida.getNoMovimiento());
        setFechaAlta(this.ordenSalida.getFechaAlta());
        setFechaSolicitud(null);
        setAlmacenSalida(this.ordenSalida.getAlmacen());
        setPersonaAutoriza(this.ordenSalida.getPersonaAutoriza());
        setDescripcion(this.ordenSalida.getDescripcion());

        List<ArticuloSalida> articulosList = new ArrayList<ArticuloSalida>();
        articulosList.addAll(ordenSalida.getArticulos());
        setArticulos(articulosList);
    }

    //Init registro de ordenes de salida
    public void initRegistroOrdenesSalida() throws Exception {
        try {
            //Buscar ordenes de salida
            //setOrdenesSalida(buscarOrdenesSalida());

            //Setting almacenes registrados
            setAlmacenes(buscarAlmacenes());

            //Setting almacen del usuario
            setAlmacen(buscarAlmacenUsuario());
            if (getAlmacen() == null) {
                setAlmacen(getAlmacenes().get(0));
            }

            //Buscar Ordenes de Salida con fecha actual del servidor
            Date fechaSalida = buscarFechaFacturacion();

            setOrdenesSalida(buscarOrdenesSalidaInit(fechaSalida, fechaSalida, almacen.getId()));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Validar Estado de Orden de Baja a Imprimir
     *
     * @throws Exception, Exception
     */
     public void ordenBajaImprimir() throws Exception {

        logger.debug("Validar Estado de Orden de Baja");

        try {

            //Obtener manager de Inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Validar Estado de Orden de Baja
            mgrInventario.validarImpresionOrdenBaja(getOrdenSalida().getId());

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

     }

    /**
     * Obteniendo listado de ordenes de entrada inventario ordinaria
     *
     * @return List
     */
    private List<OrdenSalida> buscarOrdenesSalidaInit(Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws Exception {

        logger.debug("Obteniendo listado de ordenes de Salida ordinaria...!");

        try {
            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            //Obtener manager inventario service
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Retornar listado de ordenes de Salida ordinaria
            List<String> estados = new ArrayList<String>();
            estados.add(EstadosMovimiento.INGRESADO.getEstado());

            return mgrInventario.buscarOrdenesSalidaPorEstados(estados, fechaDesde, fechaHasta, idAlmacen);

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }


    //*************************************************************************************
    //ACTION EVENTS
    //*************************************************************************************

    /**
     * Crear orden de baja inventario
     *
     * @throws Exception, Exception
     */
    public void crearOrdenSalida() throws Exception {

        logger.debug("Crear orden de salida de inventario.");

        try {

            //Manager inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Crear registro de orden salida
            OrdenSalida ordenSalida = mgrInventario.crearOrdenSalida(getFechaAlta(), getFechaSolicitud(), getAlmacenSalida().getId(),
                    getPersonaAutoriza(), getDescripcion(), getArticulos());

            //Init orden de salida
            this.ordenSalida = ordenSalida;

            //Init Modificacion
            initModificacion();

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar orden de baja de inventario
     *
     * @throws Exception, Exception
     */
    public void modificarOrdenSalida() throws Exception {

        logger.debug("Modificar orden de salida de inventario.");

        try {

            //Obtener manager inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Modificar registro de orden de salida
            OrdenSalida ordenSalida = mgrInventario.modificarOrdenSalida(getOrdenSalida().getId(), getFechaAlta(), getFechaSolicitud(),
                    getPersonaAutoriza(), getDescripcion(), getArticulos());

            //Init orden de salida
            this.ordenSalida = ordenSalida;

            //Init Modificacion
            initModificacion();

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Agregar articulo de salida
     *
     * @param producto,      Producto
     * @param almacenSalida, Almacen
     * @param cantidad,      long
     * @throws Exception, Exception
     */
    public void agregarArticulo(Producto producto, Almacen almacenSalida, long cantidad) throws Exception {

        logger.debug("Agregando articulo Salida");

        try {

            //Creando articulo para salida
            ArticuloSalida articulo = null;

            //Buscando articulo en listado
            for (ArticuloSalida entity : getArticulos()) {
                if (entity.getCodigo().equals(producto.getCodigo())) {
                    if (entity.getCantidadAnterior() <= 0) {
                        entity.setCantidadAnterior(entity.getCantidad());
                    }

                    articulo = entity;
                }
            }

            //Validar existencias producto
            ProductoExistencia productoExistencia = buscarProductoExistencia(producto.getCodigo(), almacenSalida.getId());

            //Actualizar existencias con la cantidad previamente reservada
            if (articulo != null) {
                productoExistencia.setExistencia(productoExistencia.getExistencia() + articulo.getCantidadAnterior());
            }

            //Throws error existencias insuficientes
            if (productoExistencia.getExistencia() < cantidad)
                throw new Exception("Existencias insuficientes para este producto.");

            if (articulo != null) {
                articulo.setCodigo(producto.getCodigo());
                articulo.setNombre(producto.getNombre());
                articulo.setCodigoFabricante(producto.getCodigoFabricante());
                articulo.setCosto(producto.getCostoPROM());
                articulo.setCantidad(cantidad);
                articulo.setCostoTotal(articulo.getCosto().multiply(new BigDecimal(cantidad)));
                articulo.setUnidadMedida(producto.getUnidadMedida().getNombre());
                articulo.setProducto(producto);
                articulo.setCreate(false);
                articulo.setUpdate(true);
            } else {
                articulo = new ArticuloSalida();
                articulo.setCodigo(producto.getCodigo());
                articulo.setNombre(producto.getNombre());
                articulo.setCodigoFabricante(producto.getCodigoFabricante());
                articulo.setCosto(producto.getCostoPROM());
                articulo.setCantidad(cantidad);
                articulo.setCantidadAnterior(0);
                articulo.setCostoTotal(articulo.getCosto().multiply(new BigDecimal(cantidad)));
                articulo.setUnidadMedida(producto.getUnidadMedida().getNombre());
                articulo.setProducto(producto);
                articulo.setCreate(true);
                articulo.setUpdate(false);

                //Adding articulo
                getArticulos().add(articulo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar ordenes de salida por rangos de fecha
     *
     * @param fechaDesde, Fecha desde inicia la busqueda
     * @param fechaHasta, Fecha hasta finaliza la busqueda
     * @throws Exception, Exception
     */
     public void buscarOrdenesSalida(Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws Exception {
        logger.debug("Buscar ordenes de salida por rangos de fecha");

        try {
            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            //Obteniendo manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Retornar listado de ordenes de salida
            List<String> estados = new ArrayList<String>();
            estados.add(EstadosMovimiento.INGRESADO.getEstado());

            //Buscar ordenes de salida por rangos de fecha
            List<OrdenSalida> ordenSalidas = mgrInventario.buscarOrdnesSalidaPorRangosFechas(estados, fechaDesde, fechaHasta, idAlmacen);
            getOrdenesSalida().clear();
            getOrdenesSalida().addAll(ordenSalidas);

            //return mgrInventario.buscarOrdnesSalidaPorRangosFechas(estados, fechaDesde, fechaHasta, idAlmacen);
            /*//Actualizar registro de ordenes de salida
            getOrdenesSalida().clear();
            getOrdenesSalida().addAll(ordenesSalida);*/

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }

    //*************************************************************************************
    //PRIVATE METHODS
    //*************************************************************************************

    /**
     * Buscar ordenes de salida ingresada
     *
     * @return List
     * @throws Exception, Exception
     */
 /*   private List<OrdenSalida> buscarOrdenesSalida() throws Exception {

        logger.debug("Buscando ordenes de salida del periodo");

        try {

            //Obteniendo manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Buscar ordenes de salida por estados
            List<String> estados = new ArrayList<String>();
            estados.add(EstadosMovimiento.INGRESADO.getEstado());

            //Retornar listado encontrado
            List<OrdenSalida> ordenesSalida = mgrInventario.buscarOrdenesSalidaPorEstados(estados, fechaDesde, fechaHasta, idAlmacen);

            if (ordenesSalida == null) {
                ordenesSalida = new ArrayList<OrdenSalida>();
            }

            return ordenesSalida;

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
*/


}
