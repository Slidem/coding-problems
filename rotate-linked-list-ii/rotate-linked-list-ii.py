# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next
    
    def __str__(self):
        return str(self.val)

class Solution:
    def reverseBetween(self, head, m, n):
        
        if m == n:
            return head
        
        i = 1 
        target_head = head
        prev_target_head = None
        while i < m:
           prev_target_head = target_head
           target_head = target_head.next 
           i += 1
       
        node = target_head 
        prev_head = node if m > 1 else None 
        
        while i < n: 
            target_head = node.next     
            node.next = node.next.next 
            target_head.next = prev_head if prev_head else node
            prev_head = target_head
            i += 1
        
        if prev_target_head:
            prev_target_head.next = target_head
     
        return head if m > 1 else target_head

# Helper functions for testing
def print_from_head(head):

    list_str = ""
    n = head
    while n:
        list_str += str(n.val)
        if n.next:
            list_str += ", "
        n = n.next
    print(list_str)

def create_from_list(l):
    n = None
    head = None
    for num in l:
        new_node = ListNode(num, None)
        if n:
            n.next = new_node
        else:
            head = new_node
        n = new_node
    return head

# simple test
head = create_from_list([1, 2, 3, 4, 5])
print_from_head(Solution().reverseBetween(head, 3, 4))
