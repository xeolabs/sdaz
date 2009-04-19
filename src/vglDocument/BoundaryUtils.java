package vglDocument;

import boundaryMap.Boundary;

class BoundaryUtils {
    public static Boundary getCollapsedBoundary() {
        return new Boundary(Long.MAX_VALUE, Long.MAX_VALUE, Long.MIN_VALUE, Long.MIN_VALUE);
    }

    public static void expandBy(Boundary b, long x, long y) {
        if (x < b.xmin) b.xmin = x;
        if (y < b.ymin) b.ymin = y;
        if (x > b.xmax) b.xmax = x;
        if (y > b.ymax) b.ymax = y;
    }

    public static void expandBy(Boundary b, VGLPoint p) {
        expandBy(b, p.x, p.y);
    }
}
