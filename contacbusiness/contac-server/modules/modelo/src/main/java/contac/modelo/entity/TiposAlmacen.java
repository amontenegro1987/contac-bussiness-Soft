package contac.modelo.entity;

/**
 * Enum representando los tipos de almacen
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 10-20-2010
 * Time: 10:51:01 PM
 */
public enum TiposAlmacen {

    CENTRAL (1),
    REGULADOR (2),
    TRANSITO (3),
    CROSSDOCKING (4),
    LOGISTICO (5);

    private int tipoAlmacen;

    TiposAlmacen(int tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public int getValue() {
        return tipoAlmacen;
    }
}
