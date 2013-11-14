/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package contac.servicio.app.util;

import java.awt.Color;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.TreePath;

import contac.commons.form.panel.GenericFrame;

/**
 *
 * @author KjSoftware
 */
public class Util {
    
    private ImageIcon image;
    private JLabel label;
    
    public Util(){
        
    }
    public void command(String command){
        try {
            Runtime.getRuntime().exec("cmd.exe /K \""+command+"\"\n");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isRemoteConexion(){
        boolean b = false;
        return b;
    }

    public boolean empty;
    public javax.swing.JComponent componentes[];
    public void createDir(String dir){
        
        File f = new File(dir);
        if(!f.exists()){
            f.mkdir();
        }
        
    }

    //expande el arbol
    public void expandTree(JTree arbolProyectos,boolean b) {
        this.setTreeState(arbolProyectos,b);
    }

    public void setTreeState(JTree tree, boolean expanded) {
        Object root = tree.getModel().getRoot();
        setTreeState(tree, new TreePath(root), expanded);
    }

    public void setTreeState(JTree tree, TreePath path, boolean expanded) {
        Object lastNode = path.getLastPathComponent();
        for (int i = 0; i < tree.getModel().getChildCount(lastNode); i++) {
            Object child = tree.getModel().getChild(lastNode, i);
            TreePath pathToChild = path.pathByAddingChild(child);
            setTreeState(tree, pathToChild, expanded);
        }
        if (expanded) {
            tree.expandPath(path);
        } else {
            tree.collapsePath(path);
        }
    }

    //obtengo el ancho estimado para la tabla
    public int getAncho(GenericFrame mdi){
        int value = mdi.getStyle().getWindowAnchor() - mdi.getStyle().getToolAnchor();
        value = value - 70;       
        return value;
    }

    public void paintTable(JTable tbl) {
        tbl.setGridColor(new Color(55,115,215));
        tbl.setSelectionForeground(Color.BLACK);
        tbl.setSelectionBackground(new Color(191,202,242));
    }
    // retorna true si encuentra al menos 1 componente vacio o no seleccionado de la lista del arreglo
    public  boolean validate(javax.swing.JComponent [] componente){
       this.componentes = componente;
       empty=false;
        for(javax.swing.JComponent component : componentes){
            if (component instanceof javax.swing.JTextField){
                if(((javax.swing.JTextField)component).getText().trim().length()!=0){
                        continue;
                }
                else{
                    empty=true;
                    return empty;
                }
            }
            if (component instanceof javax.swing.JList){
                if(((javax.swing.JList)component).isSelectionEmpty()!=true){
                        continue;
                }
                else{
                    empty=true;
                    return empty;
                }
            }
            if (component instanceof javax.swing.JComboBox){
                if(((javax.swing.JComboBox)component).getSelectedItem().toString().trim().length()!=0){
                        continue;
                }
                else{
                    empty=true;
                    return empty;
                }
            }
            if (component instanceof javax.swing.JTextPane){
                if(((javax.swing.JTextPane)component).getText().trim().length()!=0){
                        continue;
                }
                else{
                    empty=true;
                    return empty;
                }
            }        
            
            if (component instanceof javax.swing.JTextArea){
                if(((javax.swing.JTextArea)component).getText().trim().length()!=0){
                        continue;
                }
                else{
                    empty=true;
                    return empty;
                }
            }        
            
            if (component instanceof javax.swing.JEditorPane){
                if(((javax.swing.JEditorPane)component).getText().trim().length()!=0){
                        continue;
                }
                else{
                    empty=true;
                    return empty;
                }
            }
            if (component instanceof javax.swing.JPasswordField){
                if(((javax.swing.JPasswordField)component).getPassword().toString().length()!=0){
                        continue;
                }
                else{
                    empty=true;
                    return empty;
                }
            }
            
            if (component instanceof JLabel){
            continue;
            }
            
        }
        if (empty!=true){
            empty=false;
        }
        return empty;
    }
    public void cleanTableModel(JTable tbl){
        while(tbl.getRowCount()>0){
            DefaultTableModel dtm = (DefaultTableModel)tbl.getModel();
            dtm.removeRow(0);
            tbl.setModel(dtm);
        }
    }
    public void cleanTableModel(TableModel tblm){
        while(tblm.getRowCount()>0){
            DefaultTableModel dtm = (DefaultTableModel) tblm;
            dtm.removeRow(0);
            tblm = dtm;
        }
    }
    public String getFecha(com.toedter.calendar.JDateChooser dateChooser){
        String fecha;
        com.toedter.calendar.JTextFieldDateEditor text = (com.toedter.calendar.JTextFieldDateEditor)dateChooser.getComponent(1);
        String dia,mes,ano;
        try{
            dia =  text.getText().substring(0,2);
            mes = text.getText().substring(3,5);
            ano = text.getText().substring(6,10);
                

        fecha=ano+"-"+mes+"-"+dia;
        }catch(Exception ex){
            fecha="";
        }
        return fecha;
    }
    
    
    
}

