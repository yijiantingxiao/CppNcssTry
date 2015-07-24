package visitors;

import cppast.AbstractVisitor;
import cppast.AstClassDeclaration;
import cppast.AstClassDefinition;
import cppast.AstConstructorDeclaration;
import cppast.AstConstructorDefinition;
import cppast.AstDestructorDeclaration;
import cppast.AstDestructorDefinition;
import cppast.AstFunctionDeclaration;
import cppast.AstFunctionDefinition;
import cppast.AstNamespaceAliasDefinition;
import cppast.AstNamespaceDefinition;
import cppast.Node;
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
	
	@Override
	public Object visit(AstConstructorDeclaration node, Object data) {
		return process(node);
	}

	@Override
	public Object visit(AstDestructorDeclaration node, Object data) {
		return process(node);
	}

	@Override
	public Object visit(AstFunctionDeclaration node, Object data) {
		return process(node);
	}

	private Object process(SimpleNode node) {
		String name = getFunctionName(node);
		if (name.contains("::")) {
			name = name.replace("::", ".");
		} else {
			String className = getClassName(node);
			if (!className.isEmpty()) {
				name = className + "." + name; 
			}
		}
		System.out.println(name);
		
		System.out.println("\tLOC: " + (node.getLastToken().endLine - node.getFirstToken().beginLine + 1));
		
		double npa = 0;
		String paraString = name.substring(name.lastIndexOf("(") + 1, name.lastIndexOf(")")).trim();
		if (!paraString.isEmpty()) {
			npa = paraString.split(", ").length;
		}
		System.out.println("\tNPA: " + npa);
		
		CxxCCVisitor ccVisitor = new CxxCCVisitor();
		node.jjtAccept(ccVisitor, null);
		System.out.println("\tCC: " + ccVisitor.getComplexity());
		
		CxxNDVisitor ndVisitor = new CxxNDVisitor();
		node.jjtAccept(ndVisitor, null);
		System.out.println("\tND: " + ndVisitor.getDepth());
		
		return null;
	}
	
	private String getFunctionName(SimpleNode node){
		String functionName = (String) new FunctionNameGenerator().visit(node, null);
		String returnType = getReturnType(node);
        return functionName + ":" + returnType;
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

	private String getClassName(SimpleNode node) {
		Node parent = node.jjtGetParent();
		if (parent instanceof AstClassDeclaration || parent instanceof AstClassDefinition 
				|| parent instanceof AstNamespaceDefinition || parent instanceof AstNamespaceAliasDefinition) {
			String className = getClassName((SimpleNode) parent);
			String name = getName((SimpleNode) parent);
			if (!className.isEmpty()) {
				return className + "." + name;
			} else {
				return name;
			}
		}
		return "";
	}

	private String getName(SimpleNode node) {
		String content = getContent(node);
		String child = getContent((SimpleNode) node.jjtGetChild(0));
		if (content.contains(child)) {
			if (content.contains(CLASSS_STRING)) {
				return content.substring(content.indexOf(CLASSS_STRING) + CLASSS_STRING.length(), content.indexOf(child)).trim();
			} else if (content.contains(NAMESPACE_STRING)) {
				return content.substring(content.indexOf(NAMESPACE_STRING)	+ NAMESPACE_STRING.length(), content.indexOf(child)).trim();
			}
		}
		return "";
	}
	
	private final String CLASSS_STRING = "class";
	private final String NAMESPACE_STRING = "namespace";

}
