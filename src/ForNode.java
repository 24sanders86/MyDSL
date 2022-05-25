import java.util.ArrayList;

public class ForNode extends MainNode {
    Token operator;
    MainNode leftNode;
    MainNode rightNode;
    MainNode action;
    public ArrayList<MainNode> operations = new ArrayList<>();

    public ForNode(Token operator, MainNode leftNode, MainNode rightNode, MainNode action) {
        this.operator = operator;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.action = action;
    }
    public void addOperations(MainNode op){
        operations.add(op);
    }
}
