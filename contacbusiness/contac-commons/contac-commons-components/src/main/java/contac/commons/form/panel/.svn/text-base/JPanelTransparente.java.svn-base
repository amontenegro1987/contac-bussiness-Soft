package contac.commons.form.panel;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class JPanelTransparente extends JPanelRedondeado {
    private float transparencia = 0.5f;

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        AlphaComposite old = (AlphaComposite) g2.getComposite();
        g2.setComposite(AlphaComposite.SrcOver.derive(getTransparencia()));
        super.paintComponent(g2);
        g2.setComposite(old);
    }

    public float getTransparencia() {
        return transparencia;
    }

    public void setTransparencia(float _transparencia) {
        this.transparencia = _transparencia;
    }

}