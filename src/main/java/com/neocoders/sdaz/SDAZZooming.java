package com.neocoders.sdaz;

class SDAZZooming extends SDAZState {
    /**
     * @supplierCardinality 1
     * @clientCardinality 1 
     */
    private static SDAZSubject subject;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1 
     * @supplierRole drag pos
     */
    private static SDAZPoint mouseDownPos;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1 
     * @supplierRole mouse down pos
     */
    private static SDAZPoint mouseOffsetPos;

    /**
     * @supplierRole restoreTarget
     * @supplierCardinality 1 
     */
    private static SDAZViewport originalViewport;

    /**
     * @supplierRole initial
     * @supplierCardinality 1
     * @clientCardinality 1 
     */
    private static SDAZViewport restorationViewport;
    private static double xMoved;
    private static double yMoved;

    public SDAZZooming(SDAZSubject sdazSubject, SDAZPoint mouseDownPos) {
        this.subject = sdazSubject;
        this.mouseDownPos = mouseDownPos;
        this.mouseOffsetPos = mouseDownPos;
        originalViewport = new SDAZViewport(sdazSubject.getViewport());
        restorationViewport = originalViewport;

    }

    public SDAZState mouseDown(SDAZPoint p) {
        return this;
    }

    public SDAZState mouseDragged(SDAZPoint p) {
        mouseOffsetPos = p;
        return update();
    }

    public SDAZState mouseReleased(SDAZPoint p) {
        SDAZViewport viewport = subject.getViewport();
        return new SDAZRestoring(subject, viewport, restorationViewport);
    }


    public SDAZState update() {
        /* Continue zooming
        */

        /* Get mouse down and drag points
        */

        double xs = (double)mouseDownPos.x;
        double ys = (double)mouseDownPos.y;
        double xo = (double)mouseOffsetPos.x;
        double yo = (double)mouseOffsetPos.y;

        /* Compute pan in window coordinates
        */

        double xPan = (xo - xs);
        double yPan = (yo - ys);

        /* Update pan indicator
        */

        updateIndicator(xPan, yPan);

        /* Update restoration viewport
        */

        updateRestorationViewport(xPan, yPan);

        /* Compute zoom
        */

        xMoved += xPan * 0.1;
        yMoved += yPan * 0.1;
        double dist = Math.abs(Math.sqrt((xPan * xPan) + (yPan * yPan)));
        double scale = dist * 0.1;
        // scale = Math.pow(minScale, (dist - minMouseMove) * (maxMouseMove * minMouseMove));
        double xCen = (originalViewport.xmax + originalViewport.xmin) * 0.5;
        double yCen = (originalViewport.ymax + originalViewport.ymin) * 0.5;
        double xWid = (originalViewport.xmax - originalViewport.xmin) * 0.5;
        double yWid = (originalViewport.ymax - originalViewport.ymin) * 0.5;
        SDAZViewport newViewport = new SDAZViewport((long)((xCen - (xWid * scale)) + xMoved), (long)((yCen - (yWid * scale)) + yMoved),
            (long)((xCen + (xWid * scale)) + xMoved), (long)((yCen + (yWid * scale)) + yMoved));
        subject.setViewport(newViewport);
        return this; // Still zooming
    }

    private void updateIndicator(double xPan, double yPan) {
        SDAZViewport viewport = subject.getViewport();
        SDAZWindow window = subject.getWindow();
        double currentXMap = ((double)window.xmax - (double)window.xmin) / (viewport.xmax - viewport.xmin);
        double currentYMap = ((double)window.ymax - (double)window.ymin) / (viewport.ymax - viewport.ymin);
        int width = (int)((double)(originalViewport.xmax - originalViewport.xmin) * currentXMap);
        int height = (int)((double)(originalViewport.ymax - originalViewport.ymin) * currentYMap);
        subject.setIndicator((int)xPan, (int)yPan, width, height);
    }

    private void updateRestorationViewport(double xPan, double yPan) {
        SDAZViewport viewport = subject.getViewport();
        SDAZWindow window = subject.getWindow();

        /* Get ratios of viewport width and height to window
        */

        double currentXMap = ((double)window.xmax - (double)window.xmin) / (viewport.xmax - viewport.xmin);
        double currentYMap = ((double)window.ymax - (double)window.ymin) / (viewport.ymax - viewport.ymin);

        /* Find center of window
        */

        int wCenX = window.xmin + ((window.xmax - window.xmin) / 2);
        int wCenY = window.ymin + ((window.ymax - window.ymin) / 2);

        /* Find end of pan indicator
        */

        double wZoomX = (double)wCenX + xPan;
        double wZoomY = (double)wCenY + yPan;

        /* Map end of pan indicator to view space
        */

        long vZoomX = viewport.xmin + (long)((double)(wZoomX - (double)window.xmin) / currentXMap);
        long vZoomY = viewport.ymin + (long)((double)(wZoomY - (double)window.ymin) / currentYMap);

        /* Find half width and height of original viewport
        */

        int halfWidth = (int)((originalViewport.xmax - originalViewport.xmin) / 2L);
        int halfHeight = (int)((originalViewport.ymax - originalViewport.ymin) / 2L);

        /* Restoration viewport is viewport same size as original, centered at view space end of pan indicator
        */

        restorationViewport = new SDAZViewport(vZoomX - halfWidth, vZoomY - halfHeight,
            vZoomX + halfWidth, vZoomY + halfHeight);
    }
}
