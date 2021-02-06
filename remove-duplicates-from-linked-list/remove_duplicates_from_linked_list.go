package main

import "fmt"

type LinkedList struct {
	Value int
	Next  *LinkedList
}

func RemoveDuplicatesFromLinkedList(linkedList *LinkedList) *LinkedList {

	visitedValues := map[int]string{}

	root := linkedList

	var prev *LinkedList
	node := linkedList

	for node != nil {
		if _, found := visitedValues[node.Value]; !found {
			visitedValues[node.Value] = ""
			prev = node
		} else {
			prev.Next = node.Next
		}
		node = node.Next
	}

	return root

}

func main() {

	linkedList := createLinkedListFromIntArray([]int{1, 1, 2, 2})
	linkedList = RemoveDuplicatesFromLinkedList(linkedList)
	printList(linkedList)
}

func createLinkedListFromIntArray(slice []int) *LinkedList {

	var root *LinkedList
	var node *LinkedList

	for _, v := range slice {
		next := &LinkedList{Value: v}
		if root == nil {
			root = next
			node = next
		} else {
			node.Next = next
			node = next
		}
	}

	return root
}

func printList(root *LinkedList) {

	node := root
	listStr := ""

	for node != nil {
		if node == root {
			listStr += fmt.Sprintf("%d", node.Value)
		} else {
			listStr += fmt.Sprintf(", %d", node.Value)
		}
		node = node.Next
	}

	fmt.Println(listStr)
}
