package ai;

import model.Node;

import java.util.*;

public class IDA_Star {
    public void search(Node startNode) {
        Queue<Node> frontier = new LinkedList<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        if (startNode.isGoal()) {
            System.out.println("you win!");
            printResult(startNode, 0);
            return;
        }
        frontier.add(startNode);
        inFrontier.put(startNode.hash(), true);
        while (!frontier.isEmpty()) {
            Node temp = frontier.poll();
            inFrontier.remove(temp.hash());
            ArrayList<Node> children = temp.successor();
            children.sort(Comparator.comparingInt(Node::getF_value));
            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash()))) {
                    if (child.isGoal()) {
                        printResult(child, 0);
                        System.out.println("you win !!!");
                        return;
                    }
                    frontier.add(child);
                    inFrontier.put(child.hash(), true);
                }
            }
        }
        System.out.println("no solution");
    }


    public void printResult(Node node, int depthCounter) {
        if (node.getParent() == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }
        System.out.println(node.toString());
        node.drawState();
        printResult(node.getParent(), depthCounter + 1);
    }

}
