package com.neocoders.vglviewer.boundaryMap;

import java.util.Vector;
import java.util.Iterator;

public class BoundaryMap {
    /** Maximum depth of BVH */
    public final static int MAX_DEPTH = 5;

    /**
     * boundary enclosing elements and/or sub nodes
     * @clientCardinality 1
     * @supplierCardinality 1
     * @supplierRole boundary
     */
    private Boundary boundary;

    /**
     * Left child node
     * @label left child
     * @clientCardinality 1
     * @supplierCardinality 0..1
     */
    private BoundaryMap leftChild;

    /**
     * Right child node
     * @label right child
     * @supplierCardinality 0..1
     * @clientCardinality 1
     */
    private BoundaryMap rightChild;

    /** Elements contained by this node */
    private Vector storedElements;

    /** Elements pending insertion into BVH rooted by this node */
    private Vector tempElements;

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @link aggregationByValue
     */

    /*# vgl.document.VGLElement lnkElement; */

    public BoundaryMap() {
        this.leftChild = null;
        this.rightChild = null;
        this.storedElements = null;
        this.boundary = newInsideOutBoundary();
        this.tempElements = null;
    }

    public void putElement(BoundaryMapElement element) {
        if (tempElements == null) {
            tempElements = new Vector();
        }
        tempElements.add(element);
        leftChild = null;
        rightChild = null;
    }

    public Iterator getElements(Boundary b) {
        buildSubMaps(); // does nothing if already built
        Vector e = new Vector();
        getElements(b, e);
        return e.iterator();
    }

    private void buildSubMaps() {
        if (tempElements == null) { // No elements were added, or all elements processed
            return;
        }
        this.boundary = newInsideOutBoundary();
        Iterator i = tempElements.iterator();
        while (i.hasNext()) {
            BoundaryMapElement element = (BoundaryMapElement)i.next();
            expandBoundary(boundary, element.getBoundary());
        }

        /* Insert elements
        */

        int recursionDepth = 0;
        i = tempElements.iterator();
        while (i.hasNext()) {
            BoundaryMapElement element = (BoundaryMapElement)i.next();
            insertElement(element, X_AXIS, recursionDepth);
        }
        tempElements = null;
    }

    private boolean insertElement(BoundaryMapElement element, int axis, int recursionDepth) {
        axis = (axis == X_AXIS) ? Y_AXIS : X_AXIS; // alternate axis
        if (intersectsBoundary(this.boundary, element.getBoundary()) != INTERSECT_INSIDE) {
            return false; // Can't insert element; not entirely inside this boundary
        }
        recursionDepth++;
        if (recursionDepth >= MAX_DEPTH) { // Max hierarchy depth reached
            storeElementInThis(element);
            return true;
        }
        if (leftChild != null) { // Try to insert into existing left child
            if (leftChild.insertElement(element, axis, recursionDepth)) {
                return true;
            }
        }
        if (rightChild != null) { // Try to insert into existing right child
            if (rightChild.insertElement(element, axis, recursionDepth)) {
                return true;
            }
        }
        Boundary leftBoundary = halfBoundary(boundary, axis, -1);
        switch (intersectsBoundary(leftBoundary, boundary)) {
            case INTERSECT_INSIDE: // Insert into left child
                ensureLeftChildExists(leftBoundary);
                leftChild.insertElement(element, axis, recursionDepth);
                break;
            case INTERSECT_PARTIAL:
                Vector leftFragments = new Vector();
                Vector rightFragments = new Vector();
                long pos = (axis == this.X_AXIS) ? leftBoundary.xmax : leftBoundary.ymax;
                if (element.split(axis, pos, leftFragments, rightFragments)) { // Element allows splitting

                    /* Insert left fragments
                    */

                    ensureLeftChildExists(leftBoundary);
                    Iterator i = leftFragments.iterator();
                    while (i.hasNext()) {
                        leftChild.insertElement((BoundaryMapElement)i.next(), axis, recursionDepth);
                    }

              		/* Insert right fragments
                    */

                    ensureRightChildExists(halfBoundary(boundary, axis, 1));
                    i = rightFragments.iterator();
                    while (i.hasNext()) {
                        rightChild.insertElement((BoundaryMapElement)i.next(), axis, recursionDepth);
                    }
                }
                else {
                    /* Element does not allow splitting
                    */

                    storeElementInThis(element);
                }
                break;
            case INTERSECT_OUTSIDE: // Insert into right child
                Boundary rightBoundary = halfBoundary(boundary, axis, 1);
                ensureRightChildExists(rightBoundary);
                rightChild.insertElement(element, axis, recursionDepth);
                break;
        }
        return true;
    }

