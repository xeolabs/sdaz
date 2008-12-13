package vglDocument;

import boundaryMap.BoundaryMapElement;

interface VGLElement extends BoundaryMapElement {
    public VGLProperties getProperties();

    public boolean tryPick(long x, long y);

    public void acceptVisitor(VGLElementVisitor visitor);
}
