// generated with ast extension for cup
// version 0.8
// 9/0/2022 20:21:37


package rs.ac.bg.etf.pp1.ast;

public class ClasDeclarations extends AllDeclarationsList {

    private AllDeclarationsList AllDeclarationsList;
    private ClassDecl ClassDecl;

    public ClasDeclarations (AllDeclarationsList AllDeclarationsList, ClassDecl ClassDecl) {
        this.AllDeclarationsList=AllDeclarationsList;
        if(AllDeclarationsList!=null) AllDeclarationsList.setParent(this);
        this.ClassDecl=ClassDecl;
        if(ClassDecl!=null) ClassDecl.setParent(this);
    }

    public AllDeclarationsList getAllDeclarationsList() {
        return AllDeclarationsList;
    }

    public void setAllDeclarationsList(AllDeclarationsList AllDeclarationsList) {
        this.AllDeclarationsList=AllDeclarationsList;
    }

    public ClassDecl getClassDecl() {
        return ClassDecl;
    }

    public void setClassDecl(ClassDecl ClassDecl) {
        this.ClassDecl=ClassDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AllDeclarationsList!=null) AllDeclarationsList.accept(visitor);
        if(ClassDecl!=null) ClassDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AllDeclarationsList!=null) AllDeclarationsList.traverseTopDown(visitor);
        if(ClassDecl!=null) ClassDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AllDeclarationsList!=null) AllDeclarationsList.traverseBottomUp(visitor);
        if(ClassDecl!=null) ClassDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClasDeclarations(\n");

        if(AllDeclarationsList!=null)
            buffer.append(AllDeclarationsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassDecl!=null)
            buffer.append(ClassDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClasDeclarations]");
        return buffer.toString();
    }
}
