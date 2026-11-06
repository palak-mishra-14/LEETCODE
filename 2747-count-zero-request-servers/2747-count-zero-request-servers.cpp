class Solution {
public:
    vector<int> countServers(int n, vector<vector<int>>& logs, int x, vector<int>& queries) {
        sort(logs.begin(), logs.end(), [](auto &a, auto &b) {
            return a[1] < b[1];
        });

        int m = queries.size();
        vector<pair<int, int>> q;

        for (int i = 0; i < m; i++) {
            q.push_back({queries[i], i});
        }

        sort(q.begin(), q.end());

        vector<int> ans(m);
        unordered_map<int, int> mp;

        int left = 0, right = 0;

        for (auto [time, idx] : q) {
            int start = time - x;

            while (right < logs.size() && logs[right][1] <= time) {
                mp[logs[right][0]]++;
                right++;
            }

            while (left < right && logs[left][1] < start) {
                int id = logs[left][0];

                mp[id]--;

                if (mp[id] == 0) {
                    mp.erase(id);
                }

                left++;
            }

            ans[idx] = n - mp.size();
        }

        return ans;
    }
};