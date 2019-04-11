
// first naive solution is use a brute force approach, where we user 3 for loops, 
// and compute all posible combinations of 3 numbers, 
// and check which is the biggest product
// complexity : O(n^3)
function largestProductBruteForce(numbers){
    if (! numbers) {
        return 0;
    }

    let maxProduct = numbers[0] * numbers[1] * numbers[2];

    for(let i = 0; i<numbers.length; i++){
        for(let j=0; j<numbers.length; j++){
            for(let z=0; z<numbers.length; z++){
                if(i == j || j == z || i == z){
                    continue;
                }
                const product = numbers[i] * numbers[j] * numbers[z];
                if(product > maxProduct){
                    maxProduct = product;
                }
            }
        }
    }

    return maxProduct;
}


// this first sorts the numbers, and than compares the first two and last element of the array.
// if sorted in ascending order, negative numbers should be first, and positive last
// if we don't find at least 2 negative numbers, than just return the last 3 number's product
// 
// if there are at least two negative numbers, 
// second negative number should be bigger than the second to last postive number in order to return a product between 2 negative numbers and one positive
// otherwise return last 3 number's product
// Complexity : O(nlogn)
function largestProductOrdered(numbers){
    
    if (! numbers) {
        return 0;
    }

    //ascending sort 
    numbers.sort((a, b) => a - b);
    
    const firstNegative = numbers[0];         
    const secondNegative = numbers[1];
    const lastPositive = numbers[numbers.length-1];
    const secondToLastPositive = numbers[numbers.length-2];
    const thirdToLastPositive = numbers[numbers.length-3];

    if( (firstNegative < 0 || secondNegative < 0) && Math.abs(secondNegative) > secondToLastPositive && secondToLastPositive >= 0){
        return firstNegative * secondNegative * lastPositive;
    } else {
        return lastPositive * secondToLastPositive * thirdToLastPositive;
    }
}


// we only need to know the maximum 3 positive numbers, and also the smallest 2 negative numbers
// we can do this in O(n) time
function largestProductLinear(numbers){

    let maxNegative = numbers[0];
    let secondToMaxNegative = numbers[0];
    let maxPositive = numbers[0];
    let secondToMaxPositive = numbers[0];
    let thirdToMaxPositive = numbers[0];

    for(let i=0; i<numbers.length; i++){
        const number = numbers[i];
        if(number >= maxPositive){
            thirdToMaxPositive = secondToMaxPositive;
            secondToMaxPositive = maxPositive;
            maxPositive = number;
        }
        if(number < 0 && number <= maxNegative){
            secondToMaxNegative = maxNegative;
            maxNegative = number;
        }        
    }

    if( (maxNegative < 0 || secondToMaxNegative < 0) && Math.abs(secondToMaxNegative) > secondToMaxPositive && secondToMaxPositive >= 0){
        return maxNegative * secondToMaxNegative * maxPositive;
    } else {
        return maxPositive * secondToMaxPositive * thirdToMaxPositive;
    }
        
}


// test [-10, -10, 5, 2] -> 500 (-10 * -10 * 5)
console.log(largestProductBruteForce([-10, -10, 5, 2]));
console.log(largestProductOrdered([-10, -10, 5, 2]));
console.log(largestProductLinear([-10, -10, 5, 2]));

// test [4, 6, 2, 6, -2, -2, -10, -1] -> 144 (6 * 6 * 4)
console.log(largestProductBruteForce([4, 6, 2, 6, -2, -2, -10, -1]));
console.log(largestProductOrdered([4, 6, 2, 6, -2, -2, -10, -1]));
console.log(largestProductLinear([4, 6, 2, 6, -2, -2, -10, -1]));

// test [-3, -1, -1, -1] -> -1 (-1 * -1 * -1)
console.log(largestProductBruteForce([-3, -1, -1, -1]));
console.log(largestProductOrdered([-3, -1, -1, -1]));
console.log(largestProductLinear([-3, -1, -1, -1]));