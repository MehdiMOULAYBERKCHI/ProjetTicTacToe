package tictactoecodingame;


/**
 * @author Franck
 */
public abstract class Coup {

    private int note;

    public Coup() {
        note = Integer.MIN_VALUE;  // un coup est tres mauvais tant qu'il n'est pas analysé.
    }

    public void setNote(int _note) {
        note = _note;
    }

    public int getNote() {
        return note;
    }

    abstract public String toString();
    
    abstract public boolean equals(Object obj);
    
    abstract public int hashCode();
    
}
