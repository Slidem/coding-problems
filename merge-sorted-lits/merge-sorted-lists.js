
/**
 * @param {ListNode} l1
 * @param {ListNode} l2
 * @return {ListNode}
 */

function ListNode(val) {
    this.val = val;
    this.next = null;
}

var mergeTwoLists = function (l1, l2) {

    if (!l1) {
        return l2;
    } else if (!l2) {
        return l1;
    }

    let lRoot;

    if (l1.val < l2.val) {
        lRoot = new ListNode(l1.val);
        l1 = l1.next;
    } else {
        lRoot = new ListNode(l2.val);
        l2 = l2.next;
    }

    let l = lRoot;

    while (l1 && l2) {
        let toAdd;
        if (l1.val < l2.val) {
            toAdd = new ListNode(l1.val);
            l1 = l1.next;
        } else {
            toAdd = new ListNode(l2.val);
            l2 = l2.next;
        }
        l.next = toAdd;
        l = l.next;
    }

    let remaining = l1 ? l1 : l2;

    while (remaining) {
        l.next = new ListNode(remaining.val);
        l = l.next;
        remaining = remaining.next;
    }

    return lRoot;
};

let l1 = new ListNode(1);
l1.next = new ListNode(2);
l1.next.next = new ListNode(4);

let l2 = new ListNode(1);
l2.next = new ListNode(3);
l2.next.next = new ListNode(4);

let result = mergeTwoLists(l1, l2);

let resultStr = "";

while (result) {
    resultStr += result.val + " ";
    result = result.next;
}

console.log(resultStr);
