import java.util.ArrayList;

public class Parser {
    ArrayList<Token> tokens; // цепочка токенов из лексера

    int pos_parser = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public Token match(String[] need) { // функция по текущей позиции возвращает токен с определенным типом из списка
        if (pos_parser < tokens.size()) { //если позиция меньше длины массива с токенами
            Token currentToken = tokens.get(pos_parser); // извлекаем токен по индексу равный текущей позиции
            // ищем необходимый тип токена
            for (String tokenTypeName : need)
                if (tokenTypeName.equals(currentToken.type.name)) { // если токен содержит тот тип, который передали
                    pos_parser++;
                    return currentToken;
                }
        }
        return null;
    }

    public void require(String[] expected) { //ищем ожидаемый токен
        Token token = match(expected);
        if (token == null) {
            throw new Error("На позииции " + pos_parser + " ожидается токен " + expected[0]);
        }
    }

    //начинаем парсить код
    //парсинг кода
    public StatementsNode parseCode() {
        StatementsNode root = new StatementsNode();
        while (pos_parser < tokens.size()) {
            MainNode codeString = parseExpression();
            require(new String[]{"SEMICOLON"});
            root.addNode(codeString);
        }
        return root;
    }

    //парсинг отдельно взятой строки
    public MainNode parseExpression(){
        //первые два элемента возможных при парсинге - переменная или принт
        //проверим сначала переменная ли попалась в строке
        if (tokens.get(pos_parser).type.name.equals("VARIABLE")) {
            //первой попалась переменная, парсим ее
            MainNode variableNode = parseVariableOrNumber();
            Token assign = match(new String[]{"ASSIGN"});
            if (assign != null) {
                MainNode rightVal = parseFormula();
                return new BinOperationNode(assign, variableNode, rightVal);
            }
            throw new Error("После переменной ожидается оператор присвоения на позиции " + this.pos_parser);
        }
        //если не переменная - значит там принт
        else if (tokens.get(pos_parser).type.name.equals("PRINT")) {
            pos_parser++;
            return new UnarOperationNode(tokens.get(pos_parser-1), this.parseFormula());
        }
        else if (tokens.get(pos_parser).type.name.equals("WHILE")) {
            pos_parser++;
            return parseWhile();
        }
        else if (tokens.get(pos_parser).type.name.equals("FOR")) {
            pos_parser++;
            return parseFor();
        }
        else if (tokens.get(pos_parser).type.name.equals("IF")) {
            pos_parser++;
            return parseIf();
        }
        throw new Error("Ожидалось действие или переменная на позиции: " + pos_parser);
    }

    public MainNode parseVariableOrNumber() { // простейший случай формулы
        if (tokens.get(pos_parser).type.name.equals("DIGIT")){
            pos_parser++;
            return new DigitNode(tokens.get(pos_parser-1)); // тк при парсинге токен преобразуем в ноду
        }
        if (tokens.get(pos_parser).type.name.equals("VARIABLE")){
            pos_parser++;
            return new VariableNode(tokens.get(pos_parser-1));
        }

        throw new Error("Ожидалась переменная или число на позиции " + pos_parser);
    }

    public MainNode parseFormula(){
        MainNode leftVal = parseParentheses();//нода - переменная или число
        Token operator = match(new String[]{"PLUS","MINUS","DEGREE"});//после ноды - оператор
        while (operator != null){//крутимся пока есть операторы
            MainNode rightVal = parseParentheses();//есть левая нода и оператор - будет и правая
            leftVal = new BinOperationNode(operator, leftVal, rightVal); //рекурсивно один узел под другой становится (пр: плюс, снизу числа)
            operator = match(new String[]{"PLUS","MINUS", "DEGREE"});
        }
        return leftVal;
    }

    public MainNode parseParentheses() { // для парсинга скобок
        if (tokens.get(pos_parser).type.name.equals("L_BC")) { // если левая открывающаяся скобка
            pos_parser++;
            //если встретилась открывающая скобка, значит, там формула
            MainNode node = parseFormula(); // рекурсивно распарсится формула
            //после парсинга формулы самой идет закрывающая скобка
            require(new String[]{"R_BC"});
            return node;
        }
        else //если левой скобки нет, то значит, там какая-то переменная\число
            return parseVariableOrNumber();
    }

    public MainNode parseFor() {
        MainNode leftVal = parseFormula();
        Token operator = match(new String[]{"LESS", "MORE", "EQUAL"});
        MainNode rightVal = parseFormula();
        require(new String[]{"SEMICOLON"});
        MainNode varNode = parseVariableOrNumber();
        Token assign = match(new String[]{"ASSIGN"});
        MainNode rightActVal = parseFormula();
        BinOperationNode action = new BinOperationNode(assign, varNode, rightActVal);
        if (assign == null)
            throw new Error("После переменной ожидается '=' на позиции:" + pos_parser);
        ForNode forNode = new ForNode(operator, leftVal, rightVal, action);
        require(new String[]{"L_SQR_BC"});
        while(!tokens.get(pos_parser).type.name.equals("R_SQR_BC")) {
            forNode.addOperations(getOperations());
            if (pos_parser == tokens.size())
                throw new Error("Ошибка, ожидалось '}'");
        }
        pos_parser++;
        return forNode;
    }

    public MainNode parseWhile() {
        MainNode leftVal = parseFormula();
        Token operator = match(new String[]{"LESS", "MORE", "EQUAL"});
        MainNode rightVal = parseFormula();
        WhileNode whileNode = new WhileNode(operator, leftVal, rightVal);
        require(new String[]{"L_SQR_BC"});
        while (!tokens.get(pos_parser).type.name.equals("R_SQR_BC")) {
            whileNode.addOperations(getOperations());
            if (pos_parser == tokens.size())
                throw new Error("Ожидалось '}' на позиции" + pos_parser);
        }
        pos_parser++;
        return whileNode;
    }

    public MainNode parseIf() {
        MainNode leftVal = parseFormula();
        Token operator = match(new String[]{"LESS", "MORE", "EQUAL"});
        MainNode rightVal = parseFormula();
        IfNode ifNode = new IfNode(operator, leftVal, rightVal);
        require(new String[]{"L_SQR_BC"});
        while (!tokens.get(pos_parser).type.name.equals("R_SQR_BC")) {
            ifNode.addOperations(getOperations());
            if (pos_parser == tokens.size())
                throw new Error("Ожидалось '}' на позиции" + pos_parser);
        }
        pos_parser++;
        return ifNode;
    }

    public MainNode getOperations() {
        MainNode codeStringNode = parseExpression();
        require(new String[]{"SEMICOLON"});
        return codeStringNode;
    }
}
