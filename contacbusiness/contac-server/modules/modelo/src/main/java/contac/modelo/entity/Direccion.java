package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-18-2010
 * Time: 11:44:00 PM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "GEN_DIRECCION")
public class Direccion implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: id
     */
    private Integer id;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: pais
     */
    private contac.modelo.entity.Pais pais;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estado o departamento
     */
    private String estado;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: ciudad
     */
    private String ciudad;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: direccion
     */
    private String direccion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: codigo postal
     */
    private String codigoPostal;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: telefono
     */
    private String telefono;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: celular
     */
    private String celular;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: fax
     */
    private String fax;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: email
     */
    private String email;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: web
     */
    private String web;

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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener codigo identificador de entidad
     *
     * @return Integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    public Integer getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear codigo identificador de entidad
     *
     * @param id, codigo entidad
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener el pais de ubicacion
     *
     * @return Pais
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_PAIS", nullable = false)
    public contac.modelo.entity.Pais getPais() {
        return pais;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Pais de ubicacion de la compania
     *
     * @param pais, pais de la compania
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }

    /**
     * Estado de ubicacion dentro del pais
     *
     * @return String
     */
    @Column(name = "C_ESTADO", nullable = false)
    public String getEstado() {
        return estado;
    }

    /**
     * Setting estado de ubicacion
     *
     * @param estado, String
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Ciudad de ubicacion
     *
     * @return String
     */
    @Column(name = "C_CIUDAD", nullable = false)
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Setting ciudad de ubicacion
     *
     * @param ciudad, String
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Direccion de ubicacion
     *
     * @return String
     */
    @Column(name = "C_DIRECCION", nullable = false)
    public String getDireccion() {
        return direccion;
    }

    /**
     * Setting direccion de ubicacion
     *
     * @param direccion, String
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Codigo postal
     *
     * @return String
     */
    @Column(name = "C_CODIGOPOSTAL")
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Setting codigo postal
     *
     * @param codigoPostal, String
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * Numero de telefono
     *
     * @return String
     */
    @Column(name = "C_TELEFONO")
    public String getTelefono() {
        return telefono;
    }

    /**
     * Setting numero de telefono
     *
     * @param telefono, String
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Numero de celular
     *
     * @return String
     */
    @Column(name = "C_CELULAR")
    public String getCelular() {
        return celular;
    }

    /**
     * Setting numero de celular
     *
     * @param celular, String
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * Numero de fax
     *
     * @return String
     */
    @Column(name = "C_FAX")
    public String getFax() {
        return fax;
    }

    /**
     * Setting numero de fax
     *
     * @param fax, String
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Direccion de correo electronico
     *
     * @return String
     */
    @Column(name = "C_EMAIL")
    public String getEmail() {
        return email;
    }

    /**
     * Setting direccion de correo electronico
     *
     * @param email, String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Direccion de pagina web
     *
     * @return String
     */
    @Column(name = "C_WEB")
    public String getWeb() {
        return web;
    }

    /**
     * Setting direccion de pagina web
     *
     * @param web, String
     */
    @Column(name = "C_WEB")
    public void setWeb(String web) {
        this.web = web;
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
