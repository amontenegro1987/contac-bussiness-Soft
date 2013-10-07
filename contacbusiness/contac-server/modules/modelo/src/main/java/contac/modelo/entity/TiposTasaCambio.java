/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.entity;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: emortiz
 * Date: 01-17-12
 * Time: 03:31 PM
 */
public enum TiposTasaCambio {

    FIJO(1),
    FLEXIBLE(2);

    private int tipoTasa;

    TiposTasaCambio(int tipoTasa) {
        this.tipoTasa = tipoTasa;
    }

    public int getValue() {
        return tipoTasa;
    }

    public String getStringValue(int value) {
        if (FIJO.getValue() == value)
            return "FIJO";
        else if (FLEXIBLE.getValue() == value)
            return "FLEXIBLE";
        else
            return "NO SOPORTADO";
    }

    public String getLabelValue() {
        if (tipoTasa == FIJO.getValue())
            return "FIJO";
        else if (tipoTasa == FLEXIBLE.getValue())
            return "FLEXIBLE";
        else
            return "NO SOPORTADO";
    }
}
