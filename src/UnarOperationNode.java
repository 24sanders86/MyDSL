public class UnarOperationNode extends MainNode{
    Token operator;
    MainNode operand;

    public UnarOperationNode(Token operator, MainNode operand) {
        this.operator = operator;
        this.operand = operand;
    }
}
