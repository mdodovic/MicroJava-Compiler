// generated with ast extension for cup
// version 0.8
// 13/0/2022 9:30:15


package rs.ac.bg.etf.pp1.ast;

public class BooleanValue extends ConstValueAssignment {

    private String constName;
    private Boolean boolConstValue;

    public BooleanValue (String constName, Boolean boolConstValue) {
        this.constName=constName;
        this.boolConstValue=boolConstValue;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Boolean getBoolConstValue() {
        return boolConstValue;
    }

    public void setBoolConstValue(Boolean boolConstValue) {
        this.boolConstValue=boolConstValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BooleanValue(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+boolConstValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BooleanValue]");
        return buffer.toString();
    }
}
