// Generated from C:/Users/julia/OneDrive/Pulpit/zajecia/mgr/1 sem/miasi/MiASI-projekt/src/grammar/MathExpr.g4 by ANTLR 4.13.2
package parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MathExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MathExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MathExprParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(MathExprParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionStmt}
	 * labeled alternative in {@link MathExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStmt(MathExprParser.ExpressionStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AbsExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbsExpr(MathExprParser.AbsExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivExpr(MathExprParser.MulDivExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberExpr(MathExprParser.NumberExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PowExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowExpr(MathExprParser.PowExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarExpr(MathExprParser.VarExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(MathExprParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FuncExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncExpr(MathExprParser.FuncExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link MathExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpr(MathExprParser.AddSubExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExprParser#func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc(MathExprParser.FuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link MathExprParser#relop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelop(MathExprParser.RelopContext ctx);
}