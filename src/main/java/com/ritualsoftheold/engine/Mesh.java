package com.ritualsoftheold.engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memCopy;

public class Mesh {
    public int vao;
    public int vertexArrayBuffer;
    public FloatBuffer verticesFB;
    public int normalArrayBuffer;
    public FloatBuffer normalsFB;
    public int elementArrayBuffer;
    public IntBuffer indicesIB;
    public int elementCount;

    /**
     * Build everything from the given {@link AIMesh}.
     *
     * @param mesh the Assimp {@link AIMesh} object
     */
    public Mesh(AIMesh mesh) {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        vertexArrayBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexArrayBuffer);
        AIVector3D.Buffer vertices = mesh.mVertices();
        int verticesBytes = 4 * 3 * vertices.remaining();
        verticesFB = BufferUtils.createByteBuffer(verticesBytes).asFloatBuffer();
        memCopy(vertices.address(), memAddress(verticesFB), verticesBytes);
        nglBufferData(GL_ARRAY_BUFFER, verticesBytes, vertices.address(), GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L);
        normalArrayBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalArrayBuffer);
        AIVector3D.Buffer normals = mesh.mNormals();
        int normalsBytes = 4 * 3 * normals.remaining();
        normalsFB = BufferUtils.createByteBuffer(normalsBytes).asFloatBuffer();
        memCopy(normals.address(), memAddress(normalsFB), normalsBytes);
        nglBufferData(GL_ARRAY_BUFFER, normalsBytes, normals.address(), GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, true, 0, 0L);
        int faceCount = mesh.mNumFaces();
        elementCount = faceCount * 3;
        indicesIB = BufferUtils.createIntBuffer(elementCount);
        AIFace.Buffer facesBuffer = mesh.mFaces();
        for (int i = 0; i < faceCount; ++i) {
            AIFace face = facesBuffer.get(i);
            if (face.mNumIndices() != 3)
                throw new IllegalStateException("AIFace.mNumIndices() != 3");
            indicesIB.put(face.mIndices());
        }
        indicesIB.flip();
        elementArrayBuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementArrayBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesIB, GL_STATIC_DRAW);
        glBindVertexArray(0);
    }
}
