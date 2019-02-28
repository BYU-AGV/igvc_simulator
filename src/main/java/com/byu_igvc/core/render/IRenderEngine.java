package com.byu_igvc.core.render;

import com.byu_igvc.core.scene.Camera;
import com.byu_igvc.core.scene.Model;
import com.byu_igvc.core.scene.Position;
import glm.mat._4.Mat4;

public interface IRenderEngine extends IEngine{
    public void init();
    public void renderMesh(Mesh mesh, Mat4 modelViewProjection);
    public void renderModel(Camera camera, Model model);
    public void startFrame();
    public void updateWindow();
    public boolean shouldShutDown();
    public void shutdown();

    public float getWidowWidth();
    public float getWindowHeight();
}
