# The gray code is a binary numeral system where two successive values differ in only one bit.
# Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. A gray code sequence must begin with 0.

class Solution:
    def grayCode(self, n):
        if n == 0:
            return [0]
        
        if n == 1:
            return [0, 1]
        
        result = []
        current_number = 0
        result.append(current_number)
        cache = set()
        cache.add(current_number)

        while len(result) < 2 ** n:

            bit_pos = 0
            while True: 
                next_number = self.inverse_bit_at(current_number, bit_pos)
                bit_pos += 1
                if next_number not in cache:
                    result.append(next_number)
                    cache.add(next_number)
                    current_number = next_number
                    break 
        
        return result 

    def inverse_bit_at(self, number, pos):
        return number ^ (1 << pos) 

print(Solution().grayCode(3))