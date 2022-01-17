// generated with ast extension for cup
// version 0.8
// 17/0/2022 22:4:24


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ClassDeclNameOptionalExtend ClassDeclNameOptionalExtend;
    private ClassBody ClassBody;

    public ClassDecl (ClassDeclNameOptionalExtend ClassDeclNameOptionalExtend, ClassBody ClassBody) {
        this.ClassDeclNameOptionalExtend=ClassDeclNameOptionalExtend;
        if(ClassDeclNameOptionalExtend!=null) ClassDeclNameOptionalExtend.setParent(this);
        this.ClassBody=ClassBody;
        if(ClassBody!=null) ClassBody.setParent(this);
    }

    public ClassDeclNameOptionalExtend getClassDeclNameOptionalExtend() {
        return ClassDeclNameOptionalExtend;
    }

    public void setClassDeclNameOptionalExtend(ClassDeclNameOptionalExtend ClassDeclNameOptionalExtend) {
        this.ClassDeclNameOptionalExtend=ClassDeclNameOptionalExtend;
    }

    public ClassBody getClassBody() {
        return ClassBody;
    }

    public void setClassBody(ClassBody ClassBody) {
        this.ClassBody=ClassBody;
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
        if(ClassDeclNameOptionalExtend!=null) ClassDeclNameOptionalExtend.accept(visitor);
        if(ClassBody!=null) ClassBody.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassDeclNameOptionalExtend!=null) ClassDeclNameOptionalExtend.traverseTopDown(visitor);
        if(ClassBody!=null) ClassBody.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassDeclNameOptionalExtend!=null) ClassDeclNameOptionalExtend.traverseBottomUp(visitor);
        if(ClassBody!=null) ClassBody.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        if(ClassDeclNameOptionalExtend!=null)
            buffer.append(ClassDeclNameOptionalExtend.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassBody!=null)
            buffer.append(ClassBody.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
