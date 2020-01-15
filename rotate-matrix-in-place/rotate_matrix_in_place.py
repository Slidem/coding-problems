class Solution:
    def rotate(self, matrix):
        if not matrix or (len(matrix) == 1 and len(matrix[0]) == 1):
            return

        self.matrix = matrix
        self.validate_matrix()
        n = len(matrix)
        s = 0
        e = n - 1
        while s < e:
            self.rotate_layer(s, e)
            s += 1
            e -= 1

    def validate_matrix(self):
        rows = len(matrix)
        cols = len(matrix[0] if matrix else -1)

        if rows != cols:
            raise ValueError(
                f"Matrix should be n x n. Given matrix has {rows} and {cols}")

    def rotate_layer(self, s, e):
        self.rotate_corners(s, e)
        for idx in range(1, e - s):
            carry = self.rotate_up_to_right(s, e, idx)
            carry = self.rotate_right_to_down(s, e, idx, carry) 
            carry = self.rotate_down_to_left(s, e, idx, carry)
            self.rotate_left_to_up(s, e, idx, carry)

    def rotate_corners(self, s, e):
        # copy left top corner to right top corner
        carry = self.matrix[s][e]
        self.matrix[s][e] = self.matrix[s][s]
        
        # copy right top corner to right bottom corner
        aux = carry
        carry = self.matrix[e][e]
        self.matrix[e][e] = aux

        # copy right bottom corner to left bottom corner
        aux = carry
        carry = self.matrix[e][s]
        self.matrix[e][s] = aux

        # copy left bottom corner to left top corner
        self.matrix[s][s] = carry
    
    def rotate_up_to_right(self, s, e, idx):
        carry = self.matrix[s + idx][e]
        self.matrix[s + idx][e] = self.matrix[s][s + idx]
        return carry
        
    def rotate_right_to_down(self, s, e, idx, carry):
        aux = carry
        carry = self.matrix[e][e - idx]
        self.matrix[e][e - idx] = aux
        return carry
        
    def rotate_down_to_left(self, s, e, idx, carry):
        aux = carry
        carry = self.matrix[e - idx][s]
        self.matrix[e - idx][s] = aux
        return carry

    def rotate_left_to_up(self, s, e, idx, carry):
        self.matrix[s][s + idx] = carry


# test solution
# matrix = [
#     [1, 2, 3], [4, 5, 6], [7, 8, 9]
# ]

matrix = [
    [1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12], [13, 14, 15, 16]
]

matrix = [
    [ 1, 2, 3, 4, 5],
    [ 6, 7, 8, 9,10],
    [11,12,13,14,15],
    [16,17,18,19,20],
    [21,22,23,24,25]
]

Solution().rotate(matrix)
print(matrix)
