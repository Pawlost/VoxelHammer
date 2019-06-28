package com.ritualsoftheold.engine;
/**
 * A triangle described by three vertices with its positions and normals.
 */
public class Triangle {
    public static final float ONE_THIRD = 1.0f / 3.0f;
    public float v0x, v0y, v0z;
    public float v1x, v1y, v1z;
    public float v2x, v2y, v2z;
    public float n0x, n0y, n0z;
    public float n1x, n1y, n1z;
    public float n2x, n2y, n2z;

    public float centroid(int axis) {
        switch (axis) {
            case 0:
                return (v0x + v1x + v2x) * ONE_THIRD;
            case 1:
                return (v0y + v1y + v2y) * ONE_THIRD;
            case 2:
                return (v0z + v1z + v2z) * ONE_THIRD;
            default:
                throw new IllegalArgumentException();
        }
    }

    public float max(int axis) {
        switch (axis) {
            case 0:
                return Math.max(Math.max(v0x, v1x), v2x);
            case 1:
                return Math.max(Math.max(v0y, v1y), v2y);
            case 2:
                return Math.max(Math.max(v0z, v1z), v2z);
            default:
                throw new IllegalArgumentException();
        }
    }

    public float min(int axis) {
        switch (axis) {
            case 0:
                return Math.min(Math.min(v0x, v1x), v2x);
            case 1:
                return Math.min(Math.min(v0y, v1y), v2y);
            case 2:
                return Math.min(Math.min(v0z, v1z), v2z);
            default:
                throw new IllegalArgumentException();
        }
    }
}
