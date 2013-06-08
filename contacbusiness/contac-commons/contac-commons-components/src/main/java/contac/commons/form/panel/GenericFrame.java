package contac.commons.form.panel;

import org.jdesktop.swingx.JXStatusBar;
import org.pushingpixels.flamingo.ribbon.JRibbonFrame;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-27-2010
 * Time: 09:19:28 PM
 */
public abstract class GenericFrame extends JRibbonFrame {

    /**
     * Principal Desk
     */
    protected JDesktopPane principalDesk;
    /**
     * Status Bar
     */
    protected JXStatusBar statusBar;

    /**
     * Style frame
     */
    protected StyleGenericFrame style;

    /**
     * Current LanguageLocale
     */
    protected Locale currLocale;

    /**
     * Default Constructor
     */
    public GenericFrame() {

        //Init basic components
        principalDesk = new JDesktopPane();
        statusBar = new JXStatusBar();

        //Status Bar configuration
        statusBar.setPreferredSize(new Dimension(27, 25));
        statusBar.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        //TODO: Setting by developement use in Nicaragua
        currLocale = new Locale("es", "NIC");

        //Set position
        getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);
        getContentPane().add(principalDesk, java.awt.BorderLayout.CENTER);

        //Set default close operation
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
    }

    //**********************************************************************************
    //EVENTS
    //**********************************************************************************

    /**
     * Form close operation
     * @param evt, event
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        this.closeFrame();
    }

    /**
     * Return principalDesk
     *
     * @return JDesktopPane
     */
    public JDesktopPane getPrincipalDesk() {
        return principalDesk;
    }

    /**
     * Return status bar
     * @return JXStatusBar
     */
    public JXStatusBar getStatusBar() {
        return statusBar;
    }

    /**
     * Return style of frame
     * @return StyleFrame
     */
    public StyleGenericFrame getStyle() {
        return style;
    }

    /**
     * Return current internationalization setting
     * @return Locale
     */
    public Locale getCurrLocale() {
        return currLocale;
    }

    //**********************************************************************************
    //ABSTRACT METHODS IMPLEMENTATIONS
    //**********************************************************************************
        
    /**
     * Close frame method abstract
     */
    public abstract void closeFrame();

}
