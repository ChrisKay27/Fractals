import org.junit.jupiter.api.Test;
import org.kaebe.fractals.mandelbrotFractalZoom.MandelbrotCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMandelbrotCalculator {
    @Test
    public void testCalculatePoint() {
        int testIterations = MandelbrotCalculator.calculatePoint(-0.75, 0);
        System.out.println("Iterations: " + testIterations); // Should be a high number or MAX_ITERATIONS
    }
}
