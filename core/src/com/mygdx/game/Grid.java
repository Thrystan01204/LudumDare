package com.mygdx.game;

import java.util.*;
import java.lang.*;

public class Grid {

        private int[][] map;

        private int nbLigne;

        private int nbColonne;

	private int etage;

        public Grid(int nbLigne, int nbColonne, int etage) {
                this.map = new int[nbLigne][nbColonne];
                this.nbLigne = nbLigne;
                this.nbColonne = nbColonne;
		this.etage = etage;
		//On place les murs 		Murs == 1
                for (int i = 0; i < nbLigne; i++) {
                        for (int j = 0; j < nbColonne; j++) {
                                if (j == 0 || i == 0 || i == nbLigne - 1 || j == nbColonne - 1 || i % 10 == 0 || j % 10 == 0) {
                                        map[i][j] = 1;
                                }
                        }
                }    
		//Positionner les escaliers	Escalier == 5
		Random r = new Random();
		int ax = 0, ay =0, bx =0, by =0;
		while (ax == bx && ay == by) {
			int a = r.nextInt(nbLigne/10);
			int c = r.nextInt(nbLigne/10);
			int b = r.nextInt(nbColonne/10);
			int d = r.nextInt(nbColonne/10);
			ax = 10*a + 5;
			bx = 10*c + 5;
			by = 10*b + 5;
			ay = 10*d + 5;
		}
		map[ax][ay] = 5;
		map[bx][by] = 5;

		//Ennemis 			Ennemie == 10
		int nbEnnemis = (int) Math.abs(etage)*10;
		while (nbEnnemis > 0) {
			int x = r.nextInt(nbLigne);
			int y = r.nextInt(nbColonne);
			if (map[x][y] == 0) {
				map[x][y] = 10;
				nbEnnemis--;
			}
		}

		//Pomme				Pomme == 20
		int cptrPomme = 0; //On place 10 pommes
		while (cptrPomme < 10) {
			int x = r.nextInt(nbLigne);
			int y = r.nextInt(nbColonne);
			if (map[x][y] == 0) {
				map[x][y] = 20;
				cptrPomme++;
			}
		}

		//Decors dangereux		DecorsDangereux == 11
		int nbDecorsD = (int) Math.abs(etage)*5;
		while (nbDecorsD > 0) {
			int x = r.nextInt(nbLigne);
			int y = r.nextInt(nbColonne);
			if (map[x][y] == 0) {
				map[x][y] = 10;
				nbDecorsD--;
			}
		}


		//Assurer un chemin entre les deux escaliers
		chemin(ax,ay,bx,by);
        }

	private boolean contient(List<int[]> liste, int[] elem) {
		for (int i = 0; i < liste.size(); i++) {
			if (liste.get(i)[0] == elem[0] && liste.get(i)[1] == elem[1]) {
				return true;
			}
		}
		return false;
	}

	private boolean egaux(int[] a, int[] b) {
		return a[0] == b[0] && b[1] == a[1];
	}

	private boolean dejaVu(List<int[]> chemin, List<int[]> cheminPossible) {
		for (int i = 0; i < cheminPossible.size(); i++) {
			if (!contient(chemin, cheminPossible.get(i))) {
				return false;
			}
		}
		return true;
	}

	private int nbDejaVu(List<int[]> chemin, List<int[]> cheminPossible) {
		int cpr = 0;
		for (int i = 0; i < cheminPossible.size(); i++) {
			if (contient(chemin, cheminPossible.get(i))) {
				cpr++;
			}
		}
		return cpr;
	}


	private void chemin(int xa, int ya, int xb, int yb) {
		int cptr = 0; //nb d'essaie
		int sizeX = nbLigne/10;
		int sizeY = nbColonne/10;
		List<int[]> chemin = new ArrayList<int[]>();
		int[] escalierA = {xa / 10, ya / 10};
		int[] escalierB = {xb / 10, yb / 10};
		chemin.add(escalierA);
		List<int[]> cheminPossible = new ArrayList<int[]>();
		do {
			int x = chemin.get(chemin.size() -1)[0];
			int y = chemin.get(chemin.size() -1)[1];
			if (x == 0 ) {
				int[] c1 = {x + 1, y};
				cheminPossible.add(c1);
				if (y > 0) {
					int[] c3 = {x , y - 1};
					cheminPossible.add(c3);
				}  
				if (y < sizeY - 1) {
					int[] c2 = {x , y + 1};
					cheminPossible.add(c2);
				}
			} else if (y == 0) {
				int[] c2 = {x , y + 1};
				cheminPossible.add(c2);
				if (x > 0) {
					int[] c1 = {x - 1, y};
					cheminPossible.add(c1);
				}
				if (x < sizeX - 1) {
					int[] c3 = {x + 1, y};
					cheminPossible.add(c3);
				}
			} else if (x == sizeX - 1) {
				int[] c1 = {x - 1, y};
				cheminPossible.add(c1);
				if (y > 0) {
					int[] c3 = {x , y - 1};
					cheminPossible.add(c3);
				}  
				if (y < sizeY - 1) {
					int[] c2 = {x , y + 1};
					cheminPossible.add(c2);
				}
			} else if (y == sizeY - 1) {
				int[] c2 = {x , y - 1};
				cheminPossible.add(c2);
				if (x > 0) {
					int[] c1 = {x - 1, y};
					cheminPossible.add(c1);
				}
				if (x < sizeX - 1) {
					int[] c3 = {x + 1, y};
					cheminPossible.add(c3);
				}
			} else {
				int[] c1 = {x + 1, y};
				int[] c2 = {x - 1, y};
				int[] c3 = {x, y - 1};
				int[] c4 = {x, y + 1};
				cheminPossible.add(c1);
				cheminPossible.add(c2);
				cheminPossible.add(c3);
				cheminPossible.add(c4);
			}
			int possible = cheminPossible.size();
			Random random = new Random();
			int nb = random.nextInt(possible);
			if (!dejaVu(chemin, cheminPossible)) {
				while (contient(chemin,cheminPossible.get(nb))) {
					nb = random.nextInt(possible);
				}
				chemin.add(cheminPossible.get(nb));
			} else {
				chemin.remove(chemin.size() - 1);
				cptr++;
			}
			if (cptr == 3) {
				chemin.clear();
				chemin.add(escalierA);
				cptr = 0;
			}
			cheminPossible.clear();
		} while (!egaux(chemin.get(chemin.size() - 1), escalierB));
		for (int i = 0; i < chemin.size() - 1; i++) {
			int xi = chemin.get(i)[0];
			int yi = chemin.get(i)[1];
			int xii = chemin.get(i+1)[0];
			int yii = chemin.get(i+1)[1];
			this.map[((xi + xii) * 10 + 10)/2][((yi + yii) * 10 + 10)/2] = 0;
		}
	}

        public void afficher() {
                for (int i = 0; i < nbLigne; i++) {
                        for (int j = 0; j < nbColonne; j++) {
                                System.out.print("[" + map[i][j] + "]");
                        }
			System.out.println();
                }
        }

	public boolean isEnnemi(int x, int y) {
		return map[x][y] == 10;
	}

	public boolean isPomme(int x, int y) {
		return map[x][y] == 20;
	}

	public boolean isDangereux(int x, int y) {
		return map[x][y] == 11;
	}

	public boolean isEscalier(int x, int y) {
		return map[x][y] == 5;
	}
	
	public boolean isMur(int x, int y) {
		return map[x][y] == 1;
	}
}

