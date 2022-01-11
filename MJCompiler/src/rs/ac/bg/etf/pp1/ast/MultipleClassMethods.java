// generated with ast extension for cup
// version 0.8
// 11/0/2022 12:56:24


package rs.ac.bg.etf.pp1.ast;

public class MultipleClassMethods extends ClassMethodDeclarations {

    private ClassMethodDeclarations ClassMethodDeclarations;
    private MethodDecl MethodDecl;

    public MultipleClassMethods (ClassMethodDeclarations ClassMethodDeclarations, MethodDecl MethodDecl) {
        this.ClassMethodDeclarations=ClassMethodDeclarations;
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.setParent(this);
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
    }

    public ClassMethodDeclarations getClassMethodDeclarations() {
        return ClassMethodDeclarations;
    }

    public void setClassMethodDeclarations(ClassMethodDeclarations ClassMethodDeclarations) {
        this.ClassMethodDeclarations=ClassMethodDeclarations;
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.accept(visitor);
        if(MethodDecl!=null) MethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.traverseTopDown(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.traverseBottomUp(visitor);
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleClassMethods(\n");

        if(ClassMethodDeclarations!=null)
            buffer.append(ClassMethodDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleClassMethods]");
        return buffer.toString();
    }
}
