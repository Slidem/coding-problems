from typing import List


class Solution:
    def nextPermutation(self, nums: List[int]) -> None:
        swap_idx = 0
        for i in reversed(range(1, len(nums))):
            if nums[i] > nums[i - 1]:
                swap_idx = i
                for j in range(i, len(nums)):
                    if j == len(nums) - 1 or (nums[j] > nums[i - 1] and nums[j - 1] < nums[i - 1]):
                        # swap
                        nums[i - 1], nums[j] = nums[j], nums[i - 1]
                        break
                break
        self.reverse(nums, swap_idx)

    def reverse(self, arr, start):
        end = len(arr)-1
        limit = int((start + end)/2) + 1
        for i in range(start, limit):
            arr[i], arr[end] = arr[end], arr[i]
            end = end - 1


nums = [1, 3, 2]
Solution().nextPermutation(nums)
print(nums)
