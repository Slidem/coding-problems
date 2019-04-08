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

    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            if j in columns_to_skip:
                continue

            # compare column character with previously set column character
            current = matrix[i][j]
            prev = last_checked_chars[j]
            if current < prev:
                columns_to_skip.add(j)

            # add checked column character
            last_checked_chars[j] = current

    print(len(columns_to_skip))


# prints 1
get_columns_to_be_removed(
    [
        ["c", "b", "a"],
        ["d", "a", "f"],
        ["g", "h", "i"]
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
