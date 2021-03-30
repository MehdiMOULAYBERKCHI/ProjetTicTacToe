/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.ArrayList;

/**
 *
 * @author franck.tempet
 */
public class GrilleTicTacToe9x9 extends Plateau {

    Jeton[][] grille9x9 = new Jeton[9][9];
    Jeton[][] grille3x3 = new Jeton[3][3];  // indique si un joueur à gagné un case de la grande grille

    Jeton[][][] grille9x9Sav = new Jeton[100][9][9];  // Pour sauvegarder la position. 100 positions Max
    Jeton[][][] grille3x3Sav = new Jeton[100][3][3];  // Pour sauvegarder la position

    CoupTicTacToe[] dernierCoup;
    int             nbCoupJoue;
    int             nbCoupJoueSav;
    CoupTicTacToe dernierCoupSav;
    Joueur vainqueur;
    boolean grilleGagnee;
    boolean[] grilleGagneeSav;  // vrai si dernier coup gagne une grille

    @Override
    public void init() {
        for (int c = 0; c < this.getNbColonnes(); c++) {
            for (int l = 0; l < this.getNbLignes(); l++) {
                grille9x9[c][l] = null;
            }
        }

        for (int c = 0; c < 3; c++) {
            for (int l = 0; l < 3; l++) {
                grille3x3[c][l] = null;
            }
        }
        dernierCoup = new CoupTicTacToe[100];
        nbCoupJoue = 0;
        nbCoupJoueSav = 0;
        grilleGagneeSav = new boolean[100];
        vainqueur = null;
        grilleGagnee = false;

    }

    @Override
    public Piece getPiece(Case _case) {
        return grille9x9[_case.getColonne()][_case.getLigne()];
    }

    @Override
    public void joueCoup(Coup _coup) {
        CoupTicTacToe coup = (CoupTicTacToe) _coup;

        grille9x9[coup.getColonne()][coup.getLigne()] = coup.getJeton();
        dernierCoup[nbCoupJoue] = coup;
        grilleGagneeSav[nbCoupJoue] = grilleGagnee;
        nbCoupJoue++;
        int x0 = (coup.getColonne() / 3) * 3;
        int y0 = (coup.getLigne() / 3) * 3;

      //  System.out.println("Test grande grille");
        if (caseGagnante(grille9x9, x0, y0, coup.getColonne(), coup.getLigne())) {
            grille3x3[coup.getColonne() / 3][coup.getLigne() / 3] = coup.getJeton();
            grilleGagnee = true;
        }
            
       
    }

    @Override
    public void annuleDernierCoup() {
        nbCoupJoue--;
        grille9x9[dernierCoup[nbCoupJoue].getColonne()][dernierCoup[nbCoupJoue].getLigne()] = null;
        grilleGagnee = grilleGagneeSav[nbCoupJoue];
        grille3x3[dernierCoup[nbCoupJoue].getColonne() / 3][dernierCoup[nbCoupJoue].getLigne() / 3] = null;  
        vainqueur = null;
    }

    @Override
    public int getNbColonnes() {
        return 9;
    }

    @Override
    public int getNbLignes() {
        return 9;
    }

    @Override
    public boolean partieTerminee() {

        if (vainqueur != null) {
            return true;
        }

        if (partieGagnee()) {
            return true;
        }

        return isGrillePleine();
    }

