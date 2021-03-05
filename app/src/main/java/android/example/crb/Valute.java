package android.example.crb;

public class Valute {
    private String charCode;
    private String nominal;
    private String name;
    private String value;

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString(){
        return "►\n" + "Code: " + charCode + "\n"
                + "Nominal: " + nominal + "\n"
                + "Name: " + name + "\n"
                + "Value: " + value + "\n\n";
    }
}
