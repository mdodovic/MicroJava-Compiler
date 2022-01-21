// generated with ast extension for cup
// version 0.8
// 21/0/2022 16:56:40


package rs.ac.bg.etf.pp1.ast;

public class MoreConstDeclarations extends MoreSingleLineConstDeclarations {

    private MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations;
    private ConstValueAssignment ConstValueAssignment;

    public MoreConstDeclarations (MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations, ConstValueAssignment ConstValueAssignment) {
        this.MoreSingleLineConstDeclarations=MoreSingleLineConstDeclarations;
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.setParent(this);
        this.ConstValueAssignment=ConstValueAssignment;
        if(ConstValueAssignment!=null) ConstValueAssignment.setParent(this);
    }

    public MoreSingleLineConstDeclarations getMoreSingleLineConstDeclarations() {
        return MoreSingleLineConstDeclarations;
    }

    public void setMoreSingleLineConstDeclarations(MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations) {
        this.MoreSingleLineConstDeclarations=MoreSingleLineConstDeclarations;
    }

    public ConstValueAssignment getConstValueAssignment() {
        return ConstValueAssignment;
    }

    public void setConstValueAssignment(ConstValueAssignment ConstValueAssignment) {
        this.ConstValueAssignment=ConstValueAssignment;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.accept(visitor);
        if(ConstValueAssignment!=null) ConstValueAssignment.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.traverseTopDown(visitor);
        if(ConstValueAssignment!=null) ConstValueAssignment.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.traverseBottomUp(visitor);
        if(ConstValueAssignment!=null) ConstValueAssignment.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MoreConstDeclarations(\n");

        if(MoreSingleLineConstDeclarations!=null)
            buffer.append(MoreSingleLineConstDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstValueAssignment!=null)
            buffer.append(ConstValueAssignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MoreConstDeclarations]");
        return buffer.toString();
    }
}
