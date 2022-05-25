public class BinOperationNode extends MainNode{
    Token operator;//ПЛЮС МИНУС РАВНО
    MainNode leftNode;//левый операнд (переменная число)
    MainNode rightNode;

    public BinOperationNode(Token operator, MainNode leftNode, MainNode rightNode) {
        super();
        this.operator = operator;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }
}
