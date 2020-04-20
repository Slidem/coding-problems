class Solution:
    def setZeroes(self, matrix):
        
        cols_with_zeros = set() 
        rows_with_zeros = set()

        for row in range(0, len(matrix)):
            for col in range(0, len(matrix[0])):
                if matrix[row][col] == 0:
                    rows_with_zeros.add(row)
                    cols_with_zeros.add(col)
        
        for col in cols_with_zeros: 
            self.make_col_zero(matrix, col)
        
        for row in rows_with_zeros:
            self.make_row_zero(matrix, row)
        

    def make_col_zero(self, matrix, col):
        for row in range(0, len(matrix)):
            matrix[row][col] = 0
    
    def make_row_zero(self, matrix, row):
        for col in range(0, len(matrix[0])):
            matrix[row][col] = 0