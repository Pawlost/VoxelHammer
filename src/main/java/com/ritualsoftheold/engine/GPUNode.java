package com.ritualsoftheold.engine;

import org.joml.Vector3f;

public class GPUNode {
    Vector3f min; int left;
    Vector3f max; int right;
    int parent, firstTri, numTris;
}
