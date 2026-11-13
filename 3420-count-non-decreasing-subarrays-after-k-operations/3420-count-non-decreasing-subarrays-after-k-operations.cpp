class Solution {
public:
    long long countNonDecreasingSubarrays(vector<int>& nums, int k) {
        int n = nums.size();
        long long ans = 0, cost = 0;

        deque<pair<int, int>> dq;
        int j = n - 1;

        for (int i = n - 1; i >= 0; i--) {
            int num = nums[i];
            int cnt = 1;

            while (!dq.empty() && dq.back().first < num) {
                auto p = dq.back();
                dq.pop_back();

                cost += 1LL * (num - p.first) * p.second;
                cnt += p.second;
            }

            dq.push_back({num, cnt});

            while (cost > k) {
                auto p = dq.front();
                dq.pop_front();

                cost -= p.first - nums[j];
                j--;

                if (p.second > 1) {
                    dq.push_front({p.first, p.second - 1});
                }
            }

            ans += j - i + 1;
        }

        return ans;
    }
};