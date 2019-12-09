package mycompiler;

import org.myorganization.mycompiler.MyGrammarParser.Function_declContext;

/**
 *
 * @author haruk
 */
public class Function {
    private String type;
    private String name;
    private Function_declContext ctx;
    private SymbolTable st;
    
    public Function() {
        this.type = "";
        this.name = "";
        this.ctx = null;
        this.st = new SymbolTable();
    }
    
    public Function(String type, String name, Function_declContext ctx) {
        this.type = type;
        this.name = name;
        this.ctx = ctx;
        this.st = new SymbolTable();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Function_declContext getCtx() {
        return ctx;
    }

    public void setCtx(Function_declContext ctx) {
        this.ctx = ctx;
    }

    public SymbolTable getSt() {
        return st;
    }

    public void setSt(SymbolTable st) {
        this.st = st;
    }
    
    
}
