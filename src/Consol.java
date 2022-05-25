public class Consol {
    static String testcode = "a=10;\n" +
            "print a;\n" +
            "b = (115 / 5*(a-2));\n" + //184
            "printed b;\n" +
            "printed a+b;\n" + //194
            "s = 5**3;\n" +
            "printed s;\n" + //125
            "i=16;\n" +
            "rez = 16;\n" +
            "for i<26;i=i+6 {\n" +
            "    printed i/2;\n" + //8,11
            "};" +
            "j = 2*8-30;\n" + //-14
            "if j<rez {\n" +
            "   printed rez;\n" +
            "};\n" +
            "while j<rez {\n" +
            "    printed j;\n" + //-14,-3, 8
            "    j=j+11;\n" +
            "};\n";

    public static void main(String[] args) {
        Lexer lexer = new Lexer(testcode);
        // System.out.println("______________________");
        System.out.println("______________________________");
    }
}
