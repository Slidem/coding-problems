
function isValid(s) {
    let parantheses = {
        "(": ")",
        "[": "]",
        "{": "}",
    };

    let stack = [];
    let toCheck = s.split("");
    for (let i = 0; i < toCheck.length; i++) {
        if (parantheses[toCheck[i]]) {
            stack.push(toCheck[i]);
        } else if (parantheses[stack.pop()] !== toCheck[i]) {
            return false;
        }
    }

    return stack.length === 0;
}

console.log(isValid("([])"));

