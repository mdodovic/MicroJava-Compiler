// generated with ast extension for cup
// version 0.8
// 11/0/2022 9:57:46


package rs.ac.bg.etf.pp1.ast;

public class ErrorInVarFromLastPart extends LastVarDecl {

    public ErrorInVarFromLastPart () {
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
        buffer.append("ErrorInVarFromLastPart(\n");

        buffer.append(tab);
        buffer.append(") [ErrorInVarFromLastPart]");
        return buffer.toString();
    }
}
