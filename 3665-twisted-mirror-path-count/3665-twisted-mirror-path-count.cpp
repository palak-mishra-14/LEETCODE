
class Solution {
public:
    int uniquePaths(vector<vector<int>>& grid) {
        const int MOD = 1e9 + 7;
        int m = grid.size(), n = grid[0].size();

        vector<vector<vector<int>>> memo(
            m, vector<vector<int>>(n, vector<int>(2, -1))
        );

        function<int(int,int,int)> getNext = [&](int r, int c, int dir) -> int {
            if (r >= m || c >= n) return -1;

            if (grid[r][c] == 0)
                return r * n + c;

            if (memo[r][c][dir] != -1)
                return memo[r][c][dir];

            if (dir == 1)
                return memo[r][c][dir] = getNext(r + 1, c, 0);
            else
                return memo[r][c][dir] = getNext(r, c + 1, 1);
        };

        vector<vector<long long>> dp(m, vector<long long>(n, 0));
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == 0) continue;

                if (j + 1 < n) {
                    int pos = getNext(i, j + 1, 1);

                    if (pos != -1) {
                        int x = pos / n;
                        int y = pos % n;
                        dp[x][y] = (dp[x][y] + dp[i][j]) % MOD;
                    }
                }

                if (i + 1 < m) {
                    int pos = getNext(i + 1, j, 0);

                    if (pos != -1) {
                        int x = pos / n;
                        int y = pos % n;
                        dp[x][y] = (dp[x][y] + dp[i][j]) % MOD;
                    }
                }
            }
        }

        return dp[m - 1][n - 1];
    }
};
