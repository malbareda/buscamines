import java.util.ArrayList;
import java.util.Scanner;

public class MineSweeper {
	static ArrayList<String> ranks = new ArrayList<>();
	static String nom;
	static Scanner sc = new Scanner(System.in);
	static int files;
	static int columnes;
	static int nmines;
	static int[][] mines = new int[files][columnes];
	static int[][] camp = new int[files][columnes];
    static Taulell t = new Taulell();
    static Finestra f = new Finestra(t);

	public static void main(String[] args) {
		
		//////Inicialització GUI

		
		
		/////
		boolean fi=false;
		while (fi == false) {
			int op = mostramenu();
			switch (op) {
			case 2:
				opcions();
				break;
			case 3:
				jugar();
				break;
			case 0:
				fi=true;
				break;
			}
		}
	}

	private static int mostramenu() {
		// TODO Auto-generated method stub
		System.out.println("1 instruccions 2 opcions 3 jugar 0 bye");
		int n = sc.nextInt();
		return n;
	}

	private static void opcions() {
		// TODO Auto-generated method stub
		nom = sc.nextLine();
		files = sc.nextInt();
		columnes = sc.nextInt();
		nmines = sc.nextInt();

	}

	private static void rankings() {
		// TODO Auto-generated method stub

	}

	private static void jugar() {
		// TODO Auto-generated method stub

		inicialitzarMines();
		inicialitzarCamp();
		inicialitzarGUI();
		System.out.println("init done");
		int viu = 0;
		//visualitzarMatriu(mines);
		while (viu == 0) {
			System.out.println("coords?");
			int x = sc.nextInt();
			int y = sc.nextInt();
			int d = descobrir(x, y);
			viu = partidaAcabada(d);
			visualitzarMatriu(camp);
		}
		fiPartida(viu);
	}

	private static void inicialitzarGUI() {
		// TODO Auto-generated method stub
		t.setActcolors(false);
        t.setFons(0xb1adad);
        t.setActimatges(false);
        t.setActlletres(true);
        String[] lletres = {"","1","2","3","4","5","6","7","8","*","MINA"};  //què s'ha d'escriure en cada casella en base al nombre
        t.setLletres(lletres);
        int[] colorlletres = {0x0000FF,0x00FF00,0xFFFF00,0xFF0000,0xFF00FF,0x00FFFF,0x521b98,0xFFFFFF,0xFF8000,0x7F00FF,0xFFFFFF};
        t.setColorlletres(colorlletres);
        String[] etiquetes2={"Mines: "+nmines};
        f.setEtiquetes(etiquetes2);
        f.setActetiquetes(true);
        f.setTitle("Cercamines");
	}

	private static int partidaAcabada(int d) {

		int lliures = casellesLliures();
		if (lliures == nmines) {
			return 1;
		} else if (d == 10) {
			return 2;
		} else {
			return 0;
		}
	}

	private static void visualitzarMatriu(int matriu[][]) {
		// TODO Auto-generated method stub
		for (int f = 0; f < files; f++) {
			for (int c = 0; c < columnes; c++) {
				System.out.print(matriu[f][c] + " ");
			}
			System.out.println();
		}
		t.dibuixa(matriu);
	}

	private static void fiPartida(int viu) {
		// TODO Auto-generated method stub
		if (viu == 1) {
			System.out.println("win");
		} else {
			System.out.println("fail");
			visualitzarMatriu(camp);
		}

	}

	private static int descobrir(int x, int y) {

		int valor = click(x, y, mines);
		if(valor==0 && camp[x][y]==9) {
			camp[x][y]=0;
			for (int i = (x - 1); i <= (x + 1); i++) {
				for (int j = (y - 1); j <= (y + 1); j++) {
					descobrir(i,j);
				}
			}
		}
		if(valor!=-1) {
			camp[x][y] = valor;
			
		}
		
		return valor;

	}

	private static int casellesLliures() {
		// TODO Auto-generated method stub
		int contador = 0;
		for (int f = 0; f < files; f++) {
			for (int c = 0; c < columnes; c++) {
				if (camp[f][c] == 9)
					
				contador++;
			}
		}
		return contador;
	}

	private static int click(int x, int y, int[][] mines) {
		int contadormines = 0;
		
		if (x<0 || y<0 || x>=files || y>=columnes) {
			return -1;
		}
		if (mines[x][y] == 1) {
			// BOOM
			contadormines = 10;
		} else {
			for (int i = (x - 1); i <= (x + 1); i++) {
				for (int j = (y - 1); j <= (y + 1); j++) {
					if (i >= 0 && j >= 0 && i < files && j < columnes) {
						if (mines[i][j] == 1) {
							contadormines++;
						}
					}
				}
			}
		}
		return contadormines;
	}

	private static void inicialitzarCamp() {
		// TODO Auto-generated method stub
		camp = new int[files][columnes];
		for (int f = 0; f < files; f++) {
			for (int c = 0; c < columnes; c++) {
				camp[f][c] = 9;
			}
		}

	}

	private static void inicialitzarMines() {
		// TODO Auto-generated method stub
		mines = new int[files][columnes];
		int n = nmines;
		while (n > 0) {
			int f = (int) (Math.random() * files);
			int c = (int) (Math.random() * columnes);
			if (mines[f][c] != 1) {
				mines[f][c] = 1;
				n--;
				System.out.println("mina colocada en"+f+" "+c);
			}
		}

	}
}
