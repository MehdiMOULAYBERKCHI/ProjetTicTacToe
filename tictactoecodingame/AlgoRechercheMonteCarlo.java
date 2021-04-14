/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoecodingame;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class AlgoRechercheMonteCarlo extends AlgoRecherche {

    Random rnd;
    public int maxIterations = 1000;
    String[][] nodeWinRates = new String[3][3];

    public AlgoRechercheMonteCarlo() {
        rnd = new Random();
    }


    @Override
    public Coup meilleurCoup(Plateau _plateau, Joueur _joueur, boolean _ponder) {

        CoupTicTacToe coupRoot = (CoupTicTacToe) _plateau.getDernierCoup();
        if (coupRoot == null){ // si l'ordi joue le premier coup.
            ArrayList<Coup> coups = _plateau.getListeCoups(_joueur);
            return coups.get(4); //milieu pour un 3x3
        }

        Node rootNode = new Node(coupRoot);

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            _plateau.sauvegardePosition(1);
            Node selectedNode = selectNode(rootNode, _plateau, _joueur);

            Node expandedNode = expandNode(selectedNode, _plateau, _joueur);

            int resultSimul = simulate(expandedNode, _plateau, _joueur, rootNode); //-1 défaite, 0 égalité, 1 victoire

            backPropagate(resultSimul, expandedNode, rootNode, _plateau, _joueur, iteration);
            _plateau.restaurePosition(1);

            //updateWinRate(rootNode);
            //System.out.println(Arrays.deepToString(nodeWinRates));
            //System.out.println("------------------------------------------------------------------------------------------");

        }
        Node bestNode = bestNode(rootNode);
        Coup bestCoup = bestNode.coup;
        return bestCoup;
    }

    //renvoit le noeud selectionné avec la méthode UCT
    public Node selectNode(Node rootNode, Plateau plateau, Joueur joueur) { //todo joueur non necessaire ?
        Node currentNode = rootNode;
        while (currentNode.enfants.size() != 0) {
            int meilleurIndice = 0;
            double meilleurUCT = calcUCTScore(currentNode.enfants.get(0), joueur);

            for (int indice = 0; indice < currentNode.enfants.size(); indice++) { //test tous les enfants de la currentNode
                double scoreUCT = calcUCTScore(currentNode.enfants.get(indice), joueur);

                if (scoreUCT > meilleurUCT) {
                    meilleurUCT = scoreUCT;
                    meilleurIndice = indice;
                }
            }

            currentNode = currentNode.getEnfant(meilleurIndice);
            plateau.joueCoup(currentNode.coup);

        }
        return currentNode;

    }

    //expend le noeud choisi
    public Node expandNode(Node node, Plateau plateau, Joueur joueurCasRoot) {

        Joueur joueur;
        if (node.parent == null){
            joueur = joueurCasRoot;
        }
        else {
            joueur = node.parent.coup.getJeton().getJoueur();
        }

        ArrayList<Coup> listeCoups = plateau.getListeCoups(joueur);

        if (listeCoups.size() == 0 || plateau.partieTerminee()) {
            return node; //si on cherche à expend un noeud feuille
        } else {
            for (int indice = 0; indice < listeCoups.size(); indice++) {
                Node newNode = new Node((CoupTicTacToe) listeCoups.get(indice), node);
            }
        }
        plateau.joueCoup(node.enfants.get(rnd.nextInt(node.enfants.size())).coup); // coup aléatoire parmis les coups de la node enfant

        return node.enfants.get(rnd.nextInt(node.enfants.size()));
    }

    public int simulate(Node expandedNode, Plateau plateau, Joueur joueur, Node routeNode) {

        Joueur joueurExpandedNode;
        if (expandedNode.parent.coup.getJeton() == null){ // si l'ordi commence à jouer, donc si le premier coup est un coup sans joueur (grille vide, pour avoir un noeud racine)
            joueurExpandedNode = joueur;
        }
        else {
            joueurExpandedNode = expandedNode.parent.coup.getJeton().getJoueur();
        }
        Joueur joueur2 = expandedNode.coup.getJeton().getJoueur();
        Joueur currentJoueur = joueurExpandedNode;

        int res = 0;

        plateau.sauvegardePosition(0);

        while (!plateau.partieTerminee()) {
            ArrayList<Coup> coups = plateau.getListeCoups(currentJoueur);
            Coup coup = coups.get(rnd.nextInt(coups.size()));
            //System.out.println("\n" + plateau );
            plateau.joueCoup(coup);

            if (currentJoueur == joueurExpandedNode) {
                currentJoueur = joueur2;
            } else {
                currentJoueur = joueurExpandedNode;
            }

        }
        //System.out.println(plateau + "\n");
        Joueur vainqueur = plateau.vainqueur();

        if (joueurExpandedNode == joueur){
            if (vainqueur != null){
                if (vainqueur == joueurExpandedNode){
                    res = 1;
                }
                else {
                    res = -1;
                }
            }
        }
        else {
            if (vainqueur != null){
                if (vainqueur == joueurExpandedNode){
                    res = -1;
                }
                else {
                    res = 1;
                }
            }
        }

        plateau.restaurePosition(0);
        //System.out.println("resultat: " + res);
        return res;
    }

    public void backPropagate(int resultSimul, Node expandedNode, Node rootNode, Plateau plateau, Joueur joueur, int i) {
        Node currentNode = expandedNode;

        while(currentNode != rootNode){
            if (resultSimul == 0){
                currentNode.setVictoires(currentNode.getVictoires() + 0.5 );
            }
            else if (resultSimul == 1){
                currentNode.setVictoires(currentNode.getVictoires() +1 );
            }
            currentNode.setVisites(currentNode.getVisites() +1);

            currentNode = currentNode.parent;
        }
        if (resultSimul == 0){
            rootNode.setVictoires(rootNode.getVictoires() + 0.5 );
        }
        else if (resultSimul == 1){
            rootNode.setVictoires(rootNode.getVictoires() +1 );
        }
        rootNode.setVisites(rootNode.getVisites() +1 );
    }

    public double calcUCTScore(Node node, Joueur joueur) { //calcul le score UTC et met à jour l'attribut du noeud.
        double w = node.getVictoires();
        double n = node.getVisites();
        double c = Math.sqrt(2);
        double t = node.getParent().getVisites();
        int epsilon = 1;
        if (node.coup.getJeton().getJoueur() != joueur){
            epsilon = -1;
        }

        if (n == 0) { //retourne l'infinie si le noeud n'a jamais été visité
            node.setScoreUCT(Integer.MAX_VALUE);
            return Integer.MAX_VALUE;
        }

        double score = epsilon * (w / n) + c * Math.sqrt(Math.log(t) / n);
        node.setScoreUCT(score);
        return score;
    }

    public Node bestNode(Node rootNode){
        int bestNodeIndice = 0;
        double bestWinRate = rootNode.getVictoires() / rootNode.getVisites();
        for (int i = 0; i < rootNode.enfants.size(); i++){
            Node currentNode = rootNode.enfants.get(i);
            if (currentNode.getVisites() != 0){
                double winRate = currentNode.getVictoires() / currentNode.getVisites();
                if (winRate > bestWinRate){
                    bestWinRate = winRate;
                    bestNodeIndice = i;
                }
            }

        }
        return rootNode.enfants.get(bestNodeIndice);
    }

    public void updateWinRate(Node rootNode){

        for (int i = 0; i < 3; i++){
            for (int j = 0; j<3; j++){
                nodeWinRates[i][j] = "0/0";
            }
        }

        for (int i=0; i< rootNode.enfants.size(); i++){
            if(rootNode.enfants.get(i).getVisites() != 0){
                String winRate = "" + rootNode.enfants.get(i).getVictoires() + "/" + rootNode.enfants.get(i).getVisites();
                //double winRate = rootNode.enfants.get(i).getVisites();
                //double winRate = rootNode.enfants.get(i).getScoreUCT();
                int c = rootNode.enfants.get(i).coup.getColonne();
                int l = rootNode.enfants.get(i).coup.getLigne();
                nodeWinRates[l][c] = winRate;
            }
        }
    }

}
