
import java.util.*;

class Solution {
    List<Integer>[] tree;
    List<Integer>[] newTree;
    int[] newParent;
    int[] answer;
    int[] last;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        tree = new ArrayList[n];
        newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        newParent = parent.clone();
        last = new int[26];
        Arrays.fill(last, -1);

        dfs(0, s);

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        answer = new int[n];
        calculateSize(0);

        return answer;
    }

    private void dfs(int node, String s) {
        int ch = s.charAt(node) - 'a';

        int previous = last[ch];

        if (previous != -1) {
            newParent[node] = previous;
        }

        last[ch] = node;

        for (int child : tree[node]) {
            dfs(child, s);
        }

        last[ch] = previous;
    }

    private int calculateSize(int node) {
        int size = 1;

        for (int child : newTree[node]) {
            size += calculateSize(child);
        }

        answer[node] = size;
        return size;
    }
}
