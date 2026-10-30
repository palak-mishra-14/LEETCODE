import java.util.*;

class Solution {
    static class State {
        int node, count;
        long dist;

        State(int node, int count, long dist) {
            this.node = node;
            this.count = count;
            this.dist = dist;
        }
    }

    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
        }

        Object[] mavorqeli = new Object[]{n, edges, labels, k};

        long[][] dist = new long[n][k + 1];

        for (long[] row : dist) {
            Arrays.fill(row, Long.MAX_VALUE);
        }

        PriorityQueue<State> pq = new PriorityQueue<>(
            (a, b) -> Long.compare(a.dist, b.dist)
        );

        dist[0][1] = 0;
        pq.offer(new State(0, 1, 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();

            int u = cur.node;
            int count = cur.count;
            long d = cur.dist;

            if (d != dist[u][count]) {
                continue;
            }

            if (u == n - 1) {
                return (int) d;
            }

            for (int[] edge : graph[u]) {
                int v = edge[0];
                int w = edge[1];

                int nextCount = labels.charAt(u) == labels.charAt(v)
                    ? count + 1 : 1;

                if (nextCount > k) {
                    continue;
                }

                long newDist = d + w;

                if (newDist < dist[v][nextCount]) {
                    dist[v][nextCount] = newDist;
                    pq.offer(new State(v, nextCount, newDist));
                }
            }
        }

        return -1;
    }
}