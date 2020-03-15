class Solution:

    def __init__(self):
        self.separator = "/"
        self.current_dir = "."
        self.prev_dir = ".."
    
    def simplifyPath(self, path):

        if not path:
            return path

        stack = [] 

        # path object represents either a dir, the . or the .. sign
        path_object = ""

        for c in path:
            if c == self.separator:
                self.evaluate_path_object(stack, path_object)
                path_object = ""
            else:
                path_object += c

        # evaluate remaining
        if path_object: 
           self.evaluate_path_object(stack, path_object)
        
        return "/" + "/".join(stack)


    def evaluate_path_object(self, stack, path_object):
        if path_object == self.prev_dir:
            if stack:
                stack.pop()
            
        elif path_object != "" and path_object != self.current_dir:
            stack.append(path_object)


print(Solution().simplifyPath("/../"))
           