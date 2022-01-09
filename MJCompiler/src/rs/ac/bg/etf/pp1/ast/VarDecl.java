// generated with ast extension for cup
// version 0.8
// 9/0/2022 19:42:13


package rs.ac.bg.etf.pp1.ast;

public class VarDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private VarDeclarationType VarDeclarationType;
    private VarDeclList VarDeclList;

    public VarDecl (VarDeclarationType VarDeclarationType, VarDeclList VarDeclList) {
        this.VarDeclarationType=VarDeclarationType;
        if(VarDeclarationType!=null) VarDeclarationType.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public VarDeclarationType getVarDeclarationType() {
        return VarDeclarationType;
    }

    public void setVarDeclarationType(VarDeclarationType VarDeclarationType) {
        this.VarDeclarationType=VarDeclarationType;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
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
        if(VarDeclarationType!=null) VarDeclarationType.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclarationType!=null) VarDeclarationType.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclarationType!=null) VarDeclarationType.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDecl(\n");

        if(VarDeclarationType!=null)
            buffer.append(VarDeclarationType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDecl]");
        return buffer.toString();
    }
}
