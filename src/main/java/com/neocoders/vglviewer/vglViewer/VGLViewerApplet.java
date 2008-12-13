package com.neocoders.vglviewer.vglViewer;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.util.Iterator;

import com.neocoders.vglviewer.vglDocument.VGLDocument;
import com.neocoders.vglviewer.vglDocument.utils.Logger;
import com.neocoders.vglviewer.vglDocument.utils.VGLDocumentBuilder;
import com.neocoders.vglviewer.vglDocument.utils.VGLDocumentLoader;

public class VGLViewerApplet extends Applet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3147763093696739058L;

	public void init() {
		setLayout(new BorderLayout());
		try {
		
			errorHandler = new MultiErrorHandler();
			setLayout(new BorderLayout());
			viewPanel = new ViewPanel();
			add(viewPanel);
			setSize(800, 800);
			validate();
			
			vglFile = this.getParameter("VGL_FILE");
			
			if (vglFile == null) {
				throw new Exception("parameter missing: 'vgl_file'");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		setSize(800, 800);
		validate();
	}

	public void start() {
		try {
			loadVGL(vglFile);
		} catch (Exception e) {
			viewPanel.setStatusMessage("Failed to load VGL file: " + e.getMessage());
		}
		validate();
		repaint();
	}

	public void stop() {

	}

	public void destroy() {
	}

	public String getAppletInfo() {
		return "Hello, World";
	}

	private void loadVGL(String vglUrlStr) throws Exception {
		errorHandler.reset();
		Logger logger = new Logger() {
			public void log(String msg) {
				viewPanel.setStatusMessage(msg);
			}
		};
		AWT_VGLFontFactory fontFactory = new AWT_VGLFontFactory(viewPanel);
		VGLDocumentBuilder builder = new VGLDocumentBuilder(errorHandler,
				fontFactory);
		builder.setLogger(logger);
		VGLDocumentLoader loader = new VGLDocumentLoader(builder);
		loader.setErrorHandler(errorHandler);
		VGLDocument doc = loader.load(vglUrlStr);
		if (errorHandler.getNumErrors() > 0) {
			reportErrors(vglUrlStr);
		} else {
			viewPanel.setDocument(doc);
		}
	}

	private void reportErrors(String url) {
		Iterator errors = errorHandler.getErrors();
		int i = 1;
		StringBuffer sb = new StringBuffer("Error(s) loading VGL file '" + url
				+ "':");
		while (errors.hasNext()) {
			sb.append("\nError " + i + ": " + (String) errors.next());
		}
		viewPanel.setStatusMessage(sb.toString());
	}

	private String vglFile;

	private ViewPanel viewPanel;

	private MultiErrorHandler errorHandler;
}
