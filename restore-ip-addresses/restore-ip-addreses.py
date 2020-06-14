class Solution:
    def restoreIpAddresses(self, s):
        if not s or len(s) < 4:
            return []

        self.s = s
        self.s_len = len(s)
        self.solution = []
        self.compute("", 0, 4)
        return self.solution


    def compute(self, ip_address, ci, remaining_groups):
        
        if ci > self.s_len:
            return
        
        if remaining_groups == 0:
            
            if ci == self.s_len :
                self.solution.append(ip_address)


        self.verify_next_byte_and_compute(ip_address, ci, ci + 1, remaining_groups)
        self.verify_next_byte_and_compute(ip_address, ci, ci + 2, remaining_groups)
        self.verify_next_byte_and_compute(ip_address, ci, ci + 3, remaining_groups)
    

    def verify_next_byte_and_compute(self, ip_address, from_idx, to_idx, remaining_groups):
        
        if remaining_groups * 3 < self.s_len - 1 - from_idx:
            return
        
        if to_idx > self.s_len:
            return

        number_str = self.s[from_idx:to_idx] 
        number = int(number_str)
        
        if (len(number_str) > 1 and number_str[0] == '0') or number > 255:
            return
        
        new_ip_address = ip_address + ("." if ip_address else "") + number_str
        
        self.compute(new_ip_address, to_idx, remaining_groups - 1)

print(Solution().restoreIpAddresses("25525511135"))

        
