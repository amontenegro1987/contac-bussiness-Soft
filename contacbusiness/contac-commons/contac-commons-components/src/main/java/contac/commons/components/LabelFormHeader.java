package contac.commons.components;

import org.jdesktop.swingx.JXHeader;

import java.awt.*;

/**
 * Contac Business Software Inc.
 * User: Eddy Montenegro
 * Date: 6/28/11
 * Time: 11:45 PM
 */
public class LabelFormHeader extends JXHeader {

    /**
     * Default Constructor
     */
    public LabelFormHeader () {
        this.setBackground(new java.awt.Color(255, 0, 0)); //Setting background color
        this.setAlpha(0.8F); //Setting alpha value
        this.setDescription("LABEL FORM HEADER");
        this.setDescriptionFont(new java.awt.Font("Arial", 1, 14));
        this.setPreferredSize(new java.awt.Dimension(51, 40));
    }

    /**
     * Constructor with custom description
     * @param description, Description label
     */
    public LabelFormHeader (String description) {
        this.setBackground(new java.awt.Color(255, 0, 0)); //Setting background color
        this.setAlpha(0.8F); //Setting alpha value
        this.setDescription(description);
        this.setDescriptionFont(new java.awt.Font("Arial", 1, 14));
        this.setPreferredSize(new java.awt.Dimension(51, 40));
    }

    /**
     * Constructor with color, font, dimension and description
     * @param description, Description label
     * @param color, Color label
     * @param font, Font type
     * @param dimension, Dimension header
     */
    public LabelFormHeader (String description, Color color, Font font, Dimension dimension) {
        this.setBackground(color);
        this.setAlpha(0.8F);
        this.setDescription(description);
        this.setDescriptionFont(font);
        this.setPreferredSize(dimension);
    }

}
