// generated with ast extension for cup
// version 0.8
// 13/0/2022 9:30:15


package rs.ac.bg.etf.pp1.ast;

public class FormalParameterDeclaration implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private String parameterName;
    private ArrayBrackets ArrayBrackets;

    public FormalParameterDeclaration (Type Type, String parameterName, ArrayBrackets ArrayBrackets) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.parameterName=parameterName;
        this.ArrayBrackets=ArrayBrackets;
        if(ArrayBrackets!=null) ArrayBrackets.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName=parameterName;
    }

    public ArrayBrackets getArrayBrackets() {
        return ArrayBrackets;
    }

    public void setArrayBrackets(ArrayBrackets ArrayBrackets) {
        this.ArrayBrackets=ArrayBrackets;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ArrayBrackets!=null) ArrayBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ArrayBrackets!=null) ArrayBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ArrayBrackets!=null) ArrayBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParameterDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+parameterName);
        buffer.append("\n");

        if(ArrayBrackets!=null)
            buffer.append(ArrayBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParameterDeclaration]");
        return buffer.toString();
    }
}
