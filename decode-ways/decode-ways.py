class Solution:
    def numDecodings(self, s: str) -> int:
        self.ways = 0 
        self.s = s
        self.memo = {} 
        return self.compute(0)
    
    
    def compute(self, idx):
        if idx in self.memo:
            return self.memo[idx]
        
        if idx >= len(self.s) - 1:
            
            if idx == len(self.s) - 1 and self.s[idx] == '0':
                return 0
            
            return 1 

        if self.s[idx] == '0':
            return 0
        
        ways = self.compute(idx+1)
        
        if self.two_digit_letter_code_possible(idx):
            ways += self.compute(idx+2) 
        
        self.memo[idx] = ways
        return ways
         
    
    def two_digit_letter_code_possible(self, idx):
        
        if idx == len(self.s) - 1:
            return False
        
        possible_code = int(self.s[idx:idx+2])
        return possible_code > 9 and possible_code < 27


# Testing

print(Solution().numDecodings("2323113"))


