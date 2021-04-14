package tictactoecodingame;

import java.util.ArrayList;

public class Node {
    CoupTicTacToe coup;
    Node parent;
    ArrayList<Node> enfants;
    double visites;
    double victoires;
    double scoreUCT;

    public Node(CoupTicTacToe coup){ //pour un noeud racine.
        this.coup = coup;
        this.visites = 0;
        this.victoires = 0;
        this.enfants = new ArrayList<Node>();
    }

    public Node(CoupTicTacToe coup, Node parentNode){ //initialise un noeud enfant.
        this.coup = coup;
        this.visites = 0;
        this.victoires = 0;
        this.parent = parentNode;
        parentNode.enfants.add(this);
        this.enfants = new ArrayList<Node>();
    }

    // setters and getters
    public void setVisites(double visites){
        this.visites = visites;
    }
    public double getVisites(){
        return this.visites;
    }
    public void setVictoires(double victoires){
        this.victoires = victoires;
    }
    public double getVictoires(){
        return this.victoires;
    }
    public void setScoreUCT(double scoreUCT){
        this.scoreUCT = scoreUCT;
    }

    public CoupTicTacToe getCoup(){ return this.coup;}
    public void setCoup(CoupTicTacToe coup){ this.coup = coup; }

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

