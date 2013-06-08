package contac.commons.models;

import contac.modelo.entity.Clasificador;
import contac.modelo.entity.TiposClasificador;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 01-17-2011
 * Time: 04:04:08 PM
 */
public class ClasificadorNode extends DefaultMutableTreeNode {

    //*********************************
    //Tipo clasificador nodo
    //**
    //Implementar el numerador para
    //tipos de clasificador
    //@see contac.modelo.entity.TipoClasificador
    //*********************************
    private Clasificador clasificador;

    /**
     * Constructor
     * @param clasificador, Clasificador
     */
    public ClasificadorNode(Clasificador clasificador) {

        //Constructor
        super(clasificador);

        //Setting values
        this.clasificador = clasificador;

        //Config Label node
        configLabelNode();
    }

    //*****************************************************
    //GETTERS & SETTERS
    //*****************************************************

    public Clasificador getClasificador() {
        return clasificador;
    }

    public void setClasificador(Clasificador clasificador) {
        this.clasificador = clasificador;
    }

    //******************************************************
    //Return object description
    //******************************************************
    public String toString() {
        return this.clasificador.getCbs() + "-" + this.clasificador.getDescripcion() + "-" +
                TiposClasificador.SEGMENTO.getValue(this.clasificador.getTipoClasificador());
    }

    //*****************************************************
    //UTILITY METHODS
    //*****************************************************

    /**
     * Config nombre de etiqueta del nodo
     */
    private void configLabelNode() {
        setUserObject(this);
    }
}
