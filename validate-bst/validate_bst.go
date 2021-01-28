package main

type BST struct {
	Value int

	Left  *BST
	Right *BST
}

func (tree *BST) Validate() bool {
	return isValid(tree, nil, nil)
}

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
