// generated with ast extension for cup
// version 0.8
// 15/0/2022 21:24:54


package rs.ac.bg.etf.pp1.ast;

public class RecordDeclName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String recordName;

    public RecordDeclName (String recordName) {
        this.recordName=recordName;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName=recordName;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("RecordDeclName(\n");

        buffer.append(" "+tab+recordName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordDeclName]");
        return buffer.toString();
    }
}
