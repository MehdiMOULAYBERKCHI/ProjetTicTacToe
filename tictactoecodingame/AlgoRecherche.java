package tictactoecodingame;

/**
 * @author  Franck
 *
 */

public abstract class AlgoRecherche {


        /**
         * 
         * @param _plateau  Grille du jeu
         * @param _joueur   c'est à ce joueur de jouer
         * @param _ponder  vrai si l'ordi analyse la position pendant le temps de reflexion de l'adversaire
         * @return 
         */
	abstract public Coup meilleurCoup( Plateau _plateau , Joueur _joueur , boolean _ponder );
	

}
