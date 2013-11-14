package contac.modelo.entity;

/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 *
 * @author Eddy Montenenegro
 *         Date: 11/8/13
 *         Time: 4:30 PM
 */
public enum TiposPago {

    EFECTIVO(1),
    TARJETA(2),
    CHEQUE(3);

    public int value;

    TiposPago(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
