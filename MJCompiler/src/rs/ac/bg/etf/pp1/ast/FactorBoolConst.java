// generated with ast extension for cup
// version 0.8
// 21/0/2022 15:30:2


package rs.ac.bg.etf.pp1.ast;

public class FactorBoolConst extends Factor {

    private Boolean constValue;

    public FactorBoolConst (Boolean constValue) {
        this.constValue=constValue;
    }

    public Boolean getConstValue() {
        return constValue;
    }

    public void setConstValue(Boolean constValue) {
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
        buffer.append("FactorBoolConst(\n");

        buffer.append(" "+tab+constValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorBoolConst]");
        return buffer.toString();
    }
}
