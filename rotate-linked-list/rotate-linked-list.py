class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None


class Solution:

    def rotateRight(self, head, k):

        if not head.next:
            return head

        list_len = self.list_len(head)
        memo = {0: self.copy_list(head)}

        for i in range(min(k, list_len)):
            s = head
            prev = head
            n = prev.next

            while n.next:
                prev = n
                n = n.next

            n.next = head
            prev.next = None
            head = n
            memo[i + 1] = self.copy_list(head)
        
        return memo[k % list_len]

    def list_len(self, root):
        l = 0
        n = root
        while n:
            l += 1
            n = n.next

        return l
    
    def copy_list(self, root):
        if not root:
            return
        
        if not root.next:
            return ListNode(root.val)
        

        n = root
        new_root = ListNode(n.val)
        new = new_root
        n = n.next
        
        while n:
           new.next = ListNode(n.val)
           new = new.next
           n = n.next

        return new_root
        
def print_list(root):
    list_str = ""
    n = root
    while n:
        list_str += str(n.val) + " "
        n = n.next

    print(list_str)


def create_list(values):

    root = ListNode(values[0])
    n = root

    for i in range(1, len(values)):
        n.next = ListNode(values[i])
        n = n.next

    return root


l = create_list([0, 1, 2])
print_list(l)
print_list(Solution().rotateRight(l, 4))
