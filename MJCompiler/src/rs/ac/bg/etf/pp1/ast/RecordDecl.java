// generated with ast extension for cup
// version 0.8
// 10/0/2022 19:23:13


package rs.ac.bg.etf.pp1.ast;

public class RecordDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String recordName;
    private RecordVarDecl RecordVarDecl;

    public RecordDecl (String recordName, RecordVarDecl RecordVarDecl) {
        this.recordName=recordName;
        this.RecordVarDecl=RecordVarDecl;
        if(RecordVarDecl!=null) RecordVarDecl.setParent(this);
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName=recordName;
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
        if(RecordVarDecl!=null) RecordVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RecordVarDecl!=null) RecordVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RecordVarDecl!=null) RecordVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RecordDecl(\n");

        buffer.append(" "+tab+recordName);
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
