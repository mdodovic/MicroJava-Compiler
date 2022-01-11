// generated with ast extension for cup
// version 0.8
// 11/0/2022 12:38:53


package rs.ac.bg.etf.pp1.ast;

public class MoreConstDeclarations extends MoreSingleLineConstDeclarations {

    private MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations;
    private String constName;
    private ConstValue ConstValue;

    public MoreConstDeclarations (MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations, String constName, ConstValue ConstValue) {
        this.MoreSingleLineConstDeclarations=MoreSingleLineConstDeclarations;
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.setParent(this);
        this.constName=constName;
        this.ConstValue=ConstValue;
        if(ConstValue!=null) ConstValue.setParent(this);
    }

    public MoreSingleLineConstDeclarations getMoreSingleLineConstDeclarations() {
        return MoreSingleLineConstDeclarations;
    }

    public void setMoreSingleLineConstDeclarations(MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations) {
        this.MoreSingleLineConstDeclarations=MoreSingleLineConstDeclarations;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public ConstValue getConstValue() {
        return ConstValue;
    }

    public void setConstValue(ConstValue ConstValue) {
        this.ConstValue=ConstValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.accept(visitor);
        if(ConstValue!=null) ConstValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.traverseTopDown(visitor);
        if(ConstValue!=null) ConstValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.traverseBottomUp(visitor);
        if(ConstValue!=null) ConstValue.traverseBottomUp(visitor);
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

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(ConstValue!=null)
            buffer.append(ConstValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MoreConstDeclarations]");
        return buffer.toString();
    }
}
