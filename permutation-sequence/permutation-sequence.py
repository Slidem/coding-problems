from functools import reduce


class Solution:
    def getPermutation(self, n, k):

        if n < 2:
            return "1"

        perm_elements = [str(x + 1) for x in range(n)]
        perm_length = len(perm_elements)
        kth = ""
        group = None
        while True:
            # at each iteration, compute how many permutations an element from the remaining elements to be permuted
            # can make with all of the other elements
            if len(perm_elements) == 2:
                a = perm_elements[0]
                b = perm_elements[1]
                if k % 2 == 0:
                    a, b = b, a
                kth += (a + b)
                return kth

            one_with_others_number_of_perms = self.factorial(
                len(perm_elements) - 1)

            if one_with_others_number_of_perms == 0:
                kth += perm_elements[0]
                break

            group = k // one_with_others_number_of_perms
            k = k % one_with_others_number_of_perms
            to_pop = group
            if k == 0:
                to_pop = group - 1 
            kth = kth + perm_elements.pop(to_pop)

        return kth

    def factorial(self, n):
        if n == 0:
            return 0

        return reduce(lambda x, y: x * y, range(1, n+1))


# test factorial
# print(Solution().factorial(4))

print(Solution().getPermutation(3, 2))
