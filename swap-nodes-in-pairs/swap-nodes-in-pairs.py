# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None


class Solution:
    def swapPairs(self, head: ListNode) -> ListNode:
        if not head or not head.next:
            return head

        prev = None
        new_head = head.next
        node = head

        while node:
            swap_this = node
            swap_that = node.next
            next = swap_that.next if swap_that else None

            if prev and swap_that:
                prev.next = swap_that

            if swap_that:
                swap_that.next = swap_this
                swap_this.next = next
                prev = node
                node = next
            else:
                node = None

        return new_head


# create list
head = ListNode(1)
head.next = ListNode(2)
head.next.next = ListNode(3)
head.next.next.next = ListNode(4)
head.next.next.next.next = ListNode(5)
head.next.next.next.next.next = ListNode(6)

# print solution
solution = Solution().swapPairs(head)
n = solution
solution_str = ""
while n:
    solution_str += str(n.val) + " "
    n = n.next

print(solution_str)
