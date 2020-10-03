def compute_bsts(n):
    memo = {}
    return compute(n, {}) 

def compute(nodes, memo):
    if nodes <= 1:
        return 1 
    
    key = str(nodes)
    if key in memo:
        return memo[key]

    ways = 0 
    for i in range(1, nodes + 1):
        ways += (compute(i - 1, memo) * compute(nodes - i, memo)) 
    
    memo[key] = ways
    return ways
    

print(compute_bsts(5))
    