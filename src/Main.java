import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import parser.*;
import converter.LatexVisitor;

public class Main {
    public static void main(String[] args) {
        // Przykładowe wejście: układ równań z różnymi funkcjami
        String input = "(1 + 2) * sqrt(4); |x+1| = 3; sin(0) + log(10) >= y";

        // Tworzenie strumienia znaków z tekstu
        CharStream cs = CharStreams.fromString(input);

        // Tworzenie analizatora leksykalnego i parsera
        MathExprLexer lexer = new MathExprLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathExprParser parser = new MathExprParser(tokens);

        // Uruchomienie parsera od reguły 'prog' (czyli całość)
        ParseTree tree = parser.prog();

        // Użycie odwiedzającego do wygenerowania LaTeX-a
        LatexVisitor visitor = new LatexVisitor();
        String latex = visitor.visit(tree);

        // Wyświetlenie wyniku
        System.out.println("LaTeX:\n" + latex);
    }
}







/*
import org.antlr.v4.runtime.*;
        import org.antlr.v4.runtime.tree.*;
        import parser.*;
        import converter.LatexVisitor;

public class Main {
    public static void main(String[] args) {
        // Przykładowe wejście — możesz potem zmienić na to, co wpisuje użytkownik
        String input = "(1 + 2) * sqrt(4)";

        // Tworzenie strumienia znaków i uruchomienie parsera
        CharStream cs = CharStreams.fromString(input);
        MathExprLexer lexer = new MathExprLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathExprParser parser = new MathExprParser(tokens);
        ParseTree tree = parser.expr();

        // Uruchomienie odwiedzającego, który generuje kod LaTeX
        LatexVisitor visitor = new LatexVisitor();
        String latex = visitor.visit(tree);

        // Wypisanie kodu LaTeX
        System.out.println("LaTeX: " + latex);
    }
}
*/