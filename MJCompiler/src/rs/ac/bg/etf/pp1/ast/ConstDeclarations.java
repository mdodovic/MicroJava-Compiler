// generated with ast extension for cup
// version 0.8
// 10/0/2022 17:5:24


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarations extends AllDeclarationsList {

    private AllDeclarationsList AllDeclarationsList;
    private ConstDecl ConstDecl;

    public ConstDeclarations (AllDeclarationsList AllDeclarationsList, ConstDecl ConstDecl) {
        this.AllDeclarationsList=AllDeclarationsList;
        if(AllDeclarationsList!=null) AllDeclarationsList.setParent(this);
        this.ConstDecl=ConstDecl;
        if(ConstDecl!=null) ConstDecl.setParent(this);
    }

    public AllDeclarationsList getAllDeclarationsList() {
        return AllDeclarationsList;
    }

    public void setAllDeclarationsList(AllDeclarationsList AllDeclarationsList) {
        this.AllDeclarationsList=AllDeclarationsList;
    }

    public ConstDecl getConstDecl() {
        return ConstDecl;
    }

    public void setConstDecl(ConstDecl ConstDecl) {
        this.ConstDecl=ConstDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AllDeclarationsList!=null) AllDeclarationsList.accept(visitor);
        if(ConstDecl!=null) ConstDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AllDeclarationsList!=null) AllDeclarationsList.traverseTopDown(visitor);
        if(ConstDecl!=null) ConstDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AllDeclarationsList!=null) AllDeclarationsList.traverseBottomUp(visitor);
        if(ConstDecl!=null) ConstDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarations(\n");

        if(AllDeclarationsList!=null)
            buffer.append(AllDeclarationsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDecl!=null)
            buffer.append(ConstDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarations]");
        return buffer.toString();
    }
}
