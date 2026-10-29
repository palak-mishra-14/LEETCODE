import java.util.*;

class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        int LOG = 17;
        int[][] up = new int[n][LOG];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] nodeMask = new int[n];
        int[] bit = new int[2 * n + 2];
        int[] timer = {0};

        for (int i = 0; i < n; i++) {
            nodeMask[i] = 1 << (s.charAt(i) - 'a');
        }

        dfs(0, 0, adj, up, tin, tout, timer, LOG);

        for (int i = 0; i < n; i++) {
            updateBit(bit, tin[i], nodeMask[i]);
            updateBit(bit, tout[i], nodeMask[i]);
        }

        List<Boolean> result = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");
            int u = Integer.parseInt(parts[1]);

            if (parts[0].equals("update")) {
                int newMask = 1 << (parts[2].charAt(0) - 'a');
                int delta = nodeMask[u] ^ newMask;

                updateBit(bit, tin[u], delta);
                updateBit(bit, tout[u], delta);

                nodeMask[u] = newMask;
            } else {
                int v = Integer.parseInt(parts[2]);
                int w = lca(u, v, up, tin, tout, LOG);

                int pathMask = prefix(bit, tin[u])
                             ^ prefix(bit, tin[v])
                             ^ nodeMask[w];

                result.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return result;
    }

    private void dfs(int u, int p, List<Integer>[] adj,
                     int[][] up, int[] tin, int[] tout,
                     int[] timer, int LOG) {
        tin[u] = timer[0]++;
        up[u][0] = p;

        for (int j = 1; j < LOG; j++) {
            up[u][j] = up[up[u][j - 1]][j - 1];
        }

        for (int v : adj[u]) {
            if (v == p) continue;
            dfs(v, u, adj, up, tin, tout, timer, LOG);
        }

        tout[u] = timer[0]++;
    }

    private boolean isAncestor(int u, int v, int[] tin, int[] tout) {
        return tin[u] <= tin[v] && tout[v] <= tout[u];
    }

    private int lca(int u, int v, int[][] up,
                    int[] tin, int[] tout, int LOG) {
        if (isAncestor(u, v, tin, tout)) return u;
        if (isAncestor(v, u, tin, tout)) return v;

        for (int j = LOG - 1; j >= 0; j--) {
            if (!isAncestor(up[u][j], v, tin, tout)) {
                u = up[u][j];
            }
        }

        return up[u][0];
    }

    private void updateBit(int[] bit, int index, int value) {
        for (int i = index + 1; i < bit.length; i += i & -i) {
            bit[i] ^= value;
        }
    }

    private int prefix(int[] bit, int index) {
        int result = 0;

        for (int i = index + 1; i > 0; i -= i & -i) {
            result ^= bit[i];
        }

        return result;
    }
}