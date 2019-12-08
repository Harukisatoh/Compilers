package mycompiler;

/**
 *
 * @author haruk
 */
public class Symbol {
    private int id;
    private String name;
    private String type;
    private String scope;
    private String value;

    public Symbol() {
        this.id = 0;
        this.name = "";
        this.type = "";
        this.scope = "";
    }
    
    public Symbol(int id, String name, String type, String scope, String value) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.scope = scope;
        this.value = value;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
