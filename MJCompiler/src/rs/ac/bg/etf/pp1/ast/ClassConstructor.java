// generated with ast extension for cup
// version 0.8
// 17/0/2022 22:4:24


package rs.ac.bg.etf.pp1.ast;

public class ClassConstructor extends ConstructorDecl {

    private String constructorName;
    private ConstructorVarDecl ConstructorVarDecl;
    private StatementList StatementList;

    public ClassConstructor (String constructorName, ConstructorVarDecl ConstructorVarDecl, StatementList StatementList) {
        this.constructorName=constructorName;
        this.ConstructorVarDecl=ConstructorVarDecl;
        if(ConstructorVarDecl!=null) ConstructorVarDecl.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public String getConstructorName() {
        return constructorName;
    }

    public void setConstructorName(String constructorName) {
        this.constructorName=constructorName;
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstructorVarDecl!=null) ConstructorVarDecl.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorVarDecl!=null) ConstructorVarDecl.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorVarDecl!=null) ConstructorVarDecl.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassConstructor(\n");

        buffer.append(" "+tab+constructorName);
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
        buffer.append(") [ClassConstructor]");
        return buffer.toString();
    }
}
