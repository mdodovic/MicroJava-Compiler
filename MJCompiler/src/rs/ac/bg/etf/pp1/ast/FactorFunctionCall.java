// generated with ast extension for cup
// version 0.8
// 21/0/2022 10:8:11


package rs.ac.bg.etf.pp1.ast;

public class FactorFunctionCall extends Factor {

    private FunctionCallName FunctionCallName;
    private ActualPars ActualPars;

    public FactorFunctionCall (FunctionCallName FunctionCallName, ActualPars ActualPars) {
        this.FunctionCallName=FunctionCallName;
        if(FunctionCallName!=null) FunctionCallName.setParent(this);
        this.ActualPars=ActualPars;
        if(ActualPars!=null) ActualPars.setParent(this);
    }

    public FunctionCallName getFunctionCallName() {
        return FunctionCallName;
    }

    public void setFunctionCallName(FunctionCallName FunctionCallName) {
        this.FunctionCallName=FunctionCallName;
    }

    public ActualPars getActualPars() {
        return ActualPars;
    }

    public void setActualPars(ActualPars ActualPars) {
        this.ActualPars=ActualPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FunctionCallName!=null) FunctionCallName.accept(visitor);
        if(ActualPars!=null) ActualPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FunctionCallName!=null) FunctionCallName.traverseTopDown(visitor);
        if(ActualPars!=null) ActualPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FunctionCallName!=null) FunctionCallName.traverseBottomUp(visitor);
        if(ActualPars!=null) ActualPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorFunctionCall(\n");

        if(FunctionCallName!=null)
            buffer.append(FunctionCallName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActualPars!=null)
            buffer.append(ActualPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorFunctionCall]");
        return buffer.toString();
    }
}
