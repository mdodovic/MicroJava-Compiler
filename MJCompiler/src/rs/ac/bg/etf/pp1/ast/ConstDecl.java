// generated with ast extension for cup
// version 0.8
// 15/0/2022 13:23:9


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ConstDeclType ConstDeclType;
    private ConstValueAssignment ConstValueAssignment;
    private MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations;

    public ConstDecl (ConstDeclType ConstDeclType, ConstValueAssignment ConstValueAssignment, MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations) {
        this.ConstDeclType=ConstDeclType;
        if(ConstDeclType!=null) ConstDeclType.setParent(this);
        this.ConstValueAssignment=ConstValueAssignment;
        if(ConstValueAssignment!=null) ConstValueAssignment.setParent(this);
        this.MoreSingleLineConstDeclarations=MoreSingleLineConstDeclarations;
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.setParent(this);
    }

    public ConstDeclType getConstDeclType() {
        return ConstDeclType;
    }

    public void setConstDeclType(ConstDeclType ConstDeclType) {
        this.ConstDeclType=ConstDeclType;
    }

    public ConstValueAssignment getConstValueAssignment() {
        return ConstValueAssignment;
    }

    public void setConstValueAssignment(ConstValueAssignment ConstValueAssignment) {
        this.ConstValueAssignment=ConstValueAssignment;
    }

    public MoreSingleLineConstDeclarations getMoreSingleLineConstDeclarations() {
        return MoreSingleLineConstDeclarations;
    }

    public void setMoreSingleLineConstDeclarations(MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations) {
        this.MoreSingleLineConstDeclarations=MoreSingleLineConstDeclarations;
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
        if(ConstDeclType!=null) ConstDeclType.accept(visitor);
        if(ConstValueAssignment!=null) ConstValueAssignment.accept(visitor);
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclType!=null) ConstDeclType.traverseTopDown(visitor);
        if(ConstValueAssignment!=null) ConstValueAssignment.traverseTopDown(visitor);
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclType!=null) ConstDeclType.traverseBottomUp(visitor);
        if(ConstValueAssignment!=null) ConstValueAssignment.traverseBottomUp(visitor);
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(ConstDeclType!=null)
            buffer.append(ConstDeclType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstValueAssignment!=null)
            buffer.append(ConstValueAssignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MoreSingleLineConstDeclarations!=null)
            buffer.append(MoreSingleLineConstDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
