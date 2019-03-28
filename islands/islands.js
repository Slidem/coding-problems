//helper class
class MatrixElement {
  constructor(row, col, value, matrix) {
    this.row = row;
    this.col = col;
    this.value = value;
    this.matrix = matrix;
  }

  elementKey() {
    return "" + this.row + this.col;
  }

  getOccupiedNeighbours() {
    const neighbours = [];
    //get left
    if (this.col != 0) {
      const leftValue = this.matrix[this.row][this.col - 1];
      if (leftValue != 0) {
        neighbours.push(
          new MatrixElement(this.row, this.col - 1, leftValue, this.matrix)
        );
      }
    }

    //get right
    if (this.col != this.matrix[0].length - 1) {
      const rightValue = this.matrix[this.row][this.col + 1];
      if (rightValue != 0) {
        neighbours.push(
          new MatrixElement(this.row, this.col + 1, rightValue, this.matrix)
        );
      }
    }

    //get up
    if (this.row != 0) {
      const upValue = this.matrix[this.row - 1][this.col];
      if (upValue != 0) {
        neighbours.push(
          new MatrixElement(this.row - 1, this.col, upValue, this.matrix)
        );
      }
    }

    //get down
    if (this.row != this.matrix.length - 1) {
      const downValue = this.matrix[this.row + 1][this.col];
      if (downValue != 0) {
        neighbours.push(
          new MatrixElement(this.row + 1, this.col, downValue, this.matrix)
        );
      }
    }

    return neighbours;
  }
}

// The problem solver ! :)
function getNumberOfIslands(matrix) {
  if (!matrix) {
    throw "Invalid matrix argument";
  }

  //create map of matrix elements;
  const matrixElements = {};
  for (let row = 0; row < matrix.length; row++) {
    for (let col = 0; col < matrix[row].length; col++) {
      const value = matrix[row][col];
      if (value != 0) {
        const matrixElement = new MatrixElement(row, col, value, matrix);
        matrixElements[matrixElement.elementKey()] = matrixElement;
      }
    }
  }

  let islands = 0;

  const visited = {};
  for (let key in matrixElements) {
    const el = matrixElements[key];
    visitIsland(el, visited, matrixElements);
    islands++;
  }

  return islands;
}

// do dfs
function visitIsland(element, visited, matrixElements) {
  const key = element.elementKey();
  if (visited.hasOwnProperty(key)) {
    return;
  }
  visited[key] = element;
  delete matrixElements[key];
  element
    .getOccupiedNeighbours()
    .forEach(neighbour => visitIsland(neighbour, visited, matrixElements));
}

//testing
const testMatrix3Islands = [
  [1, 1, 0, 0, 0],
  [0, 1, 1, 1, 0],
  [0, 1, 1, 0, 0],
  [0, 1, 0, 1, 0],
  [1, 1, 0, 0, 1],
  [1, 1, 0, 0, 1]
];

const testMatrix5Islands = [
  [1, 1, 0, 0, 0],
  [0, 0, 1, 1, 0],
  [0, 0, 1, 0, 0],
  [0, 1, 0, 1, 0],
  [1, 1, 0, 0, 1],
  [1, 1, 0, 0, 1]
];

const testMatrix2Islands = [
  [1, 1, 0, 0, 0],
  [0, 1, 1, 1, 0],
  [0, 1, 1, 0, 0],
  [0, 1, 0, 1, 0],
  [1, 1, 0, 1, 1],
  [1, 1, 0, 0, 1]
];

const testNoIslands = [
  [0, 0, 0, 0, 0],
  [0, 0, 0, 0, 0],
  [0, 0, 0, 0, 0],
  [0, 0, 0, 0, 0],
  [0, 0, 0, 0, 0]
];

const cornerIslands = [
  [1, 0, 0, 0, 0],
  [0, 0, 0, 0, 1],
  [0, 0, 0, 0, 0],
  [0, 0, 0, 0, 0],
  [1, 0, 0, 0, 1]
];

console.log(getNumberOfIslands(testMatrix2Islands)); // returns 2
console.log(getNumberOfIslands(testMatrix3Islands)); // returns 3
console.log(getNumberOfIslands(testMatrix5Islands)); // returns 5
console.log(getNumberOfIslands(testNoIslands)); // returns  0
console.log(getNumberOfIslands(cornerIslands)); // returns 4
