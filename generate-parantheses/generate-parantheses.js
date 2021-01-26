/**
 * @param {number} n
 * @return {string[]}
 */
function generateParenthesis(n) {

    let result = [];
    generate(result, "", n, n);
    return result;
}

function generate(result, str, open, close) {
    if (open == 0 && close == 0) {
        result.push(str);
        return;
    }
    if (open > 0) {
        generate(result, str + "(", open - 1, close);
    }
    if (close > open) {
        generate(result, str + ")", open, close - 1);
    }
}


console.log(generateParenthesis(4));

