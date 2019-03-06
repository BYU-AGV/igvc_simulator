package com.byu_igvc.core.scene.model;

import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class ImportedMesh implements IMesh {
    private AIMesh mesh;
    private int vertexArrayBuffer;
    private int normalArrayBuffer;
    private int elementArrayBuffer;
    private int elementCount;
    private FloatBuffer vertexFB;
    private FloatBuffer normalFB;

    @Override
    public void compile() {
        createArrays();
        vertexArrayBuffer = glGenVertexArrays();
        glBindVertexArray(vertexArrayBuffer);

        int vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertexFB, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        int normalBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalBuffer);
        glBufferData(GL_ARRAY_BUFFER, normalFB, GL_STATIC_DRAW);

        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void createArrays() {
        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();
        AIFace.Buffer facesBuffer = mesh.mFaces();
        List<Integer> indexList = new ArrayList<>();
        List<AIVector3D> nm = new ArrayList<>();
        for (int i = 0; i < mesh.mNumFaces(); i++) {
            indexList.add(facesBuffer.get(i).mIndices().get(0));
            indexList.add(facesBuffer.get(i).mIndices().get(1));
            indexList.add(facesBuffer.get(i).mIndices().get(2));
        }

        for (int i = 0; i < normals.capacity(); i++) {
            nm.add(normals.get(i));
        }

        // Create float buffer
        vertexFB = BufferUtils.createFloatBuffer((indexList.size() * 3));
        normalFB = BufferUtils.createFloatBuffer((indexList.size() * 3));
        for (Integer index : indexList) {
            vertexFB.put(vertices.get(index).x());
            vertexFB.put(vertices.get(index).y());
            vertexFB.put(vertices.get(index).z());

            normalFB.put(normals.get(index).x());
            normalFB.put(normals.get(index).y());
            normalFB.put(normals.get(index).z());
        }
        vertexFB.flip();
    }

    public ImportedMesh(AIMesh aiMesh) {
        this.mesh = aiMesh;
    }

    public AIMesh getMesh() {
        return mesh;
    }

    public int getVertexArrayBuffer() {
        return vertexArrayBuffer;
    }

    public int getNormalArrayBuffer() {
        return normalArrayBuffer;
    }

    public int getElementArrayBuffer() {
        return elementArrayBuffer;
    }

    public int getElementCount() {
        return elementCount;
    }

    public int getNumVertices() {
        return vertexFB.capacity();
    }
}
