package tictactoecodingame;

/**
 * @author  Franck
 *
 */

import java.io.InputStreamReader;
import java.io.IOException;

/**
 * @author Franck
 */
public class JoueurHumain extends Joueur {


    public JoueurHumain( String _joueur ) {
        super( _joueur );
    }

    public Coup joue(Plateau _plateau) {
        InputStreamReader clavier = new InputStreamReader(System.in);
        char buffer[] = new char[10];
        
        String monCoup = null;

        // Demande au joueur de saisir son Coup
        System.out.println("Votre coup : ");

        try {
            int nbCar = clavier.read(buffer, 0, 5);
            monCoup = new String(buffer, 0, nbCar-1);

            return _plateau.stringToCoup(monCoup, this);
            
        } catch (IOException e) {
            return null;
        }

    }
}
