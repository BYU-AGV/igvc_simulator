package com.byu_igvc.core.scene;

import com.byu_igvc.core.render.Mesh;

public class Model {
    private Mesh mesh;
    private Position position;

    public Model(Mesh mesh, Position position) {
        this.mesh = mesh;
        this.position = position;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
