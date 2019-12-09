package mycompiler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.myorganization.mycompiler.*;
import org.myorganization.mycompiler.MyGrammarParser.Out_params_terminalsContext;


/**
 *
 * @author haruk
 */
public class Visitor extends MyGrammarBaseVisitor {
    
    FunctionTable ft = new FunctionTable();
    String currentContext = null;
    
    /*@Override
    public Object visitMyGrammar(MyGrammarParser.MyGrammarContext ctx) {
        System.out.println("entrou no mygrammar");
        return visitChildren(ctx);
    }
    
    @Override
    public Object visitProgram(MyGrammarParser.ProgramContext ctx) {
        System.out.println("entrou no program");
        return visitChildren(ctx);
    }*/
    
    @Override
    public Object visitFunction_decl(MyGrammarParser.Function_declContext ctx) {
        System.out.println("entrou no function");
        currentContext = ctx.NAME().getText();
        // Gets function info and add to function table
        String type = (String) visit(ctx.type());
        String name = ctx.NAME().getText();
        Function function = new Function(type, name, ctx);
        ft.addFunction(function);
        
        // ADD PARAMETERS VARIABLE DO SYMBOL TABLE - NOT WORKING YET
        visit(ctx.parameters());
        
        // Goes to block's rule
        return visit(ctx.block());
    }
    
    /*@Override
    public Object visitParams_decl(MyGrammarParser.Params_declContext ctx) {        
        return visitChildren(ctx);
    }
    
    @Override
    public Object visitBlock(MyGrammarParser.BlockContext ctx) {
        return visitChildren(ctx);
    }*/
    
    @Override
    public String visitType(MyGrammarParser.TypeContext ctx) {
        return ctx.getText();
    }
    
    @Override
    public Object visitVar_declaration(MyGrammarParser.Var_declarationContext ctx) {
        System.out.println("entrou na declaração");
        
        // Gets symbol table from current function
        SymbolTable currentSt = ft.getFunction(currentContext).getSt();
        
        // Gets var type from declaration
        String varType = ctx.start.getText();
        // Gets var name from declaration
        String varName = ctx.NAME().toString();
        // Gets scope from declaration
        String varScope = currentContext;
        // Gets value from declaration
        Value varValue = null;
        
        // Checks if this variable already exists
        if(currentSt.checkIfAlreadyExists(varName) == true) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: The variable '" + ctx.NAME().getText() + "' already exists");
            System.exit(0);
        }
        
