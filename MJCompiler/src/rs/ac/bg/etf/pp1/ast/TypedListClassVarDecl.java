// generated with ast extension for cup
// version 0.8
// 17/0/2022 22:4:24


package rs.ac.bg.etf.pp1.ast;

public class TypedListClassVarDecl extends ClassVarDecl {

    private ClassVarDeclType ClassVarDeclType;
    private ClassVarDeclList ClassVarDeclList;

    public TypedListClassVarDecl (ClassVarDeclType ClassVarDeclType, ClassVarDeclList ClassVarDeclList) {
        this.ClassVarDeclType=ClassVarDeclType;
        if(ClassVarDeclType!=null) ClassVarDeclType.setParent(this);
        this.ClassVarDeclList=ClassVarDeclList;
        if(ClassVarDeclList!=null) ClassVarDeclList.setParent(this);
    }

    public ClassVarDeclType getClassVarDeclType() {
        return ClassVarDeclType;
    }

    public void setClassVarDeclType(ClassVarDeclType ClassVarDeclType) {
        this.ClassVarDeclType=ClassVarDeclType;
    }

    public ClassVarDeclList getClassVarDeclList() {
        return ClassVarDeclList;
    }

    public void setClassVarDeclList(ClassVarDeclList ClassVarDeclList) {
        this.ClassVarDeclList=ClassVarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassVarDeclType!=null) ClassVarDeclType.accept(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassVarDeclType!=null) ClassVarDeclType.traverseTopDown(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassVarDeclType!=null) ClassVarDeclType.traverseBottomUp(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TypedListClassVarDecl(\n");

        if(ClassVarDeclType!=null)
            buffer.append(ClassVarDeclType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassVarDeclList!=null)
            buffer.append(ClassVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypedListClassVarDecl]");
        return buffer.toString();
    }
}
