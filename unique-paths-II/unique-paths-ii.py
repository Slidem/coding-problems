class Solution:

    def __init__(self):
        self.grid = None
        self.memo = {} 

    def uniquePathsWithObstacles(self, obstacleGrid) -> int:
        if not obstacleGrid:
            return 0

        self.rows = len(obstacleGrid)
        self.cols = len(obstacleGrid[0])

        if self.cols == 0:
            return 0
        
        if obstacleGrid[self.rows - 1][self.cols - 1] == 1 or obstacleGrid[0][0] == 1:
            return 0

        self.grid = obstacleGrid
        return self.compute(0, 0)

    def compute(self, row, col):
        
        if row == self.rows - 1 and col == self.cols - 1:
            return 1
        
        memo_key = f"{row}#{col}"
        
        if memo_key in self.memo:
            return self.memo[memo_key] 

        right_moves = 0
        down_moves = 0

        if row != self.rows - 1 and self.grid[row + 1][col] != 1:
            right_moves = self.compute(row + 1, col)

        if col  != self.cols - 1 and self.grid[row][col + 1] != 1:
            down_moves = self.compute(row, col + 1)
        
        paths = right_moves + down_moves
        self.memo[memo_key] = paths 

        return paths
      

g = [
    [0,0,0,0],
    [0,1,0,0],
    [0,0,0,0],
    [0,0,1,0],
    [0,0,0,0]
    ]


# g = [
#     [0, 0, 0],
#     [0, 1, 0],
#     [0, 0, 0],
# ]

# g = [
#     [0, 0]
# ]

# g = [
#     [0, 0, 1],
#     [0, 0, 0],
#     [0, 1, 0],
# ]

print(Solution().uniquePathsWithObstacles(g))
