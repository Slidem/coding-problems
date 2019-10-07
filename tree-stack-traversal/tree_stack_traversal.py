class Node:

    def __init__(self, value, children):
        self.__dependency_resolved = False
        self.__value = value
        self.__resolved_children = []
        self.__children = children.copy() if children else []

    def add(self, child):
        if not child:
            raise ValueError("Cannot add a null child")
        self.__children.append(child)

    def child_resolved(self, resolved_child):
        if resolved_child:
            self.__resolved_children.append(resolved_child)
            if len(self.__resolved_children) == len(self.__children):
                self.__dependency_resolved = True

    @property
    def children(self):
        return self.__children

    @property
    def value(self):
        return self.__value

    @property
    def resolved_children(self):
        return self.__resolved_children

    @property
    def dependencies_resolved(self):
        return self.__dependency_resolved


class ResolvedChild:
    def __init__(self, node):
        self.__value = node.value

    def __str__(self):
        return "resolved: " + self.__value


class NodeTraversal:
    def __init__(self, node, prev):
        self.node = node
        self.prev = prev


# This is a simple tree traversal algorithm, that performs a DFS, visiting the leaf nodes first,
# than coming up the recursive call and solving the parent node of the visited leaf nodes, and so on.
# this can be used to resolve a dependency tree, where you would want to :
# - first resolve dependencies that don't have any other dependencies (leaf nodes)
# - than imediatle solve the next dependency for which the leaf dependencies have just been resolved, and so on
# The nice thing about this algorithm, is that is uses a stack instead of a recursive call
class LeafNodesTraversal:

    def __init__(self, root, node_resolved_callback):
        if not root:
            raise ValueError("Root cannot be empty")
        self.__root = root
        self.__node_resolved_callback = node_resolved_callback

    def traverse(self):

        stack = []
        stack.append(NodeTraversal(self.__root, None))

        while stack:
            # peek stack
            node_traversal = stack[-1]
            if not node_traversal.node.children or node_traversal.node.dependencies_resolved:
                self.__node_resolved_callback(node_traversal.node)
                if node_traversal.prev:
                    node_traversal.prev.child_resolved(
                        ResolvedChild(node_traversal.node))
                stack.pop()
            else:
                for child in node_traversal.node.children:
                    stack.append(NodeTraversal(child, node_traversal.node))


n_g = Node("G", None)
n_h = Node("H", None)
n_f = Node("F", [n_g, n_h])
n_e = Node("E", None)
n_c = Node("C", [n_e, n_f])
n_b = Node("B", None)
n_d = Node("D", None)
n_a = Node("A", [n_b, n_c, n_d])

node_traversal = LeafNodesTraversal(
    n_a, lambda n: print("Depdendencies resolved: " + n.value))

node_traversal.traverse()
print(*n_a.resolved_children, sep=",")
print(*n_c.resolved_children, sep=",")
print(*n_f.resolved_children, sep=",")
