
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;



/**
 * Taulellitzador
 * Converteix una matriu de digits positius donada en un taulell a una representació gràfica.
 * Creat per Marc Albareda
 *
 */


public class Taulell extends JPanel {
	/**
	 * Constructor i variables globals.
	 */
    SquareRx2[][] squares;
    private int PAD = 20;  //el Padding es el marge que tindrà el taulell.
    private int FILES = -1;
    private int COLS = -1;
    private int[][] a;
    private boolean init = false;
    private boolean change = false;
	private boolean actcolors =true ;   //els act activen cada funcio. si estan desactivats no s'aplica aquella funcio (colors, lletres, imatges, etc)
	private boolean actborde = true;
    private int borde = 0x8cc8a0;
    private int fons = 0x0000ff;
    private int[] colors = {0x0000FF,0x00FF00,0xFFFF00,0xFF0000,0xFF00FF,0x00FFFF,0x000000,0xFFFFFF,0xFF8000,0x7F00FF};//paleta de colors per a cada nombre (la primera posicio correspon al numero 0 a la amtriu, la segona al 1, etc)
    private boolean actlletres = true;
    private String[] lletres = {"0","1","2","3","4","5","6","7","8","*"};  //què s'ha d'escriure en cada casella en base al nombre
    private int[] colorlletres = {0x0000FF,0x00FF00,0xFFFF00,0xFF0000,0xFF00FF,0x00FFFF,0x000000,0xFFFFFF,0xFF8000,0x7F00FF};  //en quin color s'ha d'escriure en cada casella en base al nombre
    private boolean actimatges = true;
    private String[] imatges = {"Link1.gif","Iron_Axe.png","Iron_Lance.png","Iron_Sword.png","Lightning.png","Vulnerary.png","Vulnerary.png","Vulnerary.png","Vulnerary.png","Vulnerary.png"};  //paths de les imatges que s'hagin d'escriure en cada casella a partir del nombre
    private Font font = new Font( "SansSerif", Font.PLAIN, 22 );
 
    public Taulell() {
        addMouseListener(ml);

    }
 
    /**
     * Mètodes de dibuix. Toqueu-ho sota el vostre risc.
     */
    
    //Mètode principal de dibuix. Cada vegada que es crida el "repaint()" es va aquí.
    protected void paintComponent(Graphics g) {

    	
        if (init) { //assegurar-me que fins que no s'envien dades no es mostra res

			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  //SERA UN JOC DE MERDA, PERO SERA MERDA HD.
			if (change||squares==null) {
				initSquares();
			}
			// Dibuixa els cuadrats.
			g2.setPaint(Color.blue);
			g2.setFont(font);
			for (int i = 0; i < FILES; i++) {
				for (int j = 0; j < COLS; j++) {
					squares[i][j].draw(g2, a[i][j], actcolors, colors, actborde, borde, fons, actlletres, lletres, colorlletres, actimatges, imatges);
				}
			}


		}

    }
    
  //Aquest mètode inicialitza la matriu per primera vegada creant els quadrats
    private void initSquares() {
        squares = new SquareRx2[FILES][COLS];
        int w = getWidth();
        int h = getHeight();
        double xInc = (double)(w - 2*PAD)/COLS;  //aixo es geometria eh. Basicament diu que cada cuadrat tindrà de mida horitzontal la amplada total - els dos marges, dividit per el nombre de quadrats que tens.
        double yInc = (double)(h - 2*PAD)/FILES;
        for(int i = 0; i < FILES; i++) {
            double y = PAD + i*yInc;
            for(int j = 0; j < COLS; j++) {
                double x = PAD + j*xInc;
                Rectangle2D.Double r =
                    new Rectangle2D.Double(x, y, xInc, yInc);
                squares[i][j] = new SquareRx2(i, j, r, x, y, xInc, yInc);
            }
        }
    }
    
    //aquest mètode redibuixa el taulell si la finestra es canvia de mida.
    ComponentListener cl = new ComponentAdapter() {   
        public void componentResized(ComponentEvent e) {
            squares = null;
            repaint();
        }
    };
    
    
    /**
     * Mètode de dibuix públic. Aquest mètode rep la matriu i crida als altres mètodes.
     */    
    public void dibuixa(int[][] a) {
    	this.a= a;
    	if(FILES!=a.length||COLS!=a[0].length){  ///qui collons canviaria el tamany del taulell igualment en mitad d'una partida?
    		FILES = a.length;
        	COLS = a[0].length;
        	change=true;
    	}else change=false;
    	init = true;
    	
    	repaint();
    };
    
    
    
    /**
     * Integració del ratolí
     */
    
 
    private MouseListener ml = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            if(!isInGrid(p)) return;
            double xInc = (double)(getWidth() - 2*PAD)/COLS;
            double yInc = (double)(getHeight() - 2*PAD)/FILES;
            int f = (int)((p.y-PAD)/yInc);
            int c = (int)((p.x-PAD)/xInc);
            System.out.println(squares[f][c]);
            boolean isSelected = squares[f][c].isSelected();
            squares[f][c].setSelected(!isSelected);
            repaint();
        }
    };
 
    private boolean isInGrid(Point p) {
        Rectangle r = getBounds();
        r.grow(-PAD, -PAD);
        return r.contains(p);
    }
    
    
    
    
    
    /**
     * Getters i Setters. Generats automaticament. Gracies Eclipse divertit.
     */
    
    
    
    public boolean isActcolors() {
		return actcolors;
	}

	public void setActcolors(boolean actcolors) {
		this.actcolors = actcolors;
	}

	public int getBorde() {
		return borde;
	}

	public void setBorde(int borde) {
		this.borde = borde;
	}

	public int getFons() {
		return fons;
	}

	public void setFons(int fons) {
		this.fons = fons;
	}

	public int[] getColors() {
		return colors;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
	}

	public boolean isActlletres() {
		return actlletres;
	}

	public void setActlletres(boolean actlletres) {
		this.actlletres = actlletres;
	}

	public String[] getLletres() {
		return lletres;
	}

	public void setLletres(String[] lletres) {
		this.lletres = lletres;
	}

	public boolean isActimatges() {
		return actimatges;
	}

	public void setActimatges(boolean actimatges) {
		this.actimatges = actimatges;
	}

	public String[] getImatges() {
		return imatges;
	}

	public void setImatges(String[] imatges) {
		this.imatges = imatges;
	}

	public int[] getColorlletres() {
		return colorlletres;
	}

	public void setColorlletres(int[] colorlletres) {
		this.colorlletres = colorlletres;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public int getPAD() {
		return PAD;
	}

	public void setPAD(int pAD) {
		PAD = pAD;
	}

	public boolean isActborde() {
		return actborde;
	}

	public void setActborde(boolean actborde) {
		this.actborde = actborde;
	}


    

 

}

/**
 * Classe auxiliar de cada cuadrat. Cada cuadrat de la matriu es un Objecte d'aquesta classe.
 * No recomano tocar res d'aquesta classe.
 */

class SquareRx2 {
    private final int fil;
    private final int col;
    private int value;
    private final double x;
    private final double y;
    private final double xInc;
    private final double yInc;
    private Color border;
    private boolean selected = false;
    
    Rectangle2D.Double rect;
    
 
    public SquareRx2(int f, int c, Rectangle2D.Double rect, double e, double d, double a, double b) {
        fil = f;
        col = c;
        x = e;
        y = d;
        xInc = a;
        yInc = b;
        this.rect = rect;
        
    }
 
    /**
     * Aquest metode dibuixa cada cuadrat individualment.
     */
    public void draw(Graphics2D g2, int a, boolean actcolors, int[] colors, boolean actborde, int borde, int fons, boolean actlletres, String[] lletres, int[] colorlletres, boolean actimatges, String[] imatges) {
    	value = a;
    	if (actcolors) {
			Color inside = new Color(colors[value]);
			g2.setPaint(inside);
			
		}else{
			g2.setPaint(new Color(fons));
		}
    	
    	border = new Color(borde);
    	
    	g2.fill(rect);
		g2.setPaint(border);
		if (actborde){
			g2.draw(rect);
		}

        if (actimatges) {
			if (!(imatges[value].equals(""))) {   //Entrendrem que un string buit significa que no vols imatge en aquella posició
				BufferedImage img = null;
				try {
					img = ImageIO.read(new File(imatges[value]));
				} catch (IOException e) {
				}
				g2.drawImage(img, (int) x, (int) y + 3, (int) (x + xInc), (int) (y + yInc), 0, 0, img.getWidth(),
						img.getHeight(), null);
				///El y+3 es per posar uns pixels de marge perque no comenci sobre la mateixa linea de borde.
			}
		}
        if(actlletres){
			Color inside = new Color(colorlletres[value]);
			g2.setPaint(inside);
        	g2.drawString(lletres[value], (int)(x+xInc/2), (int)(y+yInc-5));
        	///El -5 es per donar un pixel de marge. Lo normal seria pensar que el punt que li dones es el punt superior esquerra de la lletra, pero no, es el punt INFERIOR esquerra.
        	///Si la font es gran acaba menjant-se el borde superior. Per aixo el -5, perque comenci abaix amb una mica de marge. Cutre cutre.
        }
    }
    
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
 
    public boolean isSelected() { return selected; }
    
    
 
    public String toString() {
        return "SQUARE[row:" + fil + ", col:" + col +
                    ", value:" +  value + "]";
    }
}