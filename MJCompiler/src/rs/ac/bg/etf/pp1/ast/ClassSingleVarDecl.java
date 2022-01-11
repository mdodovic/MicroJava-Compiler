// generated with ast extension for cup
// version 0.8
// 11/0/2022 10:16:32


package rs.ac.bg.etf.pp1.ast;

public class ClassSingleVarDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String varName;
    private ArrayBrackets ArrayBrackets;

    public ClassSingleVarDecl (String varName, ArrayBrackets ArrayBrackets) {
        this.varName=varName;
        this.ArrayBrackets=ArrayBrackets;
        if(ArrayBrackets!=null) ArrayBrackets.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
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
        if(ArrayBrackets!=null) ArrayBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayBrackets!=null) ArrayBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayBrackets!=null) ArrayBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassSingleVarDecl(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(ArrayBrackets!=null)
            buffer.append(ArrayBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassSingleVarDecl]");
        return buffer.toString();
    }
}