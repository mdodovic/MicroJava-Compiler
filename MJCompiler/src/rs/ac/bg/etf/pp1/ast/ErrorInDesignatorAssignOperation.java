// generated with ast extension for cup
// version 0.8
// 11/0/2022 13:7:7


package rs.ac.bg.etf.pp1.ast;

public class ErrorInDesignatorAssignOperation extends DesignatorStatement {

    public ErrorInDesignatorAssignOperation () {
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
        buffer.append("ErrorInDesignatorAssignOperation(\n");

        buffer.append(tab);
        buffer.append(") [ErrorInDesignatorAssignOperation]");
        return buffer.toString();
    }
}
