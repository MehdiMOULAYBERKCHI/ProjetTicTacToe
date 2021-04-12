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
    public int maxIterations = 10;

    public AlgoRechercheMonteCarlo() {
        rnd = new Random();
    }


    @Override
    public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {

        Node rootNode = new Node(_plateau, _joueur); //todo rajouter le cas ou expand n'est pas possible car on est sur une feuille
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            Node selectedNode = selectNode(rootNode);
            System.out.println(selectedNode); //virer
            System.out.println(selectedNode.enfants.size()); //virer
            expand(selectedNode);
            System.out.println(selectedNode.enfants.size()); //virer
            Node expandedNode = selectedNode.enfants.get(0);
            boolean isItAWin = simulate(expandedNode);
            retropropagate(isItAWin, expandedNode, rootNode);
        }
        Node bestNode = bestNode(rootNode);
        Coup bestCoup = bestNode.plateau.getDernierCoup();
        return bestCoup;
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
        System.out.println(node.plateau); //virer
        ArrayList<Coup> listeCoups = node.plateau.getListeCoups(node.joueur); //todo vérifier cette ligne
        System.out.println(listeCoups); //virer
        if (listeCoups.size() == 0) {
            //todo si on cherche à expend un noeud feuille
        } else {
            for (int indice = 0; indice < listeCoups.size(); indice++) {
                Node newNode = new Node(node.plateau, node.joueur, node);
                newNode.plateau.joueCoup(listeCoups.get(indice));
                CoupTicTacToe coup = (CoupTicTacToe) newNode.plateau.getDernierCoup();
                Joueur newjoueur = coup.getJeton().getJoueur();
                newNode.joueur = newjoueur;

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

    public void retropropagate(boolean isItAWin, Node expandedNode, Node rootNode) {
        Node currentNode = expandedNode;
        while(currentNode != rootNode){
            if(isItAWin){
                currentNode.setVictoires(currentNode.getVictoires() +1 );
            }
            currentNode.setVisites(currentNode.getVisites() +1 );
            currentNode = currentNode.parent;
        }
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

    public Node bestNode(Node rootNode){
//        int bestNodeIndice = 0;
//        double bestWinRate = rootNode.getVictoires() / rootNode.getVisites();
//        for (int i = 0; i < rootNode.enfants.size(); i++){
//            double winRate = rootNode.enfants.get(i).getVictoires() / rootNode.enfants.get(i).getVisites();
//            if (winRate > bestWinRate){
//                bestWinRate = winRate;
//                bestNodeIndice = i;
//            }
//        }
        //return rootNode.enfants.get(bestNodeIndice);
        return rootNode.enfants.get(0);
    }

}
