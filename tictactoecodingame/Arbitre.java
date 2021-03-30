package tictactoecodingame;


/**
 * @author  Franck
 *
 */
public class Arbitre {

    Joueur joueur1;         // Joueur qui commence la partie
    Joueur joueur2;         // Adversaire
    Joueur currentJoueur;   // C'est à son tour de jouer
    Plateau plateau;        // Le plateau du jeu

    public Arbitre(Plateau _plateau, Joueur _joueur1, Joueur _joueur2) {
        plateau = _plateau;
        joueur1 = _joueur1;
        joueur2 = _joueur2;
        currentJoueur = joueur1;     // Le joueur1 commence la partie.        
    }

    /**
     * Demarre une nouvelle Partie
     * Retourne le joueur qui gagne , null si c'est un match nul
     *
     * @param _trace   si vrai affiche le plateau et le joueur courant
     */
    public Joueur startNewGame( boolean _trace ) {
        Coup coup;

        plateau.init();		     // Prépare le plateau pour le jeu.
        
        
        while (!plateau.partieTerminee()) {
            do {
                if ( _trace ) {
                    System.out.println(plateau);
                    System.out.println(currentJoueur + " ( " +currentJoueur.getIdJoueur() + "  )  joue : " );
                }
                coup = currentJoueur.joue(plateau);

                if (!plateau.isValide(coup)) {
                    if ( _trace )
                        System.err.println("Coup non valide ! : " + currentJoueur);
                    else {  // en mode batch le joueur perd la partie
                        System.err.println("Coup non valide ! : " + currentJoueur);
                        if (currentJoueur == joueur1) return joueur2; else return joueur1;
                    }
                }

            } while (!plateau.isValide(coup));

            plateau.joueCoup(coup);
            
            
            if (currentJoueur == joueur1) {
                currentJoueur = joueur2;
            } else {
                currentJoueur = joueur1;
            }
        }
        
        Joueur vainqueur = plateau.vainqueur();
        if ( vainqueur != null )
            System.out.println( vainqueur + " gagne la partie ");
        else
            System.out.println( "Partie nulle ");
        
        return vainqueur;

    }
    
    public void startTournament( int _nbPartie , boolean _trace) {
        double[] nbVictoire = new double[2]; 
        Joueur vainqueur;
        
        currentJoueur = joueur1;
        int numJoueur = 0;
        
        nbVictoire[0] = nbVictoire[1]  = 0;
        for (int i = 0 ; i < _nbPartie ; i++ ) {
            vainqueur = startNewGame(_trace);
            
            if ( vainqueur == joueur1 ) nbVictoire[0]++;
            if ( vainqueur == joueur2 ) nbVictoire[1]++;
            
            if ( vainqueur == null ) {
                nbVictoire[0]+=0.5;
                nbVictoire[1]+=0.5;
            }

            if ( numJoueur == 0  )  {              
                currentJoueur = joueur2;
                numJoueur=1;
            }
            else {
                currentJoueur = joueur1;
                numJoueur=0;
            }
            System.out.println(joueur1 + " score : " + nbVictoire[0]);
            System.out.println(joueur2 + " score : " + nbVictoire[1]);

        }
        
        System.out.println(joueur1 + " score : " + nbVictoire[0]);
        System.out.println(joueur2 + " score : " + nbVictoire[1]);
        
        if (nbVictoire[0] > nbVictoire[1]) 
            System.out.println(joueur1 + " GAGNE ");
        else
        if (nbVictoire[1] > nbVictoire[0]) 
            System.out.println(joueur2 + " GAGNE ");
        else
            System.out.println("Match nul");               
    }

    public Joueur getCurrentJoueur() {
        return currentJoueur;
    }
}
