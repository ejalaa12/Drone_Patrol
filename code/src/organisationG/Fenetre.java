package organisationG;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.util.Arrays;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.JToolBar;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;
import javax.swing.JLabel;

import elementsG.CaseHexa;
import elementsG.CaseTerrain;

import javax.swing.JTabbedPane;

import organisation.Environnement;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JProgressBar;


public class Fenetre {

	private JFrame frmSimulationDunSystme;
	private ConfigurationEnvironnement configEnv;
	private Apropos apropos;
	private JLayeredPane layeredPane;
	private JPanel panel_hexagone;
	private JPanel panel_drone;
	private boolean dessin = false;
	private boolean dessin_drone = false;
	private boolean dessin_bateaux = false;
	private boolean dessin_naufrages = false;

	// Private de TEST FINAL
	private Environnement envif ;
	private Environnement eStat ;
	private Thread calcul;
	private int hexaXmouse, hexaYmouse;

	// Private d'animation
	private Timer go;
	private String[][] infoDrones;
	private String[][] infoBateauxSauvetages;
	private String[][] infoBateaux;
	private String[][] infoNaufrages;
	private String[][] infoAlertes;
	private String[][] infoAlertesMissions;
	private String[][] infoAlertesRecherches;
	private String[][] infoAlertesBateauxSauvetages;
	private String[][] infoStatistiques;
	private int nbTourStat = 1000;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fenetre window = new Fenetre();
					window.frmSimulationDunSystme.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Fenetre() {
		initialize();		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	//	@SuppressWarnings("serial")
	private void initialize() {
		frmSimulationDunSystme = new JFrame();
		frmSimulationDunSystme.setTitle("Simulation d\u2019un syst\u00E8me de patrouille maritime avec drones");
		frmSimulationDunSystme.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmSimulationDunSystme.setBounds(100, 100, 1432, 744);
		frmSimulationDunSystme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmSimulationDunSystme.setJMenuBar(menuBar);

		JMenu Menu1 = new JMenu("Fichier");
		menuBar.add(Menu1);

		JMenuItem mntmNewMenuItem = new JMenuItem("Nouveau...");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configEnv = new ConfigurationEnvironnement();
				configEnv.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				configEnv.setVisible(true);
				// Creation de l'environnement
				if (configEnv.isaValider()){
					creationEnvironnement(configEnv);
					getBtnPlay().setEnabled(true);
					getBtnPasPas().setEnabled(true);
					getBtnPause().setEnabled(true);
					getBtnStop().setEnabled(true);
					// On autorise le dessin du fond a s'afficher ainsi que la meteo
					dessin = true;
					// On initialise le premier tour de l'environnement ce qui a pour effet de creer drones, bateauS, meteo.
					envif.Suivant();
					actualiseTableDrones();
					actualiseTableBateauxSauvetages();
					actualiseTableBateaux();
					actualiseTableNaufrages();
					actualiseTableAlertesTous();
					actualiseTableAlertesMissions();
					actualiseTableAlertesRecherches();
					actualiseTableAlertesBateauxSauvetages();
					actualiseTableStatistiques();
					// On peut donc autoriser le dessin des drones, de la meteo et des bateaux
					dessin_drone = true;
					dessin_bateaux = true;
					dessin_naufrages = true;
					getPanel_hexagone().repaint();
				}
			}
		});
		Menu1.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Quitter");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSimulationDunSystme.dispose();
			}
		});
		Menu1.add(mntmNewMenuItem_1);

		JMenu mnAPropos = new JMenu("A propos");
		menuBar.add(mnAPropos);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("A propos");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apropos = new Apropos();
				apropos.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				apropos.setVisible(true);
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(Fenetre.class.getResource("/com/jgoodies/looks/plastic/icons/32x32/dialog-question.png")));
		mnAPropos.add(mntmNewMenuItem_2);
		frmSimulationDunSystme.getContentPane().setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		frmSimulationDunSystme.getContentPane().add(toolBar, BorderLayout.NORTH);

		btnPlay = new JButton("Play");
		btnPlay.setEnabled(false);
		btnPlay.addActionListener(animation);
		btnPlay.setHorizontalAlignment(SwingConstants.LEFT);
		btnPlay.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/Play-32.png")));
		toolBar.add(btnPlay);

		btnPasPas = new JButton("Pas \u00E0 Pas");
		btnPasPas.setEnabled(false);
		btnPasPas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				envif.Suivant();
				actualiseTableDrones();
				actualiseTableBateauxSauvetages();
				actualiseTableBateaux();
				actualiseTableNaufrages();
				actualiseTableAlertesTous();
				actualiseTableAlertesMissions();
				actualiseTableAlertesRecherches();
				actualiseTableAlertesBateauxSauvetages();
				actualiseTableStatistiques();
			}
		});
		btnPasPas.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/Pas-32.png")));
		toolBar.add(btnPasPas);

		btnPause = new JButton("Pause");
		btnPause.setEnabled(false);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				go.stop();
				getBtnPlay().setEnabled(true);
				getBtnPasPas().setEnabled(true);
			}
		});
		btnPause.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/Pause-32.png")));
		toolBar.add(btnPause);

		btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				go.stop();
				getBtnPasPas().setEnabled(false);
				getBtnPause().setEnabled(false);
				getBtnPlay().setEnabled(false);
			}
		});
		btnStop.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/Stop-32.png")));
		toolBar.add(btnStop);

		lblClicSouris = new JLabel("Clic de souris en: 000,000");
		lblClicSouris.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/mouse.png")));
		toolBar.add(lblClicSouris);

		lblHexagone = new JLabel("Hexagone: [00,00]");
		lblHexagone.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/hexa.png")));
		toolBar.add(lblHexagone);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator);

		progressBar = new JProgressBar(0,nbTourStat);
		progressBar.setStringPainted(true);
		toolBar.add(progressBar);

		layeredPane = new JLayeredPane();
		layeredPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getLblClicSouris().setText("Clic de souris en: "+arg0.getX()+","+arg0.getY());
				if (envif!=null){
					int[] l = envif.getDim().pixToHex2(arg0.getX(), arg0.getY());
					getLblHexagone().setText("Hexagone:"+Arrays.toString(l));
					hexaXmouse = l[0]; hexaYmouse=l[1];
					getPanel_nauf().repaint();
				}

			}
		});
		frmSimulationDunSystme.getContentPane().add(layeredPane, BorderLayout.CENTER);

		panel_bat = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 4810864991723851842L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (dessin_bateaux == true  ){
					envif.dessinBateaux(g);
					envif.dessinBateauxSauvetage(g);
				}
				if (dessin==true) envif.dessinAlertes(g);
			}
		};
		panel_bat.setOpaque(false);
		panel_bat.setBounds(0, 0, 912, 644);
		layeredPane.add(panel_bat);

		panel_drone = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 4810864991723851842L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (dessin_drone == true  ){
					envif.dessinDrones(g);
				}
			}
		};
		panel_drone.setBounds(0, 0, 912, 644);
		layeredPane.add(panel_drone);
		panel_drone.setOpaque(false);

		panel_nauf = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (dessin_naufrages == true  ){
					envif.dessinNaufrages(g);
				}
				Graphics2D g2d = (Graphics2D) g;
				if (hexaXmouse!=0){
					CaseHexa c = new CaseHexa(new int[] {hexaXmouse,hexaYmouse}, envif);
					g2d.setStroke(new BasicStroke(2));
					g2d.setColor(Color.decode("#ae0001"));
					g2d.drawPolygon(c.getX(), c.getY(), 6);
				}
			}
		};
		panel_nauf.setOpaque(false);
		panel_nauf.setBounds(0, 0, 912, 644);
		layeredPane.add(panel_nauf);

		panel_meteo = new JPanel(){
			/**
			 * Ce panel ne dessine que le terrain et la meteo par dessus
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (dessin ==true){ 
					envif.dessinMeteo(g);
					for(int colonne=1;colonne<=envif.getLargeurTotalCarte();colonne++){
						for(int ligne =1;ligne<=envif.getHauteurTotalCarte();ligne++){
							CaseTerrain c = new CaseTerrain(new int[] {ligne,colonne}, envif);
							c.affiche(g);
						}
					}
				}
			}
		};
		panel_meteo.setOpaque(false);
		panel_meteo.setBounds(0, 0, 912, 644);
		layeredPane.add(panel_meteo);

		panel_hexagone = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (dessin ==true){ 
					for(int colonne=1;colonne<=envif.getLargeurTotalCarte();colonne++){
						for(int ligne =1;ligne<=envif.getHauteurTotalCarte();ligne++){
							CaseHexa c = new CaseHexa(new int[] {ligne,colonne}, envif);
							c.affiche(g);
						}
					}
				}
			}
		};
		panel_hexagone.setOpaque(false);
		panel_hexagone.setBounds(0, 0, 912, 644);
		layeredPane.add(panel_hexagone);

		JPanel panel = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				int height = getSize().height;
				int width = getSize().width;

				// draw vertical rule at left side (bottom to top)
				for (int y=height; y > 0; y--) {
					int pos = (y-height);

					if (pos % 50 == 0) {          // 20 pixel tick every 50 pixels
						g.drawLine(0, y, 20, y);
					}
					else if (pos % 10 == 0) {     // 10 pixel tick every 10 pixels
						g.drawLine(0, y, 10, y);
					}
					else if (pos % 2 == 0) {      // 5 pixel tick every 2 pixels
						g.drawLine(0, y, 5, y);
					}
				}
				// draw horizontal rule at bottom (left to right)
				for (int x=0; x < width; x++) {
					if (x % 50 == 0) {
						g.drawLine(x, height - 20, x, height);
					}
					else if (x % 10 == 0) {
						g.drawLine(x, height - 10, x, height);
					}
					else if (x % 2 == 0) {
						g.drawLine(x, height - 5, x, height);
					}
				}
			}
		};
		panel.setBounds(0, 0, 912, 644);
		layeredPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_3 = new JLabel("");
		panel.add(lblNewLabel_3, BorderLayout.CENTER);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(922, 0, 452, 644);
		layeredPane.add(tabbedPane);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		tabbedPane.addTab("El\u00E9ments", null, splitPane, null);

		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setResizeWeight(0.5);
		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_3);

		JSplitPane splitPane_5 = new JSplitPane();
		splitPane_5.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_5.setResizeWeight(0.01);
		splitPane_3.setLeftComponent(splitPane_5);

		JLabel lblDrones = new JLabel("Drones");
		lblDrones.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/predator-32p.png")));
		splitPane_5.setLeftComponent(lblDrones);

		JScrollPane scrollPane = new JScrollPane();
		splitPane_5.setRightComponent(scrollPane);

		table_Drones = new JTable();
		table_Drones.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Nom", "Position", "Etat", "Kit de survie", "Essences"
				}
				));
		table_Drones.getColumnModel().getColumn(0).setPreferredWidth(20);
		table_Drones.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_Drones.getColumnModel().getColumn(2).setPreferredWidth(150);
		table_Drones.getColumnModel().getColumn(3).setPreferredWidth(70);
		table_Drones.getColumnModel().getColumn(4).setPreferredWidth(60);
		scrollPane.setViewportView(table_Drones);

		JSplitPane splitPane_6 = new JSplitPane();
		splitPane_6.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_6.setResizeWeight(0.01);
		splitPane_3.setRightComponent(splitPane_6);

		JLabel lblBateauxDeSauvetages = new JLabel("Bateaux de sauvetages");
		lblBateauxDeSauvetages.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/yacht.png")));
		splitPane_6.setLeftComponent(lblBateauxDeSauvetages);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_6.setRightComponent(scrollPane_1);

		table_BateauxSauvetages = new JTable();
		table_BateauxSauvetages.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Nom", "Position", "Etat", "Naufrag\u00E9(s) \u00E0 bord", "Essence"
				}
				));
		table_BateauxSauvetages.getColumnModel().getColumn(1).setPreferredWidth(61);
		table_BateauxSauvetages.getColumnModel().getColumn(3).setPreferredWidth(105);
		scrollPane_1.setViewportView(table_BateauxSauvetages);

		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setResizeWeight(0.5);
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_4);

		JSplitPane splitPane_7 = new JSplitPane();
		splitPane_7.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_7.setResizeWeight(0.01);
		splitPane_4.setLeftComponent(splitPane_7);

		JLabel lblBateaux = new JLabel("Bateaux");
		lblBateaux.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/ocean_liner.png")));
		splitPane_7.setLeftComponent(lblBateaux);

		JScrollPane scrollPane_2 = new JScrollPane();
		splitPane_7.setRightComponent(scrollPane_2);

		table_Bateaux = new JTable();
		table_Bateaux.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Nom", "Position", "Destination", "Passagers \u00E0 bord", "Essence"
				}
				));
		scrollPane_2.setViewportView(table_Bateaux);

		JSplitPane splitPane_8 = new JSplitPane();
		splitPane_8.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_8.setResizeWeight(0.01);
		splitPane_4.setRightComponent(splitPane_8);

		JLabel lblNaufrags = new JLabel("Naufrag\u00E9s");
		lblNaufrags.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/noye.png")));
		splitPane_8.setLeftComponent(lblNaufrags);

		JScrollPane scrollPane_3 = new JScrollPane();
		splitPane_8.setRightComponent(scrollPane_3);

		table_Naufrages = new JTable();
		table_Naufrages.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Nom", "Position", "Suivis", "Kit de survie", "Vie"
				}
				));
		scrollPane_3.setViewportView(table_Naufrages);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.5);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		tabbedPane.addTab("Gestion des alertes", null, splitPane_1, null);

		JSplitPane splitPane_9 = new JSplitPane();
		splitPane_9.setResizeWeight(0.5);
		splitPane_9.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setLeftComponent(splitPane_9);

		JSplitPane splitPane_11 = new JSplitPane();
		splitPane_11.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_9.setLeftComponent(splitPane_11);

		JLabel lblAlertesmises = new JLabel("Alertes \u00E9mises");
		splitPane_11.setLeftComponent(lblAlertesmises);

		JScrollPane scrollPane_4 = new JScrollPane();
		splitPane_11.setRightComponent(scrollPane_4);

		table_Alertes = new JTable();
		table_Alertes.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Position", "Temps depuis l'\u00E9mission", "Etat"
				}
				));
		table_Alertes.getColumnModel().getColumn(1).setPreferredWidth(122);
		scrollPane_4.setViewportView(table_Alertes);

		JSplitPane splitPane_12 = new JSplitPane();
		splitPane_12.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_9.setRightComponent(splitPane_12);

		JLabel lblAlertesPrisentEn = new JLabel("Alertes prisent en compte par un drone");
		splitPane_12.setLeftComponent(lblAlertesPrisentEn);

		JScrollPane scrollPane_5 = new JScrollPane();
		splitPane_12.setRightComponent(scrollPane_5);

		table_AlertesMissions = new JTable();
		table_AlertesMissions.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Position", "Temps depuis l'\u00E9mission"
				}
				));
		table_AlertesMissions.getColumnModel().getColumn(1).setPreferredWidth(123);
		scrollPane_5.setViewportView(table_AlertesMissions);

		JSplitPane splitPane_10 = new JSplitPane();
		splitPane_10.setResizeWeight(0.5);
		splitPane_10.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_10);

		JSplitPane splitPane_13 = new JSplitPane();
		splitPane_13.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_10.setLeftComponent(splitPane_13);

		JLabel lblNewLabel = new JLabel("Alertes autour de laquelle un drone cherche des naufrag\u00E9s");
		splitPane_13.setLeftComponent(lblNewLabel);

		JScrollPane scrollPane_6 = new JScrollPane();
		splitPane_13.setRightComponent(scrollPane_6);

		table_AlertesRecherches = new JTable();
		table_AlertesRecherches.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Position", "Temps depuis l'\u00E9mission"
				}
				));
		table_AlertesRecherches.getColumnModel().getColumn(1).setPreferredWidth(123);
		scrollPane_6.setViewportView(table_AlertesRecherches);

		JSplitPane splitPane_14 = new JSplitPane();
		splitPane_14.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_10.setRightComponent(splitPane_14);

		JLabel lblNewLabel_1 = new JLabel("Alertes \u00E9misent aux bateaux de sauvetages");
		splitPane_14.setLeftComponent(lblNewLabel_1);

		JScrollPane scrollPane_7 = new JScrollPane();
		splitPane_14.setRightComponent(scrollPane_7);

		table_AlertesBateauxSauvetages = new JTable();
		table_AlertesBateauxSauvetages.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Position", "Temps depuis l'\u00E9mission"
				}
				));
		table_AlertesBateauxSauvetages.getColumnModel().getColumn(1).setPreferredWidth(123);
		scrollPane_7.setViewportView(table_AlertesBateauxSauvetages);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setResizeWeight(0.5);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		tabbedPane.addTab("Statistiques", null, splitPane_2, null);

		JSplitPane splitPane_15 = new JSplitPane();
		splitPane_15.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setLeftComponent(splitPane_15);

		JLabel lblStatistiquesEnCours = new JLabel("Statistiques en cours");
		lblStatistiquesEnCours.setIcon(new ImageIcon(Fenetre.class.getResource("/ressources/img/s32/stats.png")));
		splitPane_15.setLeftComponent(lblStatistiquesEnCours);

		JScrollPane scrollPane_8 = new JScrollPane();
		splitPane_15.setRightComponent(scrollPane_8);

		table_Statistiques = new JTable();
		table_Statistiques.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Statistiques", "Valeur"
				}
				));
		table_Statistiques.getColumnModel().getColumn(0).setPreferredWidth(370);
		table_Statistiques.getColumnModel().getColumn(1).setPreferredWidth(70);
		scrollPane_8.setViewportView(table_Statistiques);

		JSplitPane splitPane_16 = new JSplitPane();
		splitPane_16.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setRightComponent(splitPane_16);

		JScrollPane scrollPane_9 = new JScrollPane();
		splitPane_16.setRightComponent(scrollPane_9);

		table_Cartes = new JTable();
		table_Cartes.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Statistiques", "Valeur"
				}
				));
		table_Statistiques.getColumnModel().getColumn(0).setPreferredWidth(370);
		table_Statistiques.getColumnModel().getColumn(1).setPreferredWidth(70);
		scrollPane_9.setViewportView(table_Cartes);

		JSplitPane splitPane_17 = new JSplitPane();
		splitPane_17.setResizeWeight(1.0);
		splitPane_16.setLeftComponent(splitPane_17);

		JLabel lblNewLabel_2 = new JLabel("Statistiques sur plusieurs tours");
		splitPane_17.setLeftComponent(lblNewLabel_2);

		JButton btnNewButton = new JButton("Lancer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getProgressBar().setValue(0);
				if (calcul!=null && calcul.isAlive())
					calcul.stop();
				ConfigurationEnvironnement configStat = new ConfigurationEnvironnement();
				configStat.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
				configStat.setVisible(true);
				if (configStat.isaValider()){
					eStat = new Environnement(configStat.getLargeur(),configStat.getHauteur(),configStat.getCote(),
							configStat.getNbDrones(),configStat. getEssenceDrone(),configStat.getNbCercleRechercheDrone(),
							configStat.getNbKitSurvie(),configStat.getNbBateauSauvetage(),configStat.getEssenceBateauSauvetage(),
							configStat.getCapaciteNaufrageBateauSauvetage(),configStat.getNbBateauMax(),configStat.getEssenceBateau(),
							configStat.getNaufrageCarteMax(),configStat.getVieNaufrage(),configStat.getProbaDetectionBeau(),
							configStat.getProbaDetectionNuageux(),configStat.getProbaDetectePluie(),configStat.getProbaDetecteBrumeux(),
							configStat.getMalusDetection(),configStat.getMeteo());
					calcul = new Thread(){
						public void run(){
							for(int i=0;i<nbTourStat;i++){
								getProgressBar().setValue(i);
								eStat.Suivant();
							}
							getProgressBar().setValue(nbTourStat);
							actualiseTableStatCarte();

						}
					};
					calcul.start();
				}


			}
		});
		splitPane_17.setRightComponent(btnNewButton);

		go = new Timer(320,animation);
	}

	protected void creationEnvironnement(ConfigurationEnvironnement configEnv2) {
		envif = new Environnement(
				configEnv.getLargeur(),configEnv.getHauteur(),configEnv.getCote(),
				configEnv.getNbDrones(),configEnv. getEssenceDrone(),configEnv.getNbCercleRechercheDrone(),
				configEnv.getNbKitSurvie(),configEnv.getNbBateauSauvetage(),configEnv.getEssenceBateauSauvetage(),
				configEnv.getCapaciteNaufrageBateauSauvetage(),configEnv.getNbBateauMax(),configEnv.getEssenceBateau(),
				configEnv.getNaufrageCarteMax(),configEnv.getVieNaufrage(),configEnv.getProbaDetectionBeau(),
				configEnv.getProbaDetectionNuageux(),configEnv.getProbaDetectePluie(),configEnv.getProbaDetecteBrumeux(),
				configEnv.getMalusDetection(),configEnv.getMeteo(), this);

	}

	protected void actualiseTableDrones(){
		infoDrones = new String[envif.getDrones().size()][5];
		for(int i=0;i<envif.getDrones().size();i++){			
			infoDrones[i][0] = envif.getDrones().get(i).getNom();
			infoDrones[i][1] = Arrays.toString(envif.getDrones().get(i).getPosition());
			infoDrones[i][2] = "Patrouille";
			if(envif.getDrones().get(i).getEtat()==2)
				infoDrones[i][2] = "Mission vers "+Arrays.toString(envif.getDrones().get(i).getAlerte().getPosition());
			if(envif.getDrones().get(i).getEtat()==3)
				infoDrones[i][2] = "Recherche autour de "+Arrays.toString(envif.getDrones().get(i).getAlerte().getPosition());
			if(envif.getDrones().get(i).getEtat()==4)
				infoDrones[i][2] = "Suiveur";
			if(envif.getDrones().get(i).getEtat()==5)
				infoDrones[i][2] = "Recalibrage vers "+Arrays.toString(envif.getDrones().get(i).getPositionPatrouille());
			if(envif.getDrones().get(i).getEtat()==6)
				infoDrones[i][2] = "Retourne faire le Plein";
			if(envif.getDrones().get(i).getEtat()==7)
				infoDrones[i][2] = "CRASH en "+Arrays.toString(envif.getDrones().get(i).getPosition());
			infoDrones[i][3] = Integer.toString(envif.getDrones().get(i).getNbKitSurvie());
			infoDrones[i][4] = Integer.toString(envif.getDrones().get(i).getEssence());
		}
		table_Drones.setModel(new DefaultTableModel(
				infoDrones,
				new String[] {
						"Nom", "Position", "Etat", "Kit de survies", "Essences"
				}
				));
		table_Drones.getColumnModel().getColumn(0).setPreferredWidth(1);
		table_Drones.getColumnModel().getColumn(1).setPreferredWidth(25);
		table_Drones.getColumnModel().getColumn(2).setPreferredWidth(150);
		table_Drones.getColumnModel().getColumn(3).setPreferredWidth(70);
		table_Drones.getColumnModel().getColumn(4).setPreferredWidth(60);
	}

	protected void actualiseTableBateauxSauvetages(){
		infoBateauxSauvetages = new String[envif.getBateauxSauvetages().size()][5];
		for(int i=0;i<envif.getBateauxSauvetages().size();i++){			
			infoBateauxSauvetages[i][0] = envif.getBateauxSauvetages().get(i).getNom();
			infoBateauxSauvetages[i][1] = Arrays.toString(envif.getBateauxSauvetages().get(i).getPosition());
			infoBateauxSauvetages[i][2] = "Attente";
			if(envif.getBateauxSauvetages().get(i).getEtat()==2)
				infoBateauxSauvetages[i][2] = "Mission vers "+Arrays.toString(envif.getBateauxSauvetages().get(i).getAlerte().getPosition());
			if(envif.getBateauxSauvetages().get(i).getEtat()==3)
				infoBateauxSauvetages[i][2] = "Retourne au port";
			if(envif.getBateauxSauvetages().get(i).getEtat()==4)
				infoBateauxSauvetages[i][2] = "Retourne faire le Plein";
			if(envif.getBateauxSauvetages().get(i).getEtat()==5)
				infoBateauxSauvetages[i][2] = "Coulé en "+Arrays.toString(envif.getBateauxSauvetages().get(i).getPosition());
			infoBateauxSauvetages[i][3] = Integer.toString(envif.getBateauxSauvetages().get(i).getNaufragesBord().size());
			infoBateauxSauvetages[i][4] = Integer.toString(envif.getBateauxSauvetages().get(i).getEssence());
		}
		table_BateauxSauvetages.setModel(new DefaultTableModel(
				infoBateauxSauvetages,
				new String[] {
						"Nom", "Position", "Etat", "Naufrag\u00E9(s) \u00E0 bord", "Essence"
				}
				));
		table_Drones.getColumnModel().getColumn(0).setPreferredWidth(30);
		table_Drones.getColumnModel().getColumn(1).setPreferredWidth(25);
		table_Drones.getColumnModel().getColumn(2).setPreferredWidth(150);
		table_Drones.getColumnModel().getColumn(3).setPreferredWidth(70);
		table_Drones.getColumnModel().getColumn(4).setPreferredWidth(10);
	}

	protected void actualiseTableBateaux(){
		infoBateaux = new String[envif.getBateaux().size()][5];
		for(int i=0;i<envif.getBateaux().size();i++){			
			infoBateaux[i][0] = envif.getBateaux().get(i).getNom();
			infoBateaux[i][1] = Arrays.toString(envif.getBateaux().get(i).getPosition());
			infoBateaux[i][2] = Arrays.toString(envif.getBateaux().get(i).getArrive());
			infoBateaux[i][3] = Integer.toString(envif.getBateaux().get(i).getNbPassager());
			infoBateaux[i][4] = Integer.toString(envif.getBateaux().get(i).getEssence());
		}
		table_Bateaux.setModel(new DefaultTableModel(
				infoBateaux,
				new String[] {
						"Nom", "Position", "Destination", "Passagers \u00E0 bord", "Essence"
				}
				));
	}

	protected void actualiseTableNaufrages(){
		infoNaufrages = new String[envif.getNaufrages().size()][5];
		for(int i=0;i<envif.getNaufrages().size();i++){			
			infoNaufrages[i][0] = envif.getNaufrages().get(i).getNom();
			infoNaufrages[i][1] = Arrays.toString(envif.getNaufrages().get(i).getPosition());
			infoNaufrages[i][2] = "non";
			if(envif.getListeAlertes().contains(envif.getNaufrages().get(i)))
				infoNaufrages[i][2] = "oui";
			String s = new Boolean(envif.getNaufrages().get(i).isKitSurvie()).toString();
			infoNaufrages[i][3] = s;
			infoNaufrages[i][4] = Integer.toString(envif.getNaufrages().get(i).getEssence());
		}
		table_Naufrages.setModel(new DefaultTableModel(
				infoNaufrages,
				new String[] {
						"Nom", "Position", "Suivis", "Kit de survie", "Vie"
				}
				));
	}

	protected void actualiseTableAlertesTous(){
		infoAlertes = new String[envif.getListeAlertes().getAlertesTous().size()][3];
		for(int i=0;i<envif.getListeAlertes().getAlertesTous().size();i++){			
			infoAlertes[i][0] = Arrays.toString(envif.getListeAlertes().getAlertesTous().get(i).getPosition());
			infoAlertes[i][1] = Integer.toString(envif.getListeAlertes().getAlertesTous().get(i).getTemps());
			infoAlertes[i][2] = Integer.toString(envif.getListeAlertes().getAlertesTous().get(i).getEtat());
		}
		table_Alertes.setModel(new DefaultTableModel(
				infoAlertes,
				new String[] {
						"Position", "Temps depuis l'\u00E9mission", "Etat"
				}
				));
	}

	protected void actualiseTableAlertesMissions(){
		infoAlertesMissions = new String[envif.getListeAlertes().getAlertesMissions().size()][2];
		for(int i=0;i<envif.getListeAlertes().getAlertesMissions().size();i++){			
			infoAlertesMissions[i][0] = Arrays.toString(envif.getListeAlertes().getAlertesMissions().get(i).getPosition());
			infoAlertesMissions[i][1] = Integer.toString(envif.getListeAlertes().getAlertesMissions().get(i).getTemps());
		}
		table_AlertesMissions.setModel(new DefaultTableModel(
				infoAlertesMissions,
				new String[] {
						"Position", "Temps depuis l'\u00E9mission"
				}
				));
	}

	protected void actualiseTableAlertesRecherches(){
		infoAlertesRecherches = new String[envif.getListeAlertes().getAlertesRecherches().size()][2];
		for(int i=0;i<envif.getListeAlertes().getAlertesRecherches().size();i++){			
			infoAlertesRecherches[i][0] = Arrays.toString(envif.getListeAlertes().getAlertesRecherches().get(i).getPosition());
			infoAlertesRecherches[i][1] = Integer.toString(envif.getListeAlertes().getAlertesRecherches().get(i).getTemps());
		}
		table_AlertesRecherches.setModel(new DefaultTableModel(
				infoAlertesRecherches,
				new String[] {
						"Position", "Temps depuis l'\u00E9mission"
				}
				));
	}

	protected void actualiseTableAlertesBateauxSauvetages(){
		infoAlertesBateauxSauvetages = new String[envif.getListeAlertes().getAlertesBateauxSauvetagesMissions().size()][2];
		for(int i=0;i<envif.getListeAlertes().getAlertesBateauxSauvetagesMissions().size();i++){			
			infoAlertesBateauxSauvetages[i][0] = Arrays.toString(envif.getListeAlertes().getAlertesBateauxSauvetagesMissions().get(i).getPosition());
			infoAlertesBateauxSauvetages[i][1] = Integer.toString(envif.getListeAlertes().getAlertesBateauxSauvetagesMissions().get(i).getTemps());
		}
		table_AlertesBateauxSauvetages.setModel(new DefaultTableModel(
				infoAlertesBateauxSauvetages,
				new String[] {
						"Position", "Temps depuis l'\u00E9mission"
				}
				));
	}

	protected void actualiseTableStatistiques(){
		infoStatistiques = new String[8][2];
		infoStatistiques[0][0] = "Nombre de naufragés morts";
		infoStatistiques[1][0] = "Nombre de naufragés sauvés par un bateau de sauvetage";
		infoStatistiques[2][0] = "Nombre de naufragés sauvés par un bateau";
		infoStatistiques[3][0] = "Nombre de naufragés sauvés par la côte";
		infoStatistiques[4][0] = "Temps moyen d'arrivé du drone sur le naufragé";
		infoStatistiques[5][0] = "Temps moyen avant le sauvetage du naufragé";
		infoStatistiques[6][0] = "Temps moyen avant la mort du naufragé";
		infoStatistiques[7][0] = "Nombre d'alertes envoyées";
		infoStatistiques[0][1] = Integer.toString(envif.getNaufragesMorts().size());
		infoStatistiques[1][1] = Integer.toString(envif.getNaufragesSauves().size());
		infoStatistiques[2][1] = Integer.toString(envif.getNaufragesSauvesBateau().size());
		infoStatistiques[3][1] = Integer.toString(envif.getNaufragesSauvesCote().size());
		infoStatistiques[4][1] = Double.toString(envif.getStats()[2]);
		infoStatistiques[5][1] = Double.toString(envif.getStats()[0]);
		infoStatistiques[6][1] = Double.toString(envif.getStats()[1]);
		infoStatistiques[7][1] = Integer.toString(envif.getListeAlertes().getAlertesTous().size());
		
		table_Statistiques.setModel(new DefaultTableModel(
				infoStatistiques,
				new String[] {
						"Statistiques", "Valeurs"
				}
				));
		table_Statistiques.getColumnModel().getColumn(0).setPreferredWidth(370);
		table_Statistiques.getColumnModel().getColumn(1).setPreferredWidth(70);
	}

	protected void actualiseTableStatCarte(){
		infoStatistiques = new String[8][2];		
		infoStatistiques[0][0] = "Nombre de naufragés morts";
		infoStatistiques[1][0] = "Nombre de naufragés sauvés par un bateau de sauvetage";
		infoStatistiques[2][0] = "Nombre de naufragés sauvés par un bateau";
		infoStatistiques[3][0] = "Nombre de naufragés sauvés par la côte";
		infoStatistiques[4][0] = "Temps moyen d'arrivé du drone sur le naufragé";
		infoStatistiques[5][0] = "Temps moyen avant le sauvetage du naufragé";
		infoStatistiques[6][0] = "Temps moyen avant la mort du naufragé";
		infoStatistiques[7][0] = "Nombre d'alertes anvoyées";
		infoStatistiques[0][1] = Integer.toString(eStat.getNaufragesMorts().size());
		infoStatistiques[1][1] = Integer.toString(eStat.getNaufragesSauves().size());
		infoStatistiques[2][1] = Integer.toString(eStat.getNaufragesSauvesBateau().size());
		infoStatistiques[3][1] = Integer.toString(eStat.getNaufragesSauvesCote().size());
		infoStatistiques[4][1] = Double.toString(eStat.getStats()[2]);
		infoStatistiques[5][1] = Double.toString(eStat.getStats()[0]);
		infoStatistiques[6][1] = Double.toString(eStat.getStats()[1]);
		infoStatistiques[7][1] = Integer.toString(eStat.getListeAlertes().getAlertesTous().size());
		
		table_Cartes.setModel(new DefaultTableModel(
				infoStatistiques,
				new String[] {
						"Statistiques", "Valeurs"
				}
				));
		table_Cartes.getColumnModel().getColumn(0).setPreferredWidth(370);
		table_Cartes.getColumnModel().getColumn(1).setPreferredWidth(70);
	}

	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}
	public JPanel getPanel_hexagone() {
		return panel_hexagone;
	}
	public JPanel getPanel_drone() {
		return panel_drone;
	}

	ActionListener animation = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			envif.Suivant();
			actualiseTableDrones();
			actualiseTableBateauxSauvetages();
			actualiseTableBateaux();
			actualiseTableNaufrages();
			actualiseTableAlertesTous();
			actualiseTableAlertesMissions();
			actualiseTableAlertesRecherches();
			actualiseTableAlertesBateauxSauvetages();
			actualiseTableStatistiques();
			if (!go.isRunning()){
				go.start();
				getBtnPlay().setEnabled(false);
				getBtnPasPas().setEnabled(false);

			}
		}
	};
	private JButton btnPlay;
	private JButton btnPasPas;
	private JButton btnPause;
	private JPanel panel_bat;
	private JPanel panel_meteo;
	private JPanel panel_nauf;
	private JButton btnStop;
	private JTable table_Drones;
	private JTable table_BateauxSauvetages;
	private JTable table_Bateaux;
	private JTable table_Naufrages;
	private JTable table_Alertes;
	private JTable table_AlertesMissions;
	private JTable table_AlertesRecherches;
	private JTable table_AlertesBateauxSauvetages;
	private JLabel lblClicSouris;
	private JLabel lblHexagone;
	private JTable table_Statistiques;
	private JTable table_Cartes;
	private JProgressBar progressBar;
	public JButton getBtnPlay() {
		return btnPlay;
	}
	public JButton getBtnPasPas() {
		return btnPasPas;
	}
	public JButton getBtnPause() {
		return btnPause;
	}
	public JPanel getPanel_bat() {
		return panel_bat;
	}
	public JPanel getPanel_meteo() {
		return panel_meteo;
	}
	public JPanel getPanel_nauf() {
		return panel_nauf;
	}
	public JButton getBtnStop() {
		return btnStop;
	}
	public JLabel getLblClicSouris() {
		return lblClicSouris;
	}
	public JLabel getLblHexagone() {
		return lblHexagone;
	}
	public JProgressBar getProgressBar() {
		return progressBar;
	}
}
