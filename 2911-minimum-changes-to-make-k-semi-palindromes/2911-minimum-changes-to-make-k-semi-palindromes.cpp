
class Solution {
public:
    int minimumChanges(string s, int k) {
        int n = s.size();

        vector<vector<int>> cost(n, vector<int>(n, 0));

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;
                cost[i][j] = INT_MAX;

                for (int d = 1; d < len; d++) {
                    if (len % d != 0) continue;

                    int changes = 0;

                    for (int r = 0; r < d; r++) {
                        int l = i + r;
                        int right = i + r + ((len - 1 - r) / d) * d;

                        while (l < right) {
                            if (s[l] != s[right]) {
                                changes++;
                            }

                            l += d;
                            right -= d;
                        }
                    }

                    cost[i][j] = min(cost[i][j], changes);
                }
            }
        }

        vector<vector<int>> dp(k + 1, vector<int>(n + 1, 1e9));

        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {
            for (int i = parts * 2; i <= n; i++) {
                for (int j = (parts - 1) * 2; j <= i - 2; j++) {
                    dp[parts][i] = min(
                        dp[parts][i],
                        dp[parts - 1][j] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
};
