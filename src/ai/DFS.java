package ai;

import model.Node;

import java.util.*;

public class DFS {

    public boolean search(Node startNode, int depthLimit) {
        int depth_counter = 0;
        Stack<Node> frontier = new Stack<Node>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> exp = new Hashtable<>();
        if (startNode.isGoal()) {
            System.out.println("you win!");
            printResult(startNode, 0);
            return true;
        }
        frontier.add(startNode);
        inFrontier.put(startNode.hash(), true);
        while (!frontier.isEmpty()) {
            Node temp = frontier.pop();
            inFrontier.remove(temp.hash());
            exp.put(temp.hash(), true);
            depth_counter = depth_check(temp);
            if (depth_counter <= depthLimit || depthLimit == -1) {
                ArrayList<Node> children = temp.successor();
                for (Node child : children) {
                    if (!(inFrontier.containsKey(child.hash())) && !exp.containsKey(child.hash()) && (depth_counter <= depthLimit || depthLimit == -1)) {
                        if (child.isGoal()) {
                            printResult(child, 0);

                            System.out.println("you win !!!");
                            return true;
                        }
                        frontier.add(child);
                        inFrontier.put(child.hash(), true);
                    }
                }
            }
        }
        if (depthLimit == -1 || depth_counter == depthLimit) {
            System.out.println("no solution");
        }
        return false;

    }

    public int depth_check(Node node) {
        int depth = 0;
        while (node.getParent() != null) {
            node = node.getParent();
            depth++;
        }
        return depth;
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
