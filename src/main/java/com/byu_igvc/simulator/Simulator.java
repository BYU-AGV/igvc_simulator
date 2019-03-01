package com.byu_igvc.simulator;

import com.byu_igvc.core.input.InputManager;
import com.byu_igvc.core.physics.IWorld;
import com.byu_igvc.core.render.IRenderEngine;
import com.byu_igvc.core.render.Mesh;
import com.byu_igvc.core.render.Shader;
import com.byu_igvc.core.scene.Camera;
import com.byu_igvc.core.scene.Model;
import com.byu_igvc.core.scene.Position;
import glm.vec._3.Vec3;

import static org.lwjgl.glfw.GLFW.*;

public class Simulator implements IWorld {
    private IRenderEngine renderEngine;
    private InputManager inputManager;
    private Mesh mesh;
    private Model model;
    private Camera camera;

    @Override
    public void setRenderEngine(IRenderEngine renderEngine) {
        this.renderEngine = renderEngine;
    }

    @Override
    public void init() {
        renderEngine.init();
        mesh = new Mesh(new Shader("src/main/resources/shaders/vert.glsl", "src/main/resources/shaders/frag.glsl"));
        mesh.addVertex(new Vec3(-1, -1, 0));
        mesh.addVertex(new Vec3(1, -1, 0));
        mesh.addVertex(new Vec3(0, 1, 0));
        mesh.compile();
        model = new Model(mesh, new Position());
        camera = new Camera();
        inputManager = new InputManager();
        inputManager.init();
    }

    @Override
    public void setPhysicsEngine() {

    }

    @Override
    public void simulate() {
        while (!renderEngine.shouldShutDown()) {
            tick();
            render();
        }
        renderEngine.shutdown();
    }

    @Override
    public void tick() {
        if (inputManager.isKeyDown(GLFW_KEY_D))
            camera.moveCamera(new Vec3(0.05, 0, 0));
        if (inputManager.isKeyDown(GLFW_KEY_A))
            camera.moveCamera(new Vec3(-0.05, 0, 0));
    }

    @Override
    public void render() {
        renderEngine.startFrame();
        renderEngine.renderModel(camera, model);
        renderEngine.updateWindow();
    }

    @Override
    public void shutdown() {
        renderEngine.shutdown();
    }
}
