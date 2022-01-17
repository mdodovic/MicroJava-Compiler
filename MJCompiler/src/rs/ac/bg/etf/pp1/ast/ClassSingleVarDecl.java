// generated with ast extension for cup
// version 0.8
// 17/0/2022 22:4:24


package rs.ac.bg.etf.pp1.ast;

public class ClassSingleVarDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String classFieldName;
    private ArrayBrackets ArrayBrackets;

    public ClassSingleVarDecl (String classFieldName, ArrayBrackets ArrayBrackets) {
        this.classFieldName=classFieldName;
        this.ArrayBrackets=ArrayBrackets;
        if(ArrayBrackets!=null) ArrayBrackets.setParent(this);
    }

    public String getClassFieldName() {
        return classFieldName;
    }

    public void setClassFieldName(String classFieldName) {
        this.classFieldName=classFieldName;
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

        buffer.append(" "+tab+classFieldName);
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
