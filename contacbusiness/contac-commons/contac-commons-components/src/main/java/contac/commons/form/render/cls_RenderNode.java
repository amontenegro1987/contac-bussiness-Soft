/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package contac.commons.form.render;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author KjSoftware
 */
public class cls_RenderNode extends DefaultTreeCellRenderer {

    private String texto;
    private JTree tree;
    private boolean isDropCell;

    public cls_RenderNode() {

    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {
        String stringValue = tree.convertValueToText(value, sel,
                expanded, leaf, row, hasFocus);

        cls_Nodo nodo = (cls_Nodo) value;

        this.setText(stringValue);

        this.tree = tree;
        this.hasFocus = hasFocus;
        setText(stringValue);

        Color fg = null;
        isDropCell = false;


        JTree.DropLocation dropLocation = tree.getDropLocation();
        if (dropLocation != null
                && dropLocation.getChildIndex() == -1
                && tree.getRowForPath(dropLocation.getPath()) == row) {

            Color col = UIManager.getColor("Tree.dropCellForeground");
            if (col != null) {
                fg = col;
            } else {
                fg = getTextSelectionColor();
            }

            isDropCell = true;
        } else if (sel) {
            fg = getTextSelectionColor();
        } else {
            fg = getTextNonSelectionColor();
        }

        setForeground(fg);

        if (tree.isEnabled()) {
            if (nodo.getDescripcion() != null) {
                if (!nodo.getDescripcion().equals("")) {
                    this.setToolTipText(nodo.getDescripcion());
                }
            }
        }

        //si el arbol esta habilitado y el nodo tiene uno de los siguientes nombres
        //entonces se establece como tipo de letra bold al texto del nodo
        if (tree.isEnabled()) {
            setEnabled(true);
            if (stringValue.equals("PROYECTOS FNG") || stringValue.equals("CATALOGO DE CUENTAS")) {
                this.setFont(new Font(tree.getFont().getFontName(), Font.BOLD, tree.getFont().getSize()));
            } else {
                this.setFont(new Font(tree.getFont().getFontName(), Font.PLAIN, tree.getFont().getSize()));
            }
        }

        //en caso que el arbol se encuentre deshabilitado se asignan estos iconos
        if (!tree.isEnabled()) {
            setEnabled(false);
            if (leaf) {
                setDisabledIcon(this.getImage(nodo.getLeafIcon()));
            } else if (expanded) {
                setDisabledIcon(this.getImage(nodo.getOpenIcon()));
            } else {
                setDisabledIcon(this.getImage(nodo.getClosedIcon()));
            }
        } else {
            //si esta habilitado el arbol se asignan estos iconos
            setEnabled(true);
            if (leaf) {//SI ES UNA HOJA
                setIcon(this.getImage(nodo.getLeafIcon()));
            } else if (expanded) {
                setIcon(this.getImage(nodo.getOpenIcon()));
            } else {
                setIcon(this.getImage(nodo.getClosedIcon()));
            }
        }
        setComponentOrientation(tree.getComponentOrientation());

        selected = sel;

        return this;
    }

    //obtenemos la imagen del texto tipo que obtenemos del nodo
    public Icon getImage(String tipo) {
        return new ImageIcon("/contac/resources/images/" + tipo);
    }

}
