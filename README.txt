
VGLViewer
----------------------

COSC427 Project,
Lindsay Stanley Kay, 
Written on September 25, 2004

Mavenized on December, 2008.


Contents of this Distribution
-----------------------------

pom.xml - Maven POM which you can use to rebuild this project
docs - JavaDocs
html - HTML guide 
lib - Java archives of VGLViewer and third-party dependencies
src - VGLViewer source code
vglFiles - sample VGL XML files 
bin - scripts to run examples
target - where artifacts will appear if you rebuild the project using the Maven POM

The bin directory has Linux/Windows scripts to run VGLViewer on the sample VGL files:

	runDemo1.sh / runDemo1.bat - run these for a demo of some polygons and text
	runDemo2.sh / runDemo2.bat - run these for a demo of text


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

    -Xms10000000


Couple of Bugs 
--------------

1.Spaces on either side of commas in polygon vertex lists cause null pointer exceptions

2. On machines with limited memory, speed dependent automatic zooming sometimes hangs VGLViewer in a few seconds when 
run from the command line. Thats probably due to a memory leak, or garbage collection struggling a bit. But hey,
VGLViewer is an educational example, so I'm not sweating that too much.
