package contac.modelo.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa un DocumentoComercial abstract entity business
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 11-08-2010
 * Time: 11:23:33 PM
 */
@MappedSuperclass
public abstract class DocumentoComercial implements java.io.Serializable {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: id
     */
    private Integer id;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: noDocumento
     */
    private long noDocumento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: serie
     */
    private char serie;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: cliente
     */
    private Cliente cliente;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: almacen
     */
    private Almacen almacen;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: moneda
     */
    private Moneda moneda;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: tasaCambio
     */
    private BigDecimal tasaCambio;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: nombreCliente
     */
    private String nombreCliente;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: terminosPago
     */
    private int terminosPago;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: direccion
     */
    private Direccion direccionEntrega;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: fechaAlta
     */
    private Date fechaAlta;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: retencionFuente
     */
    private BigDecimal retencionFuente;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: retencionMunicipal
     */
    private BigDecimal retencionMunicipal;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: montoDescuento
     */
    private BigDecimal montoDescuento;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: montoBruto
     */
    private BigDecimal montoBruto;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: montoNeto
     */
    private BigDecimal montoNeto;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Monto de Impuesto
     */
    private BigDecimal montoIVA;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Porcentaje de impuesto IVA
     */
    private BigDecimal porcIVA;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Porcentaje de retencion fuente
     */
    private BigDecimal porcRetFuente;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Porcentaje de retencion municipal
     */
    private BigDecimal porcRetMunicipal;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Porcentaje de descuento global
     */
    private BigDecimal porcDescuento;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: exonerada
     */
    private boolean exonerada;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Calcular retencion fuente
     */
    private boolean retencionF;

    /**
     * <!--begin-user-doc -->
     * <!--end-user-doc -->
     * PROPERTY NAME: EXONERADA
     */
    private boolean excenta;
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Calcular retencion municipal
     */
    private boolean retencionM;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * PROPERTY NAME: Estado movimiento
     */
    private EstadoMovimiento estadoMovimiento;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "N_NODOCUMENTO")
    public long getNoDocumento() {
        return noDocumento;
    }

    public void setNoDocumento(long noDocumento) {
        this.noDocumento = noDocumento;
    }

    @Column(name = "C_SERIE")
    public char getSerie() {
        return serie;
    }

    public void setSerie(char serie) {
        this.serie = serie;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_CLIENTE", referencedColumnName = "N_ID", nullable = false)
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_ALMACEN", referencedColumnName = "N_ID", nullable = false)
    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_MONEDA", referencedColumnName = "N_ID", nullable = false)
    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    @Column(name = "N_TASA_CAMBIO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(BigDecimal tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    @Column(name = "C_NOMBRECLIENTE", nullable = false)
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    @Column(name = "N_TERMINOSPAGO", nullable = false)
    public int getTerminosPago() {
        return terminosPago;
    }

    public void setTerminosPago(int terminosPago) {
        this.terminosPago = terminosPago;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_DIRECCION", referencedColumnName = "N_ID", nullable = false)
    public Direccion getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(Direccion direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    @Column(name = "D_FECHAALTA", nullable = false)
    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Column(name = "N_RETENCIONFUENTE", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getRetencionFuente() {
        return retencionFuente;
    }

    public void setRetencionFuente(BigDecimal retencionFuente) {
        this.retencionFuente = retencionFuente;
    }

    @Column(name = "N_RETENCIONMUNICIPAL", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getRetencionMunicipal() {
        return retencionMunicipal;
    }

    public void setRetencionMunicipal(BigDecimal retencionMunicipal) {
        this.retencionMunicipal = retencionMunicipal;
    }

    @Column(name = "N_MONTODESCUENTO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(BigDecimal montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    @Column(name = "N_MONTOBRUTO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoBruto() {
        return montoBruto;
    }

    public void setMontoBruto(BigDecimal montoBruto) {
        this.montoBruto = montoBruto;
    }

    @Column(name = "N_MONTONETO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoNeto() {
        return montoNeto;
    }

    public void setMontoNeto(BigDecimal montoNeto) {
        this.montoNeto = montoNeto;
    }

    @Column(name = "N_MONTOIVA", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getMontoIVA() {
        return montoIVA;
    }

    public void setMontoIVA(BigDecimal montoIVA) {
        this.montoIVA = montoIVA;
    }

    @Column(name = "B_EXONERADA", nullable = false)
    public boolean isExonerada() {
        return exonerada;
    }

    public void setExonerada(boolean exonerada) {
        this.exonerada = exonerada;
    }

    @Column(name = "B_EXCENTA", nullable = false)
    public boolean isExcenta(){
        return excenta;
    }

    public void setExcenta(boolean excenta){
        this.excenta = excenta;
    }

    @Column(name = "B_RETENCION_FUENTE", nullable = false)
    public boolean isRetencionF() {
        return retencionF;
    }

    public void setRetencionF(boolean retencionF) {
        this.retencionF = retencionF;
    }

    @Column(name = "B_RETENCION_MUNICIPAL", nullable = false)
    public boolean isRetencionM() {
        return retencionM;
    }

    public void setRetencionM(boolean retencionM) {
        this.retencionM = retencionM;
    }

    @Column(name = "N_PORC_IVA", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getPorcIVA() {
        return porcIVA;
    }

    public void setPorcIVA(BigDecimal porcIVA) {
        this.porcIVA = porcIVA;
    }

    @Column(name = "N_PORC_RET_FUENTE", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getPorcRetFuente() {
        return porcRetFuente;
    }

    public void setPorcRetFuente(BigDecimal porcRetFuente) {
        this.porcRetFuente = porcRetFuente;
    }

    @Column(name = "N_PORC_RET_MUNICIPAL", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getPorcRetMunicipal() {
        return porcRetMunicipal;
    }

    public void setPorcRetMunicipal(BigDecimal porcRetMunicipal) {
        this.porcRetMunicipal = porcRetMunicipal;
    }

    @Column(name = "N_PORC_DESCUENTO", precision = 19, scale = 4, columnDefinition = "decimal(19, 4)", nullable = false)
    public BigDecimal getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(BigDecimal porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_ESTADO", nullable = false)
    public EstadoMovimiento getEstadoMovimiento() {
        return estadoMovimiento;
    }

    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
        this.estadoMovimiento = estadoMovimiento;
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