    private void ensureLeftChildExists(Boundary leftBoundary) {
        if (leftChild == null) {
            leftChild = new BoundaryMap();
            leftChild.boundary = leftBoundary;
        }
    }

    private void ensureRightChildExists(Boundary rightBoundary) {
        if (rightChild == null) {
            rightChild = new BoundaryMap();
            rightChild.boundary = rightBoundary;
        }
    }

    /** Store element in this list */
    private void storeElementInThis(BoundaryMapElement element) {
        if (storedElements == null) { // lazy instantiation; some branch lists may not actually store elements
            storedElements = new Vector();
        }
        storedElements.add(element);
    }

    /** Recursive version of above */
    private void getElements(Boundary b, Vector e) {
        if (intersectsBoundary(boundary, b) == INTERSECT_OUTSIDE) {
            return; // Recursion boundary; not intersecting
        }
        if (storedElements != null) {
            Iterator i = storedElements.iterator();
            BoundaryMapElement element;
            while (i.hasNext()) {
                element = (BoundaryMapElement)i.next();
                if (intersectsBoundary(b, element.getBoundary()) != INTERSECT_OUTSIDE) {
                    e.add(element);
                }
            }
        }
        if (leftChild != null) {
            leftChild.getElements(b, e);
        }
        if (rightChild != null) {
            rightChild.getElements(b, e);
        }
    }

    /** Get boundary that encloses elements in this */
    public Boundary getBoundary() {
        buildSubMaps(); // does nothing if already built
        return this.boundary;
    }

    /** Results of intersection tests */
    private static final int INTERSECT_OUTSIDE = 0;
    private static final int INTERSECT_PARTIAL = 1;
    private static final int INTERSECT_INSIDE = 2;

    /** Identities for axis */
    private static final int X_AXIS = 0;
    private static final int Y_AXIS = 1;

    /**
     * @supplierCardinality 0..*
     * @clientCardinality 1 
     */
    private BoundaryMapElement lnkDisplayListElement;

    private Boundary newInsideOutBoundary() {
        return new Boundary(Long.MAX_VALUE, Long.MAX_VALUE, Long.MIN_VALUE, Long.MIN_VALUE);
    }

    private static void expandBoundary(Boundary b, long x, long y) {
        if (x < b.xmin) b.xmin = x;
        if (y < b.ymin) b.ymin = y;
        if (x > b.xmax) b.xmax = x;
        if (y > b.ymax) b.ymax = y;
    }

    private Boundary halfBoundary(Boundary b, int axis, int sign) {
        if (axis == X_AXIS) {
            long xmid = (b.xmax + b.xmin) / 2L;
            return (sign < 0) ? new Boundary(b.xmin, b.ymin, xmid, b.ymax) : new Boundary(xmid, b.ymin, b.xmax, b.ymax);
        }
        else {
            long ymid = (b.ymax + b.ymin) / 2L;
            return (sign < 0) ? new Boundary(b.xmin, b.ymin, b.xmax, ymid) : new Boundary(b.xmin, ymid, b.xmax, b.ymax);
        }
    }

    private int intersectsBoundary(Boundary a, Boundary b) {
        if (a.xmax < b.xmin || a.xmin > b.xmax || a.ymax < b.ymin || a.ymin > b.ymax) {
            return INTERSECT_OUTSIDE;
        }
        if (b.xmax <= a.xmax && b.ymax <= a.ymax && b.xmin >= a.xmin && b.ymin >= a.ymin) {
            return INTERSECT_INSIDE;
        }
        return INTERSECT_PARTIAL;
    }

    private void expandBoundary(Boundary a, Boundary b) {
        if (b.xmin < a.xmin) a.xmin = b.xmin;
        if (b.ymin < a.ymin) a.ymin = b.ymin;
        if (b.xmax > a.xmax) a.xmax = b.xmax;
        if (b.ymax > a.ymax) a.ymax = b.ymax;
    }
}
