
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[n + 1];
        long min = Long.MAX_VALUE;

        for (int[] x : items) {
            freq[x[0]]++;
            min = Math.min(min, x[1]);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 2 * i; j <= n; j += i) {
                freq[i] += freq[j];
            }
        }

        Arrays.sort(items, (a, b) -> Integer.compare(a[1], b[1]));

        long ans = 0, rem = budget;

        for (int[] x : items) {
            int bonus = freq[x[0]] - 1;

            if (x[1] >= 2 * min) break;

            long take = Math.min(bonus, rem / x[1]);

            ans += 2 * take;
            rem -= take * x[1];
        }

        ans += rem / min;
        return (int) ans;
    }
}
