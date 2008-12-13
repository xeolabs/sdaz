package vglDocument;



/** A VGL boundary */
public class VGLBoundary {
    public long xmin;
    public long ymin;
    public long xmax;
    public long ymax;

    /** Initialise new unit boundary where (xmin, ymin, xmax, ymax) = (0,0,1,1) */
    public VGLBoundary() {
        xmin = 0L;
        ymin = 0L;
        xmax = 1L;
        ymax = 1L;
    }

    /** Initialise boundary to given extents */
    public VGLBoundary(long xmin, long ymin, long xmax, long ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }
}
