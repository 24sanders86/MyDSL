import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    String code; // сам код, прилетающий в лексер
    int pos_lex = 0; // позиция, в которой находится лексер на текущий момент
    ArrayList<Token> tokenList = new ArrayList<>(); // сами список токенов

    public Lexer(String code) {
        this.code = code;
    }

    //функция лексического анализа
    public ArrayList<Token> lexAnalysis() { //пока есть токены, ...
        while (nextToken()) {}
        for (Token token : tokenList)
            if (!(token.type.name.equals("SPACE") || token.type.name.equals("ENDLINE"))) // если токен не пробел и не перенос строки
                System.out.println("[Токен: " + token.type.name + ", значение: " + token.text + ", позиция в коде: " + token.position + "]");
        return this.tokenList;
    }

    //функция перебора токенов один за одним
    public boolean nextToken() {
        TypeOfToken[] allTokenTypes = TypeOfToken.tokenTypeList; // все значения из списка типа токенов
        if (this.pos_lex >= code.length()) { //если текущая позиция больше длины кода, то завершаем
            return false;
        }
        for (int i = 0; i < allTokenTypes.length; i++) { //берем все токены
            TypeOfToken tokenType = allTokenTypes[i];
            String regex = tokenType.regex;
            //pattern - скомпилированное представление регулярки
            //для создания объекта - статич метод компайл, аргумент - регулярка
            //matcher - класс, который представляет строку, реализует механизм согласования с РВ
            // и хранит результаты этого согласования
            Matcher matcher = Pattern.compile(regex).matcher(code);
            //find - ищет подстроку, кот. удовлетворит регулярке
            //start - для поиска с нужной позиции
            if (matcher.find(this.pos_lex) && matcher.start() == this.pos_lex) {
                //substring - возвращает подстроку строки от а до б
                String result = this.code.substring(this.pos_lex, this.pos_lex + matcher.group().length());
                Token token = new Token(tokenType, result, this.pos_lex);
                this.pos_lex += result.length();
                //if (token.type != TypeOfToken.tokenTypeList[18] && token.type != TypeOfToken.tokenTypeList[4] && token.type != TypeOfToken.tokenTypeList[1])
                    tokenList.add(token);
                return true;
            }
        }
        throw new Error("Ошибка на позиции: " + this.pos_lex);
    }
}