    /**
     * Regarde si le joueur vient de gagner sur une case en 3x3
     *
     * @param _grille soit la grille 9x9 soit la grille 3x3
     * @param _x0 x min de la grille à analyser
     * @param _y0 y min de la grille à analyser
     * @param _coupX où le joueur a joué
     * @param _coupY
     * @return
     */
    public boolean caseGagnante(Jeton[][] _grille, int _x0, int _y0, int _coupX, int _coupY) {
        int[][] dir = {{1, 0}, {1, 1}, {0, 1}, {1, -1}};
        int[][] dirOps = {{-1, 0}, {-1, -1}, {0, -1}, {-1, 1}};

        int xMax = _x0 + 3, yMax = _y0 + 3;

        int x, y;

        int nbJetonAligne;

        Joueur dernierJoueur = dernierCoup[nbCoupJoue-1].getJeton().getJoueur();

        /* Regarde si le dernier coup est gagnant */
        for (int d = 0; d < 4; d++) {
            nbJetonAligne = 0;
            x = _coupX;
            y = _coupY;

            while (x < xMax && x >= _x0 && y < yMax && y >= _y0 && _grille[x][y] != null && _grille[x][y].getJoueur() == dernierJoueur) {
                nbJetonAligne++;
                if (nbJetonAligne >= 3) {
                    return true;
                }
                x += dir[d][0];
                y += dir[d][1];
            }

            //regarde dans la direction opposée
            x = _coupX;
            y = _coupY;
            nbJetonAligne--;

            while (x < xMax && x >= _x0 && y < yMax && y >= _y0 && _grille[x][y] != null && _grille[x][y].getJoueur() == dernierJoueur) {
                nbJetonAligne++;
                if (nbJetonAligne >= 3) {
                    return true;
                }
                x += dirOps[d][0];
                y += dirOps[d][1];
            }
        }

        return false;

    }

    @Override
    public boolean partieGagnee() {
        if (nbCoupJoue == 0) {
            return false;
        }

        if ( grilleGagnee) { // si le dernier coup a gagne une grille on regarde si on gagne sur la grande grille
            if (caseGagnante(grille3x3, 0, 0, dernierCoup[nbCoupJoue-1].getColonne() / 3, dernierCoup[nbCoupJoue-1].getLigne() / 3)) {
                
                vainqueur = dernierCoup[nbCoupJoue-1].getJeton().getJoueur();
                return true;
            }
        }

        // Compte le nombre de cases remportées par chaque joueur
        if (isGrillePleine()) {
            int[] nbCase = new int[2];
            Joueur[] joueur = new Joueur[2];

            for (int c = 0; c < 3; c++) {
                for (int l = 0; l < 3; l++) {
                    if (grille3x3[c][l] != null) {
                        nbCase[grille3x3[c][l].getJoueur().getIdJoueur()]++;
                        joueur[grille3x3[c][l].getJoueur().getIdJoueur()] = grille3x3[c][l].getJoueur();
                    }
                }
            }

            if (nbCase[0] > nbCase[1]) {
                vainqueur = joueur[0];
                return true;
            }
            if (nbCase[1] > nbCase[0]) {
                vainqueur = joueur[1];
                return true;
            }
            vainqueur = null;
            return true;
        }

        return false;

    }

    @Override
    public boolean partieNulle() {

        if (vainqueur != null) {
            return false;
        }

        if (partieGagnee()) {
            return false;
        }

        return isGrillePleine();
    }

    @Override
    public ArrayList<Coup> getListeCoups(Joueur _joueur) {

        ArrayList<Coup> listeCoups = new ArrayList<Coup>();

        if (nbCoupJoue != 0 ) {
            int x0 = (dernierCoup[nbCoupJoue-1].getColonne() % 3) * 3;
            int y0 = (dernierCoup[nbCoupJoue-1].getLigne() % 3) * 3;
            
            for (int c = x0; c < x0 + 3; c++) {
                for (int l = y0; l < y0 + 3; l++) {                   
                    if ( grille3x3[(c/3)][(l/3)] != null ) continue;   // on ne peut pas jouer dans une grille gagnée
                    
                    if (grille9x9[c][l] == null) {
                        listeCoups.add(new CoupTicTacToe(c, l, new Jeton(_joueur)));
                    }
                }
            }
        }

        if (listeCoups.isEmpty()) {
            for (int c = 0; c < this.getNbColonnes(); c++) {
                for (int l = 0; l < this.getNbLignes(); l++) {
                    if ( grille3x3[(c/3)][(l/3)] != null ) continue;   // on ne peut pas jouer dans une grille gagnée
                    if (grille9x9[c][l] == null) {
                        listeCoups.add(new CoupTicTacToe(c, l, new Jeton(_joueur)));
                    }
                }
            }
        }
        return listeCoups;
    }

