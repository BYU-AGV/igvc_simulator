package com.byu_igvc.core.scene;

import com.byu_igvc.core.render.OpenGLRenderingEngine;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    /**
     * Projection matrix
     */
    private Matrix4f projection = new Matrix4f();
    /**
     * Camera view matrix
     */
    private Matrix4f view = new Matrix4f();

    /**
     * Vectors to keep track of direction, position and crossed vectors
     */
    private Vector3f position;
    private Vector3f direction;
    private Vector3f right;
    private Vector3f cameraUp;
    private Vector3f worldUp;

    private float xOffset = 0.0f;
    private float yOffset = 0.0f;
    private float xOld = 0;
    private float yOld = 0;

    private float sensitivity = 0.5f;

    private static final float CAMERA_NEAR = 0.01f;
    private static final float CAMERA_FAR = 1000f;
    private static final float CAMERA_FOV = 45;

    public Camera() {
        projection.setPerspective((float) Math.toRadians(CAMERA_FOV), OpenGLRenderingEngine.getWidth() / OpenGLRenderingEngine.getHeight(), CAMERA_NEAR, CAMERA_FAR);
        position = new Vector3f(0, 0, 3);
        direction = new Vector3f(0, 0, -1);
        right = new Vector3f();
        cameraUp = new Vector3f(0, 1, 0);
        worldUp = new Vector3f(0, 1, 0);
       updateMatricesFromInput(0 ,0 ,1);
    }

    /**
     * This will get a projection matrix that will give perspective
     * TODO must find out why the coordinate system is flipped. X and Y axises are flipped
     * @return projection matrix
     */
    public Matrix4f getProjectionMatrix() {
        return projection.setPerspective((float) Math.toRadians(CAMERA_FOV), OpenGLRenderingEngine.getWidth() / OpenGLRenderingEngine.getHeight(), CAMERA_NEAR, CAMERA_FAR);
    }

    /**
     * Gets the view matrix
     * @return matrix that is transformed and rotated to reflect camera
     */
    public Matrix4f getViewMatrix() {
        Vector3f front = new Vector3f(position);
        return view.identity().lookAt(position, front.add(direction), worldUp);
    }

    public void goForward() {
        position.add(direction);
    }

    public void goBackward() {
        position.sub(direction);
    }

    public void goLeft() {
        position.add(right);
    }

    public void goRight() {
        position.sub(right);
    }

    public void goUp() {
        position.sub(cameraUp);
    }

    public void goDown() {
        position.add(cameraUp);
    }

    public void rotateHorizontal(float degree) {
        updateMatricesFromInput(degree, 0, 1);
    }

    public void rotateVertical(float degree) {
        updateMatricesFromInput(0, degree, 1);
    }

    public void updateMatricesFromInput(double x, double y, double deltatime) {
        xOffset -= (xOld - x) * sensitivity * deltatime;
        yOffset -= (y - yOld) * sensitivity * deltatime;

        direction.x = (float) Math.cos((xOffset) * Math.cos(yOffset));
        direction.y = (float) Math.sin((yOffset));
        direction.z = (float) (Math.sin(xOffset) * Math.cos(yOffset));
        direction.normalize();

        direction.cross(worldUp, right);
        right.negate().normalize();
        right.cross(direction, cameraUp);
        cameraUp.normalize();

        xOld = (float) x;
        yOld = (float) y;
    }

    public float getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }
}
