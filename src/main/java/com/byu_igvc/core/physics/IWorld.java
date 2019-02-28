package com.byu_igvc.core.physics;

import com.byu_igvc.core.render.IEngine;
import com.byu_igvc.core.render.IRenderEngine;

public interface IWorld extends IEngine {
    public void setRenderEngine(IRenderEngine engine);
    public void setPhysicsEngine();
    public void simulate();
    public void tick();
    public void render();
}
