package org.mensajes.ventanas;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.mensajes.crypto.Cifrado;
import org.mensajes.network.Cliente;
import org.mensajes.network.Servidor;
import org.mensajes.ventanas.estilo.Decoracion;
import org.mensajes.ventanas.estilo.Paleta;

public class VentanaConectar extends JFrame {
	
	// Class constants
	private final int ANCHO = 470;
	private final int ALTO = 500;
	
	private final String TITULO = "Chat settings";
	
	private final int DEAULT_CLOP = JFrame.EXIT_ON_CLOSE;
		
	private final boolean REDIMENSIONABLE = false;

	//FE
	public VentanaConectar () {
		// We configure the JFrame
		setSize(ANCHO, ALTO);
		setTitle(TITULO);
		setDefaultCloseOperation(DEAULT_CLOP);
		setResizable(REDIMENSIONABLE);
		
		// We added the frame
		MarcoConectar marco = new MarcoConectar();
		
		add(marco);
	}

	//FE
	// Show window
	public void mostrarVentana() {
		setVisible(true);
	}

	//FE
	// Show the window if show equals true, if not, hide it
	public void mostrarVentana(boolean mostrar) {
		setVisible(mostrar);
	}

	//FE
	// Close the window
	public void cerrarVentana() {
		dispose();
	}
	
}

class MarcoConectar extends JPanel {

	// Variables necessary to check that both things are started and go to chat
	private boolean clientActivated, serverActivated;

	// Framework objects that must be public to access and control their properties
	private JPanel pPrincipal, pInitserver, pInitClient, pCrypto;
	private JButton btn_crypto, btn_InitServer, btn_InitClient;
	private JTextField Key_txt, SPort_txt, CPort_txt, CIP_txt;

