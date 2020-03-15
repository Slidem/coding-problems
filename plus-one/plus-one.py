class Solution:

    def plusOne(self, digits):

        if not digits:        
            return digits

        carry = -1
        idx = len(digits) - 1
        
        while carry != 0 and idx != -1:

           digit = digits[idx] 
           plus_one = digit + 1  
           digits[idx] = plus_one % 10 
           carry = plus_one // 10
           idx -= 1

        if carry != 0:
            digits.insert(0, carry)
           
        
        return digits



# testing

print(Solution().plusOne([9, 9, 9]))
           
            

