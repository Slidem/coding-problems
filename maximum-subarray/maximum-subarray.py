class Solution:
    def maxSubArray(self, nums):

        s = 0  
        e = 0
        m = 0
        sum = 0

        while e < len(nums):
            
            sum += nums[e]
            m = max(m, sum) 

            while sum < 0 and s <= e and s < len(nums) :
                sum -= nums[s]
                s += 1 

            if s > e:
                e = s
            else:
                e += 1

        
        return m


print(Solution().maxSubArray([-2, 1, -3, 4, -1, 2, 1, -5, 4]))