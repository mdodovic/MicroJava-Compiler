// generated with ast extension for cup
// version 0.8
// 16/0/2022 12:4:34


package rs.ac.bg.etf.pp1.ast;

public class ClassFieldDesignator extends Designator {

    private Designator Designator;
    private String leftField;

    public ClassFieldDesignator (Designator Designator, String leftField) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.leftField=leftField;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public String getLeftField() {
        return leftField;
    }

    public void setLeftField(String leftField) {
        this.leftField=leftField;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassFieldDesignator(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+leftField);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFieldDesignator]");
        return buffer.toString();
    }
}
