package com.neocoders.sdaz;

public interface SDAZSubject {
    public void setIndicator(int x, int y, int width, int height);

    public SDAZViewport getViewport();

    public void setViewport(SDAZViewport volume);

    public SDAZWindow getWindow();

    public void update();

    /**
     * @supplierCardinality 1
     * @clientCardinality 1 
     */
    /*# SDAZWindow lnkSDAZWindow; */

    /**
     * @supplierCardinality 1
     * @clientCardinality 1 
     */
    /*# SDAZViewport lnkSDAZViewport; */
}
