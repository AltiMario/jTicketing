/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.servlets;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.BinaryData;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageProcessor extends HttpServlet implements ImageObserver {
	private static final long serialVersionUID = 3138285796405979801L;
	
	private final static int MAX_WIDTH = 200;
	private final static int MAX_HEIGHT = 200;
	
	Logger log=Logger.getLogger(this.getClass());

	private boolean imageLoaded = false;
	HttpSession session=null;

	private static int getWidth(final String maxWidth) {
		int maxWidthInt = MAX_WIDTH;
		try {
			maxWidthInt = new Integer(maxWidth).intValue();
		} catch (NumberFormatException nfe) {
		}
		return maxWidthInt;
	}

	private static int getHeight(final String maxHeight) {
		int maxHeightInt = MAX_HEIGHT;
		try {
			maxHeightInt = new Integer(maxHeight).intValue();
		} catch (NumberFormatException nfe) {
		}
		return maxHeightInt;
	}

	public void service(HttpServletRequest req, HttpServletResponse resp) {
		String maxHeight = req.getParameter("maxHeight");
		String maxWidth = req.getParameter("maxWidth");
		String imgURL = req.getParameter("urlImage");
		String imgFile = req.getParameter("File");
		String uid = req.getParameter("uid");
		String typeString=req.getParameter("type");
		int type = 0;
		if(typeString!=null)
			type=Integer.parseInt(typeString);
		String sessionFile = req.getParameter("sessionFile");

		User user = (User) req.getSession().getAttribute("user");
		session=req.getSession();
		try {
			if (imgURL != null && imgURL.trim().length() > 0) {
				writeThumbnail(new URL(imgURL), resp, getWidth(maxWidth), getHeight(maxHeight));
			}
			if (imgFile != null && imgFile.length() > 0) {
				writeThumbnail(new File(imgFile), resp, getWidth(maxWidth), getHeight(maxHeight));
			}
			if (imgURL == null && imgFile == null && typeString!=null) {
				writeThumbnail(Long.parseLong(uid), resp, getWidth(maxWidth), getHeight(maxHeight), type);
			}
			if (sessionFile != null && sessionFile.length()>0) {
				writeThumbnail(resp, getWidth(maxWidth), getHeight(maxHeight), sessionFile);
			}
		} catch (IOException ioe) {
			resp.setContentType("text/html");
			final ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ioe.printStackTrace(new PrintStream(bout));
			try {
				final PrintWriter pw = resp.getWriter();
				pw.print("<pre>" + ioe.getMessage() + "\n\n" + bout.toString() + "</pre>");
			} catch (Exception e) {
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void writeThumbnail(final URL URL, final ServletResponse resp, final int maxWidth, final int maxHeight) throws IOException {
		resp.setContentType("image/jpeg");
		ByteArrayOutputStream baos = null;
		try {
			baos = processImage(URL, maxWidth, maxHeight);
			baos.writeTo(resp.getOutputStream());
			baos.flush();
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
	}
	
	private void writeThumbnail(final byte[] bytes, final ServletResponse resp, final int maxWidth, final int maxHeight) throws IOException , SQLException {
		resp.setContentType("image/jpeg");
		ByteArrayOutputStream baos = null;
		try {
			baos = processImage(bytes, maxWidth, maxHeight);
			baos.writeTo(resp.getOutputStream());
			baos.flush();
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
	}

	private void writeThumbnail(final long id, final ServletResponse resp, final int maxWidth, final int maxHeight,final int type) throws IOException , SQLException {
		resp.setContentType("image/jpeg");
		ByteArrayOutputStream baos = null;
		try {
			baos = processImage(id, maxWidth, maxHeight, type);
			baos.writeTo(resp.getOutputStream());
			baos.flush();
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
	}
	
	private void writeThumbnail(final ServletResponse resp, final int maxWidth, final int maxHeight,final String sessionFile) throws IOException {
		resp.setContentType("image/jpeg");
		ByteArrayOutputStream baos = null;
		try {
			baos = processImage(maxWidth, maxHeight, sessionFile);
			baos.writeTo(resp.getOutputStream());
			baos.flush();
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
	}

	private void writeThumbnail(final File file, final ServletResponse resp, final int maxWidth, final int maxHeight) throws IOException {
		resp.setContentType("image/jpeg");
		ByteArrayOutputStream baos = null;
		try {
			baos = processImage(file, maxWidth, maxHeight);
			baos.writeTo(resp.getOutputStream());
			baos.flush();
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
	}

	public static Image getImage(final File f) {
		return new ImageIcon(f.toString()).getImage();
	}

	public static Image getImage(final byte[] b) {
		return new ImageIcon(b).getImage();
	}

	public static Image getImage(final URL u) {
		return new ImageIcon(u).getImage();
	}

	synchronized ByteArrayOutputStream processImage(final URL u, final int maxWidth, final int maxHeight) throws IOException {
		final Image photo = getImage(u);

		// ImageOveserve is ingnored for BufferedImages
		final int photoWidth = photo.getWidth(null);
		final int photoHeight = photo.getHeight(null);

		int thumbWidth = maxWidth;
		int thumbHeight = maxHeight;
		final double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		final double photoRatio = (double) photoWidth / (double) photoHeight;

		if (thumbRatio < photoRatio)
			thumbHeight = (int) (thumbWidth / photoRatio);
		else
			thumbWidth = (int) (thumbHeight * photoRatio);

		final BufferedImage thumbnail = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = thumbnail.createGraphics();

		// Best Quality Render Hints!
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		final AffineTransform t = new AffineTransform();
		t.scale((double) thumbWidth / photoWidth, (double) thumbHeight / photoHeight);

		graphics2D.setBackground(Color.WHITE);
		final boolean complete = graphics2D.drawImage(photo, t, this);

		if (!complete) {
			while (!imageLoaded) {
				try {
					wait(100);
				} catch (InterruptedException ie) {
				}
			}
		}

		graphics2D.dispose();
		final ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		final JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bOut);
		final JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbnail);
		param.setQuality(0.75F, true);
		encoder.encode(thumbnail, param);
		return bOut;
	}
	
	synchronized ByteArrayOutputStream processImage(final byte[] bytes, final int maxWidth, final int maxHeight) throws IOException , SQLException {
		final Image photo = getImage(bytes);

		// ImageOveserve is ingnored for BufferedImages
		final int photoWidth = photo.getWidth(null);
		final int photoHeight = photo.getHeight(null);

		int thumbWidth = maxWidth;
		int thumbHeight = maxHeight;
		final double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		final double photoRatio = (double) photoWidth / (double) photoHeight;

		if (thumbRatio < photoRatio)
			thumbHeight = (int) (thumbWidth / photoRatio);
		else
			thumbWidth = (int) (thumbHeight * photoRatio);

		final BufferedImage thumbnail = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = thumbnail.createGraphics();

		// Best Quality Render Hints!
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		final AffineTransform t = new AffineTransform();
		t.scale((double) thumbWidth / photoWidth, (double) thumbHeight / photoHeight);

		graphics2D.setBackground(Color.WHITE);
		final boolean complete = graphics2D.drawImage(photo, t, this);

		if (!complete) {
			while (!imageLoaded) {
				try {
					wait(100);
				} catch (InterruptedException ie) {
				}
			}
		}

		graphics2D.dispose();
		final ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		final JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bOut);
		final JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbnail);
		param.setQuality(0.75F, true);
		encoder.encode(thumbnail, param);
		return bOut;
	}

	synchronized ByteArrayOutputStream processImage(long uid, final int maxWidth, final int maxHeight, final int type) throws IOException , SQLException {
		DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
		BinaryData x = null;
		try {
			switch(type){
			//Immagine di dettaglio del luogo
			case 1: x = service.readLuogoInteresseImage(String.valueOf(uid));
			        break;
			//Immagine di dettaglio del biglietto
			case 2: x = service.readTicketImage(String.valueOf(uid));
	                break;
			}
			
		} catch (SystemException e) {
			log.error(e);
		}


		final Image photo = getImage(x.getBinaryData());

		// ImageOveserve is ingnored for BufferedImages
		final int photoWidth = photo.getWidth(null);
		final int photoHeight = photo.getHeight(null);

		int thumbWidth = maxWidth;
		int thumbHeight = maxHeight;
		final double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		final double photoRatio = (double) photoWidth / (double) photoHeight;

		if (thumbRatio < photoRatio)
			thumbHeight = (int) (thumbWidth / photoRatio);
		else
			thumbWidth = (int) (thumbHeight * photoRatio);

		final BufferedImage thumbnail = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = thumbnail.createGraphics();

		// Best Quality Render Hints!
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		final AffineTransform t = new AffineTransform();
		t.scale((double) thumbWidth / photoWidth, (double) thumbHeight / photoHeight);

		graphics2D.setBackground(Color.WHITE);
		final boolean complete = graphics2D.drawImage(photo, t, this);

		if (!complete) {
			while (!imageLoaded) {
				try {
					wait(100);
				} catch (InterruptedException ie) {
				}
			}
		}

		graphics2D.dispose();
		final ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		final JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bOut);
		final JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbnail);
		param.setQuality(0.75F, true);
		encoder.encode(thumbnail, param);
		return bOut;
	}
	
	synchronized ByteArrayOutputStream processImage(final int maxWidth, final int maxHeight, final String sessionFile) throws IOException {
		DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
		BinaryData x = new BinaryData();
		
    	 x.setBinaryData((byte[])session.getAttribute(sessionFile));


		final Image photo = getImage(x.getBinaryData());

		// ImageOveserve is ingnored for BufferedImages
		final int photoWidth = photo.getWidth(null);
		final int photoHeight = photo.getHeight(null);

		int thumbWidth = maxWidth;
		int thumbHeight = maxHeight;
		final double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		final double photoRatio = (double) photoWidth / (double) photoHeight;

		if (thumbRatio < photoRatio)
			thumbHeight = (int) (thumbWidth / photoRatio);
		else
			thumbWidth = (int) (thumbHeight * photoRatio);

		final BufferedImage thumbnail = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = thumbnail.createGraphics();

		// Best Quality Render Hints!
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		final AffineTransform t = new AffineTransform();
		t.scale((double) thumbWidth / photoWidth, (double) thumbHeight / photoHeight);

		graphics2D.setBackground(Color.WHITE);
		final boolean complete = graphics2D.drawImage(photo, t, this);

		if (!complete) {
			while (!imageLoaded) {
				try {
					wait(100);
				} catch (InterruptedException ie) {
				}
			}
		}

		graphics2D.dispose();
		final ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		final JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bOut);
		final JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbnail);
		param.setQuality(0.75F, true);
		encoder.encode(thumbnail, param);
		return bOut;
	}

	synchronized ByteArrayOutputStream processImage(final File u, final int maxWidth, final int maxHeight) throws IOException {
		final Image photo = getImage(u);

		// ImageOveserve is ingnored for BufferedImages
		final int photoWidth = photo.getWidth(null);
		final int photoHeight = photo.getHeight(null);

		int thumbWidth = maxWidth;
		int thumbHeight = maxHeight;
		final double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		final double photoRatio = (double) photoWidth / (double) photoHeight;

		if (thumbRatio < photoRatio)
			thumbHeight = (int) (thumbWidth / photoRatio);
		else
			thumbWidth = (int) (thumbHeight * photoRatio);

		final BufferedImage thumbnail = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = thumbnail.createGraphics();

		// Best Quality Render Hints!
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		final AffineTransform t = new AffineTransform();
		t.scale((double) thumbWidth / photoWidth, (double) thumbHeight / photoHeight);

		graphics2D.setBackground(Color.WHITE);
		final boolean complete = graphics2D.drawImage(photo, t, this);

		if (!complete) {
			while (!imageLoaded) {
				try {
					wait(100);
				} catch (InterruptedException ie) {
				}
			}
		}

		graphics2D.dispose();
		final ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		final JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bOut);
		final JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbnail);
		param.setQuality(0.75F, true);
		encoder.encode(thumbnail, param);
		return bOut;
	}

	public boolean imageUpdate(final Image img, final int infoflags, final int x, final int y, final int width, final int height) {
		if (infoflags != ALLBITS) {
			// Image has not finished loading!
			// Return true to tell the image loading thread to keep drawing
			// until image fully loads.
			return true;
		} else {
			imageLoaded = true;
			return false;
		}
	}

}
