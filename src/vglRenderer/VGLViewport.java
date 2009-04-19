package vglRenderer;

public class VGLViewport {
    public long xmin;
    public long ymin;
    public long xmax;
    public long ymax;

    public VGLViewport() {
        xmin = Long.MAX_VALUE;
        ymin = Long.MAX_VALUE;
        xmax = Long.MIN_VALUE;
        ymax = Long.MIN_VALUE;
    }

    public VGLViewport(long xmin, long ymin, long xmax, long ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    public VGLViewport(VGLViewport v) {
        this(v.xmin, v.ymin, v.xmax, v.ymax);
    }
}
