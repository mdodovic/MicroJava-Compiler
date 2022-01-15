// generated with ast extension for cup
// version 0.8
// 15/0/2022 18:24:24


package rs.ac.bg.etf.pp1.ast;

public class VoidType extends MethodReturnType {

    public VoidType () {
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
        buffer.append("VoidType(\n");

        buffer.append(tab);
        buffer.append(") [VoidType]");
        return buffer.toString();
    }
}
