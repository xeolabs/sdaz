
VGLViewer V1.0.0
----------------------

COSC427 Project,
Lindsay Stanley Kay, 
September 25,
2004


Contents of this Distribution
-----------------------------

lib - third-party JARs
src - VGLViewer source code
vglFiles - sample VGL XML files 
bin - scripts to run examples

The bin directory has two scripts:

	runDemo1.sh - run this for a demo of some polygons and text
	runDemo2.sh - run this for a demo of text


Building
--------

You'll need to have ANT installed. I recommend V1.7. In the 'ant' directory, type

    ant build

This will create a 'classes' directory containing compiled class files, and a 'jar'
directory containing a JAR archive of those classes.


Running
-------

Having built VGLViewer as above, in the 'bin' dir you can run some scripts to try VGLViewer
out on the sample VGL files. For linux, type:

    sh runDemo1.sh
or
    sh runDemo2.sh

For Windows, there are a couple of equivalent batch files. If you get an error complaining
about there not being enough memory allocated, you can edit these files to increase the value
for the java -Xms option:

    -Xms1000000


Bugs
----

1.Spaces on either side of commas in polygon vertex lists cause null pointer exceptions

2. On machines with limited memory, speed dependent automatic zooming sometimes hangs VGLViewer in a few seconds when 
run from the command line. Thats probably due to a memory leak, or garbage collection struggling a bit. But hey,
VGLViewer is an educational example, so I'm not sweating that too much.
