// generated with ast extension for cup
// version 0.8
// 21/0/2022 15:30:2


package rs.ac.bg.etf.pp1.ast;

public class StatementGoTo extends SingleStatement {

    private String labelName;

    public StatementGoTo (String labelName) {
        this.labelName=labelName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName=labelName;
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
        buffer.append("StatementGoTo(\n");

        buffer.append(" "+tab+labelName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementGoTo]");
        return buffer.toString();
    }
}
