package com.byu_igvc.simulator;

import com.byu_igvc.core.input.InputManager;
import com.byu_igvc.core.input.event.CursorMoveEvent;
import com.byu_igvc.core.input.listener.CursorMoveListener;
import com.byu_igvc.core.physics.IWorld;
import com.byu_igvc.core.render.*;
import com.byu_igvc.core.scene.Camera;
import com.byu_igvc.core.scene.model.AiModel;
import com.byu_igvc.core.scene.model.AssimpModelLoader;
import com.byu_igvc.core.scene.model.Model;
import com.byu_igvc.logger.Logger;
import glm.vec._3.Vec3;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Simulator implements IWorld {
    private IRenderEngine renderEngine;
    private InputManager inputManager;
    private Mesh mesh;
    private Model model;
    private Model model1;
    private Camera camera;
    private AiModel importedModel;
    private Mesh mesh2;

    public Simulator() {
        camera = new Camera();
        inputManager = new InputManager();
    }

    @Override
    public void setRenderEngine(IRenderEngine renderEngine) {
        this.renderEngine = renderEngine;
    }

    @Override
    public void init() {
        renderEngine.init();
        mesh = new Mesh(new Shader("src/main/resources/shaders/vert.glsl", "src/main/resources/shaders/frag.glsl"));
        mesh.addVertex(new Vec3(-1, -1, 0)).addVertex(new Vec3(1, -1, 0)).addVertex(new Vec3(0, 1, 0));
        mesh.compile();
        mesh2 = new Mesh(new Shader("src/main/resources/shaders/vert.glsl", "src/main/resources/shaders/frag.glsl"))
                .addVertex(new Vec3(-10, 0, -10))
                .addVertex(new Vec3(-10, 0, 10))
                .addVertex(new Vec3(10, 0, 10))
                .addVertex(new Vec3(-10, 0, -10))
                .addVertex(new Vec3(10, 0, 10))
                .addVertex(new Vec3(10, 0, -10));
        mesh2.compile();
        model1 = new Model(mesh2, new Vector3f(1, 1, 1));
        model = new Model(mesh, new Vector3f());
        inputManager.init();
        inputManager.registerListener(CursorMoveEvent.class, new CursorMoveListener() {
            @Override
            public void cursorMove(double x, double y) {
                camera.updateMatricesFromInput(x, y, 0.0016);
            }
        });
        importedModel = AssimpModelLoader.loadModel("src/main/resources/magnet/magnet.obj");
        importedModel.compile();
    }

    @Override
    public void setPhysicsEngine() {

    }

    @Override
    public void simulate() {
        while (!renderEngine.shouldShutDown()) {
            FPS.startFrame();
            tick();
            render();
            FPS.endFrame();
        }
        Logger.head("Shutting down");
        renderEngine.shutdown();
        inputManager.shutdown();
    }

    @Override
    public void tick() {
        if (inputManager.isKeyDown(GLFW_KEY_D))
            camera.goRight();
        if (inputManager.isKeyDown(GLFW_KEY_A))
            camera.goLeft();
        if (inputManager.isKeyDown(GLFW_KEY_W))
            camera.goForward();
        if (inputManager.isKeyDown(GLFW_KEY_S))
            camera.goBackward();
        if (inputManager.isKeyDown(GLFW_KEY_LEFT_SHIFT))
            camera.goUp();
        if (inputManager.isKeyDown(GLFW_KEY_LEFT_CONTROL))
            camera.goDown();
        if (inputManager.isKeyDown(GLFW_KEY_ESCAPE))
            triggerShutdownEvent();
    }

    private void triggerShutdownEvent() {
        glfwSetWindowShouldClose(OpenGLRenderingEngine.getWindow(), true);
    }

    @Override
    public void render() {
        renderEngine.startFrame();
        renderEngine.renderModel(camera, model);
        renderEngine.renderModel(camera, model1);
        renderEngine.renderModel(camera, importedModel);
        renderEngine.updateWindow();
    }

    @Override
    public void shutdown() {
        renderEngine.shutdown();
    }
}
