package vglDocument;

public class VGLProperties {
    public VGLProperties(VGLStyle style, String description, String id) {
        this.style = style;
        this.description = description;
        this.id = id;
    }

    public VGLStyle getStyle() {
        return style;
    }

    public String getDescription() {
        return description;
    }

    public String getID() {
        return id;
    }

    private String description;

    /**
     * @clientCardinality 1..*
     * @supplierCardinality 1 
     * @supplierRole style
     */
    private VGLStyle style;
    private String id;
}
