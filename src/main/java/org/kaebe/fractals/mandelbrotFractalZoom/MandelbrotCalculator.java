package org.kaebe.fractals.mandelbrotFractalZoom;

public class MandelbrotCalculator {

    public static final int MAX_ITERATIONS = 200;

    /**
     * Computes whether a point belongs to the Mandelbrot set.
     *
     * @param x The real part of the complex number.
     * @param y The imaginary part of the complex number.
     * @return The number of iterations before the sequence escapes.
     */
    public static int calculatePoint(double x, double y) {
        double real = x;
        double imaginary = y;
        int iterations = 0;

        while (iterations < MAX_ITERATIONS) {
            double newReal = real * real - imaginary * imaginary + x;
            double newImaginary = 2 * real * imaginary + y;

            real = newReal;
            imaginary = newImaginary;

            if (real * real + imaginary * imaginary > 4) {
                break;
            }

            iterations++;
        }

        return iterations;
    }
}
