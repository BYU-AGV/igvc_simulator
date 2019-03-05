package com.byu_igvc.core.render;

import com.byu_igvc.logger.Logger;
import glm.vec._3.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL33C.*;

public class Mesh {
    private List<Float> vertexData;
    private List<Integer> indices;
    private int indexBufferID;
    private int vertexBuffer;
    private int vertexArray;
    private Shader shader;

    public Mesh(Shader shader) {
        this.shader = shader;
        vertexData = new ArrayList<>();
        indices = new ArrayList<>();
    }

    public void compile() {
        double[] data = vertexData.stream().mapToDouble(i -> i).toArray();
        double[] ind = indices.stream().mapToDouble(d -> d).toArray();

        Logger.fine(Arrays.toString(data));
        Logger.fine(Arrays.toString(ind));

        vertexArray = glGenVertexArrays();
        glBindVertexArray(vertexArray);

        vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        indexBufferID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ind, GL_STATIC_DRAW);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        shader.compile();
    }

    public Mesh addVertex(Vec3 point) {
        this.vertexData.add(point.x);
        this.vertexData.add(point.y);
        this.vertexData.add(point.z);
        indices.add(indices.size());
        return this;
    }

    public int getVertexBuffer() {
        return vertexBuffer;
    }

    public Shader getShader() {
        return shader;
    }

    public int getNumberOfVerticies() {
        return vertexData.size();
    }

    public int getIndexSize() {
        return indices.size();
    }

    public int getVertexArrayID() {
        return vertexArray;
    }

    public int getIndexBufferID() {
        return indexBufferID;
    }
}
