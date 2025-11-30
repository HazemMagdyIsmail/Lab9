import java.util.List;
import java.util.concurrent.Callable;

public interface Validator extends Callable<List<ValidationResult>> {
    // Callable returns list of ValidationResult objects (possibly empty)
}
