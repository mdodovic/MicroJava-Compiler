// generated with ast extension for cup
// version 0.8
// 11/0/2022 9:16:27


package rs.ac.bg.etf.pp1.ast;

public class VariableIsNotArray extends ArrayBrackets {

    public VariableIsNotArray () {
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
        buffer.append("VariableIsNotArray(\n");

        buffer.append(tab);
        buffer.append(") [VariableIsNotArray]");
        return buffer.toString();
    }
}
