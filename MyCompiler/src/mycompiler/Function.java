package mycompiler;

import java.util.ArrayList;
import org.myorganization.mycompiler.MyGrammarParser.Function_declContext;

/**
 *
 * @author haruk
 */
public class Function {
    private String type;
    private String name;
    private ArrayList<Symbol> expectedParams;
    private Function_declContext ctx;
    private SymbolTable st;
    
    public Function() {
        this.type = "";
        this.name = "";
        this.expectedParams = new ArrayList();
        this.ctx = null;
        this.st = new SymbolTable();
    }
    
    public Function(String type, String name, ArrayList<Symbol> expectedParams, Function_declContext ctx) {
        this.type = type;
        this.name = name;
        this.expectedParams = expectedParams;
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

    public ArrayList<Symbol> getExpectedParams() {
        return expectedParams;
    }

    public void setExpectedParams(ArrayList<Symbol> expectedParams) {
        this.expectedParams = expectedParams;
    }
    
    public Boolean checkIfExpectParam(String name) {
        for(int i = 0; i < expectedParams.size(); i++) {
            if(name.equals(expectedParams.get(i).getName())) {
                return true;
            }
        }
        return false;
    }
    
    public String getExpectedParamType(String name) {
        for(int i = 0; i < expectedParams.size(); i++) {
            if(name.equals(expectedParams.get(i).getName())) {
                return expectedParams.get(i).getType();
            }
        }
        return null;
    }
}
