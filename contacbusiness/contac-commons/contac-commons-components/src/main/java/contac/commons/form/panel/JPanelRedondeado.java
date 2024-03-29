package contac.commons.form.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;


public class JPanelRedondeado extends JPanel {
    private Color colorPrimario = new Color(0);
    private Color colorSecundario = new Color(256);
    private Color colorContorno = new Color(125);
    private int angHorizontal = 20;
    private int angVertical = 20;

    public JPanelRedondeado() {
        super();
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Paint oldPaint = g2.getPaint();
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() - 1, getAngHorizontal(), getAngVertical());
        g2.clip(r2d);
        g2.setPaint(new GradientPaint(0.0f, 0.0f, getColorPrimario().darker(), 0.0f, getHeight(), getColorSecundario().darker()));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setStroke(new BasicStroke(4f));
        g2.setPaint(new GradientPaint(0.0f, 0.0f, getColorContorno(), 0.0f, getHeight(), getColorContorno()));
        g2.drawRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 18, 18);
        g2.setPaint(oldPaint);
        super.paintComponent(g2);
    }

    public Color getColorPrimario() {
        return colorPrimario;
    }

    public void setColorPrimario(Color colorPrimario) {
        this.colorPrimario = colorPrimario;
    }

    public Color getColorSecundario() {
        return colorSecundario;
    }

    public void setColorSecundario(Color colorSecundario) {
        this.colorSecundario = colorSecundario;
    }

    public Color getColorContorno() {
        return colorContorno;
    }

    public void setColorContorno(Color colorContorno) {
        this.colorContorno = colorContorno;
    }

    public int getAngHorizontal() {
        return angHorizontal;
    }

    public void setAngHorizontal(int _angHorizontal) {
        this.angHorizontal = _angHorizontal;
    }

    public int getAngVertical() {
        return angVertical;
    }

    public void setAngVertical(int angVertical) {
        this.angVertical = angVertical;
    }
}