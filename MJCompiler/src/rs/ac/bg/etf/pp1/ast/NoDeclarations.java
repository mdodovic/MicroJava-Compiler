// generated with ast extension for cup
// version 0.8
// 11/0/2022 12:38:53


package rs.ac.bg.etf.pp1.ast;

public class NoDeclarations extends AllDeclarationsList {

    public NoDeclarations () {
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
        buffer.append("NoDeclarations(\n");

        buffer.append(tab);
        buffer.append(") [NoDeclarations]");
        return buffer.toString();
    }
}
