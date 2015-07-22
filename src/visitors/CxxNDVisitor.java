package visitors;

import cppast.AbstractVisitor;
import cppast.AstIfStatement;
import cppast.AstIterationStatement;
import cppast.AstSwitchStatement;
import cppast.AstTryBlock;
import cppast.SimpleNode;

public class CxxNDVisitor extends AbstractVisitor{

	private double depth = 1;

	@Override
	public Object visit(AstIfStatement node, Object data) {
		processDepthPart(node);
		return null;
	}

	@Override
	public Object visit(AstIterationStatement node, Object data) {
		processDepthPart(node);
		return null;
	}

	@Override
	public Object visit(AstSwitchStatement node, Object data) {
		processDepthPart(node);
		return null;
	}

	@Override
	public Object visit(AstTryBlock node, Object data) {
		processDepthPart(node);
		return null;
	}

	private void processDepthPart(SimpleNode node) {
		double depth = 0;
		int number = node.jjtGetNumChildren();
		for (int i = 0; i < number; i++) {
			CxxNDVisitor visitor = new CxxNDVisitor();
			node.jjtGetChild(i).jjtAccept(visitor, null);
			if (visitor.getDepth() > depth) {
				depth = visitor.getDepth(); 
			}
		}
		depth += 1;
		if (depth > this.depth) {
			this.depth = depth;
		}
	}

	public double getDepth() {
		return depth;
	}
	
}
