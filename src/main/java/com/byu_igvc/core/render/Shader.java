package com.byu_igvc.core.render;

import com.byu_igvc.logger.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AIColor4D;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL33C.*;

public class Shader {
    private String vertexPath;
    private String fragmentPath;

    private int vertexID;
    private int fragmentID;
    private int programID;

    public Shader(String vertexPath, String fragmentPath) {
        this.vertexPath = vertexPath;
        this.fragmentPath = fragmentPath;
    }

    public void compile() {
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        String vertexCode = null;
        String fragmentCode = null;
        try {
            vertexCode = new String(Files.readAllBytes(Paths.get(vertexPath)));
            fragmentCode = new String(Files.readAllBytes(Paths.get(fragmentPath)));
        } catch (IOException e) {
            Logger.error("Unable to read shaders", e);
        }

        glShaderSource(vertexID, vertexCode);
        glCompileShader(vertexID);

        Logger.fine("Vertex shader compilation status: " + glGetShaderi(vertexID, GL_COMPILE_STATUS));

        glShaderSource(fragmentID, fragmentCode);
        glCompileShader(fragmentID);

        Logger.fine("Fragment shader compilation status: " + glGetShaderi(fragmentID, GL_COMPILE_STATUS));

        programID = glCreateProgram();
        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);
        glLinkProgram(programID);
    }

    public int getProgramID() {
        return programID;
    }

    public static void setUniformMat4(Shader shader, String name, Matrix4f mat4) {
        int location = glGetUniformLocation(shader.getProgramID(), name);
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        mat4.get(fb);
        glUniformMatrix4fv(location, false, fb);
    }

    public static void setUniformMat4(Shader shader, String name, FloatBuffer mat4) {
        int location = glGetUniformLocation(shader.getProgramID(), name);
        glUniformMatrix4fv(location, false, mat4);
    }

    public static void setUniformVec3(Shader shader, String name, Vector3f vec3) {
        int location = glGetUniformLocation(shader.getProgramID(), name);
        glUniform3f(location, vec3.x, vec3.y, vec3.y);
    }

    public static void setUniformVec3(Shader shader, String name, AIColor4D vec3) {
        int location = glGetUniformLocation(shader.getProgramID(), name);
        glUniform3f(location, vec3.r(), vec3.g(), vec3.b());
    }

    public static void setUniformVec4(Shader shader, String name, AIColor4D color4D) {
        int location = glGetUniformLocation(shader.getProgramID(), name);
        glUniform4f(location, color4D.r(), color4D.g(), color4D.b(), color4D.a());
    }
}
