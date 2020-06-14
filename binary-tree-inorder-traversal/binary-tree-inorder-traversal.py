# Definition for a binary tree node.
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right
class Solution:
    def inorderTraversal(self, root: TreeNode):
        """ In order traversal of a binary tree
            Prints the values of the binary tree in order
            Iterative solution using a stack
        """
        in_order = []
        stack = []
        stack.append(root)
        while stack:
            node = stack[-1]
            if not node.left:
                in_order.append(node.val)
                stack.pop()
                if node.right:
                    stack.append(node.right)
            else:
                stack.append(node.left)
                node.left = None
        return in_order


root = TreeNode(3) 
left = TreeNode(1)
left_right = TreeNode(2)
root.left = left
left.right = left_right

print(Solution().inorderTraversal(root))