package vglRenderer;

import java.util.Vector;
import java.util.Iterator;
import java.awt.Color;
import java.awt.Font;
import vglDocument.*;

/**
 * Renders a VGLDocument. A VGLDocumentRenderer has a viewport, a window, and may be given a document and a rendering
 * strategy. When rendering, document elements intersecting the viewport are mapped to the window and rendered with the
 * rendering strategy, which is a user-supplied implementation of VGLAbstractRenderingStrategy.
 */
public class VGLDocumentRenderer {
    /**
     * Initialise renderer. The renderer has a default viewport of (0,0,1,1) and a window of (0,0,1,1). Until a VGLDocument
     * and a VGLRenderingStrategy has been supplied, render() will do nothing.
     */
    public VGLDocumentRenderer() {
        renderContext = new RenderContext();
        renderStrategy = null;
        document = null;
        gridLines = new GridLines(100);
    }

    /** Specify viewport. The renderer will then render only the elements which intersect the viewport. */
    public void setViewport(VGLViewport viewport) {
        renderContext.setViewport(viewport);
    }

    /** Specify window on physical device in which to render document elements that intersect the viewport. */
    public void setWindow(VGLWindow window) {
        renderContext.setWindow(window);
    }

    /** Specify a strategy with which to render document elements on the physical device. */
    public void setRenderStrategy(VGLRenderingStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }

    /** Specify a document to render */
    public void setDocument(VGLDocument document) {
        this.document = document;
    }

    /**
     * Render elements of the document that intersect the viewport in the window, using the rendering strategy.
     * Does nothing if no document or rendering strategy has been specified.
     */
    public void render() {
        if (document == null || renderStrategy == null) {
            return;
        }
        try {
            VGLElementRenderer elementRenderer = new VGLElementRenderer(renderContext, renderStrategy);
            VGLViewport viewport = this.renderContext.getViewport();
            document.visitElements(
                new VGLBoundary(viewport.xmin, viewport.ymin, viewport.xmax, viewport.ymax), elementRenderer);
                gridLines.render(renderContext, renderStrategy);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     */
    private RenderContext renderContext;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @supplierRole renderStrategy
     */
    private VGLRenderingStrategy renderStrategy;
    private VGLDocument document;
    private String statusMessage;

    /**
     * @supplierCardinality 1
     * @link aggregationByValue
     */
    private GridLines gridLines;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     */
    private VGLElementRenderer lnkElementRenderer;
}
