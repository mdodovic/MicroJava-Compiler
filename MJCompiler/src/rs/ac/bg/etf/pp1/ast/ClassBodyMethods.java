// generated with ast extension for cup
// version 0.8
// 21/0/2022 16:56:40


package rs.ac.bg.etf.pp1.ast;

public class ClassBodyMethods extends ClassBody {

    private ClassFieldsVariables ClassFieldsVariables;
    private InnerClassBodyDummyStart InnerClassBodyDummyStart;
    private ClassMethodDeclarations ClassMethodDeclarations;

    public ClassBodyMethods (ClassFieldsVariables ClassFieldsVariables, InnerClassBodyDummyStart InnerClassBodyDummyStart, ClassMethodDeclarations ClassMethodDeclarations) {
        this.ClassFieldsVariables=ClassFieldsVariables;
        if(ClassFieldsVariables!=null) ClassFieldsVariables.setParent(this);
        this.InnerClassBodyDummyStart=InnerClassBodyDummyStart;
        if(InnerClassBodyDummyStart!=null) InnerClassBodyDummyStart.setParent(this);
        this.ClassMethodDeclarations=ClassMethodDeclarations;
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.setParent(this);
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

    public ClassMethodDeclarations getClassMethodDeclarations() {
        return ClassMethodDeclarations;
    }

    public void setClassMethodDeclarations(ClassMethodDeclarations ClassMethodDeclarations) {
        this.ClassMethodDeclarations=ClassMethodDeclarations;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassFieldsVariables!=null) ClassFieldsVariables.accept(visitor);
        if(InnerClassBodyDummyStart!=null) InnerClassBodyDummyStart.accept(visitor);
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFieldsVariables!=null) ClassFieldsVariables.traverseTopDown(visitor);
        if(InnerClassBodyDummyStart!=null) InnerClassBodyDummyStart.traverseTopDown(visitor);
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFieldsVariables!=null) ClassFieldsVariables.traverseBottomUp(visitor);
        if(InnerClassBodyDummyStart!=null) InnerClassBodyDummyStart.traverseBottomUp(visitor);
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBodyMethods(\n");

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

        if(ClassMethodDeclarations!=null)
            buffer.append(ClassMethodDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBodyMethods]");
        return buffer.toString();
    }
}
