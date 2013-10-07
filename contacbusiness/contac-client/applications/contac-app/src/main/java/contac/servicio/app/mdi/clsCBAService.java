/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contac.servicio.app.mdi;

import contac.net.cls_NetServer;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * @author KjSoftware
 */
public class clsCBAService extends JFrame {


    private TrayIcon trayIcon;
    private JPanel panel;
    private JButton boton;

    cls_NetServer server = null;


    public clsCBAService() {
        propiedades();

        construirComponentes();
        propiedadesComponentes();
        anadirTrayIcon();

        this.startService();
    }

    private void startService() {
        server = new cls_NetServer();
    }

    private void anadirTrayIcon() {
        if (SystemTray.isSupported()) {

            SystemTray tray = SystemTray.getSystemTray();

            ImageIcon imagen = new ImageIcon(getClass().getResource("/contac/servicio/app/images/CBA_Tray2.png"));
            ActionListener eventoSalir = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            };

            ActionListener eventoAcercaDe = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    startService();
                    String var;
                    if (server.isRunning()) {
                        var = "Corriendo...";
                    } else {
                        var = "Apagado...";
                    }
                    trayIcon.setToolTip("Contact Business Administration Inventory V1.0\n" +
                            "Estado: " + var);
                }
            };

            final PopupMenu popup = new PopupMenu();
            MenuItem mi4 = new MenuItem("Salir");
            MenuItem mi5 = new MenuItem("Iniciar Servicio");

            popup.add(mi4);
            popup.addSeparator();
            popup.add(mi5);

            mi4.addActionListener(eventoSalir);
            mi5.addActionListener(eventoAcercaDe);

            trayIcon = new TrayIcon(imagen.getImage(), "Servicio de Red CBA inventory V1.0\n" + "Estado: Activo", null);
            trayIcon.setImageAutoSize(true);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {

            }
        }
    }

    private void construirComponentes() {
        panel = new JPanel();
        boton = new JButton("Hazme click ...");
    }

    private void propiedadesComponentes() {
        panel.setBackground(Color.BLACK);
        add(panel);
        panel.add(boton);
    }

    private void propiedades() {
        setSize(200, 70);
        setTitle("Icon Tray");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        clsCBAService ventana = new clsCBAService();
    }
}
