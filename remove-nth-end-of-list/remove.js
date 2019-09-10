// Given a linked list, remove the n-th node from the end of list and return its head.


function ListNode(val) {
    this.val = val;
    this.next = null;
}

/**
 * @param {ListNode} head
 * @param {number} n
 * @return {ListNode}
 */
function removeNthFromEnd(head, n) {

    let s = head;
    let prev = null;
    let runner = head;

    for (let i = 0; i < n; i++) {
        runner = runner.next;
    }

    while (runner) {
        prev = s;
        s = s.next;
        runner = runner.next;
    }

    if (s == head) {
        return s.next;
    }

    prev.next = s.next;

    return head;
}


function createLinkedList(arr) {
    let n = new ListNode(arr[0]);
    let head = n;
    for (let i = 1; i < arr.length; i++) {
        n.next = new ListNode(arr[i]);
        n = n.next;
    }
    return head;
}

function printLinkedList(head) {
    let n = head;
    let list = "";
    while (n) {
        list += (n.val + " ");
        n = n.next;
    }
    console.log(list);
}


// test
printLinkedList(
    removeNthFromEnd(
        createLinkedList([1, 2, 3, 4, 5]),
        5
    )
);