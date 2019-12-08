package mycompiler;

import java.util.ArrayList;

/**
 *
 * @author haruk
 */
public class SymbolTable {
    private ArrayList<Symbol> symbols;
    
    public SymbolTable() {
        this.symbols = new ArrayList();
    }

    public Symbol getSymbol(int pos) {
        return symbols.get(pos);
    }

    public void setSymbol(int pos, Symbol s) {
        symbols.set(pos, s);
    }
    
    public void insert(String name, String type, String scope, String value) {
        System.out.println("por enquanto ta tudo certo");
        int id = symbols.size() + 1;
        Symbol s = new Symbol(id, name, type, scope, value);
        symbols.add(s);
    }

}
