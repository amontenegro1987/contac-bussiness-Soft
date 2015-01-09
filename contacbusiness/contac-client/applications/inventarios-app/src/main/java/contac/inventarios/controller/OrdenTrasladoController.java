/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.inventarios.controller;

import com.sun.corba.se.impl.presentation.rmi.ExceptionHandlerImpl;
import contac.internationalization.LanguageLocale;
import contac.modelo.entity.*;
import contac.servicio.inventario.ManagerInventarioServiceBusiness;
import contac.servicio.inventario.ManagerInventarioServiceBusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller orden de traslado
 * User: EMontenegro
 * Date: 01-06-12
 * Time: 10:39 PM
 */
public class OrdenTrasladoController extends InventarioBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(OrdenTrasladoController.class);

    //Message resource bundle
    private ResourceBundle messageBundle = ResourceBundle.getBundle("contac/inventarios/app/mensajes/Mensajes",
            LanguageLocale.getInstance().getLocale());

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private long noMovimiento;
    private Date fechaAlta;
    private Date fechaSolicitud;
    private Almacen almacenSalida;
    private Almacen almacenIngreso;
    private String personaEntrega;
    private String personaRecibe;
    private String descripcion;
    private List<ArticuloTraslado> articulos;
    private Almacen almacen;

    private OrdenTraslado ordenTraslado;
    private List<OrdenTraslado> ordenesTraslado;

    private Date fechaDesde;
    private Date fechaHasta;

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

    public Almacen getAlmacenIngreso() {
        return almacenIngreso;
    }

    public void setAlmacenIngreso(Almacen almacenIngreso) {
        this.almacenIngreso = almacenIngreso;
    }

    public String getPersonaEntrega() {
        return personaEntrega;
    }

    public void setPersonaEntrega(String personaEntrega) {
        this.personaEntrega = personaEntrega;
    }

    public String getPersonaRecibe() {
        return personaRecibe;
    }

    public void setPersonaRecibe(String personaRecibe) {
        this.personaRecibe = personaRecibe;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ArticuloTraslado> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ArticuloTraslado> articulos) {
        this.articulos = articulos;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public OrdenTraslado getOrdenTraslado() {
        return ordenTraslado;
    }

    public void setOrdenTraslado(OrdenTraslado ordenTraslado) {
        this.ordenTraslado = ordenTraslado;
    }

    public List<OrdenTraslado> getOrdenesTraslado() {
        return ordenesTraslado;
    }

    public void setOrdenesTraslado(List<OrdenTraslado> ordenesTraslado) {
        this.ordenesTraslado = ordenesTraslado;
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

        //Editar orden de traslado FALSE
        set_edit(false);

        setNoMovimiento(VALUE_INT_ZERO_DEFINED);
        setFechaAlta(new Date());
        setFechaSolicitud(new Date());
        setAlmacenSalida(null);
        setAlmacenIngreso(null);
        setPersonaEntrega(VALUE_STRING_NOT_DEFINED);
        setPersonaRecibe(VALUE_STRING_NOT_DEFINED);
        setDescripcion(VALUE_STRING_NOT_DEFINED);
        setArticulos(new ArrayList<ArticuloTraslado>());

        try {

            //Cargar listado de almacenes
            setAlmacenes(buscarAlmacenes());


            //Setting almacen del usuario
            setAlmacen(buscarAlmacenUsuario());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    //Init modificacion values
    public void initModificacion() {

        //Init carga datos
        init();

        //Editar orden de traslado TRUE
        set_edit(true);

        setNoMovimiento(this.ordenTraslado.getNoMovimiento());
        setFechaAlta(this.ordenTraslado.getFechaAlta());
        setFechaSolicitud(null);
        setAlmacenSalida(this.ordenTraslado.getAlmacenSalida());
        setAlmacenIngreso(this.ordenTraslado.getAlmacenEntrada());
        setPersonaEntrega(this.ordenTraslado.getPersonaEntrega());
        setPersonaRecibe(this.ordenTraslado.getPersonaRecibe());
        setDescripcion(this.ordenTraslado.getDescripcion());

        List<ArticuloTraslado> articulosList = new ArrayList<ArticuloTraslado>();
        articulosList.addAll(ordenTraslado.getArticulos());
        setArticulos(articulosList);
    }

    //Init registro ordenes traslado
    public void initRegistroOrdenesTraslado() {

        try {
            //Setting almacenes registrados
            setAlmacenes(buscarAlmacenes());

            //Setting almacen del usuario
            setAlmacen(buscarAlmacenUsuario());
            if (getAlmacen() == null) {
                setAlmacen(getAlmacenes().get(0));
            }

            //Buscar registro de facturas con fecha actual del servidor
            Date fechaFacturacion = buscarFechaFacturacion();

            setOrdenesTraslado(buscarOrdenesTraslado(fechaFacturacion, fechaFacturacion, almacen.getId()));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    //*************************************************************************************
    //ACTION EVENTS
    //*************************************************************************************

    /**
     * Crear orden de traslado
     *
     * @throws Exception, Exception
     */
    public void crearOrdenTraslado() throws Exception {

        logger.debug("Crear orden de traslado de inventario");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Crear registro orden traslado
            OrdenTraslado ordenTraslado = mgrInventario.crearOrdenTraslado(getFechaAlta(), getFechaSolicitud(), getAlmacenSalida().getId(),
                    getAlmacenIngreso().getId(), getPersonaEntrega(), getPersonaRecibe(), getDescripcion(), getArticulos());

            //Setting articulos persist
            this.ordenTraslado = ordenTraslado;

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
    //*************************************************************************************
    //ACTION EVENTS
    //*************************************************************************************

    /**
     * Validar Estado de Orden de Traslado a Imprimir
     *
     * @throws Exception, Exception
     */

    public void ordenTrasladoImprimir() throws Exception {

        logger.debug("Validar Estado de Orden de Traslado");

        try {

            //Obtener manager de Inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Validar Estado de Orden de Traslado
            mgrInventario.validarImpresionOrdenTraslado(getOrdenTraslado().getId());

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }

    /**
     * Aplicar orden de traslado
     *
     * @throws Exception, Exception
     */
    public void aplicarOrdenTraslado() throws Exception {

        logger.debug("Aplicando Orden de Traslado de inventario");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Cambiar Estado de Orden de Traslado
            mgrInventario.aplicarTraslado(getOrdenTraslado().getId());

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
    /**
     * Modificar orden de traslado
     *
     * @throws Exception, Exception
     */
    public void modificarOrdenTraslado() throws Exception {

        logger.debug("Modificar orden de traslado de inventario");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Modificar registro orden traslado
            OrdenTraslado ordenTraslado = mgrInventario.modificarOrdenTraslado(getOrdenTraslado().getId(), getFechaAlta(),
                    getFechaSolicitud(), getPersonaEntrega(), getPersonaRecibe(), getDescripcion(), getArticulos());

            //Setting articulos persist
            this.ordenTraslado = ordenTraslado;

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
     * Anular orden de traslado
     *
     * @throws Exception, Exception
     */
    public void anularOrdenTraslado() throws Exception {

        logger.debug("Anular orden de traslado de inventario");

        try {

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Anular orden de traslado de inventario
            mgrInventario.anularOrdenTraslado(getOrdenTraslado().getId());

            //Init registro de ordenes de traslado
            initRegistroOrdenesTraslado();

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Agregar articulo traslado
     *
     * @param producto, Producto
     * @param almacen,  Almacen
     * @param cantidad, long
     * @throws Exception, Exception
     */
    public void agregarArticulo(Producto producto, Almacen almacen, long cantidad) throws Exception {

        logger.debug("Agregando articulo traslado");

        try {

            //Creando articulo para traslado
            ArticuloTraslado articulo = null;

            //Buscando articulo en listado
            for (ArticuloTraslado entity : getArticulos()) {
                if (entity.getCodigo().equals(producto.getCodigo())) {
                    if (entity.getCantidadAnterior() <= 0) {
                        entity.setCantidadAnterior(entity.getCantidad());
                    }

                    articulo = entity;
                }
            }

            //Validar existencias producto
            ProductoExistencia productoExistencia = buscarProductoExistencia(producto.getCodigo(), almacen.getId());

            //Actualizar existencias con la cantidad previamente reservada
            if (articulo != null) {
                productoExistencia.setExistencia(productoExistencia.getExistencia() + articulo.getCantidadAnterior());
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
                articulo = new ArticuloTraslado();
                articulo.setCodigo(producto.getCodigo());
                articulo.setNombre(producto.getNombre());
                articulo.setCodigoFabricante(producto.getCodigoFabricante());
                articulo.setModelo(producto.getModelo());
                articulo.setCosto(producto.getCostoPROM());
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
            logger.error(e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar Ordenes de Traslado por rangos de fechas
     *
     * @param fechaDesde, Fecha desde donde inicia la busqueda
     * @param fechaHasta, Fecha donde termina la busqueda
     * @throws Exception, Exception
     */
    public void buscarOrdenesTraslado(Date fechaDesde, Date fechaHasta, Integer idAlmacen, Integer idAlmacenSalida) throws Exception {

        logger.debug("Buscando ordenes de traslado por rangos de fecha");

        try {

            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            if (idAlmacen == null)
                idAlmacen = -1;

            if (idAlmacenSalida == null)
                idAlmacenSalida = -1;
            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Buscar ordenes de traslado por rangos de fecha
            List<OrdenTraslado> ordenesTraslado = mgrInventario.buscarOrdenesTrasladoPorRangosFechas(fechaDesde, fechaHasta, idAlmacen, idAlmacenSalida);

            //Setting ordenes de traslado
            getOrdenesTraslado().clear();
            getOrdenesTraslado().addAll(ordenesTraslado);

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
     * Buscar ordenes de traslado del periodo
     *
     * @return List
     * @throws Exception, Exception
     */
    private List<OrdenTraslado> buscarOrdenesTraslado(Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws Exception {

        logger.debug("Buscando ordenes de traslado del periodo");

        try {
            //Validar que los campos de fechas no sean nulos
            if (fechaDesde == null)
                throw new Exception("Debe ingresar una fecha de inicio de busqueda: [fecha desde]");

            if (fechaHasta == null)
                throw new Exception("Debe ingresar una fecha de fin de busqueda: [fecha hasta]");

            //Obtener manager de inventario
            ManagerInventarioServiceBusiness mgrInventario = getMgrInventarioService();

            //Buscar ordenes de traslado por estados
            List<String> estados = new ArrayList<String>();

            //04-26-2014 Se quita el estado Ingresado para que solo aparezcan los pendientes al momento de ingresar por primera vez a la pantalla
            //estados.add(EstadosMovimiento.INGRESADO.getEstado());

            estados.add(EstadosMovimiento.PENDIENTE.getEstado());

            //Retornar listado encontrado
            List<OrdenTraslado> ordenesTraslado = mgrInventario.buscarOrdenesTrasladoPorEstados(estados, fechaDesde, fechaHasta, idAlmacen);
            if (ordenesTraslado == null) {
                ordenesTraslado = new ArrayList<OrdenTraslado>();
            }

            return ordenesTraslado;

        } catch (ManagerInventarioServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }


}
