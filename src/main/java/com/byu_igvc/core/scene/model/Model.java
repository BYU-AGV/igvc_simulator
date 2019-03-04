package com.byu_igvc.core.scene.model;

import com.byu_igvc.core.render.Mesh;
import org.joml.Vector3f;

public class Model {
    private Vector3f position;
    private Mesh mesh;

    public Model(Mesh mesh, Vector3f position) {
        this.position = position;
        this.mesh = mesh;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
