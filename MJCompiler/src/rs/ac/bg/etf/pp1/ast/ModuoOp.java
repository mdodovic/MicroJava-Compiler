// generated with ast extension for cup
// version 0.8
// 18/0/2022 14:16:11


package rs.ac.bg.etf.pp1.ast;

public class ModuoOp extends Mulop {

    public ModuoOp () {
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
        buffer.append("ModuoOp(\n");

        buffer.append(tab);
        buffer.append(") [ModuoOp]");
        return buffer.toString();
    }
}
