// generated with ast extension for cup
// version 0.8
// 10/0/2022 10:59:51


package rs.ac.bg.etf.pp1.ast;

public class ErrorInClassVarDecl extends ClassVarDecl {

    public ErrorInClassVarDecl () {
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
        buffer.append("ErrorInClassVarDecl(\n");

        buffer.append(tab);
        buffer.append(") [ErrorInClassVarDecl]");
        return buffer.toString();
    }
}
