// generated with ast extension for cup
// version 0.8
// 10/0/2022 20:38:10


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Unmatched Unmatched);
    public void visit(ArrayBrackets ArrayBrackets);
    public void visit(Mulop Mulop);
    public void visit(MethodDecl MethodDecl);
    public void visit(ConstructorDecl ConstructorDecl);
    public void visit(Matched Matched);
    public void visit(TermList TermList);
    public void visit(ClassFieldsVariables ClassFieldsVariables);
    public void visit(MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations);
    public void visit(StatementList StatementList);
    public void visit(LastVarDecl LastVarDecl);
    public void visit(ClassVarDecl ClassVarDecl);
    public void visit(FactorList FactorList);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(RecordVarDecl RecordVarDecl);
    public void visit(Designator Designator);
    public void visit(ClassVarDeclList ClassVarDeclList);
    public void visit(ClassBody ClassBody);
    public void visit(NotLastVarDecl NotLastVarDecl);
    public void visit(ConstValue ConstValue);
    public void visit(ActualParamList ActualParamList);
    public void visit(MethodVarDecl MethodVarDecl);
    public void visit(ClassMethodDeclarations ClassMethodDeclarations);
    public void visit(ConstructorBody ConstructorBody);
    public void visit(FormalParamList FormalParamList);
    public void visit(VarDeclList VarDeclList);
    public void visit(AllDeclarationsList AllDeclarationsList);
    public void visit(Expr Expr);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ActualPars ActualPars);
    public void visit(MethodReturnType MethodReturnType);
    public void visit(Statement Statement);
    public void visit(ConstructorVarDecl ConstructorVarDecl);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(SingleStatement SingleStatement);
    public void visit(FormPars FormPars);
    public void visit(OptionalExtend OptionalExtend);
    public void visit(ModuoOp ModuoOp);
    public void visit(DivideOp DivideOp);
    public void visit(MultiplyOp MultiplyOp);
    public void visit(MinusOp MinusOp);
    public void visit(PlusOp PlusOp);
    public void visit(ProcCall ProcCall);
    public void visit(MatchedStatement MatchedStatement);
    public void visit(ReturnNoExpr ReturnNoExpr);
    public void visit(ReturnExpr ReturnExpr);
    public void visit(PrintStmt PrintStmt);
    public void visit(UnmatchedIfElse UnmatchedIfElse);
    public void visit(IfStatement IfStatement);
    public void visit(ActualParam ActualParam);
    public void visit(ActualParams ActualParams);
    public void visit(NoActuals NoActuals);
    public void visit(Actuals Actuals);
    public void visit(FactorBracketExpression FactorBracketExpression);
    public void visit(FactorNewOperator FactorNewOperator);
    public void visit(FactorBoolConst FactorBoolConst);
    public void visit(FactorCharConst FactorCharConst);
    public void visit(FactorNumConst FactorNumConst);
    public void visit(FactorFunctionCall FactorFunctionCall);
    public void visit(FactorVariable FactorVariable);
    public void visit(SingleFactor SingleFactor);
    public void visit(MulOpFactorList MulOpFactorList);
    public void visit(Term Term);
    public void visit(SingleTerm SingleTerm);
    public void visit(AddOpTermList AddOpTermList);
    public void visit(NegativeExpr NegativeExpr);
    public void visit(PositiveExpr PositiveExpr);
    public void visit(SimpleDesignator SimpleDesignator);
    public void visit(ArrayDesignator ArrayDesignator);
    public void visit(ClassFieldDesignator ClassFieldDesignator);
    public void visit(DesignatorFunctionCall DesignatorFunctionCall);
    public void visit(ErrorInDesignatorAssignOperation ErrorInDesignatorAssignOperation);
    public void visit(DesignatorAssignOperation DesignatorAssignOperation);
    public void visit(StatementDesignator StatementDesignator);
    public void visit(Label Label);
    public void visit(StatementBlock StatementBlock);
    public void visit(SingleStatementMatch SingleStatementMatch);
    public void visit(LabeledSingleStatementMatch LabeledSingleStatementMatch);
    public void visit(NoStatements NoStatements);
    public void visit(NonEmptyStement NonEmptyStement);
    public void visit(FormalParameterDeclaration FormalParameterDeclaration);
    public void visit(ErrorInListOfFormalParameters ErrorInListOfFormalParameters);
    public void visit(ErrorInOneFormalParameter ErrorInOneFormalParameter);
    public void visit(SingleFormalParameter SingleFormalParameter);
    public void visit(MultipleFormalParameters MultipleFormalParameters);
    public void visit(NoFormalParameters NoFormalParameters);
    public void visit(FormalParameters FormalParameters);
    public void visit(MethodHasNotVariables MethodHasNotVariables);
    public void visit(MethodHasVariables MethodHasVariables);
    public void visit(VoidType VoidType);
    public void visit(ConcreteType ConcreteType);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(CorrectMethodDecl CorrectMethodDecl);
    public void visit(EmptyMethodDeclList EmptyMethodDeclList);
    public void visit(NonEmptyMethodDeclList NonEmptyMethodDeclList);
    public void visit(NoClassMethodDecl NoClassMethodDecl);
    public void visit(EmptyConstructorBody EmptyConstructorBody);
    public void visit(ConstructorHasNotVariables ConstructorHasNotVariables);
    public void visit(ConstructorHasVariables ConstructorHasVariables);
    public void visit(NoClassConstructor NoClassConstructor);
    public void visit(ClassConstructor ClassConstructor);
    public void visit(ClassSingleVarDecl ClassSingleVarDecl);
    public void visit(ClassVarDeclSingle ClassVarDeclSingle);
    public void visit(ClassVarDeclMultiple ClassVarDeclMultiple);
    public void visit(ErrorInClassVarDecl ErrorInClassVarDecl);
    public void visit(TypedListClassVarDecl TypedListClassVarDecl);
    public void visit(NoClassFields NoClassFields);
    public void visit(ClassFields ClassFields);
    public void visit(ClassFullBody ClassFullBody);
    public void visit(ClassBodyNoConstructorNoMethod ClassBodyNoConstructorNoMethod);
    public void visit(ErrorInExtendingClass ErrorInExtendingClass);
    public void visit(ClassHasNoParent ClassHasNoParent);
    public void visit(ClassHasParent ClassHasParent);
    public void visit(ClassDecl ClassDecl);
    public void visit(RecordHasNotVariables RecordHasNotVariables);
    public void visit(RecordHasVariables RecordHasVariables);
    public void visit(RecordDecl RecordDecl);
    public void visit(VariableIsNotArray VariableIsNotArray);
    public void visit(VariableIsArray VariableIsArray);
    public void visit(ErrorInVarFromLastPart ErrorInVarFromLastPart);
    public void visit(VarFromLastPart VarFromLastPart);
    public void visit(ErrorInVarFromMultiplePart ErrorInVarFromMultiplePart);
    public void visit(VarFromMultiplePart VarFromMultiplePart);
    public void visit(VarDeclLast VarDeclLast);
    public void visit(VarDeclMultiple VarDeclMultiple);
    public void visit(VarDeclType VarDeclType);
    public void visit(VarDecl VarDecl);
    public void visit(Type Type);
    public void visit(NoMoreConstDeclarations NoMoreConstDeclarations);
    public void visit(MoreConstDeclarations MoreConstDeclarations);
    public void visit(CharValue CharValue);
    public void visit(IntegerValue IntegerValue);
    public void visit(BooleanValue BooleanValue);
    public void visit(ConstDeclType ConstDeclType);
    public void visit(ConstDecl ConstDecl);
    public void visit(NoDeclarations NoDeclarations);
    public void visit(RecordDeclarations RecordDeclarations);
    public void visit(ClassDeclarations ClassDeclarations);
    public void visit(ConstDeclarations ConstDeclarations);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
