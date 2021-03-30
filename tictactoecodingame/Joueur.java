package tictactoecodingame;


/**
 * @author  Franck
 *
 */

public abstract class Joueur {
	private String nom;
	private static int nbJoueur = 0;
	private int idJoueur;
	
	public Joueur( String _nom ) {
		idJoueur = nbJoueur;
                nbJoueur++;
                this.nom = _nom;
	}
	
	public void setNom ( String _nom ) {
		this.nom = _nom;
	}
	
	public String getNom() {
		return nom;
	}

	public int getIdJoueur() {
		return idJoueur;
	}
	abstract public Coup joue( Plateau p_plateau );
	
	public String toString() {
		return nom;
	}
}
