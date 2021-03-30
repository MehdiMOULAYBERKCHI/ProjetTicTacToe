/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author franck.tempet
 */
public class AlgoRechercheAleatoire extends AlgoRecherche{
    Random rnd;

    public AlgoRechercheAleatoire() {
        rnd = new Random();       
    }
    
    
    
    @Override
    public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {
        
        ArrayList<Coup> coups = _plateau.getListeCoups(_joueur);
        
        return coups.get(rnd.nextInt( coups.size()));
    }

   
    
}
