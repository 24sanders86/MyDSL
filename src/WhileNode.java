import java.util.ArrayList;

public class WhileNode extends MainNode {
    Token operator;
    MainNode leftNode;
    MainNode rightNode;
    public ArrayList<MainNode> operations = new ArrayList<>();

    public WhileNode(Token operator, MainNode leftNode, MainNode rightNode) {
        this.operator = operator;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }
    public void addOperations(MainNode op){
        operations.add(op);
    }
}
