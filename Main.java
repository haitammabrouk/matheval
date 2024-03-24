import java.util.List;

class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Main <mathematical_expression>");
            System.exit(1);
        }

        String expression = args[0];
        Scanner sc = new Scanner(expression);
        List<Token> scanExp = sc.scan();
        Parser parser = new Parser(scanExp);
        List<Token> parsed = parser.parse();
        System.out.println(sc.eval(parsed));
    }
}
