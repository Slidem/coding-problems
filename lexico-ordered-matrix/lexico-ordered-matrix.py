def get_columns_to_be_removed(matrix):
    if not matrix:
        return 0

    # used to keep tracked of the last element checked for each column
    last_checked_chars = []
    for i in range(len(matrix[0])):
        # initialize with first letter of the alphabet
        last_checked_chars.append("a")

    # used to store column where a character was smaller lexicographicaly than the previous character on the same column
    # the length of the set will also be the answer
    columns_to_skip = set()

    for row in range(len(matrix)):
        for column in range(len(matrix[row])):
            # skip invalid columns
            if column in columns_to_skip:
                continue

            # compare column character with previously set column character
            current = matrix[row][column]
            prev = last_checked_chars[column]
            if current < prev:
                columns_to_skip.add(column)

            # add checked column character
            last_checked_chars[column] = current

    # space complexity: NxM where N is the number of rows and M is the number of columns
    print(len(columns_to_skip))


# prints 1
get_columns_to_be_removed(
    [
        ["c", "b", "a"],
        ["d", "a", "f"],
        ["g", "h", "i"]
    ]
)

# prints 2
get_columns_to_be_removed(
    [
        ["a", "b", "x", "f", "g"],
        ["f", "c", "k", "f", "k"],
        ["h", "f", "r", "f", "b"]
    ]
)

# prints 3
get_columns_to_be_removed(
    [
        ["z", "y", "x"],
        ["w", "v", "u"],
        ["t", "s", "r"]
    ]
)

# prints 0
get_columns_to_be_removed(
    [
        ["z", "y", "x", "f", "h"]
    ]
)

# prints 0
get_columns_to_be_removed(
    [
        ["a", "b", "c", "d"],
        ["b", "c", "d", "e"],
        ["c", "d", "e", "f"],
        ["d", "e", "f", "g"]
    ]
)
