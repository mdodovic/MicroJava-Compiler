// generated with ast extension for cup
// version 0.8
// 19/0/2022 16:36:40


package rs.ac.bg.etf.pp1.ast;

public class ConstructorDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ConstructorDeclName ConstructorDeclName;
    private ConstructorVarDecl ConstructorVarDecl;
    private StatementList StatementList;

    public ConstructorDecl (ConstructorDeclName ConstructorDeclName, ConstructorVarDecl ConstructorVarDecl, StatementList StatementList) {
        this.ConstructorDeclName=ConstructorDeclName;
        if(ConstructorDeclName!=null) ConstructorDeclName.setParent(this);
        this.ConstructorVarDecl=ConstructorVarDecl;
        if(ConstructorVarDecl!=null) ConstructorVarDecl.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public ConstructorDeclName getConstructorDeclName() {
        return ConstructorDeclName;
    }

    public void setConstructorDeclName(ConstructorDeclName ConstructorDeclName) {
        this.ConstructorDeclName=ConstructorDeclName;
    }

    public ConstructorVarDecl getConstructorVarDecl() {
        return ConstructorVarDecl;
    }

    public void setConstructorVarDecl(ConstructorVarDecl ConstructorVarDecl) {
        this.ConstructorVarDecl=ConstructorVarDecl;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
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
        if(ConstructorDeclName!=null) ConstructorDeclName.accept(visitor);
        if(ConstructorVarDecl!=null) ConstructorVarDecl.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorDeclName!=null) ConstructorDeclName.traverseTopDown(visitor);
        if(ConstructorVarDecl!=null) ConstructorVarDecl.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorDeclName!=null) ConstructorDeclName.traverseBottomUp(visitor);
        if(ConstructorVarDecl!=null) ConstructorVarDecl.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstructorDecl(\n");

        if(ConstructorDeclName!=null)
            buffer.append(ConstructorDeclName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstructorVarDecl!=null)
            buffer.append(ConstructorVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstructorDecl]");
        return buffer.toString();
    }
}
