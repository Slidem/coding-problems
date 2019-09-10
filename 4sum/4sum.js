// Given an array nums of n integers and an integer target, 
// are there elements a, b, c, and d in nums such that a + b + c + d = target? 
// Find all unique quadruplets in the array which gives the sum of target.



function fourSum(nums, target) {

    nums.sort((a, b) => a - b);

    let solutionsHash = new Set();
    let solutions = [];

    for (let i = 0; i < nums.length - 1; i++) {
        for (let j = i + 1; j < nums.length; j++) {
            let s = j + 1;
            let e = nums.length - 1;
            while (s < e) {
                let sum = nums[i] + nums[j] + nums[s] + nums[e];
                if (sum < target) {
                    s++;
                } else if (sum > target) {
                    e--;
                } else {
                    let key = "" + nums[i] + nums[j] + nums[s] + nums[e];
                    if (!solutionsHash.has(key)) {
                        solutionsHash.add(key);
                        solutions.push([nums[i], nums[j], nums[s], nums[e]]);
                    }
                    s++;
                    e--;
                }
            }
        }
    }

    return solutions;
}

console.log(fourSum([-1, 0, -5, -2, -2, -4, 0, 1, -2], -9));