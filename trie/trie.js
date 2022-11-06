var Node = function(val) {
    this.val = val;
    this.hasWord = false;
    this.children = {};
}

function createRootNode() {
    return new Node("")
}


Node.prototype.addChild = function(node) {
    this.children[node.val] = node
}

var Trie = function() {
    this.root = createRootNode();
};


Trie.prototype.insert = function(word) {
    var n = this.root;
    for (var i = 0; i < word.length; i++) {
        var k = word[i];
        var child = null;
        if (k in n.children) {
            child = n.children[k];
        } else {
            child = new Node(k);
            n.addChild(child);
        }
        n = child;
    }
    n.hasWord = true
};


Trie.prototype.search = function(word) {
    var n = this.root
    for (var i = 0; i < word.length; i++) {
        if(word[i] in n.children) {
            n = n.children[word[i]]
        } else {
            return false;
        }
    }
    return n.hasWord && n.val !== ""
};


Trie.prototype.startsWith = function(prefix) {
    var n = this.root
    for (var i = 0; i < prefix.length; i++) {
        if(prefix[i] in n.children) {
            n = n.children[prefix[i]]
        } else {
            return false;
        }
    }
    return true;
};

