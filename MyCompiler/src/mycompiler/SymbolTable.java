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
    
    public Symbol getSymbol(String name) {
        for(int i = 0; i < symbols.size(); i++) {
            if(symbols.get(i).getName().equals(name)) {
                return symbols.get(i);
            }
        }
        return null;
    }

    public void setSymbol(int pos, Symbol s) {
        symbols.set(pos, s);
    }
    
    public void setSymbol(String name, Value value) {
        Symbol aux;
        
        // Set value from symbol name
        for(int i = 0; i < symbols.size(); i++) {
            if(symbols.get(i).getName().equals(name)) {
                // Gets previous values
                String type = symbols.get(i).getType();
                String scope = symbols.get(i).getScope();
                
                // Sets new value
                aux = new Symbol(type, name, scope, value);
                symbols.set(i, aux);
            }
        }
    }
    
    public void insert(String type, String name, String scope, Value value) {
        Symbol s = new Symbol(type, name, scope, value);
        symbols.add(s);
    }
    
    public void remove(String name) {
        for(int i = 0; i < symbols.size(); i++) {
            if(symbols.get(i).getName().equals(name)) {
                symbols.remove(i);
            }
        }
    }
    
    public boolean checkIfAlreadyExists(String name) {
        for(int i = 0; i < symbols.size(); i++) {
            if(symbols.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public Value getValueFromSymbolName(String name) {
        for(int i = 0; i < symbols.size(); i++) {
            if(symbols.get(i).getName().equals(name)) {
                return symbols.get(i).getValue();
            }
        }
        return null;
    }
    
    public String getTypeFromSymbolName(String name) {
        for(int i = 0; i < symbols.size(); i++) {
            if(symbols.get(i).getName().equals(name)) {
                return symbols.get(i).getType();
            }
        }
        return null;
    }
    
    public ArrayList<Symbol> returnArrayList() {
        return symbols;
    }

}
