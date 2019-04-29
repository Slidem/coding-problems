
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
// Complexity : O(nlogn)
function largestProductOrdered(numbers){
    
    if (! numbers) {
        return 0;
    }

    //ascending sort 
    numbers.sort((a, b) => a - b);
    
    const largestProductWithNegatives = numbers[0] * numbers[1] * numbers[numbers.length-1];
    const largestProductWithPositives = numbers[numbers.length-1] * numbers[numbers.length-2] * numbers[numbers.length-3];

    return largestProductWithNegatives < largestProductWithPositives ? largestProductWithPositives : largestProductWithNegatives;
}


// we only need to know the maximum 3 positive numbers, and also the smallest 2 negative numbers
// we can do this in O(n) time
function largestProductLinear(numbers){

    const minimum = {
        "minimum" : numbers[0],
        "secondToMinimum" : numbers[1]
    };

    const maximum = {
        "max" : numbers[0],
        "secondToMax" : numbers[1],
        "thirdToMax" : numbers[2]
    };
    
    computeMinimum(minimum, null);
    computeMinimum(minimum, numbers[2]);
    computeMaximum(maximum, null);
    for(let i=3; i<numbers.length; i++){
        computeMinimum(minimum, numbers[i]);
        computeMaximum(maximum, numbers[i]);
    }

    const largestProductWithNegatives = minimum.minimum * minimum.secondToMinimum * maximum.max;
    const largestProductWithPositives = maximum.max * maximum.secondToMax * maximum.thirdToMax;
    return largestProductWithNegatives < largestProductWithPositives ? largestProductWithPositives : largestProductWithNegatives;
}

function computeMinimum(current, nb){
    if(!nb && current.minimum > current.secondToMinimum){
        const aux = current.minimum;
        current.minimum = current.secondToMinimum;
        current.secondToMinimum = aux;
    }

    if(nb < current.minimum){
       current.secondToMinimum = current.minimum; 
       current.minimum = nb;
    } else if(nb < current.secondToMinimum){
       current.secondToMinimum = nb;  
    }
}

function computeMaximum(current, nb){
    const aux = [current.max, current.secondToMax, current.thirdToMax];    
    if(nb){
        aux.push(nb);
    }
    aux.sort((a, b) => b - a);
    current.max = aux[0];
    current.secondToMax = aux[1];
    current.thirdToMax = aux[2];
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