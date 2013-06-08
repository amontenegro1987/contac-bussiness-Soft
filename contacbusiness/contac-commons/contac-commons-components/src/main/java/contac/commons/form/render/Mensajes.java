/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package contac.commons.form.render;

import java.awt.Component;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;


/**
 *
 * @author KjSoftware
 */

public class Mensajes extends JOptionPane{
    
    private String e_title = "Error - CBA Inventory";
    private String w_title = "Precaución - CBA Inventory";
    private String i_title = "Información - CBA Inventory";
    
    
    public Mensajes(){
        
    }
    
    //MUESTRA UN MENSAJE DE ERROR
    public void Error(Component comp,String message){
        JOptionPane.showMessageDialog(comp,message,this.getError_title(),Mensajes.ERROR_MESSAGE);
    }
    //MUESTRA UN MENSAJE DE ADVERTENCIA
    public void Warning(Component comp,String message){
        JOptionPane.showMessageDialog(comp,message,this.getWarning_title(),Mensajes.WARNING_MESSAGE);
    }
    //MUESTRA UN MENSAJE DE INFORMACION
    public void Information(Component comp,String message){
        JOptionPane.showMessageDialog(comp,message,this.getInformation_title(),Mensajes.INFORMATION_MESSAGE);
    }
    public void ErrorPane(String mensaje,String detalle,String categoria,Exception ex2,Level level){
        
        Exception cause = new Exception("Origen del Error:");
        ex2 = new Exception("Detalles:",cause);
        ErrorInfo info = new ErrorInfo("Error - CBA Inventory",mensaje,detalle,categoria,ex2,level,null);
        JXErrorPane.showDialog(null,info);
        
    }    

    //METODOS SETTER Y GETTER PARA LOS TITULOS LOS MENSAJES
    public String getError_title() {
        return e_title;
    }

    public void setError_title(String e_title) {
        this.e_title = e_title;
    }

    public String getWarning_title() {
        return w_title;
    }

    public void setWarning_title(String w_title) {
        this.w_title = w_title;
    }

    public String getInformation_title() {
        return i_title;
    }

    public void setI_title(String i_title) {
        this.i_title = i_title;
    }
    
    
}
