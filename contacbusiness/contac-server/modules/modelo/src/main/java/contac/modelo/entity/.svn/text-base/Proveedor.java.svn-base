package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-22-2008
 * Time: 01:25:28 PM
 */

/**
 * Proveedor Entity business
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "CMP_PROVEEDOR")
public class Proveedor implements Serializable {

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
    private long codigo;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: razonSocial
     */
    private String razonSocial;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: nombreComercial
     */
    private String nombreComercial;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: nit
     */
    private String nit;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: cuenta
     */
    private String cuenta;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: descuento
     */
    private BigDecimal descuento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: plazoCredito
     */
    private int plazoCredito;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: limiteCredito
     */
    private BigDecimal limiteCredito;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tipoPersona
     */
    private byte tipoPersona;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: actividadEconomica
     */
    private contac.modelo.entity.ActividadEconomica actividadEconomica;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: direccion
     */
    private contac.modelo.entity.Direccion direccion;

     /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: contactos
     */
    //private Set<contac.modelo.entity.Contacto> contactos;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estado
     */
    private byte estatus;

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
     * Obtener el id de Proveedore Garsa Business
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear el id de Proveedor Garsa Business
     *
     * @param id, nro. de registro del objeto
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtener el codigo interno del proveedor
     * @return long
     */
    @Column(name = "N_CODIGO", nullable = false)
    public long getCodigo() {
        return codigo;
    }

    /**
     * Setear el codigo interno del proveedor
     * @param codigo, long
     */
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtener razon social
     * @return String
     */
    @Column(name = "C_RAZONSOCIAL", nullable = false)
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Setear razon social
     * @param razonSocial, String
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * Obtener nombre comercial
     * @return String
     */
    @Column(name = "C_NOMBRECOMERCIAL", nullable = true)
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * Setear nombre comercial
     * @param nombreComercial, String
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    /**
     * Obtener codigo contribuyente registrado NIT
     * @return String
     */
    @Column(name = "C_NIT", nullable = true)
    public String getNit() {
        return nit;
    }

    /**
     * Setear codigo nit contribuyente
     * @param nit, String
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * Obtener numero de cuenta
     * @return String
     */
    @Column(name = "C_CUENTA", nullable = true)
    public String getCuenta() {
        return cuenta;
    }

    /**
     * Setear numero de cuenta
     * @param cuenta, String
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * Obtener descuento otorgado por el proveedor
     * @return BigDecimal
     */
    @Column(name = "N_DESCUENTO", nullable = true)
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Setear descuento otorgado por el proveedor
     * @param descuento, BigDecimal
     */
    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    /**
     * Obtener plazo credito otorgado por el proveedor
     * @return int
     */
    @Column(name = "N_PLAZOCREDITO", nullable = false)
    public int getPlazoCredito() {
        return plazoCredito;
    }

    /**
     * Setear plazo de credito otorgado por el proveedor
     * @param plazoCredito, int
     */
    public void setPlazoCredito(int plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    /**
     * Obtener limite de credito otorgado por el proveedor
     * @return BigDecimal
     */
    @Column(name = "N_LIMITECREDITO", nullable = false)
    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    /**
     * Setear limite de credito otorgado por el proveedor
     * @param limiteCredito, BigDecimal
     */
    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    /**
     * Obtener tipo de persona
     * @return byte
     */
    @Column(name = "N_TIPOPERSONA")
    public byte getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Setear tipo de persona
     * @param tipoPersona, byte
     */
    public void setTipoPersona(byte tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /**
     * Obtener actividad economica
     * @return ActividadEconomica
     */
    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "N_ID_ACTIVIDADECONOMICA", nullable = false)
    public contac.modelo.entity.ActividadEconomica getActividadEconomica() {
        return actividadEconomica;
    }

    /**
     * Setear actividad economica
     * @param actividadEconomica, ActividadEconomica
     */
    public void setActividadEconomica(ActividadEconomica actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    /**
     * Obtener Direccion del proveedor
     * @return Direccion
     */
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL}, fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "N_ID_DIRECCION", nullable = false)
    public contac.modelo.entity.Direccion getDireccion() {
        return direccion;
    }

    /**
     * Obtener direccion del proveedor
     * @param direccion, Direccion
     */
    public void setDireccion(contac.modelo.entity.Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtener listado de contactos del proveedor
     * @return Set<contac.modelo.entity.Proveedor>
     */
    //@ManyToMany(cascade = {javax.persistence.CascadeType.ALL}, fetch = javax.persistence.FetchType.EAGER)
    //@JoinTable(name = "INV_PROVEEDOR_CONTACTOS", joinColumns = @JoinColumn(name = "N_ID_PROVEEDOR", nullable = false),
    //                                              inverseJoinColumns = @JoinColumn(name = "N_ID_CONTACTO", nullable = false))
    //public Set<contac.modelo.entity.Contacto> getContactos() {
    //    return contactos;
    //}

    /**
     * Setear listado de contactos del proveedor
     * @param contactos, Set<contac.modelo.entity.Proveedor>
     */
    //public void setContactos(Set<contac.modelo.entity.Contacto> contactos) {
    //    this.contactos = contactos;
    //}

    /**
     * Obtener estatus del proveedor
     * @return byte
     */
    @Column(name = "N_ESTATUS")
    public byte getEstatus() {
        return estatus;
    }

    /**
     * Setear estatus del proveedor
     * @param estatus
     */
    public void setEstatus(byte estatus) {
        this.estatus = estatus;
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
     * Setear usuario de modificaci�n del Objeto
     *
     * @param muser, login de usuario
     */
    public void setMuser(String muser) {
        this.muser = muser;
    }
}
