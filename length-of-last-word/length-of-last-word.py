# Given a string s consists of upper/lower-case alphabets and empty space characters ' ',
#  return the length of last word (last word means the last appearing word if we loop from left to right) in the string.

# If the last word does not exist, return 0.

# Note: A word is defined as a maximal substring consisting of non-space characters only.


def length_of_last_word(s):
    l = 0

    word_counting_started = False

    for c in reversed(s):

        if c != ' ' and not word_counting_started:
            word_counting_started = True

        if c == ' ' and word_counting_started:
            return l

        if c != ' ':
            l += 1

    return l


print(length_of_last_word("Hello world!"))
