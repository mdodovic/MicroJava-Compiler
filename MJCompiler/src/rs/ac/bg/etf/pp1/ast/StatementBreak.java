// generated with ast extension for cup
// version 0.8
// 16/0/2022 14:52:0


package rs.ac.bg.etf.pp1.ast;

public class StatementBreak extends SingleStatement {

    public StatementBreak () {
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
        buffer.append("StatementBreak(\n");

        buffer.append(tab);
        buffer.append(") [StatementBreak]");
        return buffer.toString();
    }
}
