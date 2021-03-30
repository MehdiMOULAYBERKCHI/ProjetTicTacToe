package tictactoecodingame;


/**
 * @author Franck
 */
public class Case {

    int colonne, ligne;

    public Case(int _colonne, int _ligne) {
        this.colonne = _colonne;
        this.ligne = _ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public int getLigne() {
        return ligne;
    }
    
   public void setLigne( int _ligne ) {
        this.ligne = _ligne;
    }

   public void setColonne( int _colonne ) {
        this.colonne = _colonne;
    }
      
}
