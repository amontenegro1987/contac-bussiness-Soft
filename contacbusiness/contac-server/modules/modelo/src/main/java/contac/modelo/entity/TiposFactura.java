package contac.modelo.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-27-2008
 * Time: 10:14:25 PM
 */
public enum TiposFactura {

    CONTADO(1),
    CREDITO(2);

    private int tipoFactura;

    TiposFactura(int tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public int getValue() {
        return tipoFactura;
    }

    public String getNombre() {
        if (tipoFactura == CONTADO.getValue()) {
            return "CONTADO";
        } else if (tipoFactura == CREDITO.getValue()) {
            return "CREDITO";
        } else {
            return "NO SOPORTADO";
        }
    }
}
