// generated with ast extension for cup
// version 0.8
// 21/0/2022 16:56:40


package rs.ac.bg.etf.pp1.ast;

public class ArrayDesignator extends Designator {

    private IndirectArrayNameDesignator IndirectArrayNameDesignator;
    private Expr Expr;

    public ArrayDesignator (IndirectArrayNameDesignator IndirectArrayNameDesignator, Expr Expr) {
        this.IndirectArrayNameDesignator=IndirectArrayNameDesignator;
        if(IndirectArrayNameDesignator!=null) IndirectArrayNameDesignator.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public IndirectArrayNameDesignator getIndirectArrayNameDesignator() {
        return IndirectArrayNameDesignator;
    }

    public void setIndirectArrayNameDesignator(IndirectArrayNameDesignator IndirectArrayNameDesignator) {
        this.IndirectArrayNameDesignator=IndirectArrayNameDesignator;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IndirectArrayNameDesignator!=null) IndirectArrayNameDesignator.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IndirectArrayNameDesignator!=null) IndirectArrayNameDesignator.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IndirectArrayNameDesignator!=null) IndirectArrayNameDesignator.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayDesignator(\n");

        if(IndirectArrayNameDesignator!=null)
            buffer.append(IndirectArrayNameDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayDesignator]");
        return buffer.toString();
    }
}