        // Inserts variable into current function's symbol table
        currentSt.insert(varType, varName, varScope, varValue);
        return 0;
    }
    
    @Override
    public Object visitVar_decl_and_attrib(MyGrammarParser.Var_decl_and_attribContext ctx) {
        System.out.println("entrou na declaração e atribuição");
        
        // Gets symbol table from current function
        SymbolTable currentSt = ft.getFunction(currentContext).getSt();
        
        // Gets var type from declaration
        String varType = ctx.start.getText();
        // Gets var name from declaration
        String varName = ctx.var_declaration().NAME().getText();
        // Gets scope from declaration
        String varScope = "0"; // NOT WORKING YET - 0 means global
        // Gets value from declaration
        Value varValue = (Value) visit(ctx.expr());
        
        // Checks if this variable already exists
        if(currentSt.checkIfAlreadyExists(varName) == true) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: The variable '" + ctx.var_declaration().NAME().getText() + "' already exists");
            System.exit(0);
        }
        
        // Inserts variable into current function's symbol table
        currentSt.insert(varType, varName, varScope, varValue);
        return 0;
    }
    
    @Override
    public Object visitVar_attrib_expr(MyGrammarParser.Var_attrib_exprContext ctx) {
        
        // Gets symbol table from current function
        SymbolTable currentSt = ft.getFunction(currentContext).getSt();
        
        // Gets var name
        String varName = ctx.NAME().getText();
        
        // Checks if that variable exists
        if(currentSt.checkIfAlreadyExists(varName) == false) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: The variable '" + ctx.NAME().getText() + "' doesn't exist");
            System.exit(0);
        }
        
        // Set new value to that variable
        Value value = (Value) visit(ctx.expr());
        currentSt.setSymbol(varName, value);
        
        return null;
    }
    
    @Override
    public Object visitVar_attrib_plus(MyGrammarParser.Var_attrib_plusContext ctx) {
        // Gets symbol table from current function
        SymbolTable currentSt = ft.getFunction(currentContext).getSt();
        
        // Gets var name
        String varName = ctx.NAME().getText();
        
        // Checks if the variable exists
        if(currentSt.checkIfAlreadyExists(varName) == false) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: The variable '" + ctx.NAME().getText() + "' doesn't exist");
            System.exit(0);
        }
        
        // Checks if the variable value is null
        if(currentSt.getValueFromSymbolName(varName) == null) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: You can't increment a NULL variable");
            System.exit(0);
        }
        
        // Increment variable's value
        Value value = new Value(currentSt.getValueFromSymbolName(varName).asDouble() + 1);
        currentSt.setSymbol(varName, value);
        
        return null;
    }

    @Override
    public Object visitVar_attrib_sub(MyGrammarParser.Var_attrib_subContext ctx) {
        // Gets symbol table from current function
        SymbolTable currentSt = ft.getFunction(currentContext).getSt();
        
        // Gets var name
        String varName = ctx.NAME().getText();
        
        // Checks if the variable exists
        if(currentSt.checkIfAlreadyExists(varName) == false) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: The variable '" + ctx.NAME().getText() + "' doesn't exist");
            System.exit(0);
        }
        
        // Checks if the variable value is null
        if(currentSt.getValueFromSymbolName(varName) == null) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: You can't decrement a NULL variable");
            System.exit(0);
        }
        
        // Increment variable's value
        Value value = new Value(currentSt.getValueFromSymbolName(varName).asDouble() - 1);
        currentSt.setSymbol(varName, value);
        
        return null;
    }
    
    @Override
    public Object visitExpr_add(MyGrammarParser.Expr_addContext ctx) {
        Value left = (Value) visit(ctx.l);
        Value right = (Value) visit(ctx.r);
        return new Value(left.asDouble() + right.asDouble());
    }
    
    @Override
    public Object visitExpr_sub(MyGrammarParser.Expr_subContext ctx) {
        Value left = (Value) visit(ctx.l);
        Value right = (Value) visit(ctx.r);
        return new Value(left.asDouble() - right.asDouble());
    }
    
    @Override
    public Object visitExpr_term(MyGrammarParser.Expr_termContext ctx) {
        return visit(ctx.t);
    }
    
    @Override
    public Value visitTerm_mul(MyGrammarParser.Term_mulContext ctx) {
        Value left = (Value) visit(ctx.l);
        Value right = (Value) visit(ctx.r);
        return new Value(left.asDouble() * right.asDouble());
    }

    @Override
    public Value visitTerm_div(MyGrammarParser.Term_divContext ctx) {
        Value left = (Value) visit(ctx.l);
        Value right = (Value) visit(ctx.r);
        
        // Checks if the right-hand side of the division is valid
        if(right.asDouble() == 0 || right.asDouble() == -0) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: Division by 0");
            System.exit(0);
        }
        
        Double result = left.asDouble() / right.asDouble();
        
        return new Value(result);
    }

    @Override
    public Value visitTerm_mod(MyGrammarParser.Term_modContext ctx) {
        // Gets values from right-hand side and left-hand side
        Value left = (Value) visit(ctx.l);
        Value right = (Value) visit(ctx.r);
        
        // Checks if the right-hand side of the mod is valid
        if(right.asDouble() == 0 || right.asDouble() == -0) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: Mod by 0");
            System.exit(0);
        }
        
        Double result = left.asDouble() % right.asDouble();       
        
        return new Value(result);
    }

    @Override
    public Object visitTerm_fact(MyGrammarParser.Term_factContext ctx) {
        return visit(ctx.f);
    }

    @Override
    public Value visitFact_name(MyGrammarParser.Fact_nameContext ctx) {
        // Gets symbol table from current function
        SymbolTable currentSt = ft.getFunction(currentContext).getSt();
        
        // Aux symbol
        Symbol s = null;
        
        // Gets var name
        String varName = ctx.NAME().getText();
        
        // Checks if that variable exists
        if(currentSt.checkIfAlreadyExists(varName) == false) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: The variable '" + ctx.NAME().getText() + "' doesn't exist");
            System.exit(0);
        } else {
            // Gets symbol from symbol table
            s = currentSt.getSymbol(varName);
        }
        
        // Returns symbol value
        return s.getValue();
    }

    @Override
    public Value visitFact_number(MyGrammarParser.Fact_numberContext ctx) {
        Double value;
        // Transforms negative values of type (¬3) to negative values that Java understands (-3)
        if(ctx.getText().startsWith("¬")) {
            value = Double.valueOf(ctx.getText().replaceFirst("¬", "-"));
        } else {
            value = Double.valueOf(ctx.getText());
        }
        return new Value(value);
    }

    @Override
    public Object visitFact_expr(MyGrammarParser.Fact_exprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Object visitFact_in(MyGrammarParser.Fact_inContext ctx) {
        return visit(ctx.in());
    }
    
    @Override
    public Value visitIn(MyGrammarParser.InContext ctx) {
        // Gets input from user
        Scanner scanner = new Scanner(System.in);
        Value value = new Value(scanner.nextLine());
        
        // Returns that input
        return value;
    }
    
    @Override
    public Object visitWriteln(MyGrammarParser.WritelnContext ctx) {
        
        // Gets write's parameters
        List<Value> params = (List<Value>) visit(ctx.out_params());
        String str = "";
        
        // Removes "" symbol from every parameter
        for(int i = 0; i < params.size(); i++) { 
            params.set(i, new Value(params.get(i).asString().replaceAll("\"", "")));
        }
        
        // Concatenates all parameters in a string
        for(int i = 0; i< params.size(); i++) {
            str = str.concat(params.get(i).asString());
        }
        
        // Divide the string by "\n", this is necessary to perform line breaks instead of just printing "\n"
        List<String> aux = new ArrayList<> (Arrays.asList(str.split("\\\\n")));
        
        // If the string ends with \n adds an empty string to aux, this is necessary to perform a line
        // break when it has \n in the end of the write's argument
        if(str.endsWith("\\n")) {
            aux.add("");
        }
        
        // Prints strings
        for(int i = 0; i < aux.size(); i++) {
            System.out.println(aux.get(i));
        }
        return null;
    }
    
    @Override
    public Object visitWrite(MyGrammarParser.WriteContext ctx) {
        // Gets write's parameters
        List<Value> params = (List<Value>) visit(ctx.out_params());
        String str = "";
        
        // Removes "" symbol from every parameter
        for(int i = 0; i < params.size(); i++) { 
            params.set(i, new Value(params.get(i).asString().replaceAll("\"", "")));
        }
        
        // Concatenates all parameters in a string
        for(int i = 0; i< params.size(); i++) {
            str = str.concat(params.get(i).asString());
        }
        
        // Divide the string by "\n", this is necessary to perform line breaks instead of just printing "\n"
        List<String> aux = new ArrayList<> (Arrays.asList(str.split("\\\\n")));
        
        // If the string ends with \n adds an empty string to aux, this is necessary to perform a line
        // break when it has \n in the end of the write's argument
        if(str.endsWith("\\n")) {
            aux.add("");
        }
        
        // Prints strings
        for(int i = 0; i < aux.size(); i++) {
            if(i == aux.size()-1) {
                System.out.print(aux.get(i));
            } else {
                System.out.println(aux.get(i));
            }
        }
        return null;
    }
    
    @Override
    public List<Value> visitOut_params(MyGrammarParser.Out_paramsContext ctx) {
        // Gets params list, except first param
        List<Out_params_terminalsContext> outParams = ctx.out_params_terminals();
        List<Value> values = new ArrayList<>();
        
        // Adds every param into only one list
        for(int i = 0; i < outParams.size(); i++) {
            Value aux2 = (Value) visit(ctx.out_params_terminals(i));
            values.add(aux2);
        }
        
        // Returns that list
        return values;
    }
    
    @Override
    public Object visitOut_params_expr(MyGrammarParser.Out_params_exprContext ctx) {
        return new Value(visit(ctx.expr()));
    }
    
    @Override
    public Value visitOut_params_string(MyGrammarParser.Out_params_stringContext ctx) {
        return new Value(ctx.STRING().getText());
    }
}