def permuteUnique(nums):
    return permute(nums, set(), "")


def permute(nums, permuted_keys, key):

    if not nums:
        return None

    if key in permuted_keys:
        return -1

    permutations = []

    initial_key = key

    for x in nums:
        remaining = nums.copy()
        remaining.remove(x)
        key = initial_key + str(x)
        remaining_permutations = permute(remaining, permuted_keys, key)

        if remaining_permutations is None:
            permutations.append([x])
            continue

        if remaining_permutations == -1:
            continue

        for r in remaining_permutations:
            r.append(x)
            permutations.append(r)

        permuted_keys.add(key)

    return permutations


print(permuteUnique([1, 1, 2]))
