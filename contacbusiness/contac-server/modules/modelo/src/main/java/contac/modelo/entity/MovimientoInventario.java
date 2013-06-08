package contac.modelo.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 11-12-2010
 * Time: 11:42:44 AM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_MOV_INVENTARIO")
public class MovimientoInventario implements java.io.Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: id
     */
    private Integer id;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Fecha Alta
     */
    private Date fechaAlta;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: afectacion
     */
    private short afectacion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: cantidad
     */
    private long cantidad;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: almacen
     */
    private Almacen almacen;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    private Producto producto;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: articuloEntrada
     */
    private ArticuloEntrada articuloEntrada;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: articuloSalida
     */
    private ArticuloSalida articuloSalida;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: articuloTraslado
     */
    private ArticuloTraslado articuloTraslado;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    private ArticuloLevantamientoFisico articuloLevantamientoFisico;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: articuloFactura
     */
    private ArticuloFactura articuloFactura;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estado
     */
    private EstadoMovimiento estado;

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
     * Obtener id Movimiento Inventario
     * @return Integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID")
    public Integer getId() {
        return id;
    }

    /**
     * Setear id Movimeinto Inventario
     * @param id, Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "D_FECHAALTA", nullable = false)
    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    /**
     * Obtener afectacion inventario (+1) | (-1)
     * @return byte
     */
    @Column(name = "N_AFECTACION", nullable = false)
    public short getAfectacion() {
        return afectacion;
    }

    /**
     * Setear afectacion inventario (+1) | (-1)
     * @param afectacion
     */
    public void setAfectacion(short afectacion) {
        this.afectacion = afectacion;
    }

    /**
     * Obtener cantidad del articulo en movimiento
     * @return int
     */
    @Column(name = "N_CANTIDAD", nullable = false)
    public long getCantidad() {
        return cantidad;
    }

    /**
     * Setear cantidad del articulo en movimiento
     * @param cantidad, int
     */
    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Almacen de afectacion del movimiento
     * @return Almacen
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_ALMACEN", referencedColumnName = "N_ID", nullable = false)
    public Almacen getAlmacen() {
        return almacen;
    }

    /**
     * Setear almacen de afectacion
     * @param almacen, Almacen
     */
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_PRODUCTO", referencedColumnName = "N_ID", nullable = false)
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Obtener articulo entrada
     * @return ArticuloEntrada
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_ARTICULO_ENTRADA", nullable = true)
    public ArticuloEntrada getArticuloEntrada() {
        return articuloEntrada;
    }

    /**
     * Setear articulo entrada
     * @param articuloEntrada, ArticuloEntrada
     */
    public void setArticuloEntrada(ArticuloEntrada articuloEntrada) {
        this.articuloEntrada = articuloEntrada;
    }

    /**
     * Obtener articulo salida
     * @return ArticuloSalida
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_ARTICULO_SALIDA", nullable = true)
    public ArticuloSalida getArticuloSalida() {
        return articuloSalida;
    }

    /**
     * Setear articulo salida
     * @param articuloSalida, ArticuloSalida
     */
    public void setArticuloSalida(ArticuloSalida articuloSalida) {
        this.articuloSalida = articuloSalida;
    }

    /**
     * Obtener articulo traslado
     * @return ArticuloTraslado
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_ARTICULO_TRASLADO", nullable = true)
    public ArticuloTraslado getArticuloTraslado() {
        return articuloTraslado;
    }

    /**
     * Setear articulo traslado
     * @param articuloTraslado, ArticuloTraslado
     */
    public void setArticuloTraslado(ArticuloTraslado articuloTraslado) {
        this.articuloTraslado = articuloTraslado;
    }

    /**
     * Obtener articulo levantamiento fisico
     * @return ArticuloLevantamientoFisico
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_ARTICULO_LEVANTAMIENTO", nullable = true)
    public ArticuloLevantamientoFisico getArticuloLevantamientoFisico() {
        return articuloLevantamientoFisico;
    }

    /**
     * Setear articulo levantamiento fisico
     * @param articuloLevantamientoFisico, ArticuloLevantamientoFisico
     */
    public void setArticuloLevantamientoFisico(ArticuloLevantamientoFisico articuloLevantamientoFisico) {
        this.articuloLevantamientoFisico = articuloLevantamientoFisico;
    }

    /**
     * Obtener articulo factura
     * @return ArticuloFactura
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_ARTICULO_FACTURA", nullable = true)
    public ArticuloFactura getArticuloFactura() {
        return articuloFactura;
    }

    /**
     * Setear articulo factura
     * @param articuloFactura, ArticuloFactura
     */
    public void setArticuloFactura(ArticuloFactura articuloFactura) {
        this.articuloFactura = articuloFactura;
    }

    /**
     * Obtener estado movimiento
     * @return EstadoMovimiento
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "N_ID_ESTADO", nullable = false)
    public EstadoMovimiento getEstado() {
        return estado;
    }

    /**
     * Setear estado movimiento
     * @param estado, EstadoMovimiento
     */
    public void setEstado(EstadoMovimiento estado) {
        this.estado = estado;
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
     * Obtener fecha de modificacion del Objeto
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
     * Setear fecha de modificacion del Objeto
     *
     * @param mtime, fecha de modificacion
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
