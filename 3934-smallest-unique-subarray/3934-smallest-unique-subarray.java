import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] temp = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = nums[i];
        }

        int classes = 100001;

        for (int k = 1; k < n; k *= 2) {
            countingSort(sa, temp, rank, k, classes, n);
            countingSort(temp, sa, rank, 0, classes, n);

            int newClasses = 1;
            int[] newRank = new int[n];

            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {
                int a = sa[i - 1];
                int b = sa[i];

                int a1 = rank[a];
                int b1 = rank[b];

                int a2 = a + k < n ? rank[a + k] : -1;
                int b2 = b + k < n ? rank[b + k] : -1;

                if (a1 != b1 || a2 != b2) {
                    newClasses++;
                }

                newRank[b] = newClasses - 1;
            }

            rank = newRank;
            classes = newClasses;

            if (classes == n) {
                break;
            }

            if (k > n / 2) {
                break;
            }
        }

        int[] pos = new int[n];
        int[] lcp = new int[n];

        for (int i = 0; i < n; i++) {
            pos[sa[i]] = i;
        }

        int h = 0;

        for (int i = 0; i < n; i++) {
            int r = pos[i];

            if (r == 0) {
                continue;
            }

            int j = sa[r - 1];

            while (i + h < n && j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        int ans = n;

        for (int i = 0; i < n; i++) {
            int r = pos[i];
            int common = lcp[r];

            if (r + 1 < n) {
                common = Math.max(common, lcp[r + 1]);
            }

            int len = common + 1;

            if (len <= n - i) {
                ans = Math.min(ans, len);
            }
        }

        return ans;
    }

    private void countingSort(int[] input, int[] output,
                              int[] rank, int offset,
                              int classes, int n) {

        int[] count = new int[classes + 1];

        for (int i = 0; i < n; i++) {
            int idx = input[i];
            int key = idx + offset < n
                    ? rank[idx + offset] + 1 : 0;

            count[key]++;
        }

        for (int i = 1; i <= classes; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int idx = input[i];
            int key = idx + offset < n
                    ? rank[idx + offset] + 1 : 0;

            output[--count[key]] = idx;
        }
    }
}