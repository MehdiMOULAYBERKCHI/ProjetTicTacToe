/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;


/**
 *
 * @author franck.tempet
 */
public class Jeton extends Piece {
 
    public Jeton( Joueur _joueur ) {
           super( _joueur );
    }

    public String toString() {

           if ((getJoueur().getIdJoueur()) == 1) {
                   return "X"; 
           }
           else {
               return "O";
           }
    }
   }