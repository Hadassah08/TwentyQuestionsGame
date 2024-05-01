public class TwentyQuestionsNode {
    // Instance variables to hold item and references to child nodes
    public String item;
    public TwentyQuestionsNode yes;
    public TwentyQuestionsNode no;

    // Constructor to create a leaf node with no child nodes
    public TwentyQuestionsNode(String item) {
        this(item, null, null);
    }

    // Constructor to create a branch node with specified item and child nodes
    public TwentyQuestionsNode(String item, TwentyQuestionsNode yes, TwentyQuestionsNode no) {
        this.item = item;
        this.yes = yes;
        this.no = no;
    }

    // Method to check if the node is a leaf node (has no child nodes)
    public boolean isitLeaf() {
        return yes == null && no == null;
    }
}
