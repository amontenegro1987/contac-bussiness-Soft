package contac.commons.form.panel;

import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class Bloqueador extends JPanelTransparente {
	
	public Bloqueador(){
		addKeyListener(new KeyAdapter(){});
		addMouseListener(new MouseAdapter(){});
		super.setBackground(null);
		super.setForeground(null);
		super.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
}