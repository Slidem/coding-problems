class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    def generateTrees(self, n):

        if n <= 0:
            return []

        if n == 1:
            return [TreeNode(1)]

        return self.generate([x for x in range(1, n + 1)])

    def generate(self, ordered_values):

        computed = []        

        for idx, v in enumerate(ordered_values):
            pivot = TreeNode(v)
            computed_on_left = self.generate(ordered_values[0:idx])
            computed_on_right = self.generate(ordered_values[idx+1:])
            if not computed_on_left and computed_on_right:
                computed += self.combinations_with(go_right, pivot, computed_on_right)
            elif not computed_on_right and computed_on_left:
                computed += self.combinations_with(go_left, pivot, computed_on_left)
            elif computed_on_left and computed_on_right:
                computed += self.full_combinations(pivot, computed_on_left, computed_on_right)
            else:
                computed += [pivot]
        
        return computed
                

    def combinations_with(self, direction, node, combine_with_nodes):

        if not node:
            return combine_with_nodes

        combinations = []
        
        for n in combine_with_nodes:
            cpy = TreeNode(node.val)
            direction(cpy, n)
            combinations.append(cpy)
        
        return combinations
    
    def full_combinations(self, node, left, right):
        
        combinations = []
        
        for l in left: 
            for r in right:
                cpy = TreeNode(node.val)
                cpy.left = l  
                cpy.right = r
                combinations.append(cpy)

        return combinations 

def go_right(node, child):
    node.right = child

def go_left(node, child):
    node.left = child


# test methods
def print_pre_order(root):
    print(generate_pre_order_list(root))


def generate_pre_order_list(root):
    l = []
    add_pre_order_into_list(root, l)
    return l


def add_pre_order_into_list(n, l):
    if not n:
        l.append(None)
        return

    l.append(n.val)
    add_pre_order_into_list(n.left, l) 
    add_pre_order_into_list(n.right, l)


generated_values = Solution().generateTrees(3)
for g in generated_values:
    print_pre_order(g)
