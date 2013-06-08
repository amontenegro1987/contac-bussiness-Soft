package contac.modelo.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-27-2008
 * Time: 10:13:38 PM
 */
public enum TiposEntrada {

    ENTRADA_ORDINARIA (1),
    FACTURA_COMERCIAL (2),
    POLIZA_IMPORTACION (3);

    public final int tipoEntrada;

    TiposEntrada(int tipoEntrada){
        this.tipoEntrada = tipoEntrada;
    }

    public int getValue(){
        return tipoEntrada;
    }

    public String getNombre() {
        if (tipoEntrada == ENTRADA_ORDINARIA.getValue()) {
            return "ENTRADA ORDINARIA";
        } else if (tipoEntrada == FACTURA_COMERCIAL.getValue()) {
            return "FACTURA COMERCIAL";
        } else if (tipoEntrada == POLIZA_IMPORTACION.getValue()) {
            return "POLIZA IMPORTACION";
        } else {
            return "NO SOPORTADO";
        }
    }
}
