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
 * Time: 11:41 AM
 */
public enum TiposOrdenamientoProducto implements Comparator<Producto> {
    
    PorCodigo {
      
        public int compare(Producto producto1, Producto producto2) {
            Integer result = validarNulos(producto1, producto2);
            if (result != null)
                return result;
            
            result = validarNulos(producto1.getCodigo(), producto2.getCodigo());
            if (result != null)
                return result;

            return (producto1.getCodigo().compareTo(producto2.getCodigo()));
        }
    };
    
    private static Integer validarNulos(Object o1, Object o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return -2;
        return null;
    }
}
