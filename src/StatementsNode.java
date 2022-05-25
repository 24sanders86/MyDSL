import java.util.ArrayList;

//корневой узел, хранит строки кода
public class StatementsNode extends MainNode {
    ArrayList<MainNode> codeStrings = new ArrayList<>();//элемент массива - строчка кода
    public void addNode(MainNode node){
        codeStrings.add(node);
    }
}
