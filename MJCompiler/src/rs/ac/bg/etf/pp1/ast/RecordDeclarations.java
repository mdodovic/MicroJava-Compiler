// generated with ast extension for cup
// version 0.8
// 18/0/2022 12:10:20


package rs.ac.bg.etf.pp1.ast;

public class RecordDeclarations extends AllDeclarationsList {

    private AllDeclarationsList AllDeclarationsList;
    private RecordDecl RecordDecl;

    public RecordDeclarations (AllDeclarationsList AllDeclarationsList, RecordDecl RecordDecl) {
        this.AllDeclarationsList=AllDeclarationsList;
        if(AllDeclarationsList!=null) AllDeclarationsList.setParent(this);
        this.RecordDecl=RecordDecl;
        if(RecordDecl!=null) RecordDecl.setParent(this);
    }

    public AllDeclarationsList getAllDeclarationsList() {
        return AllDeclarationsList;
    }

    public void setAllDeclarationsList(AllDeclarationsList AllDeclarationsList) {
        this.AllDeclarationsList=AllDeclarationsList;
    }

    public RecordDecl getRecordDecl() {
        return RecordDecl;
    }

    public void setRecordDecl(RecordDecl RecordDecl) {
        this.RecordDecl=RecordDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AllDeclarationsList!=null) AllDeclarationsList.accept(visitor);
        if(RecordDecl!=null) RecordDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AllDeclarationsList!=null) AllDeclarationsList.traverseTopDown(visitor);
        if(RecordDecl!=null) RecordDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AllDeclarationsList!=null) AllDeclarationsList.traverseBottomUp(visitor);
        if(RecordDecl!=null) RecordDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RecordDeclarations(\n");

        if(AllDeclarationsList!=null)
            buffer.append(AllDeclarationsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RecordDecl!=null)
            buffer.append(RecordDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordDeclarations]");
        return buffer.toString();
    }
}
