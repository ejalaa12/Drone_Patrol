package organisationG;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

@SuppressWarnings("serial")
public class ConfigurationEnvironnement extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblDimensions;
	private JLabel lblLargeur;
	private JLabel lblHauteur;
	private JLabel lblNombreDeDrones;
	private JLabel lblQuantiteEssence;
	private JLabel lblConfig_Bateaux;
	private JSeparator separator_2;
	private JLabel lblNombresDeBateaux;
	private JSlider slider_vie;
	private JButton okButton;
	private boolean champ_longueur_ok = false;
	private boolean champ_largeur_ok = false;
	private boolean champ_nbCercDrone_ok = false;
	private boolean champ_nbDrones_ok = false;
	private boolean champ_essence_ok = false;
	private boolean champsOK = false; // verifie si tout les champs sont bien compl�t�s, faux initialement
	private JSpinner Largeur;
	private JSpinner Hauteur;
	private JSpinner spinner_2;
	private JSpinner spinner;
	private JSpinner spinner_1;
	private JSpinner spinner_14;
	private JSpinner spinner_16;
	private JSpinner spinner_3;
	private JSpinner spinner_4;
	private JSpinner spinner_15;
	private JSpinner spinner_5;
	private JSpinner spinner_6;
	private JSpinner spinner_7;
	private JSpinner spinner_8;
	private JSpinner spinner_9;
	private JSpinner spinner_10;
	private JSpinner spinner_13;
	private JSpinner spinner_11;
	private JSpinner spinner_12;
	private JSeparator separator;
	private boolean aValider=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			ConfigurationEnvironnement dialog = new ConfigurationEnvironnement();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConfigurationEnvironnement() {
		setModalityType(ModalityType.DOCUMENT_MODAL);
		setTitle("Configuration");
		setModal(true);
		setBounds(100, 100, 482, 580);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblDimensions = new JLabel("<html><strong>Dimensions:</strong></html>");
		}
		{
			lblLargeur = new JLabel("Largeur");
		}
		{
			lblHauteur = new JLabel("Hauteur");
		}

		JSeparator separator_1 = new JSeparator();

		JLabel lblConfig_drones = new JLabel("<html><strong>Drones :</strong></html>");
		lblNombreDeDrones = new JLabel("Nombre de drones");

		lblQuantiteEssence = new JLabel("Quantit\u00E9 d'essence");

		lblConfig_Bateaux = new JLabel("<html><strong>Bateaux de sauvetages :</strong></html>");
		separator_2 = new JSeparator();
		lblNombresDeBateaux = new JLabel("Nombres de bateaux de sauvetages");

		slider_vie = new JSlider();
		slider_vie.setToolTipText("Valeur");
		slider_vie.setValue(5);
		slider_vie.setMaximum(10);
		slider_vie.setSnapToTicks(true);
		slider_vie.setPaintTicks(true);
		slider_vie.setPaintLabels(true);

		Largeur = new JSpinner();
		Largeur.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				spinner_2.setModel(new SpinnerNumberModel(0, 0, getLargeur()-8, 1));
			}
		});
		Largeur.setModel(new SpinnerNumberModel(33, 8, 100, 1));

		Hauteur = new JSpinner();
		Hauteur.setModel(new SpinnerNumberModel(20, 8, 100, 1));

		spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(4, 0, 10, 1));

		JLabel lblCote = new JLabel("C\u00F4te");

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(4), new Integer(1), null, new Integer(1)));

		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Integer(1000), new Integer(0), null, new Integer(1)));

		spinner_3 = new JSpinner();
		spinner_3.setModel(new SpinnerNumberModel(new Integer(5), new Integer(0), null, new Integer(1)));

		JLabel label = new JLabel("Quantit\u00E9 d'essence");

		spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerNumberModel(new Integer(1000), null, null, new Integer(1)));

		JLabel lblDureDeVie_1 = new JLabel("<html><strong>Bateaux:</strong></html>");

		JLabel lblNombresDeBateaux_1 = new JLabel("Nombres de bateaux max");

		spinner_5 = new JSpinner();
		spinner_5.setModel(new SpinnerNumberModel(new Integer(5), new Integer(1), null, new Integer(1)));

		JLabel label_2 = new JLabel("Quantit\u00E9 d'essence");

		JLabel lblNaufrag = new JLabel("<html><strong>Naufrag\u00E9s:</strong></html>");

		spinner_6 = new JSpinner();
		spinner_6.setModel(new SpinnerNumberModel(new Integer(500), new Integer(0), null, new Integer(1)));

		JSeparator separator_4 = new JSeparator();

		JLabel lblNombresDeNaufrags = new JLabel("Nombres de naufrag\u00E9s max");

		spinner_7 = new JSpinner();
		spinner_7.setModel(new SpinnerNumberModel(new Integer(5), null, null, new Integer(1)));

		JLabel lblDureDeVie_2 = new JLabel("Dur\u00E9e de vie");

		spinner_8 = new JSpinner();
		spinner_8.setModel(new SpinnerNumberModel(new Integer(10), null, null, new Integer(1)));

		JLabel lblMto = new JLabel("<html><strong>Capteurs:</strong></html>");

		JSeparator separator_5 = new JSeparator();

		JLabel lblEnsoleill = new JLabel("Ensoleill\u00E9");

		JLabel lblPluvieux = new JLabel("Pluvieux");

		JLabel lblBrumeux = new JLabel("Brumeux");

		spinner_9 = new JSpinner();
		spinner_9.setModel(new SpinnerNumberModel(90, 0, 100, 1));

		spinner_10 = new JSpinner();
		spinner_10.setModel(new SpinnerNumberModel(70, 0, 100, 1));

		spinner_11 = new JSpinner();
		spinner_11.setModel(new SpinnerNumberModel(40, 0, 100, 1));

		spinner_12 = new JSpinner();
		spinner_12.setModel(new SpinnerNumberModel(10, 0, 100, 1));

		JLabel lblMalusDeDtection = new JLabel("Malus de d\u00E9tection \u00E0 distance");

		spinner_13 = new JSpinner();
		spinner_13.setModel(new SpinnerNumberModel(10, 0, 100, 1));

		JLabel lblMto_1 = new JLabel("<html><strong>M\u00E9t\u00E9o:</strong></html>");

		JSeparator separator_6 = new JSeparator();

		JLabel lblBeau = new JLabel("Beau");

		JLabel lblMdiocre = new JLabel("M\u00E9diocre");

		JLabel lblNombreDeCerlce = new JLabel("Nombre de cerlce de recherche");

		spinner_14 = new JSpinner();
		spinner_14.setModel(new SpinnerNumberModel(1, 1, 5, 1));

		JLabel lblCapacitBord = new JLabel("Capacit\u00E9 de naufrag\u00E9s \u00E0 bord");

		spinner_15 = new JSpinner();
		spinner_15.setModel(new SpinnerNumberModel(2, 1, 4, 1));

		JLabel lblNombreDeKit = new JLabel("Nombre de kit de survie");

		spinner_16 = new JSpinner();
		spinner_16.setModel(new SpinnerNumberModel(3, 1, 10, 1));

		JLabel lblNuageux_1 = new JLabel("Nuageux");

		JSeparator separator_3 = new JSeparator();

		separator = new JSeparator();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
				gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblCapacitBord, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(289, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPanel.createSequentialGroup()
												.addContainerGap()
												.addComponent(lblLargeur)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(Largeur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(lblHauteur)
												.addGap(10)
												.addComponent(Hauteur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(56)
												.addComponent(lblCote)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_contentPanel.createSequentialGroup()
														.addGap(10)
														.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
																.addComponent(lblConfig_drones)
																.addGroup(gl_contentPanel.createSequentialGroup()
																		.addComponent(lblDimensions)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(separator, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE))))
																		.addGroup(gl_contentPanel.createSequentialGroup()
																				.addContainerGap()
																				.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
																						.addComponent(lblNombreDeCerlce, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
																						.addComponent(lblNombreDeDrones))
																						.addPreferredGap(ComponentPlacement.RELATED)
																						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
																								.addComponent(spinner)
																								.addComponent(spinner_14, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
																								.addGap(0)
																								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
																										.addGroup(gl_contentPanel.createSequentialGroup()
																												.addGap(18)
																												.addComponent(lblNombreDeKit)
																												.addGap(18)
																												.addComponent(spinner_16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																												.addGroup(gl_contentPanel.createSequentialGroup()
																														.addGap(37)
																														.addComponent(lblQuantiteEssence)
																														.addPreferredGap(ComponentPlacement.RELATED)
																														.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)))))
																														.addGap(451))
																														.addGroup(gl_contentPanel.createSequentialGroup()
																																.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
																																		.addGroup(gl_contentPanel.createSequentialGroup()
																																				.addGap(64)
																																				.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
																																				.addGroup(gl_contentPanel.createSequentialGroup()
																																						.addContainerGap()
																																						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
																																								.addGroup(gl_contentPanel.createSequentialGroup()
																																										.addComponent(lblNombresDeBateaux_1)
																																										.addGap(53)
																																										.addComponent(spinner_5, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
																																										.addGap(18)
																																										.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
																																										.addGap(35)
																																										.addComponent(spinner_6, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
																																										.addGroup(gl_contentPanel.createSequentialGroup()
																																												.addComponent(lblDureDeVie_1)
																																												.addPreferredGap(ComponentPlacement.RELATED)
																																												.addComponent(separator_3, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
																																												.addPreferredGap(ComponentPlacement.RELATED))
																																												.addGroup(gl_contentPanel.createSequentialGroup()
																																														.addComponent(lblNaufrag)
																																														.addPreferredGap(ComponentPlacement.RELATED)
																																														.addComponent(separator_4, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
																																														.addGroup(gl_contentPanel.createSequentialGroup()
																																																.addComponent(lblNombresDeNaufrags)
																																																.addGap(46)
																																																.addComponent(spinner_7, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
																																																.addGap(18)
																																																.addComponent(lblDureDeVie_2)
																																																.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
																																																.addComponent(spinner_8, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
																																																.addGap(25))
																																																.addGroup(gl_contentPanel.createSequentialGroup()
																																																		.addComponent(lblMto, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
																																																		.addPreferredGap(ComponentPlacement.RELATED)
																																																		.addComponent(separator_5, GroupLayout.PREFERRED_SIZE, 381, GroupLayout.PREFERRED_SIZE))
																																																		.addGroup(gl_contentPanel.createSequentialGroup()
																																																				.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
																																																						.addGroup(gl_contentPanel.createSequentialGroup()
																																																								.addComponent(lblEnsoleill, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
																																																								.addPreferredGap(ComponentPlacement.RELATED)
																																																								.addComponent(spinner_9, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
																																																								.addGap(18)
																																																								.addComponent(lblPluvieux, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
																																																								.addGroup(gl_contentPanel.createSequentialGroup()
																																																										.addComponent(lblNuageux_1, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
																																																										.addPreferredGap(ComponentPlacement.RELATED)
																																																										.addComponent(spinner_10, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
																																																										.addGap(18)
																																																										.addComponent(lblBrumeux, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
																																																										.addPreferredGap(ComponentPlacement.UNRELATED)
																																																										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
																																																												.addComponent(spinner_12, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
																																																												.addComponent(spinner_11, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
																																																												.addPreferredGap(ComponentPlacement.RELATED, 73, Short.MAX_VALUE))
																																																												.addGroup(gl_contentPanel.createSequentialGroup()
																																																														.addComponent(lblMalusDeDtection, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
																																																														.addGap(18)
																																																														.addComponent(spinner_13, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
																																																														.addPreferredGap(ComponentPlacement.RELATED, 209, Short.MAX_VALUE))
																																																														.addGroup(gl_contentPanel.createSequentialGroup()
																																																																.addComponent(lblMto_1)
																																																																.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																																																																.addComponent(separator_6, GroupLayout.PREFERRED_SIZE, 381, GroupLayout.PREFERRED_SIZE)
																																																																.addGap(9))
																																																																.addGroup(gl_contentPanel.createSequentialGroup()
																																																																		.addGap(71)
																																																																		.addComponent(lblBeau)
																																																																		.addPreferredGap(ComponentPlacement.RELATED)
																																																																		.addComponent(slider_vie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																																																																		.addPreferredGap(ComponentPlacement.RELATED)
																																																																		.addComponent(lblMdiocre))
																																																																		.addGroup(gl_contentPanel.createSequentialGroup()
																																																																				.addComponent(lblConfig_Bateaux, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
																																																																				.addPreferredGap(ComponentPlacement.RELATED)
																																																																				.addComponent(separator_2, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
																																																																				.addGroup(gl_contentPanel.createSequentialGroup()
																																																																						.addComponent(lblNombresDeBateaux)
																																																																						.addGap(10)
																																																																						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
																																																																								.addComponent(spinner_15, Alignment.LEADING)
																																																																								.addComponent(spinner_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
																																																																								.addPreferredGap(ComponentPlacement.UNRELATED)
																																																																								.addComponent(label, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
																																																																								.addGap(35)
																																																																								.addComponent(spinner_4, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))))
																																																																								.addGap(456))
				);
		gl_contentPanel.setVerticalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDimensions))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblLargeur)
												.addComponent(Largeur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(Hauteur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblHauteur))
												.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblCote)
														.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
														.addGap(18)
														.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
																.addComponent(lblConfig_drones)
																.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(ComponentPlacement.RELATED)
																.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
																		.addComponent(lblNombreDeDrones)
																		.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
																				.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(lblQuantiteEssence)))
																				.addPreferredGap(ComponentPlacement.RELATED)
																				.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
																						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
																								.addComponent(lblNombreDeCerlce)
																								.addComponent(spinner_14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addComponent(lblNombreDeKit))
																								.addComponent(spinner_16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																								.addPreferredGap(ComponentPlacement.RELATED)
																								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
																										.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																										.addComponent(lblConfig_Bateaux))
																										.addPreferredGap(ComponentPlacement.RELATED)
																										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
																												.addComponent(spinner_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
																														.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																														.addComponent(label))
																														.addComponent(lblNombresDeBateaux))
																														.addPreferredGap(ComponentPlacement.RELATED)
																														.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
																																.addComponent(lblCapacitBord)
																																.addComponent(spinner_15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																																.addGap(14)
																																.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
																																		.addComponent(lblDureDeVie_1)
																																		.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																																		.addPreferredGap(ComponentPlacement.RELATED)
																																		.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
																																				.addComponent(lblNombresDeBateaux_1)
																																				.addComponent(spinner_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																																				.addComponent(spinner_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																																				.addComponent(label_2))
																																				.addPreferredGap(ComponentPlacement.UNRELATED)
																																				.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
																																						.addComponent(lblNaufrag)
																																						.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																																						.addPreferredGap(ComponentPlacement.RELATED)
																																						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
																																								.addComponent(lblNombresDeNaufrags)
																																								.addComponent(spinner_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																																								.addComponent(lblDureDeVie_2)
																																								.addComponent(spinner_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																																								.addPreferredGap(ComponentPlacement.UNRELATED)
																																								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
																																										.addComponent(lblMto)
																																										.addComponent(separator_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																																										.addPreferredGap(ComponentPlacement.RELATED)
																																										.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
																																												.addComponent(lblEnsoleill)
																																												.addComponent(spinner_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																																												.addComponent(lblPluvieux)
																																												.addComponent(spinner_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																																												.addPreferredGap(ComponentPlacement.RELATED)
																																												.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
																																														.addComponent(lblBrumeux)
																																														.addComponent(spinner_10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																																														.addComponent(spinner_12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																																														.addComponent(lblNuageux_1))
																																														.addPreferredGap(ComponentPlacement.RELATED)
																																														.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
																																																.addComponent(lblMalusDeDtection)
																																																.addComponent(spinner_13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																																																.addGap(18)
																																																.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
																																																		.addComponent(lblMto_1)
																																																		.addComponent(separator_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																																																		.addPreferredGap(ComponentPlacement.RELATED)
																																																		.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
																																																				.addComponent(lblMdiocre)
																																																				.addComponent(slider_vie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																																																				.addComponent(lblBeau))
																																																				.addGap(72))
				);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Cr\u00E9er...");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setaValider(true);
						dispose();
					}
				});
				okButton.setActionCommand("Cr\u00E9er...");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Annuler");
				buttonPane.add(cancelButton);
			}
		}
	}

	// * GETTERS *

	// * ACTION PERFORMED *
	private boolean isInteger(String s){
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}

	private void modifOkButton(){
		champsOK = champ_largeur_ok && champ_longueur_ok && champ_nbCercDrone_ok && champ_nbDrones_ok && champ_essence_ok;
		okButton.setEnabled(champsOK);
	}
	public boolean isaValider() {
		return aValider;
	}

	public void setaValider(boolean aValider) {
		this.aValider = aValider;
	}

	public int getLargeur() {
		return (Integer) Largeur.getValue();
	}
	public int getHauteur() {
		return (Integer) Hauteur.getValue();
	}
	public int getCote() {
		return (Integer) spinner_2.getValue();
	}
	public int getNbDrones() {
		return (Integer) spinner.getValue();
	}
	public int getEssenceDrone() {
		return (Integer) spinner_1.getValue();
	}
	public int getNbCercleRechercheDrone() {
		return (Integer) spinner_14.getValue();
	}
	public int getNbKitSurvie() {
		return (Integer) spinner_16.getValue();
	}
	public int getNbBateauSauvetage() {
		return (Integer) spinner_3.getValue();
	}
	public int getEssenceBateauSauvetage() {
		return (Integer) spinner_4.getValue();
	}
	public int getCapaciteNaufrageBateauSauvetage() {
		return (Integer) spinner_15.getValue();
	}
	public int getNbBateauMax() {
		return (Integer) spinner_5.getValue();
	}
	public int getEssenceBateau() {
		return (Integer) spinner_6.getValue();
	}
	public int getNaufrageCarteMax() {
		return (Integer) spinner_7.getValue();
	}
	public int getVieNaufrage() {
		return (Integer) spinner_8.getValue();
	}
	public int getProbaDetectionBeau() {
		return (Integer) spinner_9.getValue();
	}
	public int getProbaDetectionNuageux() {
		return (Integer) spinner_10.getValue();
	}
	public int getMalusDetection() {
		return (Integer) spinner_13.getValue();
	}
	public int getMeteo() {
		return (Integer) slider_vie.getValue();
	}
	public int getProbaDetectePluie() {
		return (Integer) spinner_11.getValue();
	}
	public int getProbaDetecteBrumeux() {
		return (Integer) spinner_12.getValue();
	}
}
