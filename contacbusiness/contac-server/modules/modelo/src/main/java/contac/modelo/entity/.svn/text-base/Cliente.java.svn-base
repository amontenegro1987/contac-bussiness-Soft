package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: Eddy Montenegro
 * Date: 10-24-2008
 * Time: 10:15:07 PM
 */

/**
 * Cliente entity business
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "FAC_CLIENTE")
public class Cliente implements Serializable {

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
     * PROPERTY NAME: ruc
     */
    private String nit;

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
    private Set<contac.modelo.entity.Contacto> contactos;

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
     * PROPERTY NAME: descuento
     */
    private BigDecimal descuento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: cuenta
     */
    private String cuenta;

     /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tipoPersona
     */
    private byte tipoPersona;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estatus
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
     * Codigo identificador de cliente
     * @return Integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID")
    public Integer getId() {
        return id;
    }

    /**
     * Setear codigo identificador del cliente
     * @param id, Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtener codigo
     * @return long
     */
    @Column(name = "N_CODIGO")
    public long getCodigo() {
        return codigo;
    }

    /**
     * Setear codigo
     * @param codigo, long
     */
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtener razon social del cliente
     * @return String
     */
    @Column(name = "C_RAZONSOCIAL")
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Setear razon social del cliente
     * @param razonSocial, String
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * Obtener nombre comercial del cliente
     * @return String
     */
    @Column(name = "C_NOMBRECOMERCIAL")
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * Setear nombre comercial del cliente
     * @param nombreComercial, String
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    /**
     * Obtener numero NIT del registro de contribuyentes
     * @return String
     */
    @Column(name = "C_NIT")
    public String getNit() {
        return nit;
    }

    /**
     * Setear numero nit de registro de contribuyentes
     * @param nit, String
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * Obtener actividad economica del cliente
     * @return ActividadEconomica
     */
    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "N_ID_ACTIVIDADECONOMICA", nullable = false)
    public contac.modelo.entity.ActividadEconomica getActividadEconomica() {
        return actividadEconomica;
    }

    /**
     * Setear actividad economica del cliente
     * @param actividadEconomica, ActividadEconomica
     */
    public void setActividadEconomica(ActividadEconomica actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    /**
     * Obtener direccion del cliente
     * @return Direccion
     */
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL}, fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "N_ID_DIRECCION", nullable = false)
    public contac.modelo.entity.Direccion getDireccion() {
        return direccion;
    }

    /**
     * Setear direccion del cliente
     * @param direccion, Direccion
     */
    public void setDireccion(contac.modelo.entity.Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtener contactos del cliente
     * @return Contacto
     */
    @ManyToMany(cascade = {javax.persistence.CascadeType.ALL}, fetch = javax.persistence.FetchType.EAGER)
    @JoinTable(name = "FAC_CLIENTE_CONTACTOS", joinColumns = @JoinColumn(name = "N_ID_CLIENTE", nullable = false),
                                               inverseJoinColumns = @JoinColumn(name = "N_ID_CONTACTO", nullable = false))
    public Set<contac.modelo.entity.Contacto> getContactos() {
        return contactos;
    }

    /**
     * Setear contactos del cliente
     * @param contactos, Contactos
     */
    public void setContactos(Set<Contacto> contactos) {
        this.contactos = contactos;
    }

    /**
     * Obtener plazo de credito
     * @return int
     */
    @Column(name = "N_PLAZOCREDITO", nullable = false)
    public int getPlazoCredito() {
        return plazoCredito;
    }

    /**
     * Setear plazo de credito del cliente
     * @param plazoCredito, int
     */
    public void setPlazoCredito(int plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    /**
     * Obtener limite de credito del cliente
     * @return BigDecimal
     */
    @Column(name = "N_LIMITECREDITO", nullable = false)
    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    /**
     * Setear limite de credito del cliente
     * @param limiteCredito, BigDecimal
     */
    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    /**
     * Obtener descuento
     * @return BigDecimal
     */
    @Column(name = "N_DESCUENTO")
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Setear descuento
     * @param descuento, BigDecimal
     */
    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    /**
     * Obtener numero de cuenta
     * @return String
     */
    @Column(name = "N_CUENTA")
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
     * Obtener estatus
     * @return byte
     */
    @Column(name = "N_ESTATUS")
    public byte getEstatus() {
        return estatus;
    }

    /**
     * Ssetear estatus
     * @param estatus, byte
     */
    public void setEstatus(byte estatus) {
        this.estatus = estatus;
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
    
    public String toString() {
        return getRazonSocial();
    }
}
