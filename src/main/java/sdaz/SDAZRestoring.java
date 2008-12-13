package sdaz;


class SDAZRestoring extends SDAZState {
    /**
     * @clientCardinality 1
     * @supplierCardinality 1 
     */
    private SDAZSubject subject;

    /**
     * @supplierRole target 
     */
    private SDAZViewport start;

    /**
     * @supplierRole initial 
     */
    private SDAZViewport finish;
    private double f;
    private double rate;

    public SDAZRestoring(SDAZSubject subject, SDAZViewport start, SDAZViewport finish) {
        this.subject = subject;
        this.start = start;
        this.finish = finish;
        f = 0.0;
        rate = 0.07;
    }

    public void reset(SDAZViewport start, SDAZViewport finish) {
        this.start = start;
        this.finish = finish;
        f = 0.0;
        rate = 0.07;
    }

    public SDAZState mouseDown(SDAZPoint p) {
//        return new SDAZZooming(subject, p);
        return this;
    }

    public SDAZState mouseDragged(SDAZPoint p) {
        return this;
    }

    public SDAZState mouseReleased(SDAZPoint p) {
        return this;
    }

    public SDAZState update() {
        f += rate;
        rate -= 0.005;
        if (rate < 0.07)
        {
            rate = 0.07;
        }
        if (f > 1.0) {
            subject.setViewport(finish);
            updateIndicator();
            return new SDAZDormant(subject);
        }
        SDAZViewport v = new SDAZViewport(lerp(start.xmin, finish.xmin, f), lerp(start.ymin, finish.ymin, f), lerp(start.xmax, finish.xmax, f),
            lerp(start.ymax, finish.ymax, f));
        subject.setViewport(v);
        updateIndicator();
        return this;
    }

    private long lerp(long min, long max, double factor) {
        return min + (long)((double)(max - min) * factor);
    }

    private void updateIndicator() {
        SDAZViewport viewport = subject.getViewport();
        SDAZWindow window = subject.getWindow();
        double currentXMap = ((double)window.xmax - (double)window.xmin) / (viewport.xmax - viewport.xmin);
        double currentYMap = ((double)window.ymax - (double)window.ymin) / (viewport.ymax - viewport.ymin);
        int width = (int)((double)(finish.xmax - finish.xmin) * currentXMap);
        int height = (int)((double)(finish.ymax - finish.ymin) * currentYMap);
        long viewportCenX = (viewport.xmin + viewport.xmax) / 2L;
        long viewportCenY = (viewport.ymin + viewport.ymax) / 2L;
        long finishCenX = (finish.xmin + finish.xmax) / 2L;
        long finishCenY = (finish.ymin + finish.ymax) / 2L;
        long difX = finishCenX - viewportCenX;
        long difY = finishCenY - viewportCenY;
        int xPan = (int)((double)difX * currentXMap);
        int yPan = (int)((double)difY * currentYMap);
        subject.setIndicator((int)xPan, (int)yPan, width, height);
    }
}
