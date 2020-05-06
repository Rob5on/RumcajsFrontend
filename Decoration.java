package org.mensajes.ventanas.estilo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextField;

public class Decoration {

	// Default decoration for the JTextField
	public static JTextField setBasicEstiloTXT(JTextField campo) {

		campo.setBackground(Palette.SECONDARY_COLOR);
		campo.setBorder(new RoundedBorder(9, Palette.LIGHT_COLOR));
		campo.setForeground(Color.white);
		campo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		campo.setPreferredSize(new Dimension(0, 32));

		return campo;
	}

	// Default decoration for the JButton
	public static JButton setAceptBTN(JButton btn) {

		btn.setBackground(Palette.ACCEPT);
		btn.setBorder(new RoundedBorder(9, Palette.ACCEPT));
		btn.setForeground(Palette.ACCEPT_2);
		btn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		btn.setPreferredSize(new Dimension(0, 40));

		return btn;
	}

}
