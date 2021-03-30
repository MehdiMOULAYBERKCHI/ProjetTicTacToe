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
public class CoupTicTacToe extends Coup {
    private int colonne, ligne;
    private Jeton jeton;

    public CoupTicTacToe( int _colonne , int _ligne , Jeton _jeton ) {
        super();
        colonne = _colonne;
        ligne = _ligne;       
        jeton = _jeton;
    }
    
    public int getColonne() {
        return colonne;
    }

    public int getLigne() {
        return ligne;
    }
    
    public Jeton getJeton() {
        return jeton;
    }

    public String toString() {
        return "(" + colonne + "," + ligne + ")" ;
    }

            
    @Override
    public boolean equals(Object obj) {
        if ( obj == null )  return false;
        
        if (this.getClass() != obj.getClass()) return false;
        
        CoupTicTacToe coup = (CoupTicTacToe)obj;
        
        return colonne == coup.getColonne() && ligne==coup.ligne && jeton.toString().equals(coup.getJeton().toString());
        
    }

    @Override
    public int hashCode() {
        return jeton.toString().hashCode() + colonne * 10 + ligne;
    }
    

}
