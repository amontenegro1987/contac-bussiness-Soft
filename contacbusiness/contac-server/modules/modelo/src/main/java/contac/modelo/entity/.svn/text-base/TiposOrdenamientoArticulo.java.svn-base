/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.modelo.entity;

import java.util.Comparator;

/**
 * Tipos enumerados de ordenamiento para producto.
 * User: emortiz
 * Date: 07-09-12
 * Time: 02:59 PM
 */
public enum TiposOrdenamientoArticulo implements Comparator<Articulo> {

    PorCodigo {

        public int compare(Articulo a1, Articulo a2) {
            Integer result = validarNulos(a1, a2);
            if (result != null)
                return result;

            result = validarNulos(a1.getCodigo(), a2.getCodigo());
            if (result != null)
                return result;

            return (a1.getCodigo().compareTo(a2.getCodigo()));
        }
    };

    private static Integer validarNulos(Object o1, Object o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return -2;
        return null;
    }
}
