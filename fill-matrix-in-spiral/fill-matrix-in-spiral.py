class Solution:
    def generateMatrix(self, n):

        self.matrix = []

        # populate with zeros
        for i in range(0, n):
            row = []
            for j in range(0, n):
                row.append(0)
            self.matrix.append(row)

        layer_size = n
        start_value = 1
        for i in range(0, n // 2 if n % 2 == 0 else n // 2 + 1):
            # populates the outter layer, given the start row, and the layer length
            start_value = self.populate_outer_layer(i, start_value, layer_size)
            layer_size -= 2

        return self.matrix

    def populate_outer_layer(self, start_idx, start_value, layer_size):

        if layer_size == 1:
            self.matrix[start_idx][start_idx] = start_value
            return start_value

        # upper row
        row = start_idx
        col = start_idx
        value = start_value

        while col < layer_size + start_idx:
            self.matrix[row][col] = value
            value += 1
            col += 1

        col -= 1
        row += 1

        while row < layer_size + start_idx:
            self.matrix[row][col] = value
            value += 1
            row += 1

        row -= 1
        col -= 1

        while col >= start_idx:
            self.matrix[row][col] = value
            value += 1
            col -= 1

        col += 1
        row -= 1

        while row > start_idx:
            self.matrix[row][col] = value
            value += 1
            row -= 1

        return value


print(Solution().generateMatrix(1))
