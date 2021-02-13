package main

type BinaryTree struct {
	Value int
	Left  *BinaryTree
	Right *BinaryTree
}

func BinaryTreeDiameter(tree *BinaryTree) int {
	diameter := 0
	search(tree, &diameter)
	return diameter - 1
}

func search(tree *BinaryTree, d *int) int{

	if tree == nil {
		return 0
	}
	if tree.Left == nil && tree.Right == nil {
		return 1
	}
	pathLeft := search(tree.Left, d)
	pathRight := search(tree.Right, d)
	*d = Max(*d, pathLeft + pathRight)
	return Max(pathLeft, pathRight) + 1
}

func Max(a, b int) int {
	if a > b {
		return a
	}
	return b
}
