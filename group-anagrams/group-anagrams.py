class Solution:
    def groupAnagrams(self, strs):
        memo = {}
        for s in strs:
            key = ''.join(sorted(s))
            if key not in memo:
                memo[key] = []
            memo[key].append(s)

        return list(memo.values())


print(Solution().groupAnagrams(["eat", "tea", "tan", "ate", "nat", "bat"]))
