class Solution:
    def canJump(self, nums):

        if len(nums) == 1:
            return True

        allowed_steps = nums[0]
        idx = 1

        while allowed_steps > 0 and idx < len(nums):
            allowed_steps -= 1
            if allowed_steps < nums[idx]:
                allowed_steps = nums[idx]
            idx += 1

        return idx == len(nums)


print(Solution().canJump([2, 0]))
