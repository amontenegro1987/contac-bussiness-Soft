package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-19-2008
 * Time: 10:15:54 PM
 */

/**
 * Usuario Garsa Business
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "SEG_USUARIO")
public class Usuario implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: login
     */
    private String username;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: password
     */
    private String password;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: passwordDescuento
     */
    private String passwordDescuento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: roles
     */
    private Set<contac.modelo.entity.Rol> roles;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: compania
     */
    private Compania compania;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: almacen
     */
    private Almacen almacen;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estado
     */
    private String estado;

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
    private java.lang.String muser;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener el login del Usuario Garsa Business
     *
     * @return String
     */
    @Id
    @Column(name = "C_USERNAME", nullable = false)
    public String getUsername() {
        return this.username;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear el login del Usuario Garsa Business
     *
     * @param username, nombre de usuario en el sistema
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener el password del Usuario Garsa Business
     *
     * @return String
     */
    @Column(name = "C_PASSWORD", nullable = false)
    public String getPassword() {
        return this.password;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear el password el Usuario Garsa Business
     *
     * @param password, clave de usuario en el sistema
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener el password del Descuento del Usuario Garsa Business
     *
     * @return String
     */
    @Column(name ="C_PASSWORDDESCUENTO", nullable = true)
    public String getPasswordDescuento() { return this.passwordDescuento; }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear el password del descuentoel Usuario Garsa Business
     *
     * @param passwordDescuento, clave de usuario en el sistema
     */
    public void setPasswordDescuento(java.lang.String passwordDescuento) { this.passwordDescuento = passwordDescuento;}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener los roles del Usuario
     *
     * @return Set<contac.modelo.entity.Rol>
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SEG_USUARIO_ROL", joinColumns = @JoinColumn(name = "C_USERNAME", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "N_ID_ROL", nullable = false))
    public Set<contac.modelo.entity.Rol> getRoles() {
        return roles;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear los roles del Usuario Garsa Business
     *
     * @param roles, roles de usuario en el sistema
     */
    public void setRoles(Set<contac.modelo.entity.Rol> roles) {
        this.roles = roles;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener compania del Usuario
     *
     * @return Compania
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "N_ID_COMPANIA", referencedColumnName = "N_ID")
    public Compania getCompania() {
        return compania;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear compania del Usuario
     *
     * @param compania, Compania
     */
    public void setCompania(Compania compania) {
        this.compania = compania;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "N_ID_ALMACEN", referencedColumnName = "N_ID")
    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener el estado del Usuario
     *
     * @return String
     */
    @Column(name = "C_ESTADO", nullable = false)
    public String getEstado() {
        return estado;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setting el estado del Usuario
     *
     * @param estado, String
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // ==================================<METODOS DE AUDITORIA>========================================================

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la fecha de creacion del objeto Usuario Garsa Business
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
     * Setear la fecha de creacion del Objeto Usuario Garsa Business
     *
     * @param ctime, fecha de creacion
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de creacion del Objeto Usuario Garsa Business
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
     * Setear usuario de creacion del Objeto Usuario Garsa Business
     *
     * @param cuser, usuario de creacion
     */
    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener fecha de modificacion del Objeto Usuario Garsa Business
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
     * Setear fecha de modificacion del Objeto Usuario Garsa Business
     *
     * @param mtime, fecha de modificacion
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener usuario de modificacion del Objeto Usuario Garsa Business
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
     * Setear usuario de modificacion del Objeto Usuario Garsa Business
     *
     * @param muser, login de usuario
     */
    public void setMuser(String muser) {
        this.muser = muser;
    }
}
