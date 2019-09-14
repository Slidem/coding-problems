from typing import List


class Solution:
    def removeDuplicates(self, nums: List[int]) -> int:
        if not nums:
            return 0

        last_moved_pos = 0

        for pos, nb in enumerate(nums, start=0):
            if nb != nums[last_moved_pos]:
                swap_with = last_moved_pos + 1
                nums[swap_with], nums[pos] = nums[pos], nums[swap_with]
                last_moved_pos = swap_with

        return last_moved_pos + 1


# print solution
nums = [0, 0, 1, 1, 1, 2, 2, 3, 3, 3, 4]
print(Solution().removeDuplicates(nums))
print(nums)
