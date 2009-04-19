
VGLViewer V1.0.0
----------------------

COSC427 Project,
Lindsay Stanley Kay, 
September 25,
2004


Contents of this Distribution
-----------------------------

classes - compiled VGL class files
jar - archived VGL class files 
docs - HTML documentation
lib - third-party JARs
src - VGLViewer source code
vglFiles - sample VGL XML files 
bin - scripts to run examples

The bin directory has two scripts:

	runDemo1.sh - run this for a demo of some polygons and text
	runDemo2.sh - run this for a demo of text

Bugs
----

1.Spaces on either side of commas in polygon vertex lists cause null pointer exceptions

2. Speed dependent automatic zooming hangs VGLViewer in a few seconds when run from the command line. 
