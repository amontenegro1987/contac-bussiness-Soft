/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.commons.form.render;

import contac.commons.models.ClasificadorNode;
import contac.modelo.entity.Clasificador;
import contac.modelo.entity.TiposClasificador;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: EMontenegro
 * Date: 01-09-12
 * Time: 11:33 PM
 */
public class ClasificadorCustomIconRenderer extends DefaultTreeCellRenderer {

    private ImageIcon segmentoIcon;
    private ImageIcon claseIcon;
    private ImageIcon articuloIcon;
    private ImageIcon familiaIcon;

    public ClasificadorCustomIconRenderer() {
        segmentoIcon = new ImageIcon(ClasificadorCustomIconRenderer.class.getResource(""));
        claseIcon = new ImageIcon(ClasificadorCustomIconRenderer.class.getResource(""));
        articuloIcon = new ImageIcon(ClasificadorCustomIconRenderer.class.getResource(""));
        familiaIcon = new ImageIcon(ClasificadorCustomIconRenderer.class.getResource(""));

    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
                                                  int row, boolean hasFocus) {

        //Call super constructor
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        //Getting clasificador node
        ClasificadorNode nodeObj = (ClasificadorNode)value;
        Clasificador clasificador = nodeObj.getClasificador();

        if (clasificador.getTipoClasificador() == TiposClasificador.SEGMENTO.getValue())
            setIcon(segmentoIcon);
        else if (clasificador.getTipoClasificador() == TiposClasificador.FAMILIA.getValue())
            setIcon(familiaIcon);
        else if (clasificador.getTipoClasificador() == TiposClasificador.CLASE.getValue())
            setIcon(claseIcon);
        else
            setIcon(articuloIcon);

        return this;
    }
}
