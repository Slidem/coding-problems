package main

type BinaryTree struct {
	Value int

	Left   *BinaryTree
	Right  *BinaryTree
	Parent *BinaryTree
}

type SuccessorFinder struct {
	successor *BinaryTree
	target    *BinaryTree
}

func (s *SuccessorFinder) find(node *BinaryTree) {

	if s.successor != nil {
		return
	}

	if node == nil {
		return
	}

	s.find(node.Left)

	if node != s.target {

		s.find(node.Right)

	} else {

		if isRoot(node) {

			if (node.Right != nil) {
				s.successor = getMin(node.Right)
				return
			}

		} else if isLeaf(node) || hasOnlyOneChild(node) {

			if comesFromParentLeft(node) {
				s.successor = node.Parent
				return
			}

			if comesFromParentRight(node) {
				s.successor = node.Parent.Parent
				return
			}

		} else {

			s.successor = getMin(node.Right)
			return
		}

	}
}

func isRoot(n *BinaryTree) bool {

	return n.Parent == nil
}

func isLeaf(n *BinaryTree) bool{

	return n.Right == nil && n.Left == nil
}

func comesFromParentLeft(n *BinaryTree) bool{

	return n.Parent.Left == n
}

func comesFromParentRight(n *BinaryTree) bool{

	return n.Parent.Right == n
}

func hasOnlyOneChild(n *BinaryTree) bool{

	return n.Left == nil && n.Right != nil || n.Left != nil && n.Right == nil
}

func getMin(node *BinaryTree) *BinaryTree{

	n := node
	for n.Left != nil {
		n = n.Left
	}
	return n
}


func FindSuccessor(tree *BinaryTree, node *BinaryTree) *BinaryTree {

	finder := SuccessorFinder{target: node}
	finder.find(tree)
	return finder.successor
}
