/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.modelo.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * User: Eddy
 * Date: 04-20-11
 * Time: 09:32 AM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "GEN_TIPO_IDENTIFICACION")
public class TipoIdentificacion implements java.io.Serializable {

    /**
     * FIELD NAME : id
     */
    private Integer id;
    /**
     * FIELD NAME : nombre
     */
    private String nombre;
    /**
     * FIELD NAME : descripcion
     */
    private String descripcion;
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
    @Column(name = "N_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "C_NOMBRE", nullable = false)
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
