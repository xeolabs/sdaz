package sdaz;

public class SDAZDriver {
    /**
     * @link aggregationByValue
     * @clientCardinality 1
     * @supplierCardinality 1 
     */
    private SDAZState state;

    /** Create SDAZ in dormant state, ie. not zooming or restoring */
    public SDAZDriver(SDAZSubject subject) {
        state = new SDAZDormant(subject);
    }

    /** Notify SDAZ that mouse is down */
    public synchronized void notifyMouseDown(SDAZPoint p) {
        state = state.mouseDown(p);
    }

    /** Notify SDAZ that mouse has been dragged */
    public synchronized void notifyMouseDragged(SDAZPoint p) {
        state = state.mouseDragged(p);
    }

    /** Notify SDAZ that mouse has been released */
    public synchronized  void notifyMouseReleased(SDAZPoint p) {
        state = state.mouseReleased(p);
    }

    /** Update SDAZ */
    public synchronized void update() {
        state = state.update();
    }
}
