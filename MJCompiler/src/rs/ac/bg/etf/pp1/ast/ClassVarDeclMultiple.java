// generated with ast extension for cup
// version 0.8
// 16/0/2022 12:4:34


package rs.ac.bg.etf.pp1.ast;

public class ClassVarDeclMultiple extends ClassVarDeclList {

    private ClassVarDeclList ClassVarDeclList;
    private ClassSingleVarDecl ClassSingleVarDecl;

    public ClassVarDeclMultiple (ClassVarDeclList ClassVarDeclList, ClassSingleVarDecl ClassSingleVarDecl) {
        this.ClassVarDeclList=ClassVarDeclList;
        if(ClassVarDeclList!=null) ClassVarDeclList.setParent(this);
        this.ClassSingleVarDecl=ClassSingleVarDecl;
        if(ClassSingleVarDecl!=null) ClassSingleVarDecl.setParent(this);
    }

    public ClassVarDeclList getClassVarDeclList() {
        return ClassVarDeclList;
    }

    public void setClassVarDeclList(ClassVarDeclList ClassVarDeclList) {
        this.ClassVarDeclList=ClassVarDeclList;
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
        if(ClassVarDeclList!=null) ClassVarDeclList.accept(visitor);
        if(ClassSingleVarDecl!=null) ClassSingleVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseTopDown(visitor);
        if(ClassSingleVarDecl!=null) ClassSingleVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseBottomUp(visitor);
        if(ClassSingleVarDecl!=null) ClassSingleVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassVarDeclMultiple(\n");

        if(ClassVarDeclList!=null)
            buffer.append(ClassVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassSingleVarDecl!=null)
            buffer.append(ClassSingleVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassVarDeclMultiple]");
        return buffer.toString();
    }
}
