// generated with ast extension for cup
// version 0.8
// 21/0/2022 16:56:40


package rs.ac.bg.etf.pp1.ast;

public class ClassBodyBrackets extends ClassBody {

    private ClassFieldsVariables ClassFieldsVariables;
    private InnerClassBodyDummyStart InnerClassBodyDummyStart;

    public ClassBodyBrackets (ClassFieldsVariables ClassFieldsVariables, InnerClassBodyDummyStart InnerClassBodyDummyStart) {
        this.ClassFieldsVariables=ClassFieldsVariables;
        if(ClassFieldsVariables!=null) ClassFieldsVariables.setParent(this);
        this.InnerClassBodyDummyStart=InnerClassBodyDummyStart;
        if(InnerClassBodyDummyStart!=null) InnerClassBodyDummyStart.setParent(this);
    }

    public ClassFieldsVariables getClassFieldsVariables() {
        return ClassFieldsVariables;
    }

    public void setClassFieldsVariables(ClassFieldsVariables ClassFieldsVariables) {
        this.ClassFieldsVariables=ClassFieldsVariables;
    }

    public InnerClassBodyDummyStart getInnerClassBodyDummyStart() {
        return InnerClassBodyDummyStart;
    }

    public void setInnerClassBodyDummyStart(InnerClassBodyDummyStart InnerClassBodyDummyStart) {
        this.InnerClassBodyDummyStart=InnerClassBodyDummyStart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassFieldsVariables!=null) ClassFieldsVariables.accept(visitor);
        if(InnerClassBodyDummyStart!=null) InnerClassBodyDummyStart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFieldsVariables!=null) ClassFieldsVariables.traverseTopDown(visitor);
        if(InnerClassBodyDummyStart!=null) InnerClassBodyDummyStart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFieldsVariables!=null) ClassFieldsVariables.traverseBottomUp(visitor);
        if(InnerClassBodyDummyStart!=null) InnerClassBodyDummyStart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBodyBrackets(\n");

        if(ClassFieldsVariables!=null)
            buffer.append(ClassFieldsVariables.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InnerClassBodyDummyStart!=null)
            buffer.append(InnerClassBodyDummyStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBodyBrackets]");
        return buffer.toString();
    }
}
