package organisationG;

import java.awt.EventQueue;

import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;

import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Apropos extends JDialog {

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
					Apropos dialog = new Apropos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Apropos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Apropos.class.getResource("/com/jgoodies/looks/plastic/icons/32x32/dialog-question.png")));
		getContentPane().setBackground(Color.decode("#eeeef1"));
		setTitle("\u00C0 propos...");
		setBounds(100, 100, 665, 342);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Apropos.class.getResource("/ressources/img/apropos.png")));
		lblNewLabel.setBounds(10, 11, 276, 270);
		getContentPane().add(lblNewLabel);

		JLabel lblSimulationDrones = new JLabel("<html><font size=\"5\"><font face=\"Calibri\"><font color=\"#2a8a86\"><strong>Simulation Drones</strong></font></font></font></html>");
		lblSimulationDrones.setIcon(new ImageIcon("C:\\Users\\ENSTA\\Desktop\\Semestre 2\\Projet Java\\Simulation.png"));
		lblSimulationDrones.setBounds(285, 11, 323, 90);
		getContentPane().add(lblSimulationDrones);

		JLabel lblCrPar = new JLabel("Cr\u00E9\u00E9 par");
		lblCrPar.setBounds(10, 246, 46, 14);
		getContentPane().add(lblCrPar);

		JLabel lblNicolasTokotuu = new JLabel("Nicolas TOKOTUU");
		lblNicolasTokotuu.setBounds(66, 267, 94, 14);
		getContentPane().add(lblNicolasTokotuu);

		JLabel lblAlaaElJawad = new JLabel("Alaa El Jawad");
		lblAlaaElJawad.setBounds(66, 246, 94, 14);
		getContentPane().add(lblAlaaElJawad);

		JSeparator separator = new JSeparator();
		separator.setBounds(285, 104, 354, 14);
		getContentPane().add(separator);

		JPanel panel = new JPanel();
		panel.setBounds(285, 117, 354, 176);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		panel.add(scrollPane, BorderLayout.CENTER);

		JLabel lblNewLabel_1 = new JLabel("<html><div align=\"justified\">Dans le cadre des nos relations Thales AS, nous avons initi\u00E9 un <br>projet pour prototyper un syst\u00E8me de sauvetage en mer bas\u00E9 <br>sur l\u2019utilisation de drones a\u00E9riens. <br>L\u2019objectif est d\u2019avoir un drone qui r\u00E9alise une surveillance d\u2019un <br> domaine maritime et \u00E0 la r\u00E9ception d\u2019un message de sauvetage <br>contenant les coordonn\u00E9es d\u2019un bateau en d\u00E9tresse, il doit se <br>rendre sur zone et localiser pr\u00E9cis\u00E9ment le naufrag\u00E9 et<br> larguer un \u00E9quipement de survie avant l\u2019arriv\u00E9e des secours. </div></html>");
		lblNewLabel_1.setFont(UIManager.getFont("PopupMenu.font"));
		lblNewLabel_1.setBorder(null);
		scrollPane.setViewportView(lblNewLabel_1);

	}
}
