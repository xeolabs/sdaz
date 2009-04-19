package docViewPanel;

import java.awt.Graphics;
import vglRenderer.VGLDocumentRenderer;
import vglRenderer.VGLViewport;
import vglRenderer.VGLWindow;
import java.awt.Color;
import vglDocument.VGLBoundary;
import vglRenderer.VGLWindow;
import vglDocument.VGLDocument;
import java.util.StringTokenizer;
import java.awt.Font;
import java.awt.FontMetrics;
import vglViewer.DocViewMouseHandler;

public class DocViewPanel extends DoubleBufferedPanel {
    private VGLViewport viewport;
    private VGLWindow window;
    private VGLDocumentRenderer renderer;
    private VGLDocument document;
    private String statusMessage;
    private VGLWindow marquee;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @link aggregationByValue 
     */
    private SDAZIndicator sdazIndicator;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @link aggregationByValue
     */
    private AWTRenderingStrategy lnkGraphicsContextAdapter;

    /** @supplierRole gets mouse events */
    private DocViewMouseHandler lnkSDAZMouseListener;

    /**
     * @supplierCardinality 1
     * @clientCardinality 1
     * @link aggregationByValue
     */
    private RepaintObserver repaintObserver;

    public DocViewPanel() {
        super();
        this.statusMessage = null;
        this.marquee = null;
        window = new VGLWindow();
        viewport = new VGLViewport();
        document = new VGLDocument();
        renderer = new VGLDocumentRenderer();
        renderer.setViewport(viewport);
        renderer.setWindow(window);
        sdazIndicator = new SDAZIndicator();
        repaintObserver = null;
    }

    public void setDocument(VGLDocument document) {
        this.document = document;
        VGLBoundary boundary = document.getBoundary();
        renderer.setViewport(new VGLViewport(boundary.xmin, boundary.ymin, boundary.xmax, boundary.ymax));
        renderer.setDocument(document);
        repaint();
    }

    public void setViewport(VGLViewport viewport) {
        this.viewport = viewport;
        renderer.setViewport(viewport);
        marquee = null;
        repaint();
    }

    public void zoomInto(VGLWindow region) {
        VGLViewport viewRegion = mapWindowRegionToView(region);
        setViewport(viewRegion);
    }

    private VGLViewport mapWindowRegionToView(VGLWindow v) {
        double xMap = (double)(viewport.xmax - viewport.xmin) / (double)(window.xmax - window.xmin);
        double yMap = (double)(viewport.ymax - viewport.ymin) / (double)(window.ymax - window.ymin);
        return new VGLViewport(viewport.xmin + (long)((double)(v.xmin - window.xmin) * xMap),
            viewport.ymin + (long)((double)(v.ymin - window.ymin) * yMap), viewport.xmin + (long)((double)(v.xmax - window.xmin) * xMap),
            viewport.ymin + (long)((double)(v.ymax - window.ymin) * yMap));
    }

    public VGLViewport getViewport() {
        return viewport;
    }

    public void setWindow(VGLWindow window) {
        this.window = window;
        renderer.setWindow(window);
        repaint();
    }

    public VGLWindow getWindow() {
        return window;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        repaint();
    }

    public void setMarquee(VGLWindow boundary) {
        if (boundary == null) {
            this.marquee = null;
        }
        else {
            this.marquee = new VGLWindow(boundary.xmin, boundary.ymin, boundary.xmax - boundary.xmin,
                boundary.ymax - boundary.ymin);
        }
        repaint();
    }

    public void setSDAZIndicator(int xa, int ya, int xb, int yb, int width, int height) {
        sdazIndicator.update(xa, ya, xb, yb, width, height);
        repaint();
    }

    public void paintBuffer(Graphics g) {
      //  g.clearRect(window.xmin, window.ymin, window.xmax, window.ymax);
        renderer.setRenderStrategy(new AWTRenderingStrategy(g));
        renderer.render();
        renderMarquee(g);
        renderStatusMessage(g);
        sdazIndicator.render(g);
        if (repaintObserver != null) {
            repaintObserver.update();
        }

    }

    private void renderStatusMessage(Graphics g) {
        if (statusMessage == null) {
            return;
        }
        Font font = new Font("Helvetica", 0, 12);
        this.setFont(font);
        FontMetrics fm = this.getFontMetrics(font);
        int x = 20;
        int y = 20;
        int yInc = fm.getAscent();
        StringTokenizer st = new StringTokenizer(statusMessage, "\n");
        while (st.hasMoreTokens()) {
            g.drawString((String)st.nextToken(), x, y);
            y += yInc;
        }
    }

    private void renderMarquee(Graphics g) {
        if (marquee == null) {
            return;
        }
        g.setColor(new Color(255, 0, 0));
        g.drawRect(marquee.xmin, marquee.ymin, marquee.xmax, marquee.ymax);
        g.drawRect(marquee.xmin - 1, marquee.ymin - 1, marquee.xmax + 2, marquee.ymax + 2);
        g.drawRect(marquee.xmin - 2, marquee.ymin - 2, marquee.xmax + 4, marquee.ymax + 4);
    }

    /** Set observer to be notified whenever view panel has repainted */
    public void setRepaintObserver(RepaintObserver repaintObserver) {
        this.repaintObserver= repaintObserver;
    }
}
