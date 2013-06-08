package contac.commons.form.label;

import com.ezware.dialog.task.TaskDialog;

import java.awt.*;

/**
 * Panel Error Option
 * User: Eddy Montenegro
 * Date: 7/14/11
 * Time: 4:13 PM
 */
public class JOptionErrorPane {

    private static final String MESSAGE_TITLE_INFO = "Contac Message Info";
    private static final String MESSAGE_TITLE_ERROR = "Contac Message Error";
    private static final String MESSAGE_TITLE_WARNING = "Contac Message Warning";

    /**
     * Show message info
     * @param parent, java.awt.window
     * @param instruction, String
     * @param messageInfo, String
     */
    public static void showMessageInfo(Window parent, String instruction, String messageInfo) {
        TaskDialog dlg = new TaskDialog(parent, "");
        dlg.setTitle(MESSAGE_TITLE_INFO);
        dlg.setInstruction(instruction);
        dlg.setText(messageInfo);
        dlg.setIcon(TaskDialog.StandardIcon.INFO);
        dlg.show();
    }

    /**
     * Show message error
     * @param parent, java.awt.Window
     * @param instruction, String
     * @param messageError, String
     */
    public static void showMessageError(Window parent, String instruction, String messageError) {
        TaskDialog dlg = new TaskDialog(parent, "");
        dlg.setTitle(MESSAGE_TITLE_ERROR);
        dlg.setInstruction(instruction);
        dlg.setText(messageError);
        dlg.setIcon(TaskDialog.StandardIcon.ERROR);
        dlg.show();
    }

    /**
     * Show message warning
     * @param parent, java.awt.Window
     * @param instruction, String
     * @param messageError, String
     */
    public static void showMessageWarning(Window parent, String instruction, String messageError) {
        TaskDialog dlg = new TaskDialog(parent, "");
        dlg.setTitle(MESSAGE_TITLE_WARNING);
        dlg.setInstruction(instruction);
        dlg.setText(messageError);
        dlg.setIcon(TaskDialog.StandardIcon.WARNING);
        dlg.show();
    }

}
