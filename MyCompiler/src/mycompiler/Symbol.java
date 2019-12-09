package mycompiler;

/**
 *
 * @author haruk
 */
public class Symbol {
    private String type;
    private String name;
    private String scope;
    private Value value;

    public Symbol() {
        this.type = "";
        this.name = "";
        this.scope = "";
    }
    
    public Symbol(String type, String name, String scope, Value value) {
        this.type = type;
        this.name = name;
        this.scope = scope;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
    
    
}
