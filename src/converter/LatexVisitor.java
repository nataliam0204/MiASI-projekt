package converter;

// Importy klas ANTLR i parsera
import parser.MathExprBaseVisitor;
import parser.MathExprParser;

// 📌 Importy do obsługi StringTemplate
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.List;
import java.util.ArrayList;


public class LatexVisitor extends MathExprBaseVisitor<String> {

    // 📌 Wczytanie pliku z szablonami LaTeX (StringTemplate)
    private final STGroup templates = new STGroupFile("converter/latex.stg");


    // 📌 Obsługa wielu równań oddzielonych średnikiem (reguła 'prog')
    @Override
    public String visitProg(MathExprParser.ProgContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ctx.statement().size(); i++) {
            sb.append(visit(ctx.statement(i)));
            if (i < ctx.statement().size() - 1) {
                sb.append(" \\\\ "); // 📌 W LaTeX: łamanie linii dla układów równań
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
        return ctx.getText();
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
            rowStrings.add(String.join(" & ", cells)); // kolumny w LaTeX: & między wartościami
        }

        //ST st = templates.getInstanceOf("matrix");
        //st.add("rows", rowStrings);
        //return st.render();

        ST st = templates.getInstanceOf("matrix");
        st.add("rows", rowStrings);
        String matrixBody = st.render();

        // Dodaj \[ \] tylko dla macierzy
        //return "\\[\n" + matrixBody + "\n\\]";
        //return st.render();  // ⬅️ bez \[ \]
        return "\\[\n" + st.render() + "\n\\]";  // TYLKO tu zostaw \[ \]

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

}
