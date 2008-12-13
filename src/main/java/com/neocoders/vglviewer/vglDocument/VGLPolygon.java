package com.neocoders.vglviewer.vglDocument;

import com.neocoders.vglviewer.boundaryMap.Boundary;
import java.util.Vector;

public class VGLPolygon implements VGLElement {
    public VGLPolygon(VGLProperties properties, VGLPoints points) {
        this.properties = properties;
        this.points = points;
        initBoundary();
    }

    private void initBoundary() {
        boundary = BoundaryUtils.getCollapsedBoundary();
        long[] sx = points.getSX();
        long[] sy = points.getSY();
        for (int i = 0; i < sx.length; i++) {
            BoundaryUtils.expandBy(boundary, sx[i], sy[i]);
        }
    }

    public VGLProperties getProperties() {
        return properties;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public VGLPoints getPoints() {
        return points;
    }

    /** Try to pick this polygon; returns true if picked else false */
    public boolean tryPick(long x, long y) {
        return false;

        /*
        double totalTheta = 0.0;
        long x1;
        long y1;
        long x2;
        long y2;
        double dp;
        double cp;
        for (int i = 0; i < 4; i++) {
            int j = i + 1;
            if (j == sx.length) {
                j = 0;
            }
            x1 = sx[i] - pos.x;
            y1 = sy[i] - pos.y;
            x2 = sx[j] - pos.x;
            y2 = sy[j] - pos.y;
            dp = (double)(x1 * x2 + y1 * y2);
            cp = (double)(x1 * y2 - y1 * x2);
            totalTheta += Math.atan2(cp, dp);
        }
        return (Math.abs(totalTheta) >= Math.PI);
        */
    }

    /** Visit this polygon */
    public void acceptVisitor(VGLElementVisitor visitor) {
        visitor.visitPolygon(this);
    }

    public boolean split(int plane, long offset, Vector leftFragments, Vector rightFragments) {
        return false;
    }

    /**
     * @supplierCardinality 1 
     */
    private VGLProperties properties;

    /**
     * @supplierCardinality 1
     * @clientCardinality 1
     * @supplierRole points*/
    private VGLPoints points;
    private Boundary boundary;
}
