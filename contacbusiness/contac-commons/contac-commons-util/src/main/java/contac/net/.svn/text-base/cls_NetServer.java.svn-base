/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package contac.net;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author KjSoftware
 */
public class cls_NetServer {

    private static final int PUERTO = 23386;
    private ServerSocket skServidor;
    public cls_NetServer(){
        try {

            System.out.println("Iniciando NetServer PRO...");
            skServidor = new ServerSocket(PUERTO);
            System.out.println("NetServer On Status: ON, PORT:" + PUERTO);

            while(true){
                Socket skCliente = skServidor.accept();
                OutputStream aux = skCliente.getOutputStream();
                DataOutputStream flujo = new DataOutputStream(aux);

                this.sendCommand();
                flujo.writeUTF("ON");
                skCliente.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean isRunning(){
        return true;
    }
    private void sendCommand(){
        System.out.println("mysql -uroot -proot -e\"set global max_sp_recursion_depth = 255\"");
        this.command("mysql -uroot -proot -e\"set global max_sp_recursion_depth = 255\"");
    }
    public void command(String command){
        try {
            Runtime.getRuntime().exec("cmd.exe /K \""+command+"\"\n");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] arg) {
        new cls_NetServer();
    }
}
