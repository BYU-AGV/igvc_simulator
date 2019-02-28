package com.byu_igvc.simulator;

import com.byu_igvc.core.input.InputManager;
import com.byu_igvc.core.input.event.KeyboardEvent;
import com.byu_igvc.core.input.listener.KeyboardListener;
import com.byu_igvc.core.physics.IWorld;
import com.byu_igvc.core.render.IRenderEngine;
import com.byu_igvc.core.render.Mesh;
import com.byu_igvc.core.render.Shader;
import com.byu_igvc.core.scene.Camera;
import com.byu_igvc.core.scene.Model;
import com.byu_igvc.core.scene.Position;
import com.byu_igvc.logger.Logger;

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
        mesh.compile();
        model = new Model(mesh, new Position());
        camera = new Camera();
        inputManager = new InputManager();
        inputManager.init();
        registerEventListeners();
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
    public void shutdown() {
        renderEngine.shutdown();
    }

    private void registerEventListeners() {
        inputManager.registerListener(KeyboardEvent.class, new KeyboardListener() {
            @Override
            public void keyPressed(int key) {
                Logger.fine("Key pressed: " + key);
            }

            @Override
            public void keyReleased(int key) {
                Logger.fine("Key released: " + key);
            }

            @Override
            public void keyTyped(int key) {
                Logger.fine("Key typed: " + key);
            }
        });
    }
}
