package main

import "fmt"

// Do not edit the class below except for
// the insert, contains, and remove methods.
// Feel free to add new properties and methods
// to the class.
type BST struct {
	Value int

	Left  *BST
	Right *BST
}

func (tree *BST) Insert(value int) *BST {
	insertedNode := &BST{
		Value: value,
	}

	var parent *BST = nil
	node := tree

	for node != nil {
		parent = node
		if value < node.Value {
			node = node.Left
		} else {
			node = node.Right
		}
	}

	if value < parent.Value {
		parent.Left = insertedNode
	} else {
		parent.Right = insertedNode
	}

	return tree
}

func (tree *BST) Contains(value int) bool {
	node := tree

	for node != nil && node.Value != value {

		if value < node.Value {
			node = node.Left
		} else {
			node = node.Right
		}
	}

	return node != nil
}

func (tree *BST) RemoveWhereNodeDiffersFrom(value int, differsFrom *BST) *BST {
	var parent *BST
	var node *BST

	node = tree

	for node != nil && (node.Value != value || node == differsFrom) {

		parent = node

		if value < node.Value {
			node = node.Left
		} else {
			node = node.Right
		}
	}

	// no values found
	if node == nil {

		return tree
	}

	// remove a leaf node, simply mark the pointer from the parent as null
	if node.Left == nil && node.Right == nil {

		if parent == nil {
			return nil
		}

		parent.RemoveChild(node)
	} else if nonNilChild, found := node.GetNonNilChild(); found {

		if parent == nil {
			// if root, we need to copy values from the nonNilChild
			node.Value = nonNilChild.Value
			node.Left = nonNilChild.Left
			node.Right = nonNilChild.Right
		} else {
			parent.ReplaceChildWith(node, nonNilChild)
		}
	} else {
		smallest := node.Right.GetSmallest()
		node.RemoveWhereNodeDiffersFrom(smallest, node)
		node.Value = smallest
	}

	return tree
}

func (tree *BST) Remove(value int) *BST {
	return tree.RemoveWhereNodeDiffersFrom(value, nil)
}

func (node *BST) GetSmallest() int {

	n := node

	for n.Left != nil {
		n = n.Left
	}

	return n.Value
}

func (node *BST) GetNonNilChild() (*BST, bool) {

	if node.Left != nil && node.Right == nil {
		return node.Left, true
	} else if node.Right != nil && node.Left == nil {
		return node.Right, true
	}

	return nil, false
}

func (node *BST) ReplaceChildWith(child *BST, with *BST) {

	if node.Right == child {
		node.Right = with
	} else if node.Left == child {
		node.Left = with
	}
}

func (node *BST) RemoveChild(child *BST) {

	node.ReplaceChildWith(child, nil)
}

func main() {
	root := &BST{
		Value: 10,
	}
	root.Insert(5)
	root.Insert(15)
	root.Insert(2)
	root.Insert(5)
	root.Insert(13)
	root.Insert(22)
	root.Insert(1)
	root.Insert(14)
	root.Insert(12)
	root.Remove(5)
	root.Remove(5)
	root.Remove(12)
	root.Remove(13)
	root.Remove(14)
	root.Remove(22)
	root.Remove(2)
	root.Remove(1)
	fmt.Println(root.Contains(15))
}
