public class CellData {
    private String value;

    public CellData(String value) {
        this.value = value != null ? value : "";
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    @Override
    public String toString() { return value; }
}
