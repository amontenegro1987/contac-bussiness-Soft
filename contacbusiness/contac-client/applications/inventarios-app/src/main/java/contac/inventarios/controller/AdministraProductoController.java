package contac.inventarios.controller;

import contac.commons.form.label.JErrorLabel;
import contac.modelo.entity.*;
import contac.servicio.catalogo.ManagerCatalogoServiceBusiness;
import contac.servicio.catalogo.ManagerCatalogoServiceBusinessException;
import contac.servicio.catalogo.ManagerProductoServiceBusiness;
import contac.servicio.catalogo.ManagerProductoServiceBusinessException;
import contac.servicio.proveedores.ManagerProveedoresServiceBusinessException;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: emontenegro
 * Date: 8/23/11
 * Time: 12:14 AM
 */
public class AdministraProductoController extends InventarioBaseController {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(AdministraProductoController.class);

    //*************************************************************************************
    //PROPERTIES BEAN FORM
    //*************************************************************************************
    private String codigo;
    private boolean compuesto;
    private String nombre;
    private String codigoFabricante;
    private String marca;
    private String modelo;
    private String alias;
    private long codigoCBS;
    private Clasificador clasificador;
    private UnidadMedida unidadMedida;
    private Proveedor proveedor;
    private Linea linea;
    private boolean exento;
    private String observaciones;
    private long minimo;
    private long maximo;
    private BigDecimal costoFOB;
    private BigDecimal costoCIF;
    private BigDecimal costoUND;
    private BigDecimal costoPROM;
    private BigDecimal precioEstandar;
    private BigDecimal precioPromocion;
    private BigDecimal descuento;
    private List<ProductoCompuesto> productos;
    private List<ProductoCompuestoFacade> productosFacade;
    private byte[] fotografia;
    private Date fechaVencimiento;
    private Producto producto;
    private Pais paisOrigen;

    //Init values
    public void init() {

        //Editar producto
        set_edit(false);

        setCodigo(VALUE_STRING_NOT_DEFINED);
        setCompuesto(false);
        setNombre(VALUE_STRING_NOT_DEFINED);
        setCodigoFabricante(VALUE_STRING_NOT_DEFINED);
        setMarca(VALUE_STRING_NOT_DEFINED);
        setModelo(VALUE_STRING_NOT_DEFINED);
        setAlias(VALUE_STRING_NOT_DEFINED);
        setCodigoCBS(VALUE_INT_NOT_DEFINED);
        setClasificador(null);
        setUnidadMedida(null);
        setProveedor(null);
        setLinea(null);
        setExento(false);
        setObservaciones(VALUE_STRING_NOT_DEFINED);
        setMinimo(VALUE_INT_NOT_DEFINED);
        setMaximo(VALUE_INT_NOT_DEFINED);
        setCostoFOB(null);
        setCostoCIF(null);
        setCostoUND(null);
        setCostoPROM(null);
        setPrecioEstandar(null);
        setPrecioPromocion(null);
        setDescuento(null);
        setProductos(new ArrayList<ProductoCompuesto>());
        setProductosFacade(new ArrayList<ProductoCompuestoFacade>());
        setFotografia(null);
        setPaisOrigen(null);
    }

    //Init Modificacion
    public void initModificacion() {

        //Editar producto
        set_edit(true);

        setCodigo(this.producto.getCodigo());
        setCompuesto(this.producto.isCompuesto());
        setNombre(this.producto.getNombre());
        setCodigoFabricante(this.producto.getCodigoFabricante());
        setMarca(this.producto.getMarca());
        setModelo(this.producto.getModelo());
        setAlias(this.producto.getAlias());
        setCodigoCBS(this.producto.getCodigoCbs());
        setClasificador(this.producto.getClasificador());
        setUnidadMedida(this.producto.getUnidadMedida());
        setProveedor(this.producto.getProveedor());
        setLinea(this.producto.getLinea());
        setPaisOrigen(this.producto.getPaisOrigen());
        setExento(this.producto.isExento());
        setObservaciones(this.producto.getObservaciones());
        setMinimo(this.producto.getMinimo());
        setMaximo(this.producto.getMaximo());
        setCostoFOB(this.producto.getCostoFOB());
        setCostoCIF(this.producto.getCostoCIF());
        setCostoUND(this.producto.getCostoUND());
        setCostoPROM(this.producto.getCostoPROM());
        setPrecioEstandar(this.producto.getPrecioESTANDAR());
        setDescuento(this.producto.getDescuento());
        //<Productos compuestos>
        List<ProductoCompuesto> productosList = new ArrayList<ProductoCompuesto>();
        productosList.addAll(this.producto.getProductosCompuestos());
        setProductos(productosList);
        //<Productos compuestos facade>
        prepararListadoProductosFacade(productosList);
        //<Fotografia>
        setFotografia(this.producto.getFotografia() != null ? this.producto.getFotografia().getImage() : null);
    }

