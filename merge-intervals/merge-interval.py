class Solution:
    def merge(self, intervals):

        if not intervals:
            return []

        # First, sort the intervals, in order to approach the problem in a greedy manner
        # Sort by interval begining
        sorted_intervals = sorted(intervals, key=lambda interval: interval[0])

        mn = intervals[0][0]
        mx = intervals[0][1]

        merged = []

        for i in range(1, len(intervals)):

            interval = sorted_intervals[i]
            
            if interval[0] > mx:
                merged.append([mn, mx])
                mn = interval[0]
                mx = interval[1]
            else:
                mx = max(mx, interval[1])

        merged.append([mn, mx])

        return merged


print(Solution().merge([[1, 4], [0, 4]]))