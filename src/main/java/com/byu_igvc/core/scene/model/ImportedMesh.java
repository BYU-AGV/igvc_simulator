package com.byu_igvc.core.scene.model;

import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.ARBVertexBufferObject.*;
import static org.lwjgl.opengl.GL15C.*;

public class ImportedMesh implements IMesh {
    private AIMesh mesh;
    private int vertexArrayBuffer;
    private int normalArrayBuffer;
    private int elementArrayBuffer;
    private int elementCount;

    @Override
    public void compile() {
        vertexArrayBuffer = glGenBuffers();
        glBindBufferARB(GL_ARRAY_BUFFER, vertexArrayBuffer);
        AIVector3D.Buffer vertices = mesh.mVertices();
        nglBufferData(GL_ARRAY_BUFFER, AIVector3D.SIZEOF * vertices.remaining(),
                vertices.address(), GL_STATIC_DRAW);

        normalArrayBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalArrayBuffer);
        AIVector3D.Buffer normals = mesh.mNormals();
        nglBufferData(GL_ARRAY_BUFFER, AIVector3D.SIZEOF * normals.remaining(),
                normals.address(), GL_STATIC_DRAW);

        int faceCount = mesh.mNumFaces();
        elementCount = faceCount * 3;
        IntBuffer elementArrayBufferData = BufferUtils.createIntBuffer(elementCount);
        AIFace.Buffer facesBuffer = mesh.mFaces();
        for (int i = 0; i < faceCount; ++i) {
            AIFace face = facesBuffer.get(i);
            if (face.mNumIndices() != 3) {
                throw new IllegalStateException("AIFace.mNumIndices() != 3");
            }
            elementArrayBufferData.put(face.mIndices());
        }
        elementArrayBufferData.flip();
        elementArrayBuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementArrayBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementArrayBufferData, GL_STATIC_DRAW);
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
}
