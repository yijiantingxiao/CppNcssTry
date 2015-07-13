package visitors;

import cppast.AbstractVisitor;
import cppast.AstCaseStatement;
import cppast.AstCatchBlock;
import cppast.AstConditionalExpression;
import cppast.AstFunctionBody;
import cppast.AstIfStatement;
import cppast.AstIterationStatement;
import cppast.AstLogicalAndExpression;
import cppast.AstLogicalOrExpression;

public class ICppCCVisitor extends AbstractVisitor {

	private double complexity;

	@Override
	public Object visit(AstCaseStatement node, Object data) {
		complexity ++;
		node.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AstCatchBlock node, Object data) {
		complexity ++;
		node.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AstConditionalExpression node, Object data) {
		complexity ++;
		node.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AstFunctionBody node, Object data) {
		complexity ++;
		node.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AstIfStatement node, Object data) {
		complexity ++;
		node.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AstIterationStatement node, Object data) {
		complexity ++;
		node.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AstLogicalAndExpression node, Object data) {
		complexity ++;
		node.accept(this, null);
		return null;
	}

	@Override
	public Object visit(AstLogicalOrExpression node, Object data) {
		complexity ++;
		node.accept(this, null);
		return null;
	}

	public double getComplexity() {
		return complexity;
	}
	
}
