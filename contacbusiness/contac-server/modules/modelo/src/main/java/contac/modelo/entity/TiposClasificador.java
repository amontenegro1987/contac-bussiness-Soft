package contac.modelo.entity;

/**
 * Enumera los tipos de clasificador para el catalogo
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 11-02-2010
 * Time: 10:28:47 PM
 */
public enum TiposClasificador {

    SEGMENTO (1),
    FAMILIA (2),
    CLASE (3),
    ARTICULO (4);

    private int tipoClasificador;

    TiposClasificador(int tipoClasificador) {
        this.tipoClasificador = tipoClasificador;
    }

    public int getValue() {
        return tipoClasificador;
    }

    public String getValue(int tipo) {
        if (tipo == SEGMENTO.getValue())
            return "SEGMENTO";
        else if (tipo == FAMILIA.getValue())
            return "FAMILIA";
        else if (tipo == CLASE.getValue())
            return "CLASE";
        else if (tipo == ARTICULO.getValue())
            return "ARTICULO";
        else
            return "";
    }
}
