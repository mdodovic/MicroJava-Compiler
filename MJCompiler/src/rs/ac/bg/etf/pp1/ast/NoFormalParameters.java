// generated with ast extension for cup
// version 0.8
// 18/0/2022 15:41:55


package rs.ac.bg.etf.pp1.ast;

public class NoFormalParameters extends FormPars {

    public NoFormalParameters () {
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
        buffer.append("NoFormalParameters(\n");

        buffer.append(tab);
        buffer.append(") [NoFormalParameters]");
        return buffer.toString();
    }
}
