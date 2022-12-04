package ai;

import model.Node;

public class IDS {
    public void search(Node startNode) {
        DFS dfs = new DFS();
        for (int i = 0; ; i++) {
            if (dfs.search(startNode, i))
                break;
        }
    }
}
