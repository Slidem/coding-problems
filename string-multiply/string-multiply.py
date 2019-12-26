from collections import deque
import copy


def multiply(num1: str, num2: str) -> str:
    if num1 == "0" or num2 == "0":
        return "0"

    multiplication_rows_container = MultiplicationRowsContainer()
    for d in reversed(num1):
        row = DigitAndStringMultiplicationHolder(int(d), num2)
        multiplication_rows_container.add_and_compute_for_existing_rows(row)
    multiplication_rows_container.compute_remaining()

    return str(multiplication_rows_container)


class MultiplicationRowsContainer():

    def __init__(self):
        self.rows_iterators = []
        self.result = deque()
        self.result_str = ""
        self.carry = 0
        self.computation_finished = False

    def add_and_compute_for_existing_rows(self, row):
        if self.computation_finished:
            raise Exception(
                "Computation finished; nothing left to do with this class but only to use the result")
        self.rows_iterators.append(iter(row))
        self.compute_column()

    def compute_remaining(self):
        if self.computation_finished:
            return

        while not self.computation_finished:
            self.compute_column()

    def compute_column(self):
        at_least_something_was_computed = False
        computed_value = 0
        for row_iterator in self.rows_iterators:
            value = next(row_iterator)
            if value != None:
                at_least_something_was_computed = True
                computed_value += value

        if not at_least_something_was_computed:
            self.computation_finished = True

            if self.carry and self.carry > 0:
                self.result.appendleft(self.carry)
                self.carry = 0

            self.result_str = "".join([str(d) for d in self.result])

            return

        computed_value += self.carry
        self.carry = computed_value // 10
        self.result.appendleft(computed_value % 10)

    def __str__(self):
        return self.result_str


class DigitAndStringMultiplicationHolder:

    def __init__(self, digit: int, string: str):
        self.multiplication = self.__multiply(digit, string)

    def __iter__(self):
        self.multiplication_copy = copy.copy(self.multiplication)
        return self

    def __next__(self):
        if len(self.multiplication_copy) > 0:
            return self.multiplication_copy.popleft()
        else:
            return None

    def __multiply(self, digit, string):

        carry = 0
        result = deque()

        for multiply_with in reversed(string):
            digit_multiplication = int(multiply_with) * digit + carry
            carry = digit_multiplication // 10
            to_add = digit_multiplication % 10
            result.append(to_add)

        if carry > 0:
            result.append(carry)

        return result

    def __str__(self):
        return "".join([str(d) for d in self.multiplication])

# Test methods ........................
# thest for the DigitAndStringMultiplicationHolder class


def test_digit_string_multiplication_computation():

    digit = 3
    string = "224"
    print(DigitAndStringMultiplicationHolder(digit, string))


def test_digit_string_multiplication_iterator():

    digit = 3
    string = "224"

    result = DigitAndStringMultiplicationHolder(digit, string)
    for r in result:
        print(r)


# test_digit_string_multiplication_computation()
# test_digit_string_multiplication_iterator()

# test multiply method
print(multiply("100", "25"))
