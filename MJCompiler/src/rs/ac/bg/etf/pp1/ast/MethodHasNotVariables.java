// generated with ast extension for cup
// version 0.8
// 10/0/2022 17:5:24


package rs.ac.bg.etf.pp1.ast;

public class MethodHasNotVariables extends MethodVarDecl {

    public MethodHasNotVariables () {
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
        buffer.append("MethodHasNotVariables(\n");

        buffer.append(tab);
        buffer.append(") [MethodHasNotVariables]");
        return buffer.toString();
    }
}
