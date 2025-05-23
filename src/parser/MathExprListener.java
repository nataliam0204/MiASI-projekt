// Generated from C:/Users/julia/OneDrive/Pulpit/zajecia/mgr/1 sem/miasi/MiASI-projekt/src/grammar/MathExpr.g4 by ANTLR 4.13.2
package parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MathExprParser}.
 */
public interface MathExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MathExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(MathExprParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(MathExprParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionStmt}
	 * labeled alternative in {@link MathExprParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStmt(MathExprParser.ExpressionStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionStmt}
	 * labeled alternative in {@link MathExprParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStmt(MathExprParser.ExpressionStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AbsExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAbsExpr(MathExprParser.AbsExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AbsExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAbsExpr(MathExprParser.AbsExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDivExpr(MathExprParser.MulDivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDivExpr(MathExprParser.MulDivExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNumberExpr(MathExprParser.NumberExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNumberExpr(MathExprParser.NumberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PowExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPowExpr(MathExprParser.PowExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PowExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPowExpr(MathExprParser.PowExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVarExpr(MathExprParser.VarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVarExpr(MathExprParser.VarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(MathExprParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(MathExprParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFuncExpr(MathExprParser.FuncExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFuncExpr(MathExprParser.FuncExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpr(MathExprParser.AddSubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpr(MathExprParser.AddSubExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExprParser#func}.
	 * @param ctx the parse tree
	 */
	void enterFunc(MathExprParser.FuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExprParser#func}.
	 * @param ctx the parse tree
	 */
	void exitFunc(MathExprParser.FuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link MathExprParser#relop}.
	 * @param ctx the parse tree
	 */
	void enterRelop(MathExprParser.RelopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MathExprParser#relop}.
	 * @param ctx the parse tree
	 */
	void exitRelop(MathExprParser.RelopContext ctx);
}