package contac.modelo.entity;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 11-09-11
 * Time: 10:25 PM
 */
public enum EstadosMovimiento {

    INGRESADO("IN"),
    ANULADO("AN"),
    PENDIENTE("PD"),
    APLICADO("AP"),
    PROCESADO("PC"),
    CALCULADO("CA"),
    PAGADO("PG"),
    IMPRESO("IM");

    public final String estado;

    EstadosMovimiento(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
