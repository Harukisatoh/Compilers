package mycompiler;

import java.util.ArrayList;

/**
 *
 * @author haruk
 */
public class FunctionTable {
    private ArrayList<Function> functions;
    
    public FunctionTable() {
        functions = new ArrayList<>();
    }
    
    public void addFunction(Function f) {
        functions.add(f);
    }
    
    public Function getFunction(int i) {
        return functions.get(i);
    }
    
    public Function getFunction(String name) {
        Function function = null;
        
        for(int i = 0; i < functions.size(); i++) {
            if(functions.get(i).getName().equals(name)) {
                function = functions.get(i);
            }
        }
        
        return function;
    }
    
    public Boolean checkIfAlreadyExists(String name) {
        for(int i = 0; i < functions.size(); i++) {
            if(name.equalsIgnoreCase(functions.get(i).getName())) {
                return true;
            }
        }
        return false;
    }
    
    public int size() {
        return functions.size();
    }
}
