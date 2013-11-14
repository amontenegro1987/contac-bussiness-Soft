package contac.modelo.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 02-23-2010
 * Time: 10:27:58 PM
 */

/**
 * Representa una clase abstracta Articulo
 */
@MappedSuperclass
public abstract class Articulo implements java.io.Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: id
     */
    private Integer id;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Renglon de posicion del articulo
     */
    private long renglon;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: codigo
     */
    private String codigo;

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
     */
    private String unidadMedida;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: cantidad
     */
    private long cantidad;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: cantidad anterior para evaluacion de existencias
     */
    private long cantidadAnterior;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: costo
     */
    private BigDecimal costo;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: costo total
     */
    private BigDecimal costoTotal;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: producto
     */
    private Producto producto;

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
     * Obtener id de articulo
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
     * Setear id de articulo
     *
     * @param id, Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "N_RENGLON", nullable = true)
    public long getRenglon() {
        return renglon;
    }

    public void setRenglon(long renglon) {
        this.renglon = renglon;
    }

    /**
     * Obtener codigo
     *
     * @return String
     */
    @Column(name = "C_CODIGO")
    public String getCodigo() {
        return codigo;
    }

    /**
     * Setear codigo
     *
     * @param codigo, String
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtener nombre del articulo
     *
     * @return String
     */
    @Column(name = "C_NOMBRE")
    public String getNombre() {
        return nombre;
    }

    /**
     * Setear nombre del articulo
     *
     * @param nombre, String
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtener codigo del frabricante
     *
     * @return String
     */
    @Column(name = "C_CODIGOFABRICANTE")
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
     * Obtener unidad de medida
     *
     * @return String
     */
    @Column(name = "C_UNIDAD_MEDIDA", nullable = false)
    public String getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * Setear unidad de medida
     *
     * @param unidadMedida, String
     */
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la cantidad de Articulo
     *
     * @return int
     */
    @Column(name = "N_CANTIDAD", nullable = false)
    public long getCantidad() {
        return cantidad;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear la cantidad de Articulo
     *
     * @param cantidad, cantidad del articulo
     */
    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la cantidad anterior del articulo
     *
     * @return int
     */
    @Transient
    public long getCantidadAnterior() {
        return cantidadAnterior;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear la cantidad de Articulo
     *
     * @return int
     */
    public void setCantidadAnterior(long cantidadAnterior) {
        this.cantidadAnterior = cantidadAnterior;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener el costoUND del Articulo de Producto
     *
     * @return double
     */
    @Column(name = "N_COSTOUND", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getCosto() {
        return costo;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear el costo del Articulo de Producto
     *
     * @param costo, costo del articulo
     */
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    /**
     * Obtener costo total del producto
     *
     * @return BigDecimal
     */
    @Column(name = "N_COSTOTOTAL", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    /**
     * Setear costo total de producto
     *
     * @param costoTotal, Costo total del articulo
     */
    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener el producto de Articulo
     *
     * @return garsa.modelo.entity.Producto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_PRODUCTO", referencedColumnName = "N_ID", nullable = false)
    public Producto getProducto() {
        return producto;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->a
     *
     * @param producto, producto asociado en Articulo
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // ==================================<METODOS DE AUDITORIA>========================================================

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la fecha de creacion
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
     * Setear la fecha de creacion
     *
     * @param ctime, fecha de creacion
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de creacion
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
     * Setear usuario de creacion
     *
     * @param cuser, usuario de creacion
     */
    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener fecha de modificacion
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
     * Setear fecha de modificacion
     *
     * @param mtime, fecha de modificacion
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de modificacion
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
     * Setear usuario de modificacion
     *
     * @param muser, login de usuario
     */
    public void setMuser(String muser) {
        this.muser = muser;
    }

    /**
     * Implement equals object
     *
     * @param o, Object
     * @return boolean
     */
    public boolean equals(Object o) {

        if (o == null)
            return false;

        if (o instanceof Articulo) {
            Articulo articulo = (Articulo) o;

            if (articulo.getProducto() == null)
                return false;

            if (this.producto == null)
                return false;

            if (articulo.getProducto().getCodigo().equals(this.producto.getCodigo()))
                return true;
        }

        return false;
    }
}
