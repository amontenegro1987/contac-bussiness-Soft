package contac.modelo.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-22-2008
 * Time: 10:26:43 AM
 */
public enum EstadosProducto {

    INGRESADO ("INGRESADO"),
    VIGENTE ("VIGENTE"),
    CERRADO ("CERRADO"),
    LIQUIDACION ("LIQUIDACION"),
    OFERTA ("OFERTA"),
    DEBAJA ("DEBAJA");

    public final String estado;

    EstadosProducto(String estado){
        this.estado = estado;
    }

    public String getNombre(){
        return estado;
    }
}
