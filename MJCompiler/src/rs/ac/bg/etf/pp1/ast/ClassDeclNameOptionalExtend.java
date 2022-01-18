// generated with ast extension for cup
// version 0.8
// 18/0/2022 14:16:10


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclNameOptionalExtend implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String className;
    private OptionalExtend OptionalExtend;

    public ClassDeclNameOptionalExtend (String className, OptionalExtend OptionalExtend) {
        this.className=className;
        this.OptionalExtend=OptionalExtend;
        if(OptionalExtend!=null) OptionalExtend.setParent(this);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className=className;
    }

    public OptionalExtend getOptionalExtend() {
        return OptionalExtend;
    }

    public void setOptionalExtend(OptionalExtend OptionalExtend) {
        this.OptionalExtend=OptionalExtend;
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
        if(OptionalExtend!=null) OptionalExtend.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalExtend!=null) OptionalExtend.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalExtend!=null) OptionalExtend.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclNameOptionalExtend(\n");

        buffer.append(" "+tab+className);
        buffer.append("\n");

        if(OptionalExtend!=null)
            buffer.append(OptionalExtend.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclNameOptionalExtend]");
        return buffer.toString();
    }
}
