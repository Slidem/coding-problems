from typing import List


class Solution:
    def removeElement(self, nums: List[int], val: int) -> int:
        last_swap_idx = -1
        for idx, x in enumerate(nums):
            if x != val:
                curent = idx
                while curent > 0 and nums[curent - 1] == val:
                    nums[curent], nums[curent -  
                                       1] = nums[curent - 1], nums[curent]
                    curent -= 1
                last_swap_idx = curent

        return last_swap_idx + 1


# print solution
nums = [4, 5]
print(Solution().removeElement(nums, 4))
print(nums)
