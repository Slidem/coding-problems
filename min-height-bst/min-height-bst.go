package main

import (
	"fmt"
)

// helper object to keep the refference to the array, in order to avoid passing the array to the recursive function
type MinHeightBstConstruction struct {
	array []int
	root  *BST
}

func (bstConstruction *MinHeightBstConstruction) Construct() {
	s := 0
	e := len(bstConstruction.array) - 1
	bstConstruction.construct(nil, s, e)
}

// helper recursive function
func (bstConstruction *MinHeightBstConstruction) construct(parentNode *BST, s, e int) {

	if s > e {
		return
	}

	h := (s + e) / 2

	var node *BST
	nodeValue := bstConstruction.array[h]
	if parentNode == nil {
		bstConstruction.root = &BST{Value: nodeValue}
		node = bstConstruction.root
	} else {
		node = parentNode.Insert(nodeValue)
	}

	bstConstruction.construct(node, s, h-1)
	bstConstruction.construct(node, h+1, e)
}

func MinHeightBST(array []int) *BST {

	// invoke the helper object
	construction := MinHeightBstConstruction{array: array}
	construction.Construct()
	return construction.root
}

type BST struct {
	Value int
	Left  *BST
	Right *BST
}

func (tree *BST) Insert(value int) *BST {
	if value < tree.Value {
		if tree.Left == nil {
			tree.Left = &BST{Value: value}
		} else {
			tree.Left.Insert(value)
		}
	} else {
		if tree.Right == nil {
			tree.Right = &BST{Value: value}
		} else {
			tree.Right.Insert(value)
		}
	}
	return tree
}

// only for testing purpouse
func (tree *BST) MaxHeight() int {
	return getMaxHeight(tree, 1)
}

// helper function
func getMaxHeight(node *BST, lvl int) int {

	if node == nil {
		return lvl - 1
	}

	return max(getMaxHeight(node.Left, lvl+1), getMaxHeight(node.Right, lvl+1))
}

func max(a, b int) int {
	if b > a {
		return b
	}
	return a
}

// only for testing purpouse
func (tree *BST) Validate() bool {
	return isValid(tree, nil, nil)
}

// helper function
func isValid(node *BST, min *int, max *int) bool {
	if node == nil {
		return true
	}

	v := node.Value
	if min != nil && v < *min || max != nil && v >= *max {
		return false
	}

	return isValid(node.Left, min, &node.Value) && isValid(node.Right, &node.Value, max)
}

func main() {

	testInput := []int{1, 2, 5, 7, 10, 13, 14, 15, 22}
	root := MinHeightBST(testInput)
	fmt.Println(root.Validate())
	fmt.Println(root.MaxHeight())
}
