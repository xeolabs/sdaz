package sdaz;

class SDAZDormant extends SDAZState {
    /**
     * @clientCardinality 1
     * @supplierCardinality 1 
     */
    private SDAZSubject subject;

    public SDAZDormant(SDAZSubject subject) {
        this.subject = subject;
    }

    public SDAZState mouseDown(SDAZPoint p) {
        return new SDAZZooming(subject, p);
    }

    public SDAZState mouseDragged(SDAZPoint p) {
        return this;
    }

    public SDAZState mouseReleased(SDAZPoint p) {
        return this;
    }

    public SDAZState update() {
        return this;
    }
}
