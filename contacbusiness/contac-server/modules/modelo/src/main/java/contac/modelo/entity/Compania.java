package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-22-2008
 * Time: 09:31:32 AM
 */

/**
 * Represent Compania entity business
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "GEN_COMPANIA")
public class Compania implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: id
     */
    private Integer id;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: nit
     */
    private String nit;

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
     * PROPERTY NAME: fechaConstitucion
     */
    private Date fechaConstitucion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: fechaAltaContribuyente
     */
    private Date fechaAltaContribuyente;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: eslogan
     */
    private String eslogan;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: direccion
     */
    private contac.modelo.entity.Direccion direccion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: clasificaciones
     */
    private Set<contac.modelo.entity.Clasificador> clasificaciones;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: monedaReferencia
     */
    private contac.modelo.entity.Moneda monedaReferencia;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: almacenes
     */
    private Set<contac.modelo.entity.Almacen> almacenes;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tipoPersona
     */
    private byte tipoPersona;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tipoPesonaDesc
     */
    private String tipoPersonaDesc;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estado
     */
    private byte estatus;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estatusDesc
     */
    private String estatusDesc;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: logotipo
     */
    private CompaniaImageLOB logotipo;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * Obtener el codigo nit (Oficina de contribuyente)
     *
     * @return String
     */
    @Column(name = "C_NIT", nullable = false)
    public String getNit() {
        return nit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear el codigo nit (Oficina de contribuyente)
     *
     * @param nit, codigo de contribuyente
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la Raz�n Social
     *
     * @return String
     */
    @Column(name = "C_RAZONSOCIAL", nullable = false)
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear la Raz�n Social
     *
     * @param razonSocial, personer�a de registro como contribuyente
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener el Nombre Comercial
     *
     * @return String
     */
    @Column(name = "C_NOMBRECOMERCIAL", nullable = true)
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear el Nombre Comercial
     *
     * @param nombreComercial, nombre comercial de registro como contribuyente
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener fecha de constitucion
     *
     * @return Date
     */
    @Column(name = "D_FECHACONSTITUCION", nullable = false)
    public Date getFechaConstitucion() {
        return fechaConstitucion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear fecha de constitucion de la compania
     *
     * @param fechaConstitucion, fecha constitucion de la compania
     */
    public void setFechaConstitucion(Date fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
    }

    /**
     * Obtener Fecha de alta como contribuyente
     *
     * @return Date
     */
    @Column(name = "D_FECHAALTACONTRIBUYENTE", nullable = true)
    public Date getFechaAltaContribuyente() {
        return fechaAltaContribuyente;
    }

    /**
     * Setear fecha de alta como contribuyente
     *
     * @param fechaAltaContribuyente, Date
     */
    public void setFechaAltaContribuyente(Date fechaAltaContribuyente) {
        this.fechaAltaContribuyente = fechaAltaContribuyente;
    }

    /**
     * Obtener eslogan compania
     *
     * @return String
     */
    @Column(name = "C_ESLOGAN", nullable = true)
    public String getEslogan() {
        return eslogan;
    }

    /**
     * Setear el eslogan de la compania
     *
     * @param eslogan, String
     */
    public void setEslogan(String eslogan) {
        this.eslogan = eslogan;
    }

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL}, fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "N_ID_DIRECCION", nullable = false)
    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtener clasificaciones comerciales de la compania
     *
     * @return Set
     * @see contac.modelo.entity.Clasificador
     */
    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = javax.persistence.FetchType.EAGER)
    @JoinTable(name = "GEN_COMPANIA_CLASIFICACION", joinColumns = @JoinColumn(name = "N_ID_COMPANIA", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "N_ID_CLASIFICADOR", nullable = false))
    public Set<contac.modelo.entity.Clasificador> getClasificaciones() {
        return clasificaciones;
    }

    /**
     * Setear clasificaciones comerciales de la compania
     *
     * @param clasificaciones, Set
     */
    public void setClasificaciones(Set<contac.modelo.entity.Clasificador> clasificaciones) {
        this.clasificaciones = clasificaciones;
    }

    /**
     * Obtener moneda de referencia de la compania para contabilidad
     *
     * @return Moneda
     */
    @ManyToOne(cascade = CascadeType.DETACH, fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "N_ID_MONEDA", nullable = false)
    public contac.modelo.entity.Moneda getMonedaReferencia() {
        return monedaReferencia;
    }

    /**
     * Setear moneda de referencia
     *
     * @param monedaReferencia, Moneda
     */
    public void setMonedaReferencia(Moneda monedaReferencia) {
        this.monedaReferencia = monedaReferencia;
    }

    /**
     * Obtener almacenes asociados a la compania
     *
     * @return almacenes
     */
    @OneToMany(cascade = {javax.persistence.CascadeType.ALL}, fetch = javax.persistence.FetchType.EAGER,
            mappedBy = "compania")
    public Set<contac.modelo.entity.Almacen> getAlmacenes() {
        return almacenes;
    }

    /**
     * Setear almacenes asociados a la compania
     *
     * @param almacenes, listado de almacenes
     */
    public void setAlmacenes(Set<contac.modelo.entity.Almacen> almacenes) {
        this.almacenes = almacenes;
    }

    /**
     * Obtener tipo de persona juridica
     *
     * @return byte
     */
    @Column(name = "N_TIPOPERSONA", nullable = false)
    public byte getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Setting tipo de persona
     *
     * @param tipoPersona, byte
     */
    public void setTipoPersona(byte tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /**
     * Obtener descripcion de tipo de persona
     * @return String
     */
    @Column(name = "C_TIPOPERSONA_DESC", nullable = false)
    public String getTipoPersonaDesc() {
        return tipoPersonaDesc;
    }

    /**
     * Setear descripcion de tipo de persona
     * @param tipoPersonaDesc, String
     */
    public void setTipoPersonaDesc(String tipoPersonaDesc) {
        this.tipoPersonaDesc = tipoPersonaDesc;
    }

    /**
     * Obtener estado de la entidad
     *
     * @return byte
     */
    @Column(name = "N_ESTATUS", nullable = false)
    public byte getEstatus() {
        return estatus;
    }

    /**
     * Setear estado de la entidad
     *
     * @param estatus, byte
     */
    public void setEstatus(byte estatus) {
        this.estatus = estatus;
    }

    /**
     * Obtener descripcion del estatus
     * @return String
     */
    @Column(name = "C_ESTATUS_DESC", nullable = false)
    public String getEstatusDesc() {
        return estatusDesc;
    }

    /**
     * Setear descripcion del estatus
     * @param estatusDesc, String
     */
    public void setEstatusDesc(String estatusDesc) {
        this.estatusDesc = estatusDesc;
    }

    /**
     * <!-- begin-user-code -->
     * <!-- end-user-code -->
     * Obtner el logotipo de la compania
     *
     * @return LogoCompaniaLOB
     */
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL}, fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "N_ID_LOGOTIPO")
    public CompaniaImageLOB getLogotipo() {
        return logotipo;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear el logotipo
     *
     * @param logotipo, CompaniaImageLOB
     */
    public void setLogotipo(CompaniaImageLOB logotipo) {
        this.logotipo = logotipo;
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
