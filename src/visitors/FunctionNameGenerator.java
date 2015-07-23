package visitors;

import cppast.AbstractVisitor;
import cppast.AstFunctionBody;
import cppast.AstFunctionName;
import cppast.AstFunctionParameters;
import cppast.AstFunctionPointerPostfix;
import cppast.AstParameterType;
import cppast.AstParameterTypeQualifier;
import cppast.Parser;
import cppast.SimpleNode;
import cppast.Token;

public class FunctionNameGenerator extends AbstractVisitor {	
	private static final Filter FUNCTION_NAME_FILTER = new Filter()
    {
        public String decorate( final Token token )
        {
            final String value = token.image;
            switch( token.kind )
            {
                case Parser.SCOPE :
                case Parser.TILDE :
                    return value;
            }
            switch( token.next.kind )
            {
                case Parser.SCOPE :
                case Parser.LPARENTHESIS :
                case Parser.RPARENTHESIS :
                case Parser.AMPERSAND :
                case Parser.STAR :
                case Parser.LESSTHAN :
                case Parser.COMMA :
                    return value;
            }
            return value + " ";
        }
    };
    private static final Filter PARAMETER_TYPE_FILTER = new Filter()
    {
        public String decorate( final Token token )
        {
            final String value = token.image;
            switch( token.kind )
            {
                case Parser.CONST :
                case Parser.SIGNED :
                case Parser.UNSIGNED :
                case Parser.LESSTHAN :
                case Parser.COMMA :
                    return value + " ";
                case Parser.GREATERTHAN :
                    return " " + value;
            }
            return value;
        }
    };
    private static final Filter PARAMETER_TYPE_QUALIFIER_FILTER = new Filter()
    {
        public String decorate( final Token token )
        {
            final String value = token.image;
            switch( token.kind )
            {
                case Parser.CONST :
                case Parser.LPARENTHESIS :
                    return " " + value;
            }
            return value;
        }
    };
    private boolean isFirstParameter;

    /**
     * {@inheritDoc}
     */
    public Object visit( final AstFunctionName node, final Object data )
    {
        return node.resolve( build( node, FUNCTION_NAME_FILTER ) );
    }

    /**
     * {@inheritDoc}
     */
    public Object visit( final AstFunctionParameters node, final Object data )
    {
        return data + "(" + process( node ) + ")";
    }

    private String process( final SimpleNode node )
    {
        isFirstParameter = true;
        final String result = (String)node.accept( this, "" );
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public Object visit( final AstParameterType node, final Object data )
    {
        final String result = build( node, PARAMETER_TYPE_FILTER );
        if( !isFirstParameter )
            return data + ", " + result;
        isFirstParameter = false;
        return data + result;
    }

    /**
     * {@inheritDoc}
     */
    public Object visit( final AstParameterTypeQualifier node, final Object data )
    {
        return data + build( node, PARAMETER_TYPE_QUALIFIER_FILTER );
    }

    /**
     * {@inheritDoc}
     */
    public Object visit( final AstFunctionBody node, final Object data )
    {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    public Object visit( final AstFunctionPointerPostfix node, final Object data )
    {
        return data;
    }

    private interface Filter // FIXME to be moved into SimpleNode ?
    {
        String decorate( final Token token );
    }

    private String build( final SimpleNode node, final Filter filter ) // FIXME to be moved into SimpleNode ?
    {
        final StringBuffer buffer = new StringBuffer();
        for( Token token = node.getFirstToken(); token != node.getLastToken().next; token = token.next ){
            buffer.append( filter.decorate( token ) );
        }
        return buffer.toString();
    }
    
}
