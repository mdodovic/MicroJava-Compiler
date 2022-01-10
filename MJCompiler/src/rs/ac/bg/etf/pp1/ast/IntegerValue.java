// generated with ast extension for cup
// version 0.8
// 10/0/2022 12:50:15


package rs.ac.bg.etf.pp1.ast;

public class IntegerValue extends ConstValue {

    private Integer constValue;

    public IntegerValue (Integer constValue) {
        this.constValue=constValue;
    }

    public Integer getConstValue() {
        return constValue;
    }

    public void setConstValue(Integer constValue) {
        this.constValue=constValue;
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

        buffer.append(" "+tab+constValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IntegerValue]");
        return buffer.toString();
    }
}
