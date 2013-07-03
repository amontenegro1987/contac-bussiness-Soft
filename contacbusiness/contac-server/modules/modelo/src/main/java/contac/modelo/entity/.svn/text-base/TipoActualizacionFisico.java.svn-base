/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.entity;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 01-13-12
 * Time: 03:31 PM
 */
public enum TipoActualizacionFisico {

    TOTAL(1),
    PARCIAL(2);

    private int value;

    TipoActualizacionFisico(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getNombre() {
        if (TOTAL.getValue() == value)
            return "TOTAL";
        else if (PARCIAL.getValue() == value)
            return "PARCIAL";
        else
            return "NO SOPORTADO";
    }

    public String getStringValue(int value) {
        if (TOTAL.getValue() == value)
            return "TOTAL";
        else if (PARCIAL.getValue() == value)
            return "PARCIAL";
        else
            return "NO SOPORTADO";
    }
}
