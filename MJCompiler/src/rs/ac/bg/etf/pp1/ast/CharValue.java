// generated with ast extension for cup
// version 0.8
// 9/0/2022 19:46:44


package rs.ac.bg.etf.pp1.ast;

public class CharValue extends ConstValue {

    public CharValue () {
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
        buffer.append("CharValue(\n");

        buffer.append(tab);
        buffer.append(") [CharValue]");
        return buffer.toString();
    }
}
