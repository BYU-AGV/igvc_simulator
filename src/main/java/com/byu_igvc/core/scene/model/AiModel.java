package com.byu_igvc.core.scene.model;

import com.byu_igvc.core.render.Shader;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.aiReleaseImport;

public class AiModel {
    private AIScene scene; // This holds information about the model
    private List<ImportedMesh> meshes; // List of meshes that make up the body
    private List<Material> materials; // List of materials accosted with the mesh
    private Vector3f position; // Position of the mesh TODO remove this into a separate class as we will need to incorporate shared resources
    private Shader shader;

    public AiModel(AIScene scene, Shader shader) {
        this.scene = scene;
        this.shader = shader;

        int meshCount = scene.mNumMeshes();
        PointerBuffer meshesBuffer = scene.mMeshes();
        meshes = new ArrayList<>();
        for (int i = 0; i < meshCount; ++i) {
            meshes.add(new ImportedMesh(AIMesh.create(meshesBuffer.get(i))));
        }

        int materialCount = scene.mNumMaterials();
        PointerBuffer materialsBuffer = scene.mMaterials();
        materials = new ArrayList<>();
        for (int i = 0; i < materialCount; ++i) {
            materials.add(new Material(AIMaterial.create(materialsBuffer.get(i))));
        }
    }

    public void compile() {
        shader.compile();
        for (ImportedMesh mesh : meshes)
            mesh.compile();
    }

    public Vector3f getPosition() {
        return position;
    }
    public List<ImportedMesh> getMeshes() {return meshes;}
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Shader getShader() {
        return shader;
    }

    /**
     * Clears the data so we keep things clean
     */
    public void destroy() {aiReleaseImport(scene);}
}
