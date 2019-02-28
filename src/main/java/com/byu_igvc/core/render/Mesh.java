package com.byu_igvc.core.render;

import com.byu_igvc.core.render.Shader;

import static org.lwjgl.opengl.GL33C.*;

public class Mesh {
    public Mesh(Shader shader) {
        this.shader = shader;
    }
    private final float[] vertexData = {
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            0.0f,  1.0f, 0.0f,
    };
    private int vertexBuffer;
    private int vertexArray;
    private Shader shader;

    public void compile() {
        vertexArray = glGenVertexArrays();
        glBindVertexArray(vertexArray);
        vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        shader.compile();
    }

    public int getVertexBuffer() {
        return vertexBuffer;
    }

    public Shader getShader() {
        return shader;
    }

    public int getNumberOfVerticies() {
        return vertexData.length;
    }
}
