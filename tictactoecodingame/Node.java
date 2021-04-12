package tictactoecodingame;

import java.util.ArrayList;

public class Node {
    Plateau plateau;
    Joueur joueur;
    Node parent;
    ArrayList<Node> enfants;
    int visites;
    int victoires;
    double scoreUCT;

    public Node(Plateau plateau, Joueur joueur){ //pour un noeud racine.
        this.plateau = plateau;
        this.joueur = joueur;
        this.visites = 0;
        this.victoires = 0;
    }

    public Node(Plateau plateau, Joueur joueur, Node parentNode){ //initialise un noeud enfant.
        this.plateau = plateau;
        this.joueur = joueur;
        this.visites = 0;
        this.victoires = 0;
        this.parent = parentNode;
        parentNode.enfants.add(this);
    }

    // setters and getters
    public void setVisites(int visites){
        this.visites = visites;
    }
    public int getVisites(){
        return this.visites;
    }
    public void setVictoires(int victoires){
        this.victoires = victoires;
    }
    public int getVictoires(){
        return this.victoires;
    }
    public void setScoreUCT(double scoreUCT){
        this.scoreUCT = scoreUCT;
    }

    public double getScoreUCT(){
        return this.scoreUCT;
    }

    public Node getEnfant(int indice){
        return this.enfants.get(indice);
    }

    public Node getParent(){
        return this.parent;
    }


}

