package converter;

import parser.MathExprBaseVisitor;
import parser.MathExprParser;

public class LatexVisitor extends MathExprBaseVisitor<String> {

    @Override
    public String visitAddSubExpr(MathExprParser.AddSubExprContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        return left + " " + ctx.op.getText() + " " + right;
    }

    @Override
    public String visitMulDivExpr(MathExprParser.MulDivExprContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        if (ctx.op.getText().equals("*")) {
            return left + " \\cdot " + right;
        } else {
            return "\\frac{" + left + "}{" + right + "}";
        }
    }

    @Override
    public String visitPowExpr(MathExprParser.PowExprContext ctx) {
        String base = visit(ctx.expr(0));
        String exponent = visit(ctx.expr(1));
        return base + "^{" + exponent + "}";
    }

    @Override
    public String visitFuncExpr(MathExprParser.FuncExprContext ctx) {
        return "\\" + ctx.func().getText() + "{" + visit(ctx.expr()) + "}";
    }

    @Override
    public String visitParenExpr(MathExprParser.ParenExprContext ctx) {
        return "(" + visit(ctx.expr()) + ")";
    }

    @Override
    public String visitNumberExpr(MathExprParser.NumberExprContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitVarExpr(MathExprParser.VarExprContext ctx) {
        return ctx.getText();
    }
}

