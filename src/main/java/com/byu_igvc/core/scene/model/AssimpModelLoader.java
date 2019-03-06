package com.byu_igvc.core.scene.model;

import com.byu_igvc.core.render.Shader;
import org.lwjgl.assimp.*;
import java.nio.file.Paths;

import static org.lwjgl.assimp.Assimp.*;

public class AssimpModelLoader {
    public static AiModel loadModel(String path) {
        AIScene scene = aiImportFile(Paths.get(path).toString(),
                aiProcess_JoinIdenticalVertices | aiProcess_Triangulate);
        if (scene == null) {
            throw new IllegalStateException(aiGetErrorString());
        }

        return new AiModel(scene, new Shader("src/main/resources/shaders/vert.glsl", "src/main/resources/shaders/frag.glsl"));
    }
}
