def permute(nums):
    if not nums:
        return []

    permutations = []

    for x in nums:
        remaining = nums.copy()
        remaining.remove(x)
        remaining_permutations = permute(remaining)
        if not remaining_permutations:
            permutations.append([x])
            continue
        for p in remaining_permutations:
            p.append(x)
            permutations.append(p)

    return permutations


print(permute([1, 2, 3]))
