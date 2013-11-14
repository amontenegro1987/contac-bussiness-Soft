package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: Eddy Montenegro
 * Date: 10-20-2008
 * Time: 11:26:24 PM
 */

/**
 * TasaCambio Contac Business
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "GEN_TASACAMBIO")
public class TasaCambio implements Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: id
     */
    private Integer id;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tipo tasa cambio
     */
    private int tipoTasaCambio;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tasa  Conversion
     */
    private BigDecimal tasaConversion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: fecha Conversion
     */
    private Date fechaConversion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: moneda  Referencia
     */
    private contac.modelo.entity.Moneda monedaReferencia;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: moneda  Conversion
     */
    private contac.modelo.entity.Moneda monedaConversion;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: banco
     */
    private contac.modelo.entity.Banco banco;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: activa facturacion
     */
    private boolean activaFacturacion;

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
     * Obtener el id de TasaCambio Garsa Business
     *
     * @return Integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID_TASACAMBIO")
    public Integer getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear el id de TasaCambio Garsa Business
     *
     * @param id, nro. de registro del objeto
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retornar tipo de cambio
     *
     * @return int
     */
    @Column(name = "N_TIPO_CAMBIO", nullable = false)
    public int getTipoTasaCambio() {
        return tipoTasaCambio;
    }

    /**
     * Setear tipo de cambio
     *
     * @param tipoTasaCambio, int
     */
    public void setTipoTasaCambio(int tipoTasaCambio) {
        this.tipoTasaCambio = tipoTasaCambio;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la tasa de TasaConversion Garsa Business
     *
     * @return Double
     */
    @Column(name = "N_TASA_CONVERSION", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getTasaConversion() {
        return tasaConversion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear la tasa de TasaConversion Garsa Business
     *
     * @param tasaConversion, tasa de cambio segun conversion
     */
    public void setTasaConversion(BigDecimal tasaConversion) {
        this.tasaConversion = tasaConversion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener el la fecha de conversion Garsa Business
     *
     * @return FechaConversion
     */
    @Column(name = "D_FECHA_CONVERSION", nullable = false)
    public Date getFechaConversion() {
        return fechaConversion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear el estado de TasaCambio Garsa Business
     *
     * @param fechaConversion, estado de la tasa de cambio
     */
    public void setFechaConversion(Date fechaConversion) {
        this.fechaConversion = fechaConversion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la moneda de Referencia de TasaCambio Garsa Business
     *
     * @return Moneda Referencia
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_MONEDA_REFERENCIA", nullable = false)
    public contac.modelo.entity.Moneda getMonedaReferencia() {
        return monedaReferencia;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear la moneda de Referencia de TasaCambio Garsa Business
     *
     * @param monedaReferencia, moneda de la tasa de cambio
     */
    public void setMonedaReferencia(contac.modelo.entity.Moneda monedaReferencia) {
        this.monedaReferencia = monedaReferencia;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Obtener la moneda de Conversion de TasaCambio Garsa Business
     *
     * @return Moneda Conversion
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_MONEDA_CONVERSION", nullable = false)
    public contac.modelo.entity.Moneda getMonedaConversion() {
        return monedaConversion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * Setear la moneda de Conversion de TasaCambio Garsa Business
     *
     * @param monedaConversion, moneda de conversion la tasa de cambio
     */
    public void setMonedaConversion(contac.modelo.entity.Moneda monedaConversion) {
        this.monedaConversion = monedaConversion;
    }

    /**
     * Obtener banco
     *
     * @return Banco
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_BANCO", nullable = false)
    public contac.modelo.entity.Banco getBanco() {
        return banco;
    }

    /**
     * Setear banco
     *
     * @param banco, Banco
     */
    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    @Column(name = "ACTIVA_FACTURACION", nullable = false)
    public boolean isActivaFacturacion() {
        return activaFacturacion;
    }

    public void setActivaFacturacion(boolean activaFacturacion) {
        this.activaFacturacion = activaFacturacion;
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
        return this.monedaConversion.getSimbolo() + "" + this.tasaConversion.toString();
    }
}
