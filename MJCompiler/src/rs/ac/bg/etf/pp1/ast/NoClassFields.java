// generated with ast extension for cup
// version 0.8
// 13/0/2022 12:50:25


package rs.ac.bg.etf.pp1.ast;

public class NoClassFields extends ClassFieldsVariables {

    public NoClassFields () {
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
        buffer.append("NoClassFields(\n");

        buffer.append(tab);
        buffer.append(") [NoClassFields]");
        return buffer.toString();
    }
}
