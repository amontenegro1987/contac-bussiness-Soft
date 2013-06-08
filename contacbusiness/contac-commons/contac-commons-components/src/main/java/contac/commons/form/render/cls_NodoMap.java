/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package contac.commons.form.render;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PersonalPC
 */
public class cls_NodoMap {
    
    Map<String,cls_Nodo> mapa_Nodo;
    
    public cls_NodoMap(){
        mapa_Nodo = new HashMap<String,cls_Nodo>();
    }
    public void putNode(String key,cls_Nodo node){
        this.mapa_Nodo.put(key, node);
    }
    public cls_Nodo getNode(String key){
        return this.mapa_Nodo.get(key);
    }
    public void cleanHashMap(){
        this.mapa_Nodo.clear();
    }
}
