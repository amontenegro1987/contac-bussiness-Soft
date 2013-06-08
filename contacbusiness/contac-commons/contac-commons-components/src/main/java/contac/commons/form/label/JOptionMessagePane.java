/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.commons.form.label;

import com.ezware.dialog.task.TaskDialog;

import java.awt.*;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: EMontenegro
 * Date: 12-15-11
 * Time: 10:06 AM
 */
public class JOptionMessagePane {

    private static final String MESSAGE_TITLE_INFO = "Contac Message Info";

    /**
     * Show a Task Dialog question for confirmation
     * @param parent, java.awt.Window
     * @param instruction, String
     * @param message, String
     * @return boolean
     */
    public static boolean showConfirmationInfo(Window parent, String instruction, String message) {
        TaskDialog dlg = new TaskDialog(parent, "");
        dlg.setTitle(MESSAGE_TITLE_INFO);
        dlg.setIcon(TaskDialog.StandardIcon.QUESTION);
        dlg.setInstruction(instruction);
        dlg.setText(message);
        dlg.setCommands(TaskDialog.StandardCommand.OK.derive("Si"), TaskDialog.StandardCommand.CANCEL.derive("No"));

        return dlg.show().getTag() == TaskDialog.StandardCommand.OK.getTag();
    }

}
