package com.byu_igvc.core.scene;

import com.byu_igvc.core.render.OpenGLRenderingEngine;
import com.byu_igvc.logger.Logger;
import glm.Glm;
import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import jglm.Mat;

public class Camera {
    Mat4 projection;

    Vec3 position;
    Vec3 forward;
    Vec3 up;

    private double xOld = 0, yOld = 0;
    private double pitch = 0;
    private double yaw = 0;
    private double mouseSpeed = 1;
    double horizontalAngle = 0;
    double verticalAngle = 0;

    public Camera() {
        projection = Glm.perspective(45, OpenGLRenderingEngine.getWidth() / OpenGLRenderingEngine.getHeight(), 0.1f, 100.0f, new Mat4());
        forward = new Vec3(0, 0, -1);
        up = new Vec3(0, 1, 0);
        position = new Vec3(0, 0, 3);
    }

    public Mat4 getProjectionMatrix() {
        return Glm.perspective(45, OpenGLRenderingEngine.getWidth() / OpenGLRenderingEngine.getHeight(), 0.1f, 100.0f, new Mat4());
    }

    public Mat4 getViewMatrix() {
//        return Glm.lookAt(new Vec3(0, 0, 3), new Vec3(0, 0, -1), new Vec3(0, 1, 0), new Mat4());
//        return Glm.lookAt(position, Glm.add(position, forward), up, new Mat4());
        return Glm.lookAt(position, forward, up, new Mat4());
    }

    public void moveCamera(Vec3 vec3) {
        this.position.add(vec3);
    }

    public void updateMatricesFromInput(double x, double y, double deltatime) {

        double xOffset = x - xOld;
        double yOffset = yOld - y;

        Logger.fine("Mouse x: " + xOffset + ", Mouse y: " + yOffset);

        xOffset *= 0.05;
        xOffset *= 0.05;

        pitch += xOffset;
        yaw += yOffset;

        if(pitch > 89.0f)
            pitch =  89.0f;
        if(pitch < -89.0f)
            pitch = -89.0f;

        forward.set(new Vec3(
                (Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw))),
                Math.sin(Math.toRadians(pitch)),
                (Math.cos(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw)))
        ));
        Vec3 front = new Vec3();
        front.x = (float) (Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw)));
        front.y = (float) Math.sin(Math.toRadians(pitch));
        front.z = (float) (Math.cos(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw)));
        forward = front.normalize();
        Logger.fine("Forward: " + forward.toString());


        xOld = x;
        yOld = y;
    }
}
