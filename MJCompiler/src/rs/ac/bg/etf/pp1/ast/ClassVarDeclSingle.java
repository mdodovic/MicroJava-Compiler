// generated with ast extension for cup
// version 0.8
// 11/0/2022 12:45:27


package rs.ac.bg.etf.pp1.ast;

public class ClassVarDeclSingle extends ClassVarDeclList {

    private ClassSingleVarDecl ClassSingleVarDecl;

    public ClassVarDeclSingle (ClassSingleVarDecl ClassSingleVarDecl) {
        this.ClassSingleVarDecl=ClassSingleVarDecl;
        if(ClassSingleVarDecl!=null) ClassSingleVarDecl.setParent(this);
    }

    public ClassSingleVarDecl getClassSingleVarDecl() {
        return ClassSingleVarDecl;
    }

    public void setClassSingleVarDecl(ClassSingleVarDecl ClassSingleVarDecl) {
        this.ClassSingleVarDecl=ClassSingleVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassSingleVarDecl!=null) ClassSingleVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassSingleVarDecl!=null) ClassSingleVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassSingleVarDecl!=null) ClassSingleVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassVarDeclSingle(\n");

        if(ClassSingleVarDecl!=null)
            buffer.append(ClassSingleVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassVarDeclSingle]");
        return buffer.toString();
    }
}
