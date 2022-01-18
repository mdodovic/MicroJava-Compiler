// generated with ast extension for cup
// version 0.8
// 18/0/2022 15:41:55


package rs.ac.bg.etf.pp1.ast;

public class CorrectMethodDecl extends MethodDecl {

    private MethodTypeName MethodTypeName;
    private FormPars FormPars;
    private MethodVarDecl MethodVarDecl;
    private MethodBodyDummyStart MethodBodyDummyStart;
    private StatementList StatementList;

    public CorrectMethodDecl (MethodTypeName MethodTypeName, FormPars FormPars, MethodVarDecl MethodVarDecl, MethodBodyDummyStart MethodBodyDummyStart, StatementList StatementList) {
        this.MethodTypeName=MethodTypeName;
        if(MethodTypeName!=null) MethodTypeName.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.MethodVarDecl=MethodVarDecl;
        if(MethodVarDecl!=null) MethodVarDecl.setParent(this);
        this.MethodBodyDummyStart=MethodBodyDummyStart;
        if(MethodBodyDummyStart!=null) MethodBodyDummyStart.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodTypeName getMethodTypeName() {
        return MethodTypeName;
    }

    public void setMethodTypeName(MethodTypeName MethodTypeName) {
        this.MethodTypeName=MethodTypeName;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public MethodVarDecl getMethodVarDecl() {
        return MethodVarDecl;
    }

    public void setMethodVarDecl(MethodVarDecl MethodVarDecl) {
        this.MethodVarDecl=MethodVarDecl;
    }

    public MethodBodyDummyStart getMethodBodyDummyStart() {
        return MethodBodyDummyStart;
    }

    public void setMethodBodyDummyStart(MethodBodyDummyStart MethodBodyDummyStart) {
        this.MethodBodyDummyStart=MethodBodyDummyStart;
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
        if(MethodTypeName!=null) MethodTypeName.accept(visitor);
        if(FormPars!=null) FormPars.accept(visitor);
        if(MethodVarDecl!=null) MethodVarDecl.accept(visitor);
        if(MethodBodyDummyStart!=null) MethodBodyDummyStart.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodTypeName!=null) MethodTypeName.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(MethodVarDecl!=null) MethodVarDecl.traverseTopDown(visitor);
        if(MethodBodyDummyStart!=null) MethodBodyDummyStart.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodTypeName!=null) MethodTypeName.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(MethodVarDecl!=null) MethodVarDecl.traverseBottomUp(visitor);
        if(MethodBodyDummyStart!=null) MethodBodyDummyStart.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CorrectMethodDecl(\n");

        if(MethodTypeName!=null)
            buffer.append(MethodTypeName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodVarDecl!=null)
            buffer.append(MethodVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodBodyDummyStart!=null)
            buffer.append(MethodBodyDummyStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CorrectMethodDecl]");
        return buffer.toString();
    }
}
