
class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[1501];
        int minPrice = Integer.MAX_VALUE;

        for (int[] x : items) {
            freq[x[0]]++;
            minPrice = Math.min(minPrice, x[1]);
        }

        int[] dp = new int[budget + 1];

        for (int[] x : items) {
            int factor = x[0];
            int price = x[1];

            int free = -1;

            for (int j = factor; j <= 1500; j += factor) {
                free += freq[j];
            }

            int value = 1 + free;

            for (int b = budget; b >= price; b--) {
                dp[b] = Math.max(dp[b],
                        dp[b - price] + value);
            }
        }

        int ans = 0;

        for (int b = 0; b <= budget; b++) {
            ans = Math.max(ans,
                    dp[b] + (budget - b) / minPrice);
        }

        return ans;
    }
}
