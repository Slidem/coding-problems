class Solution:

    def __init__(self):
        self.s = None

    def longestValidParentheses(self, s: str) -> int:
        self.s = s

        sidx = 0
        while sidx < len(s) and self.isClosing(sidx):
            sidx += 1

        eidx = len(s) - 1
        while eidx > 0 and not self.isClosing(eidx):
            eidx -= 1

        longest = 0

        for i in range(sidx, eidx):
            openings = 0
            count = 0
            j = i
            for j in range(i, len(s)):
                if openings == 0 and self.isClosing(j):
                    break

                if self.isClosing(j):
                    openings -= 1
                else:
                    openings += 1

                count += 1

            # should go back now
            while openings != 0 and j >= 0:
                if not self.isClosing(j):
                    openings -= 1
                else:
                    openings += 1
                j -= 1
                count -= 1

            if openings > 0:
                i = i + openings
            else:
                longest = max(longest, count)

        return longest

    def isClosing(self, idx: int):
        return ')' == self.s[idx]


print(Solution().longestValidParentheses("(()"))
