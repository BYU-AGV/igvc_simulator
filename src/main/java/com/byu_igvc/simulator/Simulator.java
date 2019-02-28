package com.byu_igvc.simulator;

import com.byu_igvc.core.physics.IWorld;
import com.byu_igvc.core.render.IRenderEngine;
import com.byu_igvc.core.render.Mesh;
import com.byu_igvc.core.render.Shader;
import com.byu_igvc.core.scene.Camera;
import com.byu_igvc.core.scene.Model;
import com.byu_igvc.core.scene.Position;

public class Simulator implements IWorld {
    private IRenderEngine renderEngine;
    private Mesh mesh;
    private Model model;
    private Camera camera;

    @Override
    public void setRenderEngine(IRenderEngine renderEngine) {
        this.renderEngine = renderEngine;
    }

    @Override
    public void setPhysicsEngine() {

    }

    @Override
    public void simulate() {
        while (!renderEngine.shouldShutDown()) {
            renderEngine.startFrame();
            renderEngine.renderModel(camera, model);
            renderEngine.updateWindow();
        }
        renderEngine.shutdown();
    }

    @Override
    public void init() {
        renderEngine.init();
        mesh = new Mesh(new Shader("src/main/resources/shaders/vert.glsl", "src/main/resources/shaders/frag.glsl"));
        mesh.compile();
        model = new Model(mesh, new Position());
        camera = new Camera();
    }

    @Override
    public void shutdown() {
        renderEngine.shutdown();
    }
}
