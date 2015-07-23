package visitors;

import cppast.AbstractVisitor;
import cppast.AstConstructorDefinition;
import cppast.AstDestructorDefinition;
import cppast.AstFunctionDefinition;
import cppast.SimpleNode;
import cppast.Token;

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
		FunctionNameGenerator generator = new FunctionNameGenerator();
		String name = (String) generator.visit(node, null);
		System.out.println(name);
		System.out.println(getReturnType(node));
		/*System.out.println("\tbeginLine: " + node.getFirstToken().beginLine);
		System.out.println("\tendLine:" + node.getLastToken().endLine);
		CxxNDVisitor ndVisitor = new CxxNDVisitor();
		node.jjtAccept(ndVisitor, null);
		System.out.println("\tnesting depth: " + ndVisitor.getDepth());*/
		return null;
	}

	private String getReturnType(SimpleNode node) {
		String content = getContent(node);
		String name = getContent((SimpleNode) node.jjtGetChild(0));
		if (content.startsWith(name)) {
			return "null";
		} else {
			return content.substring(0, content.indexOf(name)).trim();
		}
	}

	private String getContent(SimpleNode node) {
		String content = "";
        for(Token token = node.getFirstToken(); token != node.getLastToken().next; token = token.next){
           content += token.toString();
        }
        return content;
	}

}