	//FE
	// Class constructor
	public MarcoConectar() {
		
		// Panels are created
		pPrincipal = new JPanel();
		pCrypto = new JPanel();
		pInitserver = new JPanel();
		pInitClient = new JPanel();
		
		// Margins are added with a CardLayout
		setLayout(new CardLayout(10,10));
		setBackground(Paleta.COLOR_PRIMARIO);
		
		// Main panel, where the other two panels will go, one with the server and one with the client
		pPrincipal = new JPanel();
		pPrincipal.setLayout(new BoxLayout(pPrincipal, BoxLayout.PAGE_AXIS));
		pPrincipal.setBackground(Paleta.COLOR_PRIMARIO);
		
		// Set the visual properties for both the client and server panels
		setCryptoPropierties();
		setInitserverVPropierties();
		setInitClientVPropierties();
		
		/* 
		*	Button list (To set the cryptographic key, start the server and the client),
        *   simply call other methods that perform the action (For a better understanding of the code)
		*/
		btn_crypto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCryptoKey();
			}
		});
		
		btn_InitServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initServer();
			}
		});
		
		btn_InitClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initClient();
			}
		});
		
		// Secondary panels are added to the main one
		pPrincipal.add(pCrypto);
		pPrincipal.add(pInitserver);
		pPrincipal.add(pInitClient);
		
		// The main is added to the top with the edges added
		add(pPrincipal);
	}

	//FE
	// Set the cryptographic key in case it hasn't been changed
	private void setCryptoKey() {
		new Cifrado(Key_txt.getText());
	}

	//BE
	// Start the server, and if the client has already started, the dialog screen opens
	private void initServer() {
		String spuerto = SPort_txt.getText();
		int puerto;
		try {
			puerto = Integer.parseInt(spuerto);
			new Servidor(puerto);
			JOptionPane.showMessageDialog(null, "Server started successfully, the other user can now connect", "Server started", JOptionPane.INFORMATION_MESSAGE);
			SPort_txt.setEditable(false);
			btn_InitServer.setEnabled(false);
			serverActivated = true;
			if (clientActivated) {
				VentanaPrincipal ventana = new VentanaPrincipal();
				ventana.mostrarVentana();
				// CLOSE THIS WINDOW MISSING
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Only numbers can be entered in the port", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	//BE
	// Start the client, and if the server has already started, the dialog screen opens
	private void initClient() {
		String IP = CIP_txt.getText();
		String spuerto = CPort_txt.getText();
		
		int puerto;
		
		try {
			puerto = Integer.parseInt(spuerto);
			new Cliente(IP, puerto);
			JOptionPane.showMessageDialog(null, "Client connected successfully", "Client started", JOptionPane.INFORMATION_MESSAGE);
			CIP_txt.setEditable(false);
			CPort_txt.setEditable(false);
			btn_InitClient.setEnabled(false);
			if (serverActivated) {
				VentanaPrincipal ventana = new VentanaPrincipal();
				ventana.mostrarVentana();
				// CLOSE THIS WINDOW MISSING
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "You can only enter numbers in the port", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	//FE
	// Sets the visual properties of the panel
	private void setCryptoPropierties() {
		pCrypto.setForeground(Color.WHITE);
		pCrypto.setBackground(Paleta.COLOR_PRIMARIO);
		pCrypto.setLayout(new GridBagLayout());
		pCrypto.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Paleta.COLOR_SECUNDARIO), 
				"Encryption",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				null,
				Color.WHITE
			)
		);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		c.gridwidth = 1;
		
		// ROW 1
		JLabel lab_key = new JLabel("Encryption key");
		lab_key.setForeground(Color.WHITE);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		pCrypto.add(lab_key,c);
		
		// ROW 2
		Key_txt = new JTextField();
		Decoracion.setBasicEstiloTXT(Key_txt);
		c.gridy = 1;
		c.insets = new Insets(0,6,0,6);
		pCrypto.add(Key_txt, c);
		
		// ROW 3
		btn_crypto = new JButton("Set password");
		Decoracion.setAceptBTN(btn_crypto);
		c.gridy = 2;
		pCrypto.add(btn_crypto, c);
	}

	//FE
	// Sets the visual properties of the panel
	private void setInitserverVPropierties() {
		pInitserver.setForeground(Color.WHITE);
		pInitserver.setBackground(Paleta.COLOR_PRIMARIO);
		pInitserver.setLayout(new GridBagLayout());
		pInitserver.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Paleta.COLOR_SECUNDARIO), 
				"Configure server",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				null,
				Color.WHITE
			)
		);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		
		// ROW 1
		JLabel lab_Port = new JLabel("port");
		lab_Port.setForeground(Color.WHITE);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 0;
		pInitserver.add(lab_Port, c);
		
		// ROW 2
		SPort_txt = new JTextField();
		Decoracion.setBasicEstiloTXT(SPort_txt);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.insets = new Insets(0,6,0,6);
		pInitserver.add(SPort_txt, c);
		
		// ROW 3
		btn_InitServer = new JButton("Start server");
		Decoracion.setAceptBTN(btn_InitServer);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.insets = new Insets(6, 6, 6, 6);
		pInitserver.add(btn_InitServer, c);
	}

	//FE
	// Sets the visual properties of the panel
	private void setInitClientVPropierties() {
		pInitClient.setForeground(Color.WHITE);
		pInitClient.setBackground(Paleta.COLOR_PRIMARIO);
		pInitClient.setLayout(new GridBagLayout());
		pInitClient.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Paleta.COLOR_SECUNDARIO), 
				"Configure client",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				null,
				Color.WHITE
			)
		);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		
		// ROW 1
		JLabel lab_IP = new JLabel("IP");
		lab_IP.setForeground(Color.WHITE);
		c.weightx = 2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		pInitClient.add(lab_IP, c);
		
		JLabel lab_Port = new JLabel("port");
		lab_Port.setForeground(Color.WHITE);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 0;
		pInitClient.add(lab_Port, c);
		
		// ROW 2
		CIP_txt = new JTextField();
		Decoracion.setBasicEstiloTXT(CIP_txt);
		c.weightx = 2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,6,0,6);
		pInitClient.add(CIP_txt, c);
		
		CPort_txt = new JTextField();
		Decoracion.setBasicEstiloTXT(CPort_txt);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,6);
		pInitClient.add(CPort_txt, c);
		
		// ROW 3
		btn_InitClient = new JButton("Start Client");
		Decoracion.setAceptBTN(btn_InitClient);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.insets = new Insets(6, 6, 6, 6);
		pInitClient.add(btn_InitClient, c);
		
	}
	
}