    /**
     * Buscar Producto
     *
     * @return boolean
     * @throws Exception, Exception
     */
    public boolean buscarProducto() throws Exception {

        logger.debug("Buscando producto...");

        try {

            //Obtener manager de productos
            ManagerProductoServiceBusiness mgrProducto = getMgrProductosService();

            //Verificar si producto se encuentra registrado
            boolean buscar = mgrProducto.isProductoParaRegistro(this.codigo);

            if (!buscar) {

                //Buscar proveedor por codigo
                this.producto = mgrProducto.buscarProductoPorCodigo(this.codigo);

                //Init Modificacion
                this.initModificacion();
            }

            return buscar;

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar clasificador por codigo CBS
     *
     * @throws Exception, Exception
     */
    public void buscarClasificador() throws Exception {

        logger.debug("Buscar clasificador");

        try {

            //Obtener manager para busqueda de clasificador
            ManagerCatalogoServiceBusiness mgrCatalogo = getMgrCatalogoService();

            //Obteniendo clasificador por codigo CBS
            Clasificador clasificador = mgrCatalogo.buscarClasificadorPorCodigoCBS(this.codigoCBS);

            //Setting clasificador encontrado
            setClasificador(clasificador);

        } catch (ManagerCatalogoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Registrar nuevo producto
     *
     * @throws Exception, Exception
     */
    public void crearProducto() throws Exception {

        logger.debug("Registrar nuevo producto");

        try {

            //Convertir listado de productos
            Set<ProductoCompuesto> productosSet = new HashSet<ProductoCompuesto>();
            productosSet.addAll(productos);

            //Obtener manager de productos
            ManagerProductoServiceBusiness mgrProducto = getMgrProductosService();

            //Crear producto
            mgrProducto.crearProducto(this.codigo, this.compuesto, this.nombre, this.codigoFabricante, this.alias,
                    this.marca, this.modelo, this.observaciones, this.minimo, this.maximo, this.costoUND, this.costoCIF,
                    this.costoFOB, this.descuento, this.exento, this.precioEstandar, this.unidadMedida, this.proveedor,
                    this.clasificador, this.linea, this.paisOrigen, this.fotografia, productosSet);

            //Init values
            this.init();

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Modificar producto
     *
     * @throws Exception, Exception
     */
    public void modificarProducto() throws Exception {

        logger.debug("Modificar producto");

        try {

            //Convertir listado de productos
            Set<ProductoCompuesto> productosSet = new HashSet<ProductoCompuesto>();
            productosSet.addAll(getProductos());

            //Obtener manager de productos
            ManagerProductoServiceBusiness mgrProducto = getMgrProductosService();
            System.out.println("Fotografia N2: " + this.fotografia);

            //Modificar producto
            mgrProducto.modificarProducto(producto.getId(), this.codigo, this.compuesto, this.nombre, this.codigoFabricante,
                    this.alias, this.marca, this.modelo, this.observaciones, this.minimo, this.maximo, this.costoUND,
                    this.costoCIF, this.costoFOB, this.costoPROM, this.descuento, this.exento, this.precioEstandar,
                    this.unidadMedida, this.proveedor, this.clasificador, this.linea, this.paisOrigen, this.fotografia, productosSet);

            //Init values
            init();

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Recodificar Producto a un codigo nuevo
     *
     * @param codigoNuevo, Codigo Nuevo
     * @throws Exception, Exception
     */
    public void recodificarProducto(String codigoNuevo) throws Exception {

        logger.debug("Recodificar producto");

        try {

            //Obtener manager de productos
            ManagerProductoServiceBusiness mgrProducto = getMgrProductosService();

            //Recodificar producto
            mgrProducto.recodificarProducto(producto.getCodigo(), codigoNuevo);

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Eliminar Producto sin movimientos de inventario
     *
     * @throws Exception, Exception
     */
    public void eliminarProducto() throws Exception {

        logger.debug("Eliminando producto");

        try {

            //Obtener manager de productos
            ManagerProductoServiceBusiness mgrProducto = getMgrProductosService();

            //Eliminar producto
            mgrProducto.eliminarProducto(producto.getId());

        } catch (ManagerProductoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Agregar producto compuesto
     *
     * @param producto,       Producto
     * @param precioEstandar, double
     * @param cantidad,       long
     * @throws Exception, Exception
     */
    public void agregarProductoCompuesto(Producto producto, double precioEstandar, long cantidad) throws Exception {

        logger.debug("Agregando producto compuesto");

        //Producto compuesto
        ProductoCompuesto productoCompuesto = null;

        for (ProductoCompuesto entity : getProductos()) {
            if (entity.getProducto().getCodigo().equalsIgnoreCase(producto.getCodigo()))
                productoCompuesto = entity;
        }

        if (productoCompuesto != null) {
            //Actualizar producto compuesto
            productoCompuesto.setCantidad(cantidad);
            productoCompuesto.setPrecio(new BigDecimal(precioEstandar).setScale(BigDecimal.ROUND_HALF_EVEN, 4));
            productoCompuesto.setPrecioTotal(new BigDecimal(precioEstandar).multiply(new BigDecimal(cantidad)).
                    setScale(BigDecimal.ROUND_HALF_EVEN, 4));

            //Cantidad menor o igual a zero remover producto del listado.
            if (cantidad <= 0) {
                getProductos().remove(productoCompuesto);
            }

        } else {

            //Validar que la cantidad no sea zero o menor que zero
            if (cantidad <= 0)
                throw new Exception("Ingresa una CANTIDAD mayor que ZERO.");

            //Crear producto compuesto
            productoCompuesto = new ProductoCompuesto();
            productoCompuesto.setProducto(producto);
            productoCompuesto.setCantidad(cantidad);
            productoCompuesto.setPrecio(new BigDecimal(precioEstandar).setScale(BigDecimal.ROUND_HALF_EVEN, 4));
            productoCompuesto.setPrecioTotal(new BigDecimal(precioEstandar).multiply(new BigDecimal(cantidad)).
                    setScale(BigDecimal.ROUND_HALF_EVEN, 4));

            //Actualizando listado
            getProductos().add(productoCompuesto);
        }

        //Preparar productos compuestos facade
        prepararListadoProductosFacade(getProductos());
    }

    /**
     * Preparar listado de  productos facade
     *
     * @param productos, List<ProductoCompuesto>
     */
    private void prepararListadoProductosFacade(List<ProductoCompuesto> productos) {

        //Limpiar listado de productos
        getProductosFacade().clear();

        for (ProductoCompuesto entity : productos) {
            ProductoCompuestoFacade producto = new ProductoCompuestoFacade();
            producto.setCantidad(entity.getCantidad());
            producto.setCodigo(entity.getProducto().getCodigo());
            producto.setCodigoFabricante(entity.getProducto().getCodigoFabricante());
            producto.setCostoPROM(entity.getProducto().getCostoPROM());
            producto.setCostoUND(entity.getProducto().getCostoPROM());
            producto.setNombre(entity.getProducto().getNombre());
            producto.setPrecioESTANDAR(entity.getPrecio());
            producto.setPrecioTOTAL(entity.getPrecioTotal());
            producto.setProductoCompuesto(entity);

            //Adding entity to producto compuesto facade
            getProductosFacade().add(producto);
        }

    }

    /**
     * Obtener listado de unidades de medida
     *
     * @return List<UnidadMedida>
     */
    public List<UnidadMedida> getUnidadesMedida() {
        try {
            return getAplicacionBaseRemote().getUnidadesMedida();
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Obtener listado de proveedores
     *
     * @return List<Proveedor>
     */
    public List<Proveedor> getProveedores() {
        try {
            return getMgrProveedoresService().buscarProveedores();
        } catch (ManagerProveedoresServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Obtener listado de lineas de producto
     *
     * @return List<Linea>
     */
    public List<Linea> getLineasProducto() {
        try {
            return getMgrCatalogoService().buscarLineas();
        } catch (ManagerCatalogoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Obtener paises
     *
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

    //*********************************************************************
    //GETTERS AND SETTERS
    //*********************************************************************

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isCompuesto() {
        return compuesto;
    }

    public void setCompuesto(boolean compuesto) {
        this.compuesto = compuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoFabricante() {
        return codigoFabricante;
    }

    public void setCodigoFabricante(String codigoFabricante) {
        this.codigoFabricante = codigoFabricante;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public long getCodigoCBS() {
        return codigoCBS;
    }

    public void setCodigoCBS(long codigoCBS) {
        this.codigoCBS = codigoCBS;
    }

    public Clasificador getClasificador() {
        return clasificador;
    }

    public void setClasificador(Clasificador clasificador) {
        this.clasificador = clasificador;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }

    public boolean isExento() {
        return exento;
    }

    public void setExento(boolean exento) {
        this.exento = exento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public long getMinimo() {
        return minimo;
    }

    public void setMinimo(long minimo) {
        this.minimo = minimo;
    }

    public long getMaximo() {
        return maximo;
    }

    public void setMaximo(long maximo) {
        this.maximo = maximo;
    }

    public BigDecimal getCostoFOB() {
        return costoFOB;
    }

    public void setCostoFOB(BigDecimal costoFOB) {
        this.costoFOB = costoFOB;
    }

    public BigDecimal getCostoCIF() {
        return costoCIF;
    }

    public void setCostoCIF(BigDecimal costoCIF) {
        this.costoCIF = costoCIF;
    }

    public BigDecimal getCostoUND() {
        return costoUND;
    }

    public void setCostoUND(BigDecimal costoUND) {
        this.costoUND = costoUND;
    }

    public BigDecimal getCostoPROM() {
        return costoPROM;
    }

    public void setCostoPROM(BigDecimal costoPROM) {
        this.costoPROM = costoPROM;
    }

    public BigDecimal getPrecioEstandar() {
        return precioEstandar;
    }

    public void setPrecioEstandar(BigDecimal precioEstandar) {
        this.precioEstandar = precioEstandar;
    }

    public BigDecimal getPrecioPromocion() {
        return precioPromocion;
    }

    public void setPrecioPromocion(BigDecimal precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public List<ProductoCompuesto> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCompuesto> productos) {
        this.productos = productos;
    }

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pais getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(Pais paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public List<ProductoCompuestoFacade> getProductosFacade() {
        return productosFacade;
    }

    public void setProductosFacade(List<ProductoCompuestoFacade> productosFacade) {
        this.productosFacade = productosFacade;
    }

    //*****************************************************
    //INNER CLASS
    //*****************************************************
    public class ProductoCompuestoFacade {

        /**
         * Codigo producto
         */
        private String codigo;
        /**
         * Nombre del producto
         */
        private String nombre;
        /**
         * Codigo del fabricante
         */
        private String codigoFabricante;
        /**
         * Costo Unitario del producto
         */
        private BigDecimal costoUND;
        /**
         * Costo Promedio del producto
         */
        private BigDecimal costoPROM;
        /**
         * Cantidad compuesta del producto
         */
        private long cantidad;
        /**
         * Precio estandar del producto
         */
        private BigDecimal precioESTANDAR;
        /**
         * Precio total del producto
         */
        private BigDecimal precioTOTAL;
        /**
         * Producto compuesto
         */
        private ProductoCompuesto productoCompuesto;

        //**********************************************
        //GETTERS AND SETTERS
        //**********************************************

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCodigoFabricante() {
            return codigoFabricante;
        }

        public void setCodigoFabricante(String codigoFabricante) {
            this.codigoFabricante = codigoFabricante;
        }

        public BigDecimal getCostoUND() {
            return costoUND;
        }

        public void setCostoUND(BigDecimal costoUND) {
            this.costoUND = costoUND;
        }

        public BigDecimal getCostoPROM() {
            return costoPROM;
        }

        public void setCostoPROM(BigDecimal costoPROM) {
            this.costoPROM = costoPROM;
        }

        public long getCantidad() {
            return cantidad;
        }

        public void setCantidad(long cantidad) {
            this.cantidad = cantidad;
        }

        public BigDecimal getPrecioESTANDAR() {
            return precioESTANDAR;
        }

        public void setPrecioESTANDAR(BigDecimal precioESTANDAR) {
            this.precioESTANDAR = precioESTANDAR;
        }

        public BigDecimal getPrecioTOTAL() {
            return precioTOTAL;
        }

        public void setPrecioTOTAL(BigDecimal precioTOTAL) {
            this.precioTOTAL = precioTOTAL;
        }

        public ProductoCompuesto getProductoCompuesto() {
            return productoCompuesto;
        }

        public void setProductoCompuesto(ProductoCompuesto productoCompuesto) {
            this.productoCompuesto = productoCompuesto;
        }
    }

}
