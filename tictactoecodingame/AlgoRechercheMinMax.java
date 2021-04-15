/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author franck.tempet
 */
public class AlgoRechercheMinMax extends AlgoRecherche{
    boolean toutPremierCoup = true;
    JoueurOrdi joueurFantome = new JoueurOrdi("joueurFantome");
    Joueur joueurMechant;
    Joueur joueurMoi;

    public AlgoRechercheMinMax() {
    }

    @Override
    public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {
        toutPremierCoup = true;
        joueurMoi = _joueur;
        ArrayList<Coup> coups = _plateau.getListeCoups(_joueur);
        if(toutPremierCoup){
            espion(_plateau);
        }

        /*
        System.out.print("CoupsPossibles :");
        for(int i=0; i<coups.size(); i++){
            System.out.print(((CoupTicTacToe) coups.get(i)).getColonne());
            System.out.print(((CoupTicTacToe) coups.get(i)).getLigne());
            System.out.print("-");
        }
        System.out.println();
        */

        int[] resultats = minMaxFilles(_plateau, _joueur);
        int kbon=0;
        int max=resultats[0];
        //System.out.println();
        //System.out.print("resultats :");
        for(int k=0; k<resultats.length; k++){
            if(max < resultats[k]){
                max = resultats[k];
                kbon = k;
            }
            //System.out.print(resultats[k]);
            //System.out.print(",");
        }
        //System.out.println();
        return(coups.get(kbon));
    }

    public void espion(Plateau _plateau){
        int colonne = _plateau.getNbColonnes();
        int ligne = _plateau.getNbLignes();
        for(int j=0; j<colonne; j++){
            for(int i=0; i<ligne; i++){
                if(_plateau.getPiece(new Case(j,i))!=null){
                    toutPremierCoup = false;
                    //System.out.print("Il y a quelqu'un ici ");
                }
                //System.out.println("Je suis passé ici");
            }
        }
        //System.out.print("Je suis l'espion");
        if(!toutPremierCoup){
            joueurMechant = ((CoupTicTacToe) _plateau.getDernierCoup()).getJeton().getJoueur();
            //System.out.println(" et j'ai trouvé le mechant");
        }
        else{
            joueurMechant = joueurFantome;
            //System.out.println(" et j'ai n'ai pas trouvé le mechant");
        }
    }

    public int[] minMaxFilles(Plateau _plateau, Joueur _joueur){
        ArrayList<Coup> coups = _plateau.getListeCoups(_joueur);
        int n = coups.size();
        int[] resultats = new int[n];
        for(int k=0; k<n; k++){
            _plateau.joueCoup(coups.get(k));
            if(_plateau.partieTerminee()){
                if(!_plateau.partieNulle()){
                    if(_joueur == joueurMoi){
                        resultats[k] = +10;
                    }
                    else{
                        resultats[k] = -10;
                    }
                }
                else{
                    resultats[k] = 0;
                }
            }
            else{
                int[] resultatsFilles;
                int minOuMax;
                if(_joueur == joueurMoi){
                    resultatsFilles = minMaxFilles(_plateau, joueurMechant);
                    minOuMax = minOuMax(resultatsFilles, joueurMechant);
                }
                else{
                    resultatsFilles = minMaxFilles(_plateau, joueurMoi);
                    minOuMax = minOuMax(resultatsFilles, joueurMoi);
                }
                resultats[k] = minOuMax;
            }
            _plateau.annuleDernierCoup();
        }

        /*
        if(_joueur == joueurMoi){
            System.out.print("Je suis gentil -");
            System.out.print(" mon id :");
            System.out.print(_joueur.getIdJoueur());
        }
        else{
            System.out.print("Je suis méchant -");
            System.out.print(_joueur.getIdJoueur());
        }
        System.out.print(" mon minOuMax :");
        System.out.print(minOuMax(resultats, _joueur));
        System.out.print(" - sous resultats :");
        for(int i=0; i<n; i++){
            System.out.print(resultats[i]);
            System.out.print(",");
        }
        System.out.println();
        */

        return(resultats);
    }

    public int minOuMax(int[] resultatsFilles, Joueur _joueur){
        int minOuMax = resultatsFilles[0];
        int n = resultatsFilles.length;
        if(_joueur == joueurMoi){
            for(int i=0; i<n; i++){
                if(minOuMax < resultatsFilles[i]){
                    minOuMax = resultatsFilles[i];
                }
            }
        }
        else{
            for(int i=0; i<n; i++){
                if(minOuMax > resultatsFilles[i]){
                    minOuMax = resultatsFilles[i];
                }
            }
        }
        return(minOuMax);
    }
}
