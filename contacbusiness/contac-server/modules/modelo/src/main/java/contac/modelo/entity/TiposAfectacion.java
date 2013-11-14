package contac.modelo.entity;

/**
 * Enum representando tipo de afectacion
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 11-12-2010
 * Time: 02:30:37 PM
 */
public enum TiposAfectacion {

    POSITIVA(1),
    NEGATIVA(-1);

    private int tipoAfectacion;

    TiposAfectacion(int tipoAfectacion){
        this.tipoAfectacion =  tipoAfectacion;
    }

    public int getValue() {
        return tipoAfectacion;
    }
}
