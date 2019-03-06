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
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Simulator implements IWorld {
    private IRenderEngine renderEngine;
    private InputManager inputManager;
    private Mesh mesh;
    private Model model;
    private Camera camera;
    private AiModel importedModel;

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
        mesh = Mesh.createCube(2);
        model = new Model(mesh, new Vector3f());
        inputManager.init();
        inputManager.registerListener(CursorMoveEvent.class, new CursorMoveListener() {
            @Override
            public void cursorMove(double x, double y) {
                camera.updateMatricesFromInput(x, y, 0.0016);
            }
        });
        importedModel = AssimpModelLoader.loadModel("src/main/resources/magnet/magnet.obj");
//        importedModel = AssimpModelLoader.loadModel("src/main/resources/magnet/magnet.obj");
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
        renderEngine.renderModel(camera, importedModel);
        renderEngine.updateWindow();
    }

    @Override
    public void shutdown() {
        renderEngine.shutdown();
    }
}
