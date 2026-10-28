class Solution {
public:
    int minCost(vector<vector<int>>& grid) {
        int m = grid.size(), n = grid[0].size();
        vector<vector<unordered_set<int>>> dp(
            m, vector<unordered_set<int>>(n)
        );

        dp[0][0].insert(grid[0][0]);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0) {
                    for (int x : dp[i - 1][j])
                        dp[i][j].insert(x ^ grid[i][j]);
                }

                if (j > 0) {
                    for (int x : dp[i][j - 1])
                        dp[i][j].insert(x ^ grid[i][j]);
                }
            }
        }

        return *min_element(
            dp[m - 1][n - 1].begin(),
            dp[m - 1][n - 1].end()
        );
    }
};