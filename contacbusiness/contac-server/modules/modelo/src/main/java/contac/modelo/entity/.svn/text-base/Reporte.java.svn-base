package contac.modelo.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 09-04-12
 * Time: 03:14 PM
 */
@Entity
@EntityListeners(Audit.class)
@Table(name = "SEG_REPORTE")
public class Reporte implements Serializable {

    /**
     * PROPERTY NAME: Identification Id
     */
    private Integer id;
    /**
     * PROPERTY NAME: Nombre del reporte
     */
    private String nombre;
    /**
     * PROPERTY NAME: Descripcion del reporte
     */
    private String descripcion;
    /**
     * PROPERTY NAME: Url del reporte ubicacion
     */
    private String url;
    /**
     * PROPERTY NAME: Servicio asociado al que pertenece el reporte
     */
    private Servicio servicio;

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

    @Column(name = "C_URL", nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "N_ID_SERVICIO", referencedColumnName = "N_ID", nullable = false)
    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
}
