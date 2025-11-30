
import java.util.*;

public class ValidationResult {
    private final RegionType regionType;
    private final int regionIndex; // 1-based (1..9)
    private final int value; // duplicated value
    private final List<Integer> positions; // 1-based positions (depends on region type)

    public ValidationResult(RegionType type, int regionIndex, int value, List<Integer> positions) {
        this.regionType = type;
        this.regionIndex = regionIndex;
        this.value = value;
        this.positions = positions;
    }

    public RegionType getRegionType() {
        return regionType;
    }

    public int getRegionIndex() {
        return regionIndex;
    }

    public int getValue() {
        return value;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    // Format: "ROW 1, #1, [1, 2, 3, ...]"
    public String formatForOutput() {
        return String.format("%s %d, #%d, %s",
                regionType.name(),
                regionIndex,
                value,
                positionsToString());
    }

    private String positionsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < positions.size(); ++i) {
            sb.append(positions.get(i));
            if (i < positions.size() - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
