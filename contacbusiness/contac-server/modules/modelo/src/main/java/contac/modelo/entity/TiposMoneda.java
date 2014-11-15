package contac.modelo.entity;

/**
 * Copyright 2014 Contac Business Software. All rights reserved.
 *
 * @author Alejandro Montenenegro
 *         Date: 13/10/2014
 *         Time: 09:10 PM
 */
public enum TiposMoneda {
      CORDOBA(1),
      DOLAR_EEUU(2),
      EURO(3);

    public int value;

    TiposMoneda(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
