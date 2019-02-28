package com.byu_igvc.core.scene;

import com.byu_igvc.core.render.OpenGLRenderingEngine;
import glm.Glm;
import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import jglm.Mat;

public class Camera {
    Mat4 projection;
    Vec3 position;
    Vec3 forward;
    Vec3 up;

    public Camera() {
        projection = Glm.perspective(45, OpenGLRenderingEngine.getWidth() / OpenGLRenderingEngine.getHeight(), 0.1f, 100.0f, new Mat4());
        forward = new Vec3(0, 0, 1);
        up = new Vec3(0, 1, 0);
        position = new Vec3(4, 3, 3);
    }

    public Mat4 getProjectionMatrix() {
        return Glm.perspective(45, OpenGLRenderingEngine.getWidth() / OpenGLRenderingEngine.getHeight(), 0.1f, 100.0f, new Mat4());
    }

    public Mat4 getViewMatrix() {
        return Glm.lookAt(position, forward, up, new Mat4());
    }
}
