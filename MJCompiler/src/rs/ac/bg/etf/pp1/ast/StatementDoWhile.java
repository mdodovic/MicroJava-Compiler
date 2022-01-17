// generated with ast extension for cup
// version 0.8
// 17/0/2022 20:11:55


package rs.ac.bg.etf.pp1.ast;

public class StatementDoWhile extends SingleStatement {

    private DoWhileDummyStart DoWhileDummyStart;
    private Statement Statement;
    private Condition Condition;

    public StatementDoWhile (DoWhileDummyStart DoWhileDummyStart, Statement Statement, Condition Condition) {
        this.DoWhileDummyStart=DoWhileDummyStart;
        if(DoWhileDummyStart!=null) DoWhileDummyStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
    }

    public DoWhileDummyStart getDoWhileDummyStart() {
        return DoWhileDummyStart;
    }

    public void setDoWhileDummyStart(DoWhileDummyStart DoWhileDummyStart) {
        this.DoWhileDummyStart=DoWhileDummyStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoWhileDummyStart!=null) DoWhileDummyStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoWhileDummyStart!=null) DoWhileDummyStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoWhileDummyStart!=null) DoWhileDummyStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDoWhile(\n");

        if(DoWhileDummyStart!=null)
            buffer.append(DoWhileDummyStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDoWhile]");
        return buffer.toString();
    }
}
