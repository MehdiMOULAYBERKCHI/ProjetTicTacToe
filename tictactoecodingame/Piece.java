package tictactoecodingame;


/**
 * @author Franck
 */

public abstract class Piece {
  private Joueur joueur;

  
   
  public Piece( Joueur _joueur ) {
  	this.joueur = _joueur;
  }
  
  public void setJoueur(Joueur _joueur) {
	joueur = _joueur;
  }
  
  

  public Joueur getJoueur() {
  	return joueur;
  }
  
  abstract public String toString();
}
