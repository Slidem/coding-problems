from typing import List


class Solution:
    def nextPermutation(self, nums: List[int]) -> None:
        # first, find the descending number starting from the end
        s = -1
        for i in range(len(nums) - 1, 0, -1):
            curent = nums[i]
            next = nums[i - 1]
            if curent > next:
                s = i - 1
                break

        swap_with_idx = len(nums) - 1
        # if s = -1, that means all numbers are ordered in desc
        if s != -1:
            swap_with_idx = self.compute_swap_idx(nums, s)
            # swap
            nums[s], nums[swap_with_idx] = nums[swap_with_idx], nums[s]

        # now reverse the subarray
        self.reverse(nums, s + 1)

    # search in the subarray delimited by the found idx (s) until the end of the array
    # and find the smallest number in the subarray bigger than the number at the found idx;
    def compute_swap_idx(self, nums, s):
        swap_with_idx = len(nums) - 1

        for i in range(s + 1, len(nums) - 1):
            curent = nums[i]
            next = nums[i + 1]
            if nums[s] < curent and nums[s] >= next:
                swap_with_idx = i
        return swap_with_idx

    # reverese in place from start (including)
    def reverse(self, nums, start):
        s = start
        end = len(nums) - 1
        while s < end:
            nums[s], nums[end] = nums[end], nums[s]
            s += 1
            end -= 1


nums = [3, 2, 1]
Solution().nextPermutation(nums)
print(nums)
