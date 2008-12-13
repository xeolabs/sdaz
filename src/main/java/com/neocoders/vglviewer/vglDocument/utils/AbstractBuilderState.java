package com.neocoders.vglviewer.vglDocument.utils;

abstract class AbstractBuilderState {
    public AbstractBuilderState(BuildContext context, AbstractBuilderState parent) {
        this.context = context;
        this.parent = parent;
    }

    public BuildContext getContext() {
        return context;
    }

    public AbstractBuilderState openStyle() {
        context.getErrorHandler().handleError("a style definition cannot be made within a " + getDescription());
        return null;
    }

    public void setStrokeColor(int r, int g, int b) {
        context.getErrorHandler().handleError("a stroke colour cannot be assigned to a " + getDescription());
    }

    public void setName(String name) {
        context.getErrorHandler().handleError("a name cannot be given to a " + getDescription());
    }

    public void setFillColor(int r, int g, int b) {
        context.getErrorHandler().handleError("a fill colour cannot be assigned to a " + getDescription());
    }

    public void setFont(String face, int style, int size) {
        context.getErrorHandler().handleError("a font cannot be assigned to a " + getDescription());
    }

    public AbstractBuilderState openPolygon() {
        context.getErrorHandler().handleError("a polygon cannot be within a " + getDescription());
        return null;
    }

    public AbstractBuilderState openPath() {
        context.getErrorHandler().handleError("a path cannot be within a " + getDescription());
        return null;
    }

    public AbstractBuilderState openText() {
        context.getErrorHandler().handleError("text cannot be within a " + getDescription());
        return null;
    }

    public AbstractBuilderState openCircle() {
        context.getErrorHandler().handleError("a circle cannot be within a " + getDescription());
        return null;
    }

    public void applyStyle(String styleName) {
        context.getErrorHandler().handleError("a style cannot be applied to a " + getDescription());
    }

    public void addVertex(long x, long y) {
        context.getErrorHandler().handleError("" + getDescription() + " cannot have a vertex");
    }

    public void setCentre(long x, long y) {
        context.getErrorHandler().handleError("" + getDescription() + " cannot have a centre");
    }

    public void setOrigin(long x, long y) {
        context.getErrorHandler().handleError("" + getDescription() + " cannot have an origin");
    }

    public void setRadius(long r) {
        context.getErrorHandler().handleError("" + getDescription() + " cannot have a radius");
    }

    public void setText(String text) {
        context.getErrorHandler().handleError("" + getDescription() + " cannot have text");
    }

    public abstract AbstractBuilderState close();

    protected abstract String getDescription();

    protected BuildContext context;

    /** @supplierRole parent*/
    protected AbstractBuilderState parent;
}
