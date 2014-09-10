package contac.modelo.entity;

/**
 * Copyright 2014 Contac Business Software. All rights reserved.
 *
 * @author Alejandro Montenenegro
 *         Date: 30/08/2014
 *         Time: 12:14 PM
 */
public enum TiposTarjeta {
    //NINGUNO(0),
    CREDOMATIC(1),
    BANPRO(2);

    public int value;

    TiposTarjeta(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
