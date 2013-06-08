package contac.modelo.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Servicio de implementacion dentro del sistema
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-15-2010
 * Time: 07:22:57 AM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "SEG_SERVICIO")
public class Servicio implements java.io.Serializable{

/**
     * FIELD NAME : id
     */
    private Integer id;
    /**
     * FIELD NAME : name
     */
    private String nombre;
    /**
     * FIELD NAME : description
     */
    private String descripcion;
    /**
     * FIELD NAME: module
     */
    private Servicio servicio;

    /**
     * FIELD NAME : ctime
     */
    private Date ctime;
    /**
     * FIELD NAME : cuser
     */
    private String cuser;
    /**
     * FIELD NAME : mtime
     */
    private Date mtime;
    /**
     * FIELD NAME : muser
     */
    private String muser;

    // ==================================<METODOS GETTTER AND SETTERS>=================================================

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "N_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "C_NOMBRE", nullable  = false)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "C_DESCRIPCION", nullable = false)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_SERVICIOPADRE", nullable = true)
    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Column(name = "D_CTIME", nullable = false)
    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    @Column(name = "C_CUSER", nullable = false)
    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    @Column(name = "D_MTIME", nullable = false)
    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    @Column(name = "C_MUSER", nullable = false)
    public String getMuser() {
        return muser;
    }

    public void setMuser(String muser) {
        this.muser = muser;
    }
}
