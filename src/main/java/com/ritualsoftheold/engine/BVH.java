package com.ritualsoftheold.engine;

import java.util.List;

public class BVH {
    float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY, minZ = Float.POSITIVE_INFINITY;
    float maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY, maxZ = Float.NEGATIVE_INFINITY;
    BVH parent, left, right;
    List<Triangle> triangles;
}
