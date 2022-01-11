// generated with ast extension for cup
// version 0.8
// 11/0/2022 12:56:24


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarations extends AllDeclarationsList {

    private AllDeclarationsList AllDeclarationsList;
    private VarDecl VarDecl;

    public VarDeclarations (AllDeclarationsList AllDeclarationsList, VarDecl VarDecl) {
        this.AllDeclarationsList=AllDeclarationsList;
        if(AllDeclarationsList!=null) AllDeclarationsList.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public AllDeclarationsList getAllDeclarationsList() {
        return AllDeclarationsList;
    }

    public void setAllDeclarationsList(AllDeclarationsList AllDeclarationsList) {
        this.AllDeclarationsList=AllDeclarationsList;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AllDeclarationsList!=null) AllDeclarationsList.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AllDeclarationsList!=null) AllDeclarationsList.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AllDeclarationsList!=null) AllDeclarationsList.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarations(\n");

        if(AllDeclarationsList!=null)
            buffer.append(AllDeclarationsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarations]");
        return buffer.toString();
    }
}
