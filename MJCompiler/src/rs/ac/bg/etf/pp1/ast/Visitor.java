// generated with ast extension for cup
// version 0.8
// 15/0/2022 18:24:24


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(ArrayBrackets ArrayBrackets);
    public void visit(Mulop Mulop);
    public void visit(MethodDecl MethodDecl);
    public void visit(ConstructorDecl ConstructorDecl);
    public void visit(Relop Relop);
    public void visit(TermList TermList);
    public void visit(ClassFieldsVariables ClassFieldsVariables);
    public void visit(MoreSingleLineConstDeclarations MoreSingleLineConstDeclarations);
    public void visit(StatementList StatementList);
    public void visit(LastVarDecl LastVarDecl);
    public void visit(ClassVarDecl ClassVarDecl);
    public void visit(FactorList FactorList);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(RecordVarDecl RecordVarDecl);
    public void visit(Designator Designator);
    public void visit(ClassVarDeclList ClassVarDeclList);
    public void visit(ClassBody ClassBody);
    public void visit(Condition Condition);
    public void visit(NotLastVarDecl NotLastVarDecl);
    public void visit(IfCondition IfCondition);
    public void visit(ActualParamList ActualParamList);
    public void visit(MethodVarDecl MethodVarDecl);
    public void visit(ClassMethodDeclarations ClassMethodDeclarations);
    public void visit(VarDeclList VarDeclList);
    public void visit(FormalParamList FormalParamList);
    public void visit(AllDeclarationsList AllDeclarationsList);
    public void visit(Expr Expr);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ActualPars ActualPars);
    public void visit(MethodReturnType MethodReturnType);
    public void visit(Statement Statement);
    public void visit(ConstructorVarDecl ConstructorVarDecl);
    public void visit(ConstValueAssignment ConstValueAssignment);
    public void visit(CondFact CondFact);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(SingleStatement SingleStatement);
    public void visit(FormPars FormPars);
    public void visit(OptionalExtend OptionalExtend);
    public void visit(ModuoOp ModuoOp);
    public void visit(DivideOp DivideOp);
    public void visit(MultiplyOp MultiplyOp);
    public void visit(MinusOp MinusOp);
    public void visit(PlusOp PlusOp);
    public void visit(LessEqualOp LessEqualOp);
    public void visit(GreaterEqualOp GreaterEqualOp);
    public void visit(GreaterOp GreaterOp);
    public void visit(LessOp LessOp);
    public void visit(NotEqualOp NotEqualOp);
    public void visit(EqualOp EqualOp);
    public void visit(ActualParam ActualParam);
    public void visit(ActualParams ActualParams);
    public void visit(NoActuals NoActuals);
    public void visit(Actuals Actuals);
    public void visit(FactorBracketExpression FactorBracketExpression);
    public void visit(FactorArrayNewOperator FactorArrayNewOperator);
    public void visit(FactorClassNewOperator FactorClassNewOperator);
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
    public void visit(DesignatorPostDecrement DesignatorPostDecrement);
    public void visit(DesignatorPostIncrement DesignatorPostIncrement);
    public void visit(DesignatorFunctionCall DesignatorFunctionCall);
    public void visit(ErrorInDesignatorAssignOperation ErrorInDesignatorAssignOperation);
    public void visit(DesignatorAssignOperation DesignatorAssignOperation);
    public void visit(SingleExpr SingleExpr);
    public void visit(RelOpExpr RelOpExpr);
    public void visit(SingleCondFact SingleCondFact);
    public void visit(AndOpCondFact AndOpCondFact);
    public void visit(SingleCondTerm SingleCondTerm);
    public void visit(OrOpCondTermList OrOpCondTermList);
    public void visit(ErrorInCondition ErrorInCondition);
    public void visit(IfPart IfPart);
    public void visit(StatementGoTo StatementGoTo);
    public void visit(StatementRead StatementRead);
    public void visit(StatementPrintWithWidth StatementPrintWithWidth);
    public void visit(StatementPrintNoWidth StatementPrintNoWidth);
    public void visit(StetementReturnExpression StetementReturnExpression);
    public void visit(StatementReturnEmpty StatementReturnEmpty);
    public void visit(StatementContinue StatementContinue);
    public void visit(StatementBreak StatementBreak);
    public void visit(StatementDoWhile StatementDoWhile);
    public void visit(StatementIfElse StatementIfElse);
    public void visit(StatementIf StatementIf);
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
    public void visit(SingleClassMethod SingleClassMethod);
    public void visit(MultipleClassMethods MultipleClassMethods);
    public void visit(ConstructorHasNotVariables ConstructorHasNotVariables);
    public void visit(ConstructorHasVariables ConstructorHasVariables);
    public void visit(ClassConstructor ClassConstructor);
    public void visit(ClassSingleVarDecl ClassSingleVarDecl);
    public void visit(ClassVarDeclSingle ClassVarDeclSingle);
    public void visit(ClassVarDeclMultiple ClassVarDeclMultiple);
    public void visit(ErrorInClassVarDecl ErrorInClassVarDecl);
    public void visit(TypedListClassVarDecl TypedListClassVarDecl);
    public void visit(NoClassFields NoClassFields);
    public void visit(ClassFields ClassFields);
    public void visit(ClassBodyFull ClassBodyFull);
    public void visit(ClassBodyMethods ClassBodyMethods);
    public void visit(ClassBodyConstructor ClassBodyConstructor);
    public void visit(ClassBodyBrackets ClassBodyBrackets);
    public void visit(ClassBodyNoConstructorNoMethod ClassBodyNoConstructorNoMethod);
    public void visit(ErrorInExtendingClass ErrorInExtendingClass);
    public void visit(ClassHasNoParent ClassHasNoParent);
    public void visit(ClassHasParent ClassHasParent);
    public void visit(ClassDecl ClassDecl);
    public void visit(RecordHasNotVariables RecordHasNotVariables);
    public void visit(RecordHasVariables RecordHasVariables);
    public void visit(RecordDeclName RecordDeclName);
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
