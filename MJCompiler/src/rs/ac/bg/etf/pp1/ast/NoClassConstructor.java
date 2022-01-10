// generated with ast extension for cup
// version 0.8
// 10/0/2022 22:39:37


package rs.ac.bg.etf.pp1.ast;

public class NoClassConstructor extends ConstructorDecl {

    public NoClassConstructor () {
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
        buffer.append("NoClassConstructor(\n");

        buffer.append(tab);
        buffer.append(") [NoClassConstructor]");
        return buffer.toString();
    }
}
