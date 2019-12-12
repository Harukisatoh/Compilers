package mycompiler;
import java.util.ArrayList;
import java.util.List;
import org.myorganization.mycompiler.*;
import org.myorganization.mycompiler.MyGrammarParser.Function_declContext;


/**
 *
 * @author haruk
 */
public class FunctionVisitor extends MyGrammarBaseVisitor {
    
    FunctionTable ft = new FunctionTable();
    
    @Override
    public FunctionTable visitMyGrammar(MyGrammarParser.MyGrammarContext ctx) {
        visitChildren(ctx);
        return ft;
    }
    
    @Override
    public Object visitMain(MyGrammarParser.MainContext ctx) {
        
        String mainType = ctx.TYPEINT().getText();
        String mainName = ctx.MAIN().getText();
        ArrayList<Symbol> expectedParams = new ArrayList<>();
        Function_declContext mainCtx = null;
        
        Function f = new Function(mainType, mainName, expectedParams, mainCtx);
        ft.addFunction(f);
        
        return null;
    }
    
    @Override
    public Object visitFunction_decl(MyGrammarParser.Function_declContext ctx) {
        // Gets function's info
        String functionType = (String) visit(ctx.type());
        String functionName = ctx.NAME().getText();
        ArrayList<Symbol> expectedParams = (ArrayList<Symbol>) visit(ctx.parameters());
        Function_declContext functionCtx = ctx;
        
        // Checks if function already exists
        if(!ft.checkIfAlreadyExists(functionName)) {
            Function f = new Function(functionType, functionName, expectedParams, functionCtx);
            ft.addFunction(f);
        } else {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: The function '" + functionName + "' already exists");
            System.exit(0);
        }
        return null;
    }
    
    @Override
    public ArrayList<Symbol> visitParameters(MyGrammarParser.ParametersContext ctx) {
        return (ArrayList<Symbol>) visit(ctx.params_decl());
    }
    
    @Override
    public ArrayList<Symbol> visitParams_decl(MyGrammarParser.Params_declContext ctx) {
        List<MyGrammarParser.Var_declarationContext> params = ctx.var_declaration();
        ArrayList<Symbol> variables = new ArrayList();
        Symbol s = null;

        // Loop to get all parameters and store on variables
        for(int i = 0; i < params.size(); i++) {
            s = (Symbol) visit(ctx.var_declaration(i));
            variables.add(s);
        }
        
        // Checks if it has parameters with same name
        for(int i = 0; i < variables.size(); i++) {
            for(int j = i+1; j < variables.size(); j++) {
                if(variables.get(i).getName().equalsIgnoreCase(variables.get(j).getName())) {
                System.err.println("ERROR [" + ctx.getStart().getLine() + ":" + (ctx.getStart().getCharPositionInLine() + 1) + "]: The function '" + ctx.getParent().getParent().getChild(1) + "' has two or more parameters with same name");
                System.exit(0);
                }
            }
        }
        
        return variables;
    }
    
    @Override
    public Symbol visitVar_declaration(MyGrammarParser.Var_declarationContext ctx) {
        // Gets params info
        String varType = (String) visit(ctx.type());
        String varName = ctx.NAME().getText();
        
        // Returns param symbol
        Symbol s = new Symbol(varType, varName, null, null);
        return s;
    }
    
    @Override
    public String visitType(MyGrammarParser.TypeContext ctx) {
        return ctx.getText();
    }
        
}