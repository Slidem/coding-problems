package main

import (
	"fmt"
	"sort"
)

func MinimumWaitingTime(queries []int) int {

	if len(queries) == 0 {
		return 0
	}

	sort.Ints(queries)

	total := 0
	prevS := queries[0]

	for i := 1; i < len(queries); i++ {
		total += prevS
		prevS += queries[i]
	}

	return total
}

func main() {

	fmt.Println(MinimumWaitingTime([]int{3, 2, 1, 2, 6}))
}
