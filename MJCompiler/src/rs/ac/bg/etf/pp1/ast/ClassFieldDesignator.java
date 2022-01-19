// generated with ast extension for cup
// version 0.8
// 19/0/2022 16:36:40


package rs.ac.bg.etf.pp1.ast;

public class ClassFieldDesignator extends Designator {

    private Designator Designator;
    private String rightField;

    public ClassFieldDesignator (Designator Designator, String rightField) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.rightField=rightField;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public String getRightField() {
        return rightField;
    }

    public void setRightField(String rightField) {
        this.rightField=rightField;
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

        buffer.append(" "+tab+rightField);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFieldDesignator]");
        return buffer.toString();
    }
}
