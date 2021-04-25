package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

import java.util.*;
import java.lang.*;

public class Grid {

        private int[][] map;

        private int nbLigne;

        private int nbColonne;

        private World world;



        public Grid(int nbLigne, int nbColonne, World world) {
                this.map = new int[nbLigne][nbColonne];
                this.nbLigne = nbLigne;
                this.nbColonne = nbColonne;
                this.world = world;
        Random r = new Random();

		//Placer les différents type de sol Sol == 0 1 2 
		for (int i = 0; i < nbLigne; i++) {
			for (int j = 0; j < nbColonne; j++) {
					double p = Math.random();
					if (p <= 0.5) {
						map[i][j] = 0;
					} else if (p > 0.5 && p < 0.85) {
						map[i][j] = 1;
					} else {
						map[i][j] = 2;
					}
				//map[i][j] = r.nextInt(3);
			}
		}

		//Positionner les escaliers	Escalier == 5
		int tailleChemin = 0;
		int nbSalle = (nbLigne/10)*(nbColonne/10);
		int ax = 0, ay =0, bx =0, by =0;
		while (tailleChemin < nbSalle - 1) {
			//On place les murs 		Murs == 3
			creationMur();
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
			//Assurer un chemin entre les deux escaliers
			tailleChemin = chemin(ax,ay,bx,by);
			
		}
		map[ax][ay] = 5;			//Escalier Montant == 5
		map[bx][by] = 6;			//Escalier Descendant == 6
		generateCollisions();
        }
	
	public void creationMur() {
                for (int i = 0; i < nbLigne; i++) {
                        for (int j = 0; j < nbColonne; j++) {
                                if (i == nbLigne - 1 || j == nbColonne - 1 || i % 10 == 0 || j % 10 == 0) {
                                        map[i][j] = 3;
                                }
                        }
                }    
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

	private int chemin(int xa, int ya, int xb, int yb) {
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
		Random r = new Random();
		for (int i = 0; i < chemin.size() - 1; i++) {
			int xi = chemin.get(i)[0];
			int yi = chemin.get(i)[1];
			int xii = chemin.get(i+1)[0];
			int yii = chemin.get(i+1)[1];
			this.map[((xi + xii) * 10 + 10)/2][((yi + yii) * 10 + 10)/2] = r.nextInt(3);
		}
		return chemin.size();
	}

	public void afficher() {
                for (int i = 0; i < nbLigne; i++) {
                        for (int j = 0; j < nbColonne; j++) {
                                System.out.print("[" + map[i][j] + "]");
                        }
			System.out.println();
                }
        }

        private void generateCollisions(){
        	for(int i=0; i < nbLigne; i++){
        		for (int j=0; j < nbColonne; j++){
        			if(map[i][j] == 3){
        				// C'est un mur

						// Propriétés du corps
						BodyDef bodyDef = new BodyDef();
						bodyDef.type = BodyDef.BodyType.StaticBody;
						bodyDef.position.set(i*16+8, j*16+8);

						// Création du corps dans le monde physique
						Body body = world.createBody(bodyDef);

						// forme du corps
						PolygonShape shape = new PolygonShape();
						shape.setAsBox(8, 8);

						FixtureDef fixtureDef = new FixtureDef();
						fixtureDef.shape = shape;
						Fixture fixture = body.createFixture(fixtureDef);
						shape.dispose();
					}
				}
			}
		}



	public boolean isEscalier(int x, int y) {
		return map[x][y] == 5;
	}
	
	public boolean isMur(int x, int y) {
		return map[x][y] == 3;
	}

	public void setGrille(int x, int y, int nvl) {
		this.map[x][y] = nvl;
	}

	public int getGrille(int x, int y) {
		return map[x][y];
	}
}

