package contac.modelo.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa un movimiento inventario abstracto
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 11-07-2010
 * Time: 09:55:39 AM
 */
@MappedSuperclass
public abstract class OrdenMovimiento implements java.io.Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: id
     */
    private Integer id;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: noMovimiento
     */
    private long noMovimiento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: fechaAlta
     */
    private Date fechaAlta;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Descripcion
     */
    private String descripcion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: personaEntrega
     */
    private String personaEntrega;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estado
     */
    private EstadoMovimiento estado;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: moneda
     */
    private Moneda moneda;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: monto total
     */
    private BigDecimal montoTotal;

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
     * Obtener id entidad
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
     * Setear id entidad
     *
     * @param id, Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtener no movimiento
     *
     * @return long
     */
    @Column(name = "N_NOMOVIMIENTO")
    public long getNoMovimiento() {
        return noMovimiento;
    }

    /**
     * Setear no movimiento
     *
     * @param noMovimiento, long
     */
    public void setNoMovimiento(long noMovimiento) {
        this.noMovimiento = noMovimiento;
    }

    /**
     * Obtener fecha alta
     *
     * @return java.util.Date
     */
    @Column(name = "D_FECHAALTA")
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Setear fecha alta
     *
     * @param fechaAlta, java.util.Date
     */
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Column(name = "C_DESCRIPCION", nullable = false)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtener persona entrega
     *
     * @return String
     */
    @Column(name = "C_PERSONAENTREGA", nullable = true)
    public String getPersonaEntrega() {
        return personaEntrega;
    }

    /**
     * Setear persona entrega
     *
     * @param personaEntrega, String
     */
    public void setPersonaEntrega(String personaEntrega) {
        this.personaEntrega = personaEntrega;
    }

    /**
     * Obtener estado del movimiento
     *
     * @return EstadoMovimiento
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_ESTADO", nullable = false)
    public EstadoMovimiento getEstado() {
        return estado;
    }

    /**
     * Setear estado del movimiento
     *
     * @param estado, EstadoMovimiento
     */
    public void setEstado(EstadoMovimiento estado) {
        this.estado = estado;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_MONEDA", nullable = false)
    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    @Column(name = "N_MONTO_TOTAL", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
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
     * Obtener usuario de modificaci�n del Objeto
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
