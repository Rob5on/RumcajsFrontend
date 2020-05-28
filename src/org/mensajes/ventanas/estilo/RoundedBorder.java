package org.mensajes.ventanas.estilo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

// Create the round border
public class RoundedBorder implements Border {

	private int radio;
	private Color color;
	
	public RoundedBorder(int radio) {
		this.radio = radio;
		this.color = Color.GRAY;
	}
	
	public RoundedBorder(int radio, Color color) {
		this.radio = radio;
		this.color = color;
	}

	@Override
	public Insets getBorderInsets(Component arg0) {
		return new Insets(radio, radio, radio-2, radio);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		g.setColor(color);
		g.drawRoundRect(x, y, w-1, h-1, radio, radio);
	}

}
