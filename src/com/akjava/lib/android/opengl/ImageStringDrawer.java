package com.akjava.lib.android.opengl;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

public class ImageStringDrawer {
	private int textureId;
	private int baseSize = 16;
	public float fontMargin = 0.5f;

	public static final float[] color_white = { 1, 1, 1, 1 };
	public static final float[] color_black = { 0, 0, 0, 1 };
	public static final float[] color_red = { 1, 0, 0, 1 };

	public static final String FPS_LABEL = "FPS:";

	public static final char[] numbers = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	public ImageStringDrawer(int textureId) {
		this.textureId = textureId;
		getImageFontRec_return = new int[] { 0, 0, baseSize, baseSize };
	}

	public ImageStringDrawer(int textureId, int baseSize) {
		this.textureId = textureId;
		this.baseSize = baseSize;
		getImageFontRec_return = new int[] { 0, 0, baseSize, baseSize * 2 };
	}

	int dx;
	int dy;
	char ch;
	int i;

	public int getStringWidth(StringBuffer text, final int fontSize, final float marginPersent) {
		return (int) (text.length() * fontSize * marginPersent) + fontSize / 2;
	}

	public synchronized void drawString(final GL10 gl, final StringBuffer text, final int startX, final int startY, final int fontSize, final float marginPersent) {
		dy = startY;
		dx = startX;
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		for (i = 0; i < text.length(); i++) {
			ch = text.charAt(i);

			// gl.glDisable(GL10.GL_BLEND);
			((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, getImageFontRec(ch), 0);
			((GL11Ext) gl).glDrawTexiOES(dx, dy, 0, fontSize, fontSize * 2);
			dx += fontSize * marginPersent;// *marginPersent;
			// gl.glEnable(GL10.GL_BLEND);
		}
	}

	public synchronized void drawFpsString(final GL10 gl, final int fps, final int startX, final int startY, final int fontSize, final float marginPersent) {
		dy = startY;
		dx = startX;
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		for (i = 0; i < FPS_LABEL.length(); i++) {
			ch = FPS_LABEL.charAt(i);
			((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, getImageFontRec(ch), 0);
			((GL11Ext) gl).glDrawTexiOES(dx, dy, 0, fontSize, fontSize * 2);
			dx += fontSize * marginPersent;
		}

		((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, getImageFontRec(numbers[fps / 100]), 0);
		((GL11Ext) gl).glDrawTexiOES(dx, dy, 0, fontSize, fontSize * 2);
		dx += fontSize * marginPersent;

		((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, getImageFontRec(numbers[fps % 100 / 10]), 0);
		((GL11Ext) gl).glDrawTexiOES(dx, dy, 0, fontSize, fontSize * 2);
		dx += fontSize * marginPersent;

		((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, getImageFontRec(numbers[fps % 10]), 0);
		((GL11Ext) gl).glDrawTexiOES(dx, dy, 0, fontSize, fontSize * 2);

	}

	public synchronized void drawString(final GL10 gl, final StringBuffer text, final int startX, final int startY, final int fontSize) {
		drawString(gl, text, startX, startY, fontSize, fontMargin);
	}

	public synchronized void drawShadowString(final GL10 gl, final StringBuffer text, final int startX, final int startY, final int fontSize, float[] shadowColor, float[] textColor, int shadowX, int shadowY) {
		gl.glColor4f(shadowColor[0], shadowColor[1], shadowColor[2], shadowColor[3]);
		drawString(gl, text, startX + shadowX, startY - shadowY, fontSize, fontMargin);// Y
																						// is
																						// mirroed
		gl.glColor4f(textColor[0], textColor[1], textColor[2], textColor[3]);
		drawString(gl, text, startX, startY, fontSize, fontMargin);// Y is
																	// mirroed
	}

	public synchronized void drawBorderString(final GL10 gl, final StringBuffer text, final int startX, final int startY, final int fontSize, float marginPersent, float[] borderColor, float[] textColor, int border) {
		dy = startY;
		dx = startX;
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		for (i = 0; i < text.length(); i++) {
			ch = text.charAt(i);
			gl.glColor4f(borderColor[0], borderColor[1], borderColor[2], borderColor[3]);
			((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, getImageFontRec(ch), 0);
			((GL11Ext) gl).glDrawTexiOES(dx, dy, 0, fontSize, fontSize);
			gl.glColor4f(textColor[0], textColor[1], textColor[2], textColor[3]);

			((GL11Ext) gl).glDrawTexiOES(dx + border / 2, dy + border / 2, 0, fontSize - border, fontSize - border);
			dx += fontSize * marginPersent;// *marginPersent;
		}

	}

	int getImageFontRec_x;
	int getImageFontRec_y;
	int getImageFontRec_return[];

	public synchronized int[] getImageFontRec(char ch) {
		getImageFontRec_x = ((int) ch) % 16;
		getImageFontRec_y = 7 - ((int) ch) / 16;// image size
		// Log.i("my","dx:"+dx+",dy="+dy);
		getImageFontRec_return[0] = baseSize * getImageFontRec_x;
		getImageFontRec_return[1] = baseSize * getImageFontRec_y * 2;
		return getImageFontRec_return;
	}

}
