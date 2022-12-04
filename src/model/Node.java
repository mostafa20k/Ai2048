package model;

import core.Constants;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Node implements Comparable<Node> {
    Board board;
    Node parent;
    Movement previousMovement;
    int cost;
    int f_value;
    int g_value;

    public Node(Board board, Node parent, Movement previousMovement) {
        this.parent = parent;
        this.board = board;
        this.previousMovement = previousMovement;
        cost = pathCost(previousMovement);
        if (parent==null){
            g_value = 0;
        }
        else {
            g_value = parent.g_value+1;
        }
        f_value = g_value + heuristic();
    }

    @Override
    public int compareTo(Node o) {
//        if (cost < o.cost) {
//            return -1;
//        } else if (cost > o.cost) {
//            return 1;
//        }
        if(f_value<o.f_value){
            return -1;
        }
        else if(f_value>o.f_value){
            return 1;
        }
        return 0;
    }

    public ArrayList<Node> successor() {
        ArrayList<Node> result = new ArrayList<Node>();
        result.add(new Node(board.moveLeft(), this, Movement.LEFT));
        result.add(new Node(board.moveRight(), this, Movement.RIGHT));
        result.add(new Node(board.moveDown(), this, Movement.DOWN));
        result.add(new Node(board.moveUp(), this, Movement.UP));
        return result;
    }

    public void drawState() {
        System.out.println("moved to : " + this.previousMovement);
        for (int i = 0; i < board.row; i++) {
            for (int j = 0; j < board.col; j++) {
                System.out.print(Constants.ANSI_BRIGHT_GREEN + board.cells[i][j] + spaceRequired(board.cells[i][j]));
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------");
    }

    public boolean isGoal() {
        return board.isGoal();
    }

    public int pathCost(Movement move) {
        switch (move) {
            case LEFT -> {
                return 1;
            }
            case RIGHT -> {
                return 3;
            }
            case DOWN -> {
                return 5;
            }
            case UP -> {
                return 7;
            }
            default -> {
                return 0;
            }
        }
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public int getF_value() {
        return f_value;
    }

    public void setF_value(int f_value) {
        this.f_value = f_value;
    }

    public int getG_value() {
        return g_value;
    }

    public int heuristic() {
        if (Board.mode == Constants.MODE_NORMAL) {
            int curbest = board.getGoalValue();
            if ((board.getGoalValue() - (board.max() + board.second_max())) < curbest) {
                curbest = board.max() + board.second_max();
            }
            return board.getGoalValue() - curbest;
        } else if (Board.mode == Constants.MODE_ADVANCE) {

            return board.zero_max();
        }
        return board.getGoalValue();
    }

    public String hash() {
        StringBuilder hash = new StringBuilder();
        hash.append(board.hash()).append("/PM=").append(previousMovement.toString());
        return hash.toString();
    }

    private String spaceRequired(int cell) {
        int length = String.valueOf(cell).length();
        String result = " ".repeat(5 - length);
        result += " ";
        return result;
    }

    public Node getParent() {
        return parent;
    }

}
