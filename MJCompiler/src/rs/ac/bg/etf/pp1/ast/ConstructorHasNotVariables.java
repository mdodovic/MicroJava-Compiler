// generated with ast extension for cup
// version 0.8
// 21/0/2022 16:56:40


package rs.ac.bg.etf.pp1.ast;

public class ConstructorHasNotVariables extends ConstructorVarDecl {

    public ConstructorHasNotVariables () {
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
        buffer.append("ConstructorHasNotVariables(\n");

        buffer.append(tab);
        buffer.append(") [ConstructorHasNotVariables]");
        return buffer.toString();
    }
}
