package converter;

// Importy klas ANTLR i parsera

// 📌 Importy do obsługi StringTemplate
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import parser.MathExprBaseVisitor;
import parser.MathExprParser;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class LatexVisitor extends MathExprBaseVisitor<String> {

    // Wczytanie pliku z szablonami LaTeX (StringTemplate)
    private final STGroup templates = new STGroupFile("converter/latex.stg");

    private static final Map<String, String> GREEK_LETTERS = Map.ofEntries(
            Map.entry("alfa", "\\alpha"),
            Map.entry("beta", "\\beta"),
            Map.entry("gamma", "\\gamma"),
            Map.entry("delta", "\\delta"),
            Map.entry("epsilon", "\\epsilon"),
            Map.entry("zeta", "\\zeta"),
            Map.entry("eta", "\\eta"),
            Map.entry("theta", "\\theta"),
            Map.entry("iota", "\\iota"),
            Map.entry("kappa", "\\kappa"),
            Map.entry("lambda", "\\lambda"),
            Map.entry("mu", "\\mu"),
            Map.entry("nu", "\\nu"),
            Map.entry("xi", "\\xi"),
            Map.entry("omicron", "\\omicron"),
            Map.entry("pi", "\\pi"),
            Map.entry("rho", "\\rho"),
            Map.entry("sigma", "\\sigma"),
            Map.entry("tau", "\\tau"),
            Map.entry("upsilon", "\\upsilon"),
            Map.entry("phi", "\\phi"),
            Map.entry("chi", "\\chi"),
            Map.entry("psi", "\\psi"),
            Map.entry("omega", "\\omega"),
            // wielkie litery
            Map.entry("Gamma", "\\Gamma"),
            Map.entry("Delta", "\\Delta"),
            Map.entry("Theta", "\\Theta"),
            Map.entry("Lambda", "\\Lambda"),
            Map.entry("Xi", "\\Xi"),
            Map.entry("Pi", "\\Pi"),
            Map.entry("Sigma", "\\Sigma"),
            Map.entry("Upsilon", "\\Upsilon"),
            Map.entry("Phi", "\\Phi"),
            Map.entry("Psi", "\\Psi"),
            Map.entry("Omega", "\\Omega")
    );

    // 📌 Obsługa wielu równań oddzielonych średnikiem (reguła 'prog')
    @Override
    public String visitProg(MathExprParser.ProgContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ctx.statement().size(); i++) {
            sb.append(visit(ctx.statement(i)));
            if (i < ctx.statement().size() - 1) {
                sb.append("\n"); // zmieniłam - bo wcześniej było "//" które znajdowało się też w środku macierzy
            }
        }
        String result = sb.toString();
        System.out.println("DEBUG: Prog zwraca:\n" + result);
        return result;
        //return sb.toString();
    }


    // 📌 Obsługa całego wyrażenia (z relacją logiczną lub bez)
    @Override
    public String visitExpressionStmt(MathExprParser.ExpressionStmtContext ctx) {
        if (ctx.relop() != null) {
            String left = visit(ctx.expr(0));
            String right = visit(ctx.expr(1));
            String op = visit(ctx.relop());
            return left + " " + op + " " + right;
        } else {
            return visit(ctx.expr(0));
        }
    }

    // 📌 Obsługa dodawania i odejmowania przez szablony StringTemplate
    // ✅ Użycie getType() do rozróżnienia operatorów
    @Override
    public String visitAddSubExpr(MathExprParser.AddSubExprContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        ST st = (ctx.op.getType() == MathExprParser.ADD)
                ? templates.getInstanceOf("add")
                : templates.getInstanceOf("sub"); // ← wybór szablonu
        st.add("left", left);
        st.add("right", right);
        return st.render();
    }

    // 📌 Obsługa mnożenia i dwóch rodzajów dzielenia
    // ✅ getType() użyty do rozpoznania operatora
    // ✅ Użycie 3 szablonów: mul, divFrac, divSlash
    @Override
    public String visitMulDivExpr(MathExprParser.MulDivExprContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        ST st;
        switch (ctx.op.getType()) {
            case MathExprParser.MUL:
                st = templates.getInstanceOf("mul");
                break;
            case MathExprParser.DIV:
                st = templates.getInstanceOf("divFrac"); // ← piętrowy ułamek
                break;
            case MathExprParser.COLON:
                st = templates.getInstanceOf("divSlash"); // ← zwykły ukośnik
                break;
            default:
                throw new RuntimeException("Nieznany operator mnożenia/dzielenia!");
        }
        st.add("left", left);
        st.add("right", right);
        return st.render();
    }

    // 📌 Potęgowanie przez szablon
    @Override
    public String visitPowExpr(MathExprParser.PowExprContext ctx) {
        ST st = templates.getInstanceOf("pow");
        st.add("base", visit(ctx.expr(0)));
        st.add("exponent", visit(ctx.expr(1)));
        return st.render();
    }

    // 📌 Wartość bezwzględna
    @Override
    public String visitAbsExpr(MathExprParser.AbsExprContext ctx) {
        ST st = templates.getInstanceOf("abs");
        st.add("value", visit(ctx.expr()));
        return st.render();
    }

    // 📌 Funkcje (np. sin, cos, sqrt) przez szablon
    @Override
    public String visitFuncExpr(MathExprParser.FuncExprContext ctx) {
        ST st = templates.getInstanceOf("func");
        st.add("name", ctx.func().getText());
        st.add("arg", visit(ctx.expr()));
        return st.render();
    }

    // 📌 Nawiasy okrągłe
    @Override
    public String visitParenExpr(MathExprParser.ParenExprContext ctx) {
        ST st = templates.getInstanceOf("paren");
        st.add("value", visit(ctx.expr()));
        return st.render();
    }

    // 📌 Liczby
    @Override
    public String visitNumberExpr(MathExprParser.NumberExprContext ctx) {
        return ctx.getText();
    }

    // 📌 Zmienne (np. x, y)
    @Override
    public String visitVarExpr(MathExprParser.VarExprContext ctx) {
        String varName = ctx.getText();
        return GREEK_LETTERS.getOrDefault(varName, varName); //jeśli klucz istnieje, to wartość według klucza, jak nie to varName
    }

    // 📌 Operatory logiczne: =, !=, <= itd.
    @Override
    public String visitRelop(MathExprParser.RelopContext ctx) {
        switch (ctx.getText()) {
            case "=": return "=";
            case "!=": return "\\neq";
            case "<": return "<";
            case ">": return ">";
            case "<=": return "\\leq";
            case ">=": return "\\geq";
            default: return ctx.getText();
        }
    }

    @Override
    public String visitMatrixExpr(MathExprParser.MatrixExprContext ctx) {
        List<String> rowStrings = new ArrayList<>();

        for (MathExprParser.RowContext rowCtx : ctx.row()) {
            List<String> cells = new ArrayList<>();
            for (MathExprParser.ExprContext cell : rowCtx.expr()) {
                cells.add(visit(cell));
            }
            rowStrings.add(String.join(" & ", cells));
        }

        ST st = templates.getInstanceOf("matrix");
        st.add("rows", rowStrings);
        String latex = st.render();

        // 🧼 Usuń \n, \r, taby – JLaTeXMath wymaga 1-liniowego LaTeX-a
        latex = latex.replace("\n", "").replace("\r", "").replace("\t", "").replaceAll(" +", " ");

        System.out.println("DEBUG FINAL MATRIX LATEX: " + latex); // sprawdź

        return latex;
    }


    // ✅ Obsługa poziomych wielokropków: ...
    @Override
    public String visitEllipsisHorizontalExpr(MathExprParser.EllipsisHorizontalExprContext ctx) {
        return "\\ldots";
    }

    // ✅ Obsługa pionowych wielokropków: :..
    @Override
    public String visitEllipsisVerticalExpr(MathExprParser.EllipsisVerticalExprContext ctx) {
        return "\\vdots";
    }

    // ✅ Obsługa ukośnych wielokropków: \..
    @Override
    public String visitEllipsisDiagonalExpr(MathExprParser.EllipsisDiagonalExprContext ctx) {
        return "\\ddots";
    }

    @Override
    public String visitEllipsisVExpr(MathExprParser.EllipsisVExprContext ctx) {
        return visit(ctx.expr(0)) + " \\vdots " + visit(ctx.expr(1));
    }

    @Override
    public String visitEllipsisHExpr(MathExprParser.EllipsisHExprContext ctx) {
        return visit(ctx.expr(0)) + " \\ldots " + visit(ctx.expr(1));
    }

    @Override
    public String visitEllipsisDExpr(MathExprParser.EllipsisDExprContext ctx) {
        return visit(ctx.expr(0)) + " \\ddots " + visit(ctx.expr(1));
    }


}