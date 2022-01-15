// generated with ast extension for cup
// version 0.8
// 15/0/2022 18:24:24


package rs.ac.bg.etf.pp1.ast;

public class RecordDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private RecordDeclName RecordDeclName;
    private RecordVarDecl RecordVarDecl;

    public RecordDecl (RecordDeclName RecordDeclName, RecordVarDecl RecordVarDecl) {
        this.RecordDeclName=RecordDeclName;
        if(RecordDeclName!=null) RecordDeclName.setParent(this);
        this.RecordVarDecl=RecordVarDecl;
        if(RecordVarDecl!=null) RecordVarDecl.setParent(this);
    }

    public RecordDeclName getRecordDeclName() {
        return RecordDeclName;
    }

    public void setRecordDeclName(RecordDeclName RecordDeclName) {
        this.RecordDeclName=RecordDeclName;
    }

    public RecordVarDecl getRecordVarDecl() {
        return RecordVarDecl;
    }

    public void setRecordVarDecl(RecordVarDecl RecordVarDecl) {
        this.RecordVarDecl=RecordVarDecl;
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
        if(RecordDeclName!=null) RecordDeclName.accept(visitor);
        if(RecordVarDecl!=null) RecordVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RecordDeclName!=null) RecordDeclName.traverseTopDown(visitor);
        if(RecordVarDecl!=null) RecordVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RecordDeclName!=null) RecordDeclName.traverseBottomUp(visitor);
        if(RecordVarDecl!=null) RecordVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RecordDecl(\n");

        if(RecordDeclName!=null)
            buffer.append(RecordDeclName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RecordVarDecl!=null)
            buffer.append(RecordVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordDecl]");
        return buffer.toString();
    }
}
