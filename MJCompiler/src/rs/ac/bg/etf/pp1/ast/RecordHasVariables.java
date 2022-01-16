// generated with ast extension for cup
// version 0.8
// 16/0/2022 12:4:34


package rs.ac.bg.etf.pp1.ast;

public class RecordHasVariables extends RecordVarDecl {

    private RecordVarDecl RecordVarDecl;
    private VarDecl VarDecl;

    public RecordHasVariables (RecordVarDecl RecordVarDecl, VarDecl VarDecl) {
        this.RecordVarDecl=RecordVarDecl;
        if(RecordVarDecl!=null) RecordVarDecl.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public RecordVarDecl getRecordVarDecl() {
        return RecordVarDecl;
    }

    public void setRecordVarDecl(RecordVarDecl RecordVarDecl) {
        this.RecordVarDecl=RecordVarDecl;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(RecordVarDecl!=null) RecordVarDecl.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RecordVarDecl!=null) RecordVarDecl.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RecordVarDecl!=null) RecordVarDecl.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RecordHasVariables(\n");

        if(RecordVarDecl!=null)
            buffer.append(RecordVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordHasVariables]");
        return buffer.toString();
    }
}
