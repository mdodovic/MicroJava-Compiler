// generated with ast extension for cup
// version 0.8
// 13/0/2022 9:30:15


package rs.ac.bg.etf.pp1.ast;

public class PlusOp extends Addop {

    public PlusOp () {
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
        buffer.append("PlusOp(\n");

        buffer.append(tab);
        buffer.append(") [PlusOp]");
        return buffer.toString();
    }
}
