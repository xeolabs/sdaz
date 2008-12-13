package com.neocoders.vglviewer.vglDocument;


import com.neocoders.vglviewer.boundaryMap.Boundary;
import java.util.Vector;

/** A VGL circle document element */
public class VGLCircle implements VGLElement {
    /** Create new circle */
    public VGLCircle(VGLProperties properties, VGLPoint centre, long radius) {
        this.properties = properties;
        this.center= centre;
        this.radius = radius;
        boundary = new Boundary(centre.x - radius, centre.y - radius, centre.x + radius, centre.y + radius);
    }

    /** Get properties of this circle */
    public VGLProperties getProperties() {
        return properties;
    }

    /** Get boundary enclosing this circle */
    public Boundary getBoundary() {
        return boundary;
    }

    /** Get centre of circle */
    public VGLPoint getCenter() {
        return center;
    }

    /** Get radius of circle */
    public long getRadius() {
        return radius;
    }

    /**
     * Try to pick this circle
     * @returns true if pos inside this circle, else false
     */
    public boolean tryPick(long x, long y) {
        return false;
    }

    /** Accept a visitor; the visitor's visitCircle method is invoked to visit this circle */
    public void acceptVisitor(VGLElementVisitor visitor) {
        visitor.visitCircle(this);
    }

    public boolean split(int plane, long offset, Vector leftFragments, Vector rightFragments) {
        return false;
    }

    /**
     * @clientCardinality 1
     * @supplierCardinality 1 
     */
    private VGLProperties properties;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1 
     * @supplierRole center
     */
    private VGLPoint center;
    private long radius;
    private Boundary boundary;
}
