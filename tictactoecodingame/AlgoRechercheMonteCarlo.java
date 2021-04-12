/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.Random;
import java.util.ArrayList;

public class AlgoRechercheMonteCarlo extends AlgoRecherche {

    Random rnd;
    public int maxIterations;

    public AlgoRechercheMonteCarlo() {
        rnd = new Random();
    }


    @Override
    public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {

        Node rootNode = new Node(_plateau, _joueur);
        for (int iteration = 0; iteration < maxIterations; iteration++) {
  //todo a completer
        }
    }

    //renvoit le noeud selectionné avec la méthode UCT
    public Node selectNode(Node _rootNode) {
        Node currentNode = _rootNode;
        while (currentNode.enfants.size() != 0) {

            int meilleurIndice = 0;
            double meilleurScore = currentNode.enfants.get(0).getScoreUCT();

            for (int indice = 0; indice < currentNode.enfants.size(); indice++) { //test tous les enfants de la currentNode
                double score = currentNode.enfants.get(indice).getScoreUCT();
                if (score > meilleurScore) {
                    meilleurScore = score;
                    meilleurIndice = indice;
                }
            }

            currentNode = currentNode.getEnfant(meilleurIndice);
        }
        return currentNode;
    }

    //expend le noeud choisi
    public void expand(Node node) {
        ArrayList<Coup> listeCoups = node.plateau.getListeCoups(node.joueur); //todo vérifier cette ligne
        if (listeCoups.size() == 0) {
            //todo si on cherche à expend un noeud feuille
        } else {
            for (int indice = 0; indice < listeCoups.size(); indice++) {
                Node newNode = new Node(node.plateau, node.joueur, node);
                newNode.plateau.joueCoup(listeCoups.get(indice));
            }
        }
    }

    public boolean simulate(Node node) {

        Plateau plateau = node.plateau;
        Joueur joueur1 = node.joueur;
        Joueur joueur2 = node.parent.joueur;
        Joueur currentJoueur = joueur1;

        plateau.sauvegardePosition(0);

        while (!plateau.partieTerminee()) {
            ArrayList<Coup> coups = plateau.getListeCoups(currentJoueur);
            Coup coup = coups.get(rnd.nextInt(coups.size()));
            plateau.joueCoup(coup);

            if (currentJoueur == joueur1) {
                currentJoueur = joueur2;
            } else {
                currentJoueur = joueur1;
            }

        }
        Joueur vainqueur = plateau.vainqueur();

        plateau.restaurePosition(0);

        if (vainqueur == joueur1){
            return true;
        }
        else {
            return false;
        }
    }


    public void retropropagate(int winOrLoose) {

    }

    public double calcUCTScore(Node node) { //calcul le score UTC et met à jour l'attribut du noeud.
        int w = node.getVictoires();
        int n = node.getVisites();
        double c = Math.sqrt(2);
        double t = node.getParent().getVisites();

        if (n == 0) { //retourne l'infinie si le noeud n'a jamais été visité
            node.setScoreUCT(Integer.MAX_VALUE);
            return Integer.MAX_VALUE;
        }

        double score = w / n + c * Math.sqrt(Math.log(t) / n);
        node.setScoreUCT(score);
        return score;
    }
}
