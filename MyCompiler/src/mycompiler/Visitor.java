/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompiler;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.myorganization.mycompiler.*;


/**
 *
 * @author haruk
 */
public class Visitor extends MyGrammarBaseVisitor {
    
    SymbolTable st = new SymbolTable();
    
    @Override
    public Object visitMyGrammar(MyGrammarParser.MyGrammarContext ctx) {
        System.out.println("entrou no mygrammar");
        return visitChildren(ctx);
    }
    
    @Override
    public Object visitProgram(MyGrammarParser.ProgramContext ctx) {
        System.out.println("entrou no program");
        return visitChildren(ctx);
    }
    
    @Override
    public Object visitFunction(MyGrammarParser.FunctionContext ctx) {
        System.out.println("entrou no function");
        
        return visitChildren(ctx);
    }
    @Override
    public Object visitVarDeclRule(MyGrammarParser.VarDeclRuleContext ctx) {
        System.out.println("entrou na declaração");
        // Get var type from declaration
        String varType = ctx.start.getText();
        // Get var name from declaration
        String varName = ctx.NAME().toString();
        // Get scope from declaration
        String varScope = "0"; // NOT WORKING YET - 0 means global
        // Get value from declaration
        String varValue = null;
        st.insert(varName, varType, varScope, varValue);
        return visitChildren(ctx);
    }
    
    @Override
    public Object visitVarDeclAndAttribRule(MyGrammarParser.VarDeclAndAttribRuleContext ctx) {
        System.out.println("entrou na declaração e atribuição");
        // Get var type from declaration
        String varType = ctx.start.getText();
        // Get var name from declaration
        String varName = ctx.var_decl_and_attrib().NAME().toString();
        // Get scope from declaration
        String varScope = "0"; // NOT WORKING YET - 0 means global
        // Get value from declaration
        Value varValue = (Value) visit(ctx.var_decl_and_attrib().expr());
        //st.insert(varName, varType, varScope, varValue);
        System.out.println("VarValue: " + varValue);
        return 0;
    }
    
    @Override
    public Object visitVar_decl_and_attrib(MyGrammarParser.Var_decl_and_attribContext ctx) {
        System.out.println("entrou na declaração e atribuição 2");
        return visitChildren(ctx);
    }
    
    @Override
    public Object visitExprAdd(MyGrammarParser.ExprAddContext ctx) {
        System.out.println("entrou no add");
        Value left = (Value) visit(ctx.l);
        Value right = (Value) visit(ctx.r);
        return new Value(left.asDouble() + right.asDouble());
    }
    
    @Override
    public Object visitExprSub(MyGrammarParser.ExprSubContext ctx) {
        System.out.println("entrou no sub");
        Value left = (Value) visit(ctx.l);
        Value right = (Value) visit(ctx.r);
        return new Value(left.asDouble() - right.asDouble());
    }
    
    @Override
    public Object visitExprTerm(MyGrammarParser.ExprTermContext ctx) {
        System.out.println("entrou no term");
        return visit(ctx.t);
    }
    
    @Override
    public Value visitTermMul(MyGrammarParser.TermMulContext ctx) {
        System.out.println("entrou no TermMul");
        Value left = (Value) visit(ctx.l);
        Value right = (Value) visit(ctx.r);
        return new Value(left.asDouble() * right.asDouble());
    }

    @Override
    public Value visitTermDiv(MyGrammarParser.TermDivContext ctx) {
        System.out.println("entrou no TermDiv");
        Value left = (Value) visit(ctx.l);
        Value right = (Value) visit(ctx.r);
        Double result = left.asDouble() / right.asDouble();
        
        if(right.asDouble() == 0 || right.asDouble() == -0) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ", " + ctx.getStart().getCharPositionInLine() + "]: Division by 0");
            System.exit(0);
        }
        
        return new Value(result);
    }

    @Override
    public Value visitTermMod(MyGrammarParser.TermModContext ctx) {
        System.out.println("entrou no TermMod");
        Value left = (Value) visit(ctx.l);
        Value right = (Value) visit(ctx.r);
        Double result = left.asDouble() % right.asDouble();
        
        if(right.asDouble() == 0 || right.asDouble() == -0) {
            System.err.println("ERROR [" + ctx.getStart().getLine() + ", " + ctx.getStart().getCharPositionInLine() + "]: Mod by 0");
            System.exit(0);
        }
        
        return new Value(result);
    }

    @Override
    public Object visitTermFact(MyGrammarParser.TermFactContext ctx) {
        System.out.println("entrou no TermFact");
        return visit(ctx.f);
    }

    @Override
    public Value visitFactName(MyGrammarParser.FactNameContext ctx) {
        //RETORNA VALOR DA VARIAVEL SE EXISTIR
        System.out.println("entrou no FactName");
        return new Value(visitChildren(ctx));
    }

    @Override
    public Value visitFactNumber(MyGrammarParser.FactNumberContext ctx) {
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
    public Object visitFactExpr(MyGrammarParser.FactExprContext ctx) {
        System.out.println("entrou no FactExpr");
        return visit(ctx.expr());
    }

    @Override
    public Value visitFactIn(MyGrammarParser.FactInContext ctx) {
        System.out.println("entrou no FactIn");
        return new Value(visitChildren(ctx));
    }
}
