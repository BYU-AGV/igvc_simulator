package com.byu_igvc.core.render;

import com.byu_igvc.core.scene.Camera;
import com.byu_igvc.core.scene.model.AiModel;
import com.byu_igvc.core.scene.model.Model;
import org.joml.Matrix4f;

public interface IRenderEngine extends IEngine{
    public void init();
    public void renderMesh(Mesh mesh, Matrix4f mvp);
    public void renderModel(Camera camera, Model model);
    public void renderModel(Camera camera, AiModel model);
    public void startFrame();
    public void updateWindow();
    public boolean shouldShutDown();
    public void shutdown();

    public float getWidowWidth();
    public float getWindowHeight();
}
