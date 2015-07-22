package visitors;

import cppast.AbstractVisitor;
import cppast.AstConstructorDefinition;
import cppast.AstDestructorDefinition;
import cppast.AstFunctionDefinition;
import cppast.SimpleNode;
import cppncss.counter.FunctionNameExtractor;

public class ICppMetricExtractionVisitor extends AbstractVisitor {

	@Override
	public Object visit(AstConstructorDefinition node, Object data) {
		return process(node);
	}

	@Override
	public Object visit(AstDestructorDefinition node, Object data) {
		return process(node);
	}

	@Override
	public Object visit(AstFunctionDefinition node, Object data) {
		return process(node);
	}

	private Object process(SimpleNode node) {
		String name = getFunctionName(node);
		CxxNDVisitor ndVisitor = new CxxNDVisitor();
		node.jjtAccept(ndVisitor, null);
		System.out.println(name);
		System.out.println(ndVisitor.getDepth());
		return null;
	}
	
	private String getFunctionName(SimpleNode node){
        return (String)new FunctionNameExtractor().visit( node, null );
    }

}
