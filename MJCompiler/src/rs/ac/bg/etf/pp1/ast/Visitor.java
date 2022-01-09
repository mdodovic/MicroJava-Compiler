// generated with ast extension for cup
// version 0.8
// 9/0/2022 19:42:13


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Factor Factor);
    public void visit(ArrayBrackets ArrayBrackets);
    public void visit(ActualParamList ActualParamList);
    public void visit(AllDeclarationsList AllDeclarationsList);
    public void visit(LastVarDecl LastVarDecl);
    public void visit(Expr Expr);
    public void visit(FormalParamList FormalParamList);
    public void visit(FormPars FormPars);
    public void visit(VarDeclList VarDeclList);
    public void visit(ConstValue ConstValue);
    public void visit(NotLastVarDecl NotLastVarDecl);
    public void visit(MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations);
    public void visit(ConstantDeclaration ConstantDeclaration);
    public void visit(Unmatched Unmatched);
    public void visit(FormalParamDecl FormalParamDecl);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(Statement Statement);
    public void visit(StatementList StatementList);
    public void visit(Matched Matched);
    public void visit(ActualPars ActualPars);
    public void visit(Addop Addop);
    public void visit(Designator Designator);
    public void visit(ActualParam ActualParam);
    public void visit(ActualParams ActualParams);
    public void visit(NoActuals NoActuals);
    public void visit(Actuals Actuals);
    public void visit(FuncCall FuncCall);
    public void visit(Var Var);
    public void visit(Const Const);
    public void visit(Term Term);
    public void visit(TermExpr TermExpr);
    public void visit(AddExpt AddExpt);
    public void visit(ProcCall ProcCall);
    public void visit(MatchedStatement MatchedStatement);
    public void visit(ReturnNoExpr ReturnNoExpr);
    public void visit(ReturnExpr ReturnExpr);
    public void visit(PrintStmt PrintStmt);
    public void visit(ErrorStmt ErrorStmt);
    public void visit(Assignment Assignment);
    public void visit(UnmatchedIfElse UnmatchedIfElse);
    public void visit(IfStatement IfStatement);
    public void visit(UnmatchedStmt UnmatchedStmt);
    public void visit(MatchedStmt MatchedStmt);
    public void visit(NoStmt NoStmt);
    public void visit(Statements Statements);
    public void visit(SingleFormalParamDecls SingleFormalParamDecls);
    public void visit(FormalParamDecls FormalParamDecls);
    public void visit(NoFormParam NoFormParam);
    public void visit(FormParams FormParams);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodDecl NoMethodDecl);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(Type Type);
    public void visit(VariableIsNotArray VariableIsNotArray);
    public void visit(VariableIsArray VariableIsArray);
    public void visit(ErrorInVarFromLastPart ErrorInVarFromLastPart);
    public void visit(VarFromLastPart VarFromLastPart);
    public void visit(ErrorInVarFromMultiplePart ErrorInVarFromMultiplePart);
    public void visit(VarFromMultiplePart VarFromMultiplePart);
    public void visit(VarDeclLast VarDeclLast);
    public void visit(VarDeclMultiple VarDeclMultiple);
    public void visit(VarDeclarationType VarDeclarationType);
    public void visit(VarDecl VarDecl);
    public void visit(NoMoreConstDeclarations NoMoreConstDeclarations);
    public void visit(MoreConstDeclarations MoreConstDeclarations);
    public void visit(CharValue CharValue);
    public void visit(IntegerValue IntegerValue);
    public void visit(BooleanValue BooleanValue);
    public void visit(ConstDeclarationType ConstDeclarationType);
    public void visit(ConstDecl ConstDecl);
    public void visit(NoDeclarations NoDeclarations);
    public void visit(ConstDeclarations ConstDeclarations);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
