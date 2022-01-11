// generated with ast extension for cup
// version 0.8
// 11/0/2022 10:16:32


package rs.ac.bg.etf.pp1.ast;

public class ClassConstructor extends ConstructorDecl {

    private String constructorName;
    private ConstructorVarDecl ConstructorVarDecl;
    private ConstructorBody ConstructorBody;

    public ClassConstructor (String constructorName, ConstructorVarDecl ConstructorVarDecl, ConstructorBody ConstructorBody) {
        this.constructorName=constructorName;
        this.ConstructorVarDecl=ConstructorVarDecl;
        if(ConstructorVarDecl!=null) ConstructorVarDecl.setParent(this);
        this.ConstructorBody=ConstructorBody;
        if(ConstructorBody!=null) ConstructorBody.setParent(this);
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

    public ConstructorBody getConstructorBody() {
        return ConstructorBody;
    }

    public void setConstructorBody(ConstructorBody ConstructorBody) {
        this.ConstructorBody=ConstructorBody;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstructorVarDecl!=null) ConstructorVarDecl.accept(visitor);
        if(ConstructorBody!=null) ConstructorBody.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorVarDecl!=null) ConstructorVarDecl.traverseTopDown(visitor);
        if(ConstructorBody!=null) ConstructorBody.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorVarDecl!=null) ConstructorVarDecl.traverseBottomUp(visitor);
        if(ConstructorBody!=null) ConstructorBody.traverseBottomUp(visitor);
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

        if(ConstructorBody!=null)
            buffer.append(ConstructorBody.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassConstructor]");
        return buffer.toString();
    }
}
