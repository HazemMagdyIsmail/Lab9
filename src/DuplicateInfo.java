public class DuplicateInfo {
    private  String type;     // "ROW", "COL", or "BOX"
    private  int index;       // 1-based index of the row/col/box
    private  int value;       // duplicated value
    private  int[] positions; // positions of duplicates within the region (1-based)

    public DuplicateInfo(String type, int index, int value, int[] positions) {
        this.type = type;
        this.index = index;
        this.value = value;
        this.positions = positions;
    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    public int[] getPositions() {
        return positions;
    }
}
