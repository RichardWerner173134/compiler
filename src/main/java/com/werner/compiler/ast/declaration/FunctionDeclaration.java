package com.werner.compiler.ast.declaration;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.expressions.type.AbstractTypeExpression;
import com.werner.compiler.ast.statements.Statement;
import com.werner.compiler.symboltable.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionDeclaration extends Declaration {

    public final Identifier identifier;
    public final List<VariableDeclaration> parametersList;
    public final List<Statement> statementList;

    public final AbstractTypeExpression returnType;

    public FunctionDeclaration(
            ComplexSymbolFactory.Location location,
            Identifier identifier,
            List<VariableDeclaration> parametersList,
            AbstractTypeExpression returnType,
            List<Statement> statementList
    ) {
        super(location);

        this.identifier = identifier;
        this.parametersList = parametersList;
        this.returnType = returnType;
        this.statementList = statementList;
    }

    @Override
    public String toString() {
        return "FunctionDeclaration(" + identifier + ", " + parametersList + ", " + returnType + "," + statementList + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "FunctionDeclaration(\n"

                // identifier
                + identifier.print(depth + 1) + "\n"

                // parameters
                + result + "\t" + "Params[" + "\n"
                + parametersList.stream().map(p -> p.print(depth + 2)).collect(Collectors.joining("\n")) + "\n"
                + result + "\t" + "]" + "\n"

                // return type
                + result + "\t" + "Return(\n"
                + returnType.print(depth + 2) + "\n"
                + result + "\t" + ")\n"

                // statements
                + result + "\t" + "Statements[\n"
                + statementList.stream().map(s -> s.print(depth + 2)).collect(Collectors.joining("\n")) + "\n"
                + result + "\t" + "]" + "\n"

                + result + ")";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