    @Override
    public boolean isValide(Coup _coup) {
        CoupTicTacToe coup = (CoupTicTacToe) _coup;

        if ( grille9x9[coup.getColonne()][coup.getLigne()] != null ) return false;
        
        if ( nbCoupJoue == 0 ) return true;
      
        int x0 = (dernierCoup[nbCoupJoue-1].getColonne() % 3 ) * 3;
        int y0 = (dernierCoup[nbCoupJoue-1].getLigne() % 3 ) * 3;
                      
        boolean grillePleine = true;
        
        for (int c = x0; c < x0+3 ; c++) {
                for (int l = y0; l < y0+3 ; l++) {
                    if ( grille3x3[c/3][l/3] != null ) continue;  // les grilles gagneés sont pleines
                    if ( grille9x9[c][l] == null ) {
                        grillePleine = false;
                        break;
                    }
                }
        }
        
        if ( grillePleine ) {           
              return true;
        }
                
      
        return ( coup.getColonne() >= x0  && coup.getColonne() < x0+3 &&  coup.getLigne()>= y0  && coup.getLigne() < y0+3 );
    }

    @Override
    public Coup stringToCoup(String _coup, Joueur _joueur) {
        int colonne = Integer.valueOf(_coup.charAt(0) + "");
        int ligne = Integer.valueOf(_coup.charAt(1) + "");

        return new CoupTicTacToe(colonne, ligne, new Jeton(_joueur));
    }

    @Override
    public void sauvegardePosition(int _index ) {
        for (int c = 0; c < this.getNbColonnes(); c++) {
            for (int l = 0; l < this.getNbLignes(); l++) {
                grille9x9Sav[_index][c][l] = grille9x9[c][l];
            }
        }

        for (int c = 0; c < 3; c++) {
            for (int l = 0; l < 3; l++) {
                grille3x3Sav[_index][c][l] = grille3x3[c][l];
            }
        }
        
        grilleGagneeSav[nbCoupJoue] = grilleGagnee;
        nbCoupJoueSav = nbCoupJoue;
       

    }

    @Override
    public void restaurePosition( int _index ) {
        
        for (int c = 0; c < this.getNbColonnes(); c++) {
            for (int l = 0; l < this.getNbLignes(); l++) {
                grille9x9[c][l] = grille9x9Sav[_index][c][l];
            }
        }

        for (int c = 0; c < 3; c++) {
            for (int l = 0; l < 3; l++) {
                grille3x3[c][l] = grille3x3Sav[_index][c][l];
            }
        }
        vainqueur = null;

        grilleGagnee = grilleGagneeSav[nbCoupJoueSav];
        nbCoupJoue = nbCoupJoueSav;
    }

    private boolean isGrillePleine() {
        for (int c = 0; c < this.getNbColonnes(); c++) {
            for (int l = 0; l < this.getNbLignes(); l++) {
                if ( grille3x3[c/3][l/3] != null ) continue;  // les grilles gagnées sont pleines
                
                if (grille9x9[c][l] == null) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public Joueur vainqueur() {
        return vainqueur;
    }

    void trace() {
        for (int l = 2; l >= 0; l--) {
            for (int c = 0; c < 3; c++) {
                System.out.print(grille3x3[c][l] + " ");
            }
            System.out.println("");
        }
    }

    @Override
    public Coup getDernierCoup() {
       if ( nbCoupJoue < 1 ) return null;
       return dernierCoup[nbCoupJoue-1];
    }

}
