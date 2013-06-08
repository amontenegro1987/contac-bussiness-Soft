/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package contac.commons.form.render;

import javax.swing.tree.DefaultMutableTreeNode;


/**
 *
 * @author PersonalPC
 */
public class cls_Nodo extends DefaultMutableTreeNode{
    
    private String[] type;
    private String Id,
            parentId,
            descripcion = "",
            estado ="1",
            name;
 
    
    public cls_Nodo(String nombreNodo){
        this.setUserObject(nombreNodo);
    }
    public void setTipoNodo(String[] type){
        this.type = type;
    }
    public String getTipoNodo(){
        return this.type[0];
    }
    public String getNodoKey(){
        return this.type[0];
    }
    public String getLeafIcon(){
        return this.type[1];
    }
    public String getClosedIcon(){
        return this.type[2];
    }
    public String getOpenIcon(){
        return this.type[3];
    }
    public String getId(){
        return this.Id;
    }
    public void setId(String id){
        this.Id = id;
    }
    public String getParentId(){
        return this.parentId;
    }
    public void setParentId(String parentId){
        this.parentId = parentId;
    }
    public void setDescripcion(String d){
        this.descripcion = d;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    //REPRESENTACION DE ICONOS          TIPO-  HOJA-      CLOSED-    OPEN-
    public static final String[] Raiz ={"Raiz","Proyecto C.png","Proyecto C.png","Proyecto A.png"};
    public static final String[] Raiz_Presupuesto ={"Raiz_Presupuesto","Presupuesto C.png","Presupuesto C.png","Presupuesto A.png"};
    public static final String[] Proyectos =  {"Proyectos","cabinet_256x48 (2).png","cabinet_256x48 (2).png","cabinet_256x48 (2).png"};
    public static final String[] ComprobantesDiario = {"RegistrosCDiario","contract_add_256x16.png","contract_add_256x16.png","contract_add_256x16.png"};
    public static final String[] ComprobantesCheque = {"RegistrosCCheque","check_add_256x16.png","check_add_256x16.png","check_add_256x16.png"};
    
    public static final String[] Reportes = {"Reportes","Reportes.png","Reportes.png","Reportes.png"};
    public static final String[] Presupuesto = {"Presupuesto_Guardado","invoice_search_256x22.png","invoice_search_256x22.png","invoice_search_256x22.png"};
    public static final String[] Presupuesto_Asignado = {"Presupuesto_Asignado","invoice_search_256x22.png","invoice_search_256x22.png","invoice_search_256x22.png"};
    
    public static final String[] Raiz_Catalogo ={"Raiz","Raiz Cat.png","Raiz Cat.png","Raiz Cat A.png"};

    public static final String[] Cuenta_Control = {"Control","Cuenta Control.png","Cuenta Control.png","Cuenta Control.png"};
    public static final String[] Cuenta_Registro = {"Registro","Registro A.png","Registro A.png","Registro A.png"};
    public static final String[] Cuenta_Control_Inactiva = {"Control_Inactiva","Cuenta Control I.png","Cuenta Control I.png","Cuenta Control I.png"};
    public static final String[] Cuenta_Registro_Inactiva = {"Registro_Inactiva","Registro I.png","Registro I.png","Registro I.png"};

    public static final String[] Nuevo_Proyecto = {"Nuevo_Proyecto","cabinet_add_256x22.png","cabinet_add_256x22.png","cabinet_add_256x22.png"};
}
