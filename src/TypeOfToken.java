public class TypeOfToken {
    String name; // название типа токена
    String regex; // регулярное выражение

    public TypeOfToken(String name, String regex) {
        this.name = name;
        this.regex = regex;
    }

    //описание ВСЕХ типов токенов
    public static TypeOfToken[] tokenTypeList={
            new TypeOfToken("DIGIT", "^0|[1-9][0-9]*"),

            new TypeOfToken("VARIABLE", "[a-z]+"),

            new TypeOfToken("SEMICOLON", "\\;"),
            new TypeOfToken("SPACE", "\\ "),
            new TypeOfToken("ASSIGN", "\\="),
            new TypeOfToken("EQUAL", "(==)"),

            new TypeOfToken("PLUS", "\\+"),
            new TypeOfToken("MINUS", "\\-"),
            new TypeOfToken("MULTIPLICATION", "\\*"),
            new TypeOfToken("DEGREE", "(\\^"),
            new TypeOfToken("DIVISION", "\\/"),

            new TypeOfToken("L_BC", "\\("),
            new TypeOfToken("R_BC", "\\)"),
            new TypeOfToken("L_SQR_BC", "\\{"),
            new TypeOfToken("R_SQR_BC", "\\}"),

            new TypeOfToken("LESS", "\\<"),
            new TypeOfToken("MORE", "\\>"),

            new TypeOfToken("ENDLINE", "\\n"),

            new TypeOfToken("PRINT", "(print)"),

            new TypeOfToken("FOR", "(for)"),
            new TypeOfToken("IF", "(if)"),
            new TypeOfToken("WHILE","(while)"),
    };
}
