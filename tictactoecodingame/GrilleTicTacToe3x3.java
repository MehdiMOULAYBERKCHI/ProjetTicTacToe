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
public class GrilleTicTacToe3x3 extends Plateau {

    Jeton[][] grille = new Jeton[3][3];
    Jeton[][][] grilleSav = new Jeton[100][3][3];  // Pour sauvegardr la position. 100 au maximum

    CoupTicTacToe[] dernierCoup;
    int nbCoupJoue ,nbCouJoueSav;
    Joueur vainqueur;

    @Override
    public void init() {
        for (int c = 0; c < this.getNbColonnes(); c++) 
            for (int l = 0; l < this.getNbLignes(); l++) 
                grille[c][l] = null;                    
        
        dernierCoup = new CoupTicTacToe[10];
        vainqueur = null;
        nbCoupJoue = 0;
    }

    @Override
    public Piece getPiece(Case _case) {
        return grille[_case.getColonne()][_case.getLigne()];
    }

    @Override
    public void joueCoup(Coup _coup) {
        CoupTicTacToe coup = (CoupTicTacToe) _coup;

        grille[coup.getColonne()][coup.getLigne()] = coup.getJeton();

        dernierCoup[nbCoupJoue] = coup;
        nbCoupJoue++;
    }

    @Override
    public void annuleDernierCoup() {
        nbCoupJoue--;                
        grille[dernierCoup[nbCoupJoue].getColonne()][dernierCoup[nbCoupJoue].getLigne()] = null;  
        vainqueur = null;
    }

    @Override
    public int getNbColonnes() {
        return 3;
    }

    @Override
    public int getNbLignes() {
        return 3;
    }

    @Override
    public boolean partieTerminee() {
        if (partieGagnee()) {
            return true;
        }

        return isGrillePleine();
    }

    @Override
    public boolean partieGagnee() {
        int[][] dir = {{1, 0}, {1, 1}, {0, 1}, {1, -1}};
        int[][] dirOps = {{-1, 0}, {-1, -1}, {0, -1}, {-1, 1}};

        int x, y;

        int nbJetonAligne;

        if (nbCoupJoue == 0) {
            return false;
        }

        Joueur dernierJoueur = dernierCoup[nbCoupJoue-1].getJeton()
                .getJoueur();

        /* Regarde si le dernier coup est gagnant */
        for (int d = 0; d < 4; d++) {
            nbJetonAligne = 0;
            x = dernierCoup[nbCoupJoue-1].getColonne();
            y = dernierCoup[nbCoupJoue-1].getLigne();

            while (x < this.getNbColonnes() && x >= 0 && y < this.getNbLignes() && y >= 0 && grille[x][y] != null && grille[x][y].getJoueur() == dernierJoueur) {
                nbJetonAligne++;
                if (nbJetonAligne >= 3) {
                    vainqueur = dernierJoueur;
                    return true;
                }
                x += dir[d][0];
                y += dir[d][1];
            }

            //regarde dans la direction oppose    
            x = dernierCoup[nbCoupJoue-1].getColonne();
            y = dernierCoup[nbCoupJoue-1].getLigne();
            nbJetonAligne--;

            while (x < this.getNbColonnes() && x >= 0 && y < this.getNbLignes() && y >= 0 && grille[x][y] != null && grille[x][y].getJoueur() == dernierJoueur) {
                nbJetonAligne++;
                if (nbJetonAligne >= 3) {
                    vainqueur = dernierJoueur;
                    return true;
                }
                x += dirOps[d][0];
                y += dirOps[d][1];
            }
        }

        return false;

    }

    @Override
    public boolean partieNulle() {
        if (partieGagnee()) {
            return false;
        }

        return isGrillePleine();
    }

    @Override
    public ArrayList<Coup> getListeCoups(Joueur _joueur) {
        
        ArrayList<Coup> listeCoups = new ArrayList<Coup>();
        
        for (int c = 0; c < this.getNbColonnes(); c++) {
            for (int l = 0; l < this.getNbLignes(); l++) {
                if (grille[c][l] == null) 
                    listeCoups.add(new CoupTicTacToe(c, l, new Jeton(_joueur)));               
            }
        }

        return listeCoups;
    }

    @Override
    public boolean isValide(Coup _coup) {
        CoupTicTacToe coup = (CoupTicTacToe) _coup;

        return grille[coup.getColonne()][coup.getLigne()] == null;
    }

    @Override
    public Coup stringToCoup(String _coup, Joueur _joueur) {
        int colonne = Integer.valueOf(_coup.charAt(0)+"");
        int ligne = Integer.valueOf(_coup.charAt(1)+"");

        return  new CoupTicTacToe(colonne, ligne , new Jeton(_joueur) );
    }

    @Override
    public void sauvegardePosition(int _index) {
        for (int c = 0; c < this.getNbColonnes(); c++) 
            for (int l = 0; l < this.getNbLignes(); l++) 
                   grilleSav[_index][c][l] = grille[c][l]; 
        
        nbCouJoueSav = nbCoupJoue;

    }

    @Override
    public void restaurePosition(int _index) {
        for (int c = 0; c < this.getNbColonnes(); c++) 
            for (int l = 0; l < this.getNbLignes(); l++) 
                   grille[c][l] = grilleSav[_index][c][l]; 
        
        vainqueur = null;
        nbCoupJoue = nbCouJoueSav;

    }

    private boolean isGrillePleine() {
        for (int c = 0; c < this.getNbColonnes(); c++) {
            for (int l = 0; l < this.getNbLignes(); l++) {
                if (grille[c][l] == null) {
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


    @Override
    public Coup getDernierCoup() {
        return dernierCoup[nbCoupJoue-1];
    }

}
