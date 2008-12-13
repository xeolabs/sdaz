package com.neocoders.vglviewer.vglDocument;


import com.neocoders.vglviewer.boundaryMap.Boundary;
import java.util.Vector;

/** A VGL text document element */
public class VGLText implements VGLElement {
    public VGLText(VGLProperties properties, VGLPoint origin, String value) {
        this.properties = properties;
        this.origin = origin;
        this.value = value;
        computeBoundary();
    }

    /** Computes boundary from font metrics obtained from the font, which is obtained from my style */
    private void computeBoundary() {
        VGLStyle myStyle = properties.getStyle();
        VGLFont myFont = myStyle.getFont();
        AbstractVGLFontMetrics fontMetrics = myFont.getMetrics();
        long width = (long)fontMetrics.stringWidth(this.value);
        long height = (long)fontMetrics.getHeight();
        boundary = new Boundary(origin.x, origin.y, origin.x + width, origin.y + height);
    }

    /** Get properties */
    public VGLProperties getProperties() {
        return properties;
    }

    /** Get boundary enclosing this */
    public Boundary getBoundary() {
        return boundary;
    }

    /** Get origin of text */
    public VGLPoint getOrigin() {
        return origin;
    }

    /** Get value of text */
    public String getValue() {
        return value;
    }

    /**
     * Try to pick this text
     * @returns true if pos inside this text, else false
     */
    public boolean tryPick(long x, long y) {
        return false;
    }

    /** Accept a visitor; the visitor's visitText method is invoked to visit this text element */
    public void acceptVisitor(VGLElementVisitor visitor) {
        visitor.visitText(this);
    }

    public boolean split(int plane, long offset, Vector leftFragments, Vector rightFragments) {
        return false;
    }

    /**
     * @supplierCardinality 1
     * @clientCardinality 1 
     */
    private VGLProperties properties;

    /**
     * @supplierCardinality 1
     * @clientCardinality 1
     * @supplierRole origin*/
    private VGLPoint origin;
    private String value;
    private Boundary boundary;
}
