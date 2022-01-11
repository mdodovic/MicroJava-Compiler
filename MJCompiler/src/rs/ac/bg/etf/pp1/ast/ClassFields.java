// generated with ast extension for cup
// version 0.8
// 11/0/2022 12:56:24


package rs.ac.bg.etf.pp1.ast;

public class ClassFields extends ClassFieldsVariables {

    private ClassFieldsVariables ClassFieldsVariables;
    private ClassVarDecl ClassVarDecl;

    public ClassFields (ClassFieldsVariables ClassFieldsVariables, ClassVarDecl ClassVarDecl) {
        this.ClassFieldsVariables=ClassFieldsVariables;
        if(ClassFieldsVariables!=null) ClassFieldsVariables.setParent(this);
        this.ClassVarDecl=ClassVarDecl;
        if(ClassVarDecl!=null) ClassVarDecl.setParent(this);
    }

    public ClassFieldsVariables getClassFieldsVariables() {
        return ClassFieldsVariables;
    }

    public void setClassFieldsVariables(ClassFieldsVariables ClassFieldsVariables) {
        this.ClassFieldsVariables=ClassFieldsVariables;
    }

    public ClassVarDecl getClassVarDecl() {
        return ClassVarDecl;
    }

    public void setClassVarDecl(ClassVarDecl ClassVarDecl) {
        this.ClassVarDecl=ClassVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassFieldsVariables!=null) ClassFieldsVariables.accept(visitor);
        if(ClassVarDecl!=null) ClassVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFieldsVariables!=null) ClassFieldsVariables.traverseTopDown(visitor);
        if(ClassVarDecl!=null) ClassVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFieldsVariables!=null) ClassFieldsVariables.traverseBottomUp(visitor);
        if(ClassVarDecl!=null) ClassVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassFields(\n");

        if(ClassFieldsVariables!=null)
            buffer.append(ClassFieldsVariables.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassVarDecl!=null)
            buffer.append(ClassVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFields]");
        return buffer.toString();
    }
}
