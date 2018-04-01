import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Finestra Crea una finestra gràfica. Dintre tindrà el taulell i alguns textos
 * informatius. Basicament aquesta classe separa el propi taulell (la matriu) de
 * la resta de coses de l'interficie gràfica. Creat per Marc Albareda
 *
 */
public class Finestra extends JFrame {

	/**
	 * De moment te etiquetes. En algun moment li puc posar coses xules com una
	 * pantalla d'intro, high scores, credits, musica
	 */

	/**
	 * No se si fer que Taulell sigui privat i l'usuari només interactui amb
	 * Finestra o tenir-ho separat. Hm...
	 */

	private Taulell taulell;
	private boolean actetiquetes = true;
	private String[] etiquetes = { "Creat per Marc Albareda", "es sorprenentment atractiu", "i esta solter!" };
	/// pots posar tantes etiquetes com vulguis eh, no estàs limitat a 3.
	private JPanel labelpanel = new JPanel(new GridLayout(0, 1, 5, 5));

	public Finestra(Taulell t) {
		taulell = t;
		inici();
	}

	private void inici() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(taulell);

		if (actetiquetes) {
			for (String s : etiquetes) {
				JLabel etiq = new JLabel(s);
				labelpanel.add(etiq);
			}
		}
		add(labelpanel, BorderLayout.LINE_END);
		labelpanel.setBorder(new EmptyBorder(10,10,10,10));

		setSize(700, 400);
		setLocation(100, 100);
		setVisible(true);
		taulell.addComponentListener(taulell.cl);

	}

	private void renovar() {
		labelpanel.removeAll();
		if (actetiquetes) {
			for (String s : etiquetes) {
				JLabel etiq = new JLabel(s);
				labelpanel.add(etiq);
			}

		}
		labelpanel.repaint();
		labelpanel.revalidate();
		// força una reinicialitzacio de tots els quadrats de taulell com a
		// efecte secundari

	}

	public boolean isActetiquetes() {
		return actetiquetes;
	}

	public void setActetiquetes(boolean actetiquetes) {
		this.actetiquetes = actetiquetes;
		if (!actetiquetes)
			renovar();
	}

	public String[] getEtiquetes() {
		return etiquetes;
	}

	public void setEtiquetes(String[] etiquetes) {
		this.etiquetes = etiquetes;
		renovar();
	}

}
