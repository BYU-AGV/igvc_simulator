package com.byu_igvc.core.render;

import static org.lwjgl.opengl.GL33C.*;

public class Mesh {
    private float[] vertexData;
    private int vertexBuffer;
    private int vertexArray;
    private Shader shader;

    public Mesh(Shader shader) {
        this.shader = shader;
    }

    public void compile() {
        vertexArray = glGenVertexArrays();
        glBindVertexArray(vertexArray);

        vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        shader.compile();
    }

    public Mesh setVertexData(float[] data) {
        this.vertexData = data;
        return this;
    }

    public int getVertexBuffer() {
        return vertexBuffer;
    }

    public Shader getShader() {
        return shader;
    }

    public int getNumberOfVertices() {
        return vertexData.length;
    }

    public int getVertexArrayID() {
        return vertexArray;
    }
    
    public static Mesh createCube(float size) {
        float[] data = {
                -size / 2.0f,-size / 2.0f,-size / 2.0f,
                -size / 2.0f,-size / 2.0f, size / 2.0f,
                -size / 2.0f, size / 2.0f, size / 2.0f,
                size / 2.0f, size / 2.0f,-size / 2.0f,
                -size / 2.0f,-size / 2.0f,-size / 2.0f,
                -size / 2.0f, size / 2.0f,-size / 2.0f,
                size / 2.0f,-size / 2.0f, size / 2.0f,
                -size / 2.0f,-size / 2.0f,-size / 2.0f,
                size / 2.0f,-size / 2.0f,-size / 2.0f,
                size / 2.0f, size / 2.0f,-size / 2.0f,
                size / 2.0f,-size / 2.0f,-size / 2.0f,
                -size / 2.0f,-size / 2.0f,-size / 2.0f,
                -size / 2.0f,-size / 2.0f,-size / 2.0f,
                -size / 2.0f, size / 2.0f, size / 2.0f,
                -size / 2.0f, size / 2.0f,-size / 2.0f,
                size / 2.0f,-size / 2.0f, size / 2.0f,
                -size / 2.0f,-size / 2.0f, size / 2.0f,
                -size / 2.0f,-size / 2.0f,-size / 2.0f,
                -size / 2.0f, size / 2.0f, size / 2.0f,
                -size / 2.0f,-size / 2.0f, size / 2.0f,
                size / 2.0f,-size / 2.0f, size / 2.0f,
                size / 2.0f, size / 2.0f, size / 2.0f,
                size / 2.0f,-size / 2.0f,-size / 2.0f,
                size / 2.0f, size / 2.0f,-size / 2.0f,
                size / 2.0f,-size / 2.0f,-size / 2.0f,
                size / 2.0f, size / 2.0f, size / 2.0f,
                size / 2.0f,-size / 2.0f, size / 2.0f,
                size / 2.0f, size / 2.0f, size / 2.0f,
                size / 2.0f, size / 2.0f,-size / 2.0f,
                -size / 2.0f, size / 2.0f,-size / 2.0f,
                size / 2.0f, size / 2.0f, size / 2.0f,
                -size / 2.0f, size / 2.0f,-size / 2.0f,
                -size / 2.0f, size / 2.0f, size / 2.0f,
                size / 2.0f, size / 2.0f, size / 2.0f,
                -size / 2.0f, size / 2.0f, size / 2.0f,
                size / 2.0f,-size / 2.0f, size / 2.0f
        };
        Mesh cube = new Mesh(new Shader("src/main/resources/shaders/vert.glsl", "src/main/resources/shaders/frag.glsl"));
        cube.setVertexData(data);
        cube.compile();
        return cube;
    }
}
