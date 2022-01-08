// generated with ast extension for cup
// version 0.8
// 8/0/2022 19:56:20


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarations extends AllDeclarationsList {

    private AllDeclarationsList AllDeclarationsList;
    private ConstantDeclaration ConstantDeclaration;

    public ConstDeclarations (AllDeclarationsList AllDeclarationsList, ConstantDeclaration ConstantDeclaration) {
        this.AllDeclarationsList=AllDeclarationsList;
        if(AllDeclarationsList!=null) AllDeclarationsList.setParent(this);
        this.ConstantDeclaration=ConstantDeclaration;
        if(ConstantDeclaration!=null) ConstantDeclaration.setParent(this);
    }

    public AllDeclarationsList getAllDeclarationsList() {
        return AllDeclarationsList;
    }

    public void setAllDeclarationsList(AllDeclarationsList AllDeclarationsList) {
        this.AllDeclarationsList=AllDeclarationsList;
    }

    public ConstantDeclaration getConstantDeclaration() {
        return ConstantDeclaration;
    }

    public void setConstantDeclaration(ConstantDeclaration ConstantDeclaration) {
        this.ConstantDeclaration=ConstantDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AllDeclarationsList!=null) AllDeclarationsList.accept(visitor);
        if(ConstantDeclaration!=null) ConstantDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AllDeclarationsList!=null) AllDeclarationsList.traverseTopDown(visitor);
        if(ConstantDeclaration!=null) ConstantDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AllDeclarationsList!=null) AllDeclarationsList.traverseBottomUp(visitor);
        if(ConstantDeclaration!=null) ConstantDeclaration.traverseBottomUp(visitor);
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

        if(ConstantDeclaration!=null)
            buffer.append(ConstantDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarations]");
        return buffer.toString();
    }
}
