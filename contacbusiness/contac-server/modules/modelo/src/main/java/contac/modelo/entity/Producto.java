package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: Eddy Montenegro
 * Date: 10-22-2008
 * Time: 03:03:06 PM
 */

/**
 * Producto Business Entity
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_PRODUCTO")
public class Producto implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: id
     */
    private Integer id;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: codigo
     */
    private String codigo;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: codigoCbs
     */
    private long codigoCbs;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: codigoBarra
     */
    private String codigoBarra;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: nombre
     */
    private String nombre;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: codigoFabricante
     */
    private String codigoFabricante;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: alias
     */
    private String alias;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: marca
     */
    private String marca;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: modelo
     */
    private String modelo;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: observaciones
     */
    private String observaciones;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: minimo
     */
    private long minimo;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: maximo
     */
    private long maximo;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: fechaVencimiento
     */
    private Date fechaVencimiento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: costoUND
     */
    private BigDecimal costoUND;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: costoCIF
     */
    private BigDecimal costoCIF;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: costoFOB
     */
    private BigDecimal costoFOB;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: cosstoPROM
     */
    private BigDecimal costoPROM;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: descuento
     */
    private BigDecimal descuento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: exento
     */
    private boolean exento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: precioUNO
     */
    private BigDecimal precioESTANDAR;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: precioPROMOCION
     */
    private BigDecimal precioPROMOCION;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: unidadMedida
     */
    private UnidadMedida unidadMedida;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: proveedor
     */
    private Proveedor proveedor;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: clasificador
     */
    private Clasificador clasificador;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: linea
     */
    private Linea linea;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estado
     */
    private EstadoProducto estado;

    /**
     * <!--begin-user-doc -->
     * <!--end-user-doc -->
     * PROPERTY NAME: compuesto
     */
    private boolean compuesto;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estatus <ACTIVO, INACTIVO>
     */
    private byte estatus;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: compania
     */
    private Compania compania;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: paisOrigen
     */
    private Pais paisOrigen;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: presentaciones
     */
    private Set<Presentacion> presentaciones;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: fotografia
     */
    private ProductoImageLOB fotografia;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: productos
     */
    private Set<ProductoCompuesto> productosCompuestos;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: modificaciones
     */
    private Set<ProductoModificacion> modificaciones;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: existencias
     */
    private Set<ProductoExistencia> existencias;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Audit (ctime)
     */
    private Date ctime;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Audit (cuser)
     */
    private String cuser;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Audit (mtime)
     */
    private Date mtime;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Audit (muser)
     */
    private String muser;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    /**
     * Obtener id
     *
     * @return Integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID")
    public Integer getId() {
        return id;
    }

    /**
     * Setear id
     *
     * @param id, Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtener codigo compuesto producto
     *
     * @return String
     */
    @Column(name = "C_CODIGO", nullable = false)
    public String getCodigo() {
        return codigo;
    }

    /**
     * Setear codigo compuesto producto
     *
     * @param codigo, String
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtener codigo CBS
     *
     * @return long
     */
    @Column(name = "N_CODIGOCBS", nullable = false)
    public long getCodigoCbs() {
        return codigoCbs;
    }

    /**
     * Setear codigo CBS
     *
     * @param codigoCbs, long
     */
    public void setCodigoCbs(long codigoCbs) {
        this.codigoCbs = codigoCbs;
    }

    /**
     * Obtener codigo de barra
     *
     * @return String
     */
    @Column(name = "C_CODIGOBARRA")
    public String getCodigoBarra() {
        return codigoBarra;
    }

    /**
     * Setear codigo de barra
     *
     * @param codigoBarra, String
     */
    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    /**
     * Obtener nombre del producto
     *
     * @return String
     */
    @Column(name = "C_NOMBRE", nullable = false)
    public String getNombre() {
        return nombre;
    }

    /**
     * Setear nombre del producto
     *
     * @param nombre, String
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtener codigo del fabricante
     *
     * @return String
     */
    @Column(name = "C_CODIGOFABRICANTE", nullable = false)
    public String getCodigoFabricante() {
        return codigoFabricante;
    }

    /**
     * Setear codigo del fabricante
     *
     * @param codigoFabricante, String
     */
    public void setCodigoFabricante(String codigoFabricante) {
        this.codigoFabricante = codigoFabricante;
    }

    /**
     * Obtener alias
     *
     * @return String
     */
    @Column(name = "C_ALIAS", nullable = false)
    public String getAlias() {
        return alias;
    }

    /**
     * Setear alias
     *
     * @param alias, String
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Obtener marca
     *
     * @return String
     */
    @Column(name = "C_MARCA", nullable = false)
    public String getMarca() {
        return marca;
    }

    /**
     * Setear marca
     *
     * @param marca, String
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtener modelo
     *
     * @return String
     */
    @Column(name = "C_MODELO")
    public String getModelo() {
        return modelo;
    }

    /**
     * Setear modelo
     *
     * @param modelo, String
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtener observaciones
     *
     * @return String
     */
    @Column(name = "C_OBSERVACIONES")
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Setear observaciones
     *
     * @param observaciones, String
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Obtener minimo
     *
     * @return int
     */
    @Column(name = "N_MINIMO", nullable = false)
    public long getMinimo() {
        return minimo;
    }

    /**
     * Setear minimo
     *
     * @param minimo, int
     */
    public void setMinimo(long minimo) {
        this.minimo = minimo;
    }

    /**
     * Obtener maximo
     *
     * @return int
     */
    @Column(name = "N_MAXIMO", nullable = false)
    public long getMaximo() {
        return maximo;
    }

    /**
     * Setear maximo
     *
     * @param maximo, int
     */
    public void setMaximo(long maximo) {
        this.maximo = maximo;
    }

    /**
     * Obtener fecha de vencimiento
     *
     * @return Date
     */
    @Column(name = "D_FECHAVENCIMIENTO")
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Setear fecha de vencimiento
     *
     * @param fechaVencimiento, Date
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Obtener costo UND
     *
     * @return BigDecimal
     */
    @Column(name = "N_COSTOUND", precision= 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getCostoUND() {
        return costoUND;
    }

    /**
     * Setear costo UND
     *
     * @param costoUND, BigDecimal
     */
    public void setCostoUND(BigDecimal costoUND) {
        this.costoUND = costoUND;
    }

    /**
     * Obtener costo CIF
     *
     * @return BigDecimal
     */
    @Column(name = "N_COSTOCIF", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getCostoCIF() {
        return costoCIF;
    }

    /**
     * Setear costo CIF
     *
     * @param costoCIF, BigDecimal
     */
    public void setCostoCIF(BigDecimal costoCIF) {
        this.costoCIF = costoCIF;
    }

    /**
     * Obtener costo FOB
     *
     * @return BigDecimal
     */
    @Column(name = "N_COSTOFOB", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getCostoFOB() {
        return costoFOB;
    }

    /**
     * Setear costo FOB
     *
     * @param costoFOB, BigDecimal
     */
    public void setCostoFOB(BigDecimal costoFOB) {
        this.costoFOB = costoFOB;
    }

    /**
     * Obtener costo PROM
     *
     * @return BigDecimal
     */
    @Column(name = "N_COSTOPROM", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getCostoPROM() {
        return costoPROM;
    }

    /**
     * Setear costo PROM
     *
     * @param costoPROM, BigDecimal
     */
    public void setCostoPROM(BigDecimal costoPROM) {
        this.costoPROM = costoPROM;
    }

    /**
     * Obtener descuento
     *
     * @return BigDecimal
     */
    @Column(name = "N_DESCUENTO", precision = 19, scale= 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Setear descuento
     *
     * @param descuento, BigDecimal
     */
    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    /**
     * Obtener producto exento impuesto
     *
     * @return boolean
     */
    @Column(name = "B_EXENTO")
    public boolean isExento() {
        return exento;
    }

    /**
     * Setear producto excento impuesto
     *
     * @param exento, boolean
     */
    public void setExento(boolean exento) {
        this.exento = exento;
    }

    /**
     * Obtener precio uno
     *
     * @return BigDecimal
     */
    @Column(name = "N_PRECIOESTANDAR", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getPrecioESTANDAR() {
        return precioESTANDAR;
    }

    /**
     * Setear precio ESTANDAR
     *
     * @param precioESTANDAR, BigDecimal
     */
    public void setPrecioESTANDAR(BigDecimal precioESTANDAR) {
        this.precioESTANDAR = precioESTANDAR;
    }

    /**
     * Obtener precio PROMOCION
     *
     * @return BigDecimal
     */
    @Column(name = "N_PRECIOPROMOCION", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getPrecioPROMOCION() {
        return precioPROMOCION;
    }

    /**
     * Setear precio PROMOCION
     *
     * @param precioPROMOCION, BigDecimal
     */
    public void setPrecioPROMOCION(BigDecimal precioPROMOCION) {
        this.precioPROMOCION = precioPROMOCION;
    }

    /**
     * Obtener unidad de medida
     *
     * @return UnidadMedida
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_UNIDADMEDIDA")
    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * Setear unidad de medida
     *
     * @param unidadMedida, UnidadMedida
     */
    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    /**
     * Obtener proveedor
     *
     * @return Proveedor
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_PROVEEDOR")
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * Setear proveedor
     *
     * @param proveedor, Proveedor
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Obtener clasificador
     *
     * @return Clasificador
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_CLASIFICADOR", nullable = false)
    public Clasificador getClasificador() {
        return clasificador;
    }

    /**
     * Setear clasificador
     *
     * @param clasificador, Clasificador
     */
    public void setClasificador(Clasificador clasificador) {
        this.clasificador = clasificador;
    }

    /**
     * Obtener linea
     *
     * @return Linea
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_LINEA", nullable = false)
    public Linea getLinea() {
        return linea;
    }

    /**
     * Setear linea
     *
     * @param linea, Linea
     */
    public void setLinea(Linea linea) {
        this.linea = linea;
    }

    /**
     * Obtener estado producto
     *
     * @return EstadoProducto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_ESTADOPRODUCTO", nullable = false)
    public EstadoProducto getEstado() {
        return estado;
    }

    /**
     * Setear estado producto
     *
     * @param estado, EstadoProducto
     */
    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    /**
     * Es un producto compuesto
     *
     * @return boolean
     */
    @Column(name = "B_COMPUESTO", nullable = false)
    public boolean isCompuesto() {
        return compuesto;
    }

    /**
     * Setear producto compuesto
     *
     * @param compuesto, boolean
     */
    public void setCompuesto(boolean compuesto) {
        this.compuesto = compuesto;
    }

    /**
     * Obtener el estatus del producto ACTIVO | INACTIVO
     *
     * @return byte
     */
    @Column(name = "N_ESTATUS", nullable = false)
    public byte getEstatus() {
        return estatus;
    }

    /**
     * Setear estatus del producto
     *
     * @param estatus, byte
     */
    public void setEstatus(byte estatus) {
        this.estatus = estatus;
    }

    /**
     * Obtener compania
     *
     * @return Compania
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_COMPANIA")
    public Compania getCompania() {
        return compania;
    }

    /**
     * Setear compania
     *
     * @param compania
     */
    public void setCompania(Compania compania) {
        this.compania = compania;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_PAIS_ORIGEN", nullable = true)
    public Pais getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(Pais paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    /**
     * Obtener presentaciones
     *
     * @return Set<Presentacion>
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "N_ID_PRESENTACION", referencedColumnName = "N_ID")
    public Set<Presentacion> getPresentaciones() {
        return presentaciones;
    }

    /**
     * Setear presentaciones
     *
     * @param presentaciones, Set<Presentacion>
     */
    public void setPresentaciones(Set<Presentacion> presentaciones) {
        this.presentaciones = presentaciones;
    }

    /**
     * Obtener fotografia
     *
     * @return ProductoImageLOB
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_FOTOGRAFIA")
    public ProductoImageLOB getFotografia() {
        return fotografia;
    }

    /**
     * Setear fotografia
     *
     * @param fotografia, ProductoImageLOB
     */
    public void setFotografia(ProductoImageLOB fotografia) {
        this.fotografia = fotografia;
    }

    /**
     * Obtener productos compuestos
     *
     * @return Set<Producto>
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name="INV_PROD_INV_PROD_COMP",
            joinColumns = {@JoinColumn(name = "N_ID_PRODUCTO", referencedColumnName = "N_ID")},
            inverseJoinColumns = {@JoinColumn(name = "N_ID_PRODUCTO_COMP", referencedColumnName = "N_ID")}
    )
    public Set<ProductoCompuesto> getProductosCompuestos() {
        return productosCompuestos;
    }

    /**
     * Setear productos compuestos
     *
     * @param productosCompuestos, Set<Producto>
     */
    public void setProductosCompuestos(Set<ProductoCompuesto> productosCompuestos) {
        this.productosCompuestos = productosCompuestos;
    }

    /**
     * Obtener productos modificaciones
     *
     * @return Set<ProductoModificacion>
     */
    @OneToMany(targetEntity = ProductoModificacion.class, mappedBy = "producto",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<ProductoModificacion> getModificaciones() {
        return modificaciones;
    }

    /**
     * Obtener modificaciones
     *
     * @param modificaciones, Set<ProductoModificacion>
     */
    public void setModificaciones(Set<ProductoModificacion> modificaciones) {
        this.modificaciones = modificaciones;
    }

    /**
     * Obtener existencias
     *
     * @return Set<ProductoExistencia>
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "N_ID_PRODUCTO", referencedColumnName = "N_ID"),
            @JoinColumn(name = "N_ID_COMPANIA_PRODUCTO", referencedColumnName = "N_ID_COMPANIA")
    })
    public Set<ProductoExistencia> getExistencias() {
        return existencias;
    }

    /**
     * Setear existencias
     *
     * @param existencias, Set<ProductoExistencia>
     */
    public void setExistencias(Set<ProductoExistencia> existencias) {
        this.existencias = existencias;
    }

    // ==================================<METODOS DE AUDITORIA>========================================================

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la fecha de creacion del objeto
     *
     * @return Date
     */
    @Column(name = "D_CTIME", nullable = false)
    public Date getCtime() {
        return this.ctime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear la fecha de creacion del Objeto
     *
     * @param ctime, fecha de creacion
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de creacion del Objeto
     *
     * @return String
     */
    @Column(name = "C_CUSER", nullable = false)
    public String getCuser() {
        return this.cuser;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear usuario de creacion del Objeto
     *
     * @param cuser, usuario de creacion
     */
    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener fecha de modificaci�n del Objeto
     *
     * @return Date
     */
    @Column(name = "D_MTIME", nullable = false)
    public Date getMtime() {
        return this.mtime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear fecha de modificaci�n del Objeto
     *
     * @param mtime, fecha de modificaci�n
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de modificacion del Objeto
     *
     * @return String
     */
    @Column(name = "C_MUSER", nullable = false)
    public String getMuser() {
        return this.muser;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear usuario de modificacion del Objeto
     *
     * @param muser, login de usuario
     */
    public void setMuser(String muser) {
        this.muser = muser;
    }
}
