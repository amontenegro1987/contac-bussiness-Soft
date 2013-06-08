/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package contac.net;


import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 *
 * @author KjSoftware
 */
public class cls_Net {

    //variable que almacena el nombre del servidor
    private String HOST = "localhost";
    private static final int PUERTO = 23386;
    private boolean status = false;

    public cls_Net(){
        try {

            //obtengo el servidor aqui
            this.HOST = "LOCALHOST";

            Socket skCliente = new Socket(HOST, PUERTO);
            InputStream aux = skCliente.getInputStream();
            DataInputStream flujo = new DataInputStream(aux);

            //realizo la condicion de codificacion aqui
            if(flujo.readUTF().equals("ON")){
                this.status = true;
            }
            skCliente.close();

        } catch (Exception e) {
            this.status = false;
        }
    }
    public boolean getStatus(){
        return this.status;
    }
    public static void main(String[] arg) {
        new cls_Net();
    }
    
}
