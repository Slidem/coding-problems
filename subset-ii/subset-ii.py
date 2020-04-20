class Solution:
    def subsetsWithDup(self, nums):
        if not nums:
            return [] 

        self.nums = nums 
        self.nums.sort()

        self.result = []
        self.memo = set() 
    
        self.compute(-1, "", []) 
        return self.result
    

    def compute(self, idx, key, current):
        
        if idx == len(self.nums) or key in self.memo:
            return 

        self.result.append(current)
        self.memo.add(key)
        
        for i in range(idx + 1, len(self.nums)):
            n = self.nums[i]
            self.compute(i, key + str(n), [n, *current])
                
        
print(Solution().subsetWithDup([1, 2, 2, 3]))
    
    
            
        
