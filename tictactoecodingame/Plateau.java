package tictactoecodingame;

import java.util.ArrayList;

/**
 * @author Franck
 *
 * Classe générique pour les jeux de plateau à 2 joueurs ( une grille de puissance 4 , un échiquier , .. )
 */
public abstract class Plateau  {
 
    abstract public void init();			// Initialise le plateau pour le démarrage d'une nouvelle partie.

    abstract public Piece getPiece(Case _case);  	// Retourne la pièce presente sur la case _case

    abstract public void joueCoup(Coup _coup);		// Joue le coup _coup sur le plateau

    abstract public void annuleDernierCoup();		// Annule le dernier coup joué

    abstract public int getNbColonnes();		// Retourne le nombre de colonnes du plateau

    abstract public int getNbLignes();			// Retourne le nombre de lignes du plateau

    abstract public boolean partieTerminee();		// Vrai si la partie est terminee ( soit un gagnant soit un match nulle )

    abstract public boolean partieGagnee();		// Vrai si le dernier joueur  a gagné la partie
    
    abstract public boolean partieNulle();              // Vrai si la partie est nulle
    
    abstract public Joueur vainqueur();                  // Retourne le joueur qui a gagné la partie

    abstract public ArrayList<Coup> getListeCoups(Joueur _joueur); 	// Retourne la liste des coups possibles.

    abstract public boolean isValide(Coup _coup);		// Retourne Vrai si le coup est valide.

    abstract public Coup stringToCoup(String _coup, Joueur _joueur);  // Convertion d'une chaine de caractères en un Coup
    
    abstract public void sauvegardePosition(int _index);              // Sauvegarde la position courante dans l'indice _index
    
    abstract public void restaurePosition(int _index);                 // restaure la  position sauvegarde en indice _index
    
    abstract public Coup getDernierCoup();                  // Retourne le dernierCoup joue
    

    public String toString() {						// Donne une représentation du plateau sous forme d'une chaine de caractères
        Piece piece;
        String chainePlateau = new String();

        for (int ligne = getNbLignes() - 1; ligne >= 0; ligne--) {
            chainePlateau += ligne;
            for (int colonne = 0; colonne < getNbColonnes(); colonne++) {
                piece = getPiece(new Case(colonne, ligne));
                if (piece == null) {
                    chainePlateau += "|   ";
                } else {
                    chainePlateau += "|" + piece + "  ";
                }
            }
            chainePlateau += "|\n  ";
            for (int colonne = 0; colonne < getNbColonnes(); colonne++) {
                chainePlateau += "____";
            }
            chainePlateau += "\n";
        }

        for (int colonne = 0; colonne < getNbColonnes(); colonne++) 
            chainePlateau += "  "+ colonne + " ";
        return chainePlateau;
    }          
}
