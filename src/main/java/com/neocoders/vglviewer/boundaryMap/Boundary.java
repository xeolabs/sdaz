package com.neocoders.vglviewer.boundaryMap;

import java.util.StringTokenizer;

public class Boundary {
    public long xmin;
    public long ymin;
    public long xmax;
    public long ymax;

    /** Initialise new unit boundary where (xmin, ymin, xmax, ymax) = (0,0,1,1) */
    public Boundary() {
        xmin = 0L;
        ymin = 0L;
        xmax = 1L;
        ymax = 1L;
    }

    /** Initialise boundary to given extents */
    public Boundary(long xmin, long ymin, long xmax, long ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }
}
