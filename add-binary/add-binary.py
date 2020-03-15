class Solution:

    def addBinary(self, a, b):
        
        idx_a = len(a) - 1
        idx_b = len(b) - 1
        
        carry = 0

        result = []

        while idx_a != -1 and idx_b != -1:
            
            bit_a = int(a[idx_a])
            bit_b = int(b[idx_b])

            addition = bit_a + bit_b + carry

            to_add = addition % 2 
            carry = addition // 2

            result.append(to_add)
            
            idx_a -= 1
            idx_b -= 1
        
        if idx_a != -1 or idx_b != -1:

            remaining = a 
            remaining_idx = idx_a 

            if idx_a == -1:
                remaining = b
                remaining_idx = idx_b

            self.add_remaining(remaining, remaining_idx, carry, result)

        elif carry != 0:
            result.append(carry)

        return "".join([str(x) for x in reversed(result)])


    def add_remaining(self, remaining, remaining_idx, carry, result):
        
        while remaining_idx != -1:
            bit = int(remaining[remaining_idx] )
            addition = carry + bit
            to_add = addition % 2
            carry = addition // 2
            result.append(to_add) 
            remaining_idx -= 1
        
        if carry != 0:
            result.append(carry)



print(Solution().addBinary("1010", "1011"))