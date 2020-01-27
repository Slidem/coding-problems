class Solution:

    def uniquePaths(self, cols, rows):
        
        paths_matrix = []
        paths_matrix.append([1 for i in range(0, cols)])
        for i in range(1, rows):
            paths_matrix.append([(1 if i == 0 else 0) for i in range(0, cols)])
         
        
        paths_matrix[0][0] = 1 
        for r in range(1, rows):
            for c in range(1, cols):
                paths_matrix[r][c] = paths_matrix[r - 1][c] + paths_matrix[r][c - 1]
        
        return paths_matrix[r - 1][c - 1]
                
    

print(Solution().uniquePaths(23, 11))
                
            

        

       


        