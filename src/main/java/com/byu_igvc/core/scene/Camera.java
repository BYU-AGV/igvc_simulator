package com.byu_igvc.core.scene;

import com.byu_igvc.core.render.OpenGLRenderingEngine;
import glm.Glm;
import glm.mat._4.Mat4;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class Camera {
    Matrix4f projection = new Matrix4f();
    Matrix4f view = new Matrix4f();

    FloatBuffer fb = BufferUtils.createFloatBuffer(16);
    Vector3f pos;
    Vector3f dir;
    Vector3f right;
    Vector3f up;

    float xOffset = 0.0f;
    float yOffset = 0.0f;

    float sensitivity = 0.5f;
    private double xOld = 0;
    private double yOld = 0;

    public Camera() {
        projection.setPerspective((float) Math.toRadians(45), OpenGLRenderingEngine.getWidth() / OpenGLRenderingEngine.getHeight(), 0.01f, 1000f);
        pos = new Vector3f(0, 0, 3);
        dir = new Vector3f(0, 0, 1);
        right = new Vector3f();
        up = new Vector3f();
       updateMatricesFromInput(0 ,0 ,0);
    }

    /**
     * This will get a projection matrix that will give perspective
     * TODO must find out why the coordinate system is flipped. X and Y axises are flipped
     * @return projection matrix
     */
    public Matrix4f getProjectionMatrix() {
        return projection.setPerspective((float) Math.toRadians(45), OpenGLRenderingEngine.getWidth() / OpenGLRenderingEngine.getHeight(), 0.01f, 1000f).rotateZ((float) Math.toRadians(-90));
    }

    /**
     * Gets the view matrix
     * @return
     */
    public Matrix4f getViewMatrix() {
        return view.identity().rotateX(xOffset).rotateY(yOffset).translate(-pos.x, -pos.y, -pos.z);
    }

    public void goForward() {
        pos.add(dir);
    }

    public void goBackward() {
        pos.sub(dir);
    }

    public void goLeft() {
        pos.add(right);
    }

    public void goRight() {
        pos.sub(right);
    }

    public void goUp() {
        pos.add(up);
    }

    public void goDown() {
        pos.sub(up);
    }

    public void updateMatricesFromInput(double x, double y, double deltatime) {
        xOffset += (xOld - x) * sensitivity * deltatime;
        yOffset += (y - yOld) * sensitivity * deltatime;

        xOld = x;
        yOld = y;

        view.identity().rotateY(yOffset).rotateX(xOffset).translate(-pos.x, -pos.y, -pos.z); // create view vector
        view.positiveZ(dir).negate(); // Create direction vector
        view.positiveY(right).negate(); // create right vector
        view.positiveX(up).negate(); // create up vector
    }
}
