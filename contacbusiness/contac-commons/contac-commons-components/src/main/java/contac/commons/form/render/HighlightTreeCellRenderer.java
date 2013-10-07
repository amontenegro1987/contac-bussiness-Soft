/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package contac.commons.form.render;

import contac.commons.models.ClasificadorNode;
import org.pushingpixels.substance.api.renderers.SubstanceDefaultTreeCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * User: emortiz
 * Date: 11-19-12
 * Time: 11:39 AM
 */
public class HighlightTreeCellRenderer extends SubstanceDefaultTreeCellRenderer {

    private static final Color ROLLOVER_ROW_COLOR = new Color(220, 240, 255);

    private boolean rollOver = false;
    private long cbs;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {

        ClasificadorNode clasificadorNode = (ClasificadorNode)value;

        JComponent c = (JComponent)super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, hasFocus);

        if(isSelected) {
            c.setBackground(getBackground());
        }else{
            rollOver = (cbs == clasificadorNode.getClasificador().getCbs()) ? true : false;
            if (rollOver) {
                c.setForeground(Color.WHITE);
                c.setBackground(Color.BLACK);
            }
        }

        return c;
    }

    public void setCbs(long cbs) {
        this.cbs = cbs;
    }
}
