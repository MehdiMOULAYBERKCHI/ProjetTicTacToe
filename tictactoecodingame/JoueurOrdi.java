package tictactoecodingame;

/**
 * @author Franck
 */
public class JoueurOrdi extends Joueur {
    boolean ponder; // Si vrai l'ordinateur réfléchi pendant la reflexion de son adversaire
    
    AlgoRecherche algoRecherche;
    
    public JoueurOrdi(String _nom) {
        super(_nom);
        ponder = false;
    }
    
    public JoueurOrdi( String _nom , AlgoRecherche _algo , boolean _ponder ) {
        super( _nom );
        algoRecherche = _algo;
        ponder = _ponder;
    }

    public JoueurOrdi( String _nom , AlgoRecherche _algo  ) {
        this( _nom , _algo , false );
    }

    public AlgoRecherche getAlgoRecherche() {
        return algoRecherche;
    }

    public void setAlgoRecherche(AlgoRecherche _algoRecherche) {
        algoRecherche = _algoRecherche;
    }
    

    public Coup joue(Plateau _plateau  ) {
        Coup coupOrdi;

        coupOrdi = algoRecherche.meilleurCoup(_plateau , this , ponder);
       
        return coupOrdi;
    }
}
