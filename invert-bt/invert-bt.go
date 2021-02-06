package main

type BinaryTree struct {
	Value int

	Left  *BinaryTree
	Right *BinaryTree
}

func (tree *BinaryTree) InvertBinaryTree() {
	l, r := tree.Right, tree.Left
	tree.Left = l
	tree.Right = r
	if l != nil {
		l.InvertBinaryTree()
	}
	if r != nil {
		r.InvertBinaryTree()
	}
}

