package contac.modelo.entity;

import java.util.Comparator;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: emortiz
 * Date: 11-21-12
 * Time: 02:50 PM
 */
public enum TiposOrdenamientoMovimientoInventario implements Comparator<MovimientoInventario> {

    PorFechaAlta {

        public int compare(MovimientoInventario movimiento1, MovimientoInventario movimiento2) {
            Integer result = validarNulos(movimiento1, movimiento2);

            if (result != null)
                return result;

            result = validarNulos(movimiento1.getFechaAlta(), movimiento2.getFechaAlta());
            if (result != null)
                return result;

            return (movimiento1.getFechaAlta().compareTo(movimiento2.getFechaAlta()));
        }
    };

    private static Integer validarNulos(Object o1, Object o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return -2;
        return null;
    }
}
