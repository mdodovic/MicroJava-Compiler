// generated with ast extension for cup
// version 0.8
// 9/0/2022 11:5:51


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl extends ConstantDeclaration {

    private ConstDeclarationType ConstDeclarationType;
    private String constName;
    private ConstValue ConstValue;
    private MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations;

    public ConstDecl (ConstDeclarationType ConstDeclarationType, String constName, ConstValue ConstValue, MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations) {
        this.ConstDeclarationType=ConstDeclarationType;
        if(ConstDeclarationType!=null) ConstDeclarationType.setParent(this);
        this.constName=constName;
        this.ConstValue=ConstValue;
        if(ConstValue!=null) ConstValue.setParent(this);
        this.MoreSingleLineConstDeclarations=MoreSingleLineConstDeclarations;
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.setParent(this);
    }

    public ConstDeclarationType getConstDeclarationType() {
        return ConstDeclarationType;
    }

    public void setConstDeclarationType(ConstDeclarationType ConstDeclarationType) {
        this.ConstDeclarationType=ConstDeclarationType;
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

    public MoreSingleLineConstDeclarations getMoreSingleLineConstDeclarations() {
        return MoreSingleLineConstDeclarations;
    }

    public void setMoreSingleLineConstDeclarations(MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations) {
        this.MoreSingleLineConstDeclarations=MoreSingleLineConstDeclarations;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclarationType!=null) ConstDeclarationType.accept(visitor);
        if(ConstValue!=null) ConstValue.accept(visitor);
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclarationType!=null) ConstDeclarationType.traverseTopDown(visitor);
        if(ConstValue!=null) ConstValue.traverseTopDown(visitor);
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclarationType!=null) ConstDeclarationType.traverseBottomUp(visitor);
        if(ConstValue!=null) ConstValue.traverseBottomUp(visitor);
        if(MoreSingleLineConstDeclarations!=null) MoreSingleLineConstDeclarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(ConstDeclarationType!=null)
            buffer.append(ConstDeclarationType.toString("  "+tab));
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
