// generated with ast extension for cup
// version 0.8
// 16/0/2022 12:56:58


package rs.ac.bg.etf.pp1.ast;

public class IntegerValue extends ConstValueAssignment {

    private String constName;
    private Integer numberConstValue;

    public IntegerValue (String constName, Integer numberConstValue) {
        this.constName=constName;
        this.numberConstValue=numberConstValue;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Integer getNumberConstValue() {
        return numberConstValue;
    }

    public void setNumberConstValue(Integer numberConstValue) {
        this.numberConstValue=numberConstValue;
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
        buffer.append("IntegerValue(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+numberConstValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IntegerValue]");
        return buffer.toString();
    }
}
