from typing import List


class Solution:
    def strStr(self, haystack: str, needle: str) -> int:

        if not needle:
            return 0

        stack_arr = list(haystack)
        needle_arr = list(needle)

        n_idx = 0
        s_idx = 0

        # while searching for needle, always remember the starting index in the haystack of the next needle occurence
        stack_next_start = (0, False)

        while s_idx < len(stack_arr):

            c = stack_arr[s_idx]

            if c != needle_arr[n_idx]:
                # reset needle search
                n_idx = 0

                # if while searching for the needle, the start of the next needle to search was found
                # start from there
                if stack_next_start[1]:
                    s_idx = stack_next_start[0]
                    stack_next_start = (0, False)
                # otherwise just continue searching the haystack for the next needle first char occurence
                else:
                    s_idx += 1
                    continue

            # arriving here only if needle first char occurence found
            while n_idx < len(needle) and s_idx < len(stack_arr):
                if n_idx != 0 and not stack_next_start[1] and stack_arr[s_idx] == needle_arr[0]:
                    stack_next_start = (s_idx, True)
                if stack_arr[s_idx] == needle_arr[n_idx]:
                    n_idx += 1
                    s_idx += 1
                else:
                    break

            if n_idx == len(needle_arr):
                return s_idx - len(needle_arr)

        return -1


# test
print(Solution().strStr("mississippi", "issip"))
