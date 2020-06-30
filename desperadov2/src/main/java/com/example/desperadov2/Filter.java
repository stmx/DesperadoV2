package com.example.desperadov2;

public class Filter {
    static float[] xs = {1, 1, 1};
    public static float filt(float x) {
        xs[0] = xs[1];
        xs[1] = xs[2];
        xs[2] = x;
        return (xs[0] + xs[1] + xs[2]) / 3;
    }
}
