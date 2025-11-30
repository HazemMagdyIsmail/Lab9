public class DuplicateInfo {
    private final RegionType regionType;
    private final int index;      // 1-based index of region
    private final int value; 
    private final int[] positions; 

    public DuplicateInfo(RegionType regionType, int index, int value, int[] positions) {
        this.regionType = regionType;
        this.index = index;
        this.value = value;
        this.positions = positions;
    }

    public RegionType getRegionType() {
        return regionType;
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
