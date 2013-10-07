package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Represent Contacto entity business
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-20-2010
 * Time: 12:37:22 AM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "GEN_CONTACTO")
public class Contacto implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: id
     */
    private Integer id;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tipo de identificacion
     */
    private TipoIdentificacion tipoIdentificacion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: numero de identificacion
     */
    private String noIdentificacion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: primer nombre
     */
    private String primerNombre;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: segundo nombre
     */
    private String segundoNombre;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: primer apellido
     */
    private String primerApellido;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: segundo apellido
     */
    private String segundoApellido;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: segundo puesto
     */
    private String puesto;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: direccion
     */
    private Direccion direccion;

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
     * Obtener codigo identificador de entidad
     * @return Integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID")
    public Integer getId() {
        return id;
    }

    /**
     * Setear codigo identificador de entidad
     * @param id, Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtener tipo de identificacion
     * @return TipoIdentificacion
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_IDENTIFICACION", referencedColumnName = "N_ID", nullable = true)
    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Setear tipo de identificacion
     * @param tipoIdentificacion, TipoIdentificacion
     */
    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Obtener no identificacion del sujeto
     * @return String
     */
    @Column(name = "C_NO_IDENTIFICACION", nullable = true)
    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    /**
     * Setear no identificador del sujeto
     * @param noIdentificacion, String
     */
    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    /**
     * Obtener primer nombre del sujeto
     * @return String
     */
    @Column(name = "C_PRIMER_NOMBRE")
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * Setear primer nombre del sujeto
     * @param primerNombre, String
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * Obtener segundo nombre del sujeto
     * @return String
     */
    @Column(name = "C_SEGUNDO_NOMBRE")
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * Setear segundo nombre del sujeto
     * @param segundoNombre, String
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * Obtener primer nombre del sujeto
     * @return String
     */
    @Column(name = "C_PRIMER_APELLIDO")
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Setear primer apellido del sujeto
     * @param primerApellido, String
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Obtener segundo apellido del sujeto
     * @return String
     */
    @Column(name = "C_SEGUNDO_APELLIDO")
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Setenar segundo apellido del sujeto
     * @param segundoApellido, String
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Obtener puesto de trabajo
     * @return String
     */
    @Column(name = "C_PUESTO")
    public String getPuesto() {
        return puesto;
    }

    /**
     * Setear puesto de trabajo
     * @param puesto, String
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    /**
     * Obtener direccion del sujeto
     * @return contac.modelo.entity.Direccion
     */
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_DIRECCION", nullable = false)
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Setear direccion del sujeto
     * @param direccion, contac.modelo.entity.Direccion
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    // ==================================<METODOS DE AUDITORIA>========================================================

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la fecha de creaci�n del objeto
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
     * Setear la fecha de creaci�n del Objeto
     *
     * @param ctime, fecha de creaci�n
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de creaci�n del Objeto
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
     * Setear usuario de creaci�n del Objeto
     *
     * @param cuser, usuario de creaci�n
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
     * Setear usuario de modificaci�n del Objeto
     *
     * @param muser, login de usuario
     */
    public void setMuser(String muser) {
        this.muser = muser;
    }
}
