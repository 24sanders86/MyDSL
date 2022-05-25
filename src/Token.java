public class Token {
    TypeOfToken type; //тип токена (переменная, число и тд)
    String text; //число, название переменной и тд
    int position; // номер позиции в коде (для вывода ошибок)

    //конструктор для удобного создания объектов из этого класса со всеми необходимыми полями
    public Token(TypeOfToken type, String text, int position) {
        this.type = type;
        this.text = text;
        this.position = position;
    }
}
