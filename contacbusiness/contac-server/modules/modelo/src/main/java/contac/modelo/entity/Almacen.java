package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-22-2008
 * Time: 10:39:57 AM
 */

/**
 * Almacen entity business
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "INV_ALMACEN")
public class Almacen implements Serializable {

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
    private int codigo;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: descripcion
     */
    private String descripcion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: referencia
     */
    private String referencia;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: direccion
     */
    private contac.modelo.entity.Direccion direccion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tipoAlmacen
     */
    private byte tipoAlmacen;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tipoAlmacenDesc
     */
    private String tipoAlmacenDesc;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: serie
     */
    private char serie;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: consecutivo
     */
    private long consecutivo;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: noAutorizacionComercial
     */
    private String noAutorizacionComercial;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: compania
     */
    private Compania compania;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estatus
     */
    private byte estatus;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: estatus desc
     */
    private String estatusDesc;

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
     * Obtiene el codigo identificador del almacen
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
     * Setear el codigo identificador del almacen
     *
     * @param id, Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtener el codigo de almacen
     *
     * @return int
     */
    @Column(name = "N_CODIGO", nullable = false)
    public int getCodigo() {
        return codigo;
    }

    /**
     * Setear el codigo de almacen
     *
     * @param codigo, int
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtener descripcion de almacen
     *
     * @return String
     */
    @Column(name = "C_DESCRIPCION", nullable = false)
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Setear descripcion de almacen
     *
     * @param descripcion, String
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtener referencia de almacen
     *
     * @return String
     */
    @Column(name = "C_REFERENCIA", nullable = false)
    public String getReferencia() {
        return referencia;
    }

    /**
     * Setear referencia de almacen
     *
     * @param referencia, String
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * Obtener direccion de almacen
     *
     * @return Direccion
     */
     @OneToOne(cascade = {javax.persistence.CascadeType.ALL}, fetch = javax.persistence.FetchType.EAGER)
     @JoinColumn(name = "N_ID_DIRECCION", nullable = false)


    public contac.modelo.entity.Direccion getDireccion() {
        return direccion;
    }

   /**
     * Setear direccion de almacen
     *
     * @param direccion, String
     */
    public void setDireccion(contac.modelo.entity.Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtener tipo de almacen
     *
     * @return byte
     */
    @Column(name = "N_TIPOALMACEN", nullable = false)
    public byte getTipoAlmacen() {
        return tipoAlmacen;
    }

    /**
     * Setear tipo de almacen
     *
     * @param tipoAlmacen, byte
     */
    public void setTipoAlmacen(byte tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    /**
     * Obtener tipo almacen descripcion
     *
     * @return String
     */
    @Column(name = "C_TIPOALMACEN", nullable = false)
    public String getTipoAlmacenDesc() {
        return tipoAlmacenDesc;
    }

    /**
     * Setear tipo almacen descripcion
     *
     * @param tipoAlmacenDesc, String
     */
    public void setTipoAlmacenDesc(String tipoAlmacenDesc) {
        this.tipoAlmacenDesc = tipoAlmacenDesc;
    }

    /**
     * Obtener serie almacen
     *
     * @return char
     */
    @Column(name = "C_SERIE")
    public char getSerie() {
        return serie;
    }

    /**
     * Setear serie almacen
     *
     * @param serie, char
     */
    public void setSerie(char serie) {
        this.serie = serie;
    }

    /**
     * Obtener numeración consecutiva
     *
     * @return long
     */
    @Column(name = "N_CONSECUTIVO")
    public long getConsecutivo() {
        return consecutivo;
    }

    /**
     * Setear numeracion consecutiva
     *
     * @param consecutivo, long
     */
    public void setConsecutivo(long consecutivo) {
        this.consecutivo = consecutivo;
    }

    /**
     * Obtener no autorizacion como empresa de comercio electronico
     *
     * @return String
     */
    @Column(name = "C_NOAUTORIZACIONCOMERCIAL", nullable = true)
    public String getNoAutorizacionComercial() {
        return noAutorizacionComercial;
    }

    /**
     * Setear numero de autorizacion comercial
     *
     * @param noAutorizacionComercial, String
     */
    public void setNoAutorizacionComercial(String noAutorizacionComercial) {
        this.noAutorizacionComercial = noAutorizacionComercial;
    }

    /**
     * Obtiene la compania del almacen
     *
     * @return Integer
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_COMPANIA", nullable = false)
    public Compania getCompania() {
        return compania;
    }

    /**
     * Setear la compania del almacen
     *
     * @param compania, contac.modelo.entity.Compania
     */
    public void setCompania(Compania compania) {
        this.compania = compania;
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
     *
     * @return String
     */
    @Column(name = "C_ESTATUS", nullable = false)
    public String getEstatusDesc() {
        return estatusDesc;
    }

    /**
     * Setear descripcion del estatus
     *
     * @param estatusDesc, String
     */
    public void setEstatusDesc(String estatusDesc) {
        this.estatusDesc = estatusDesc;
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
    
    public String toString() {
        return getDescripcion();
    }
}
