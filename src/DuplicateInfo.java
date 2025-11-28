public class DuplicateInfo {
    public final String type;   // "ROW", "COL", or "BOX"
    public final int index;     // 1-based index of the row, column, or box
    public final int value;     // duplicated digit
    public final int[] positions;  // 1-based positions of duplicates within the group

    public DuplicateInfo(String type, int index, int value, int[] positions) {
        this.type = type;
        this.index = index;
        this.value = value;
        this.positions = positions;
    }
}
