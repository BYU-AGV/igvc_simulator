package com.byu_igvc.core.scene.model;

import org.lwjgl.assimp.*;
import static org.lwjgl.assimp.Assimp.*;

public class Material {
    public AIMaterial mMaterial;
    public AIColor4D mAmbientColor;
    public AIColor4D mDiffuseColor;
    public AIColor4D mSpecularColor;

    public Material(AIMaterial material) {

        mMaterial = material;

        mAmbientColor = AIColor4D.create();
        if (aiGetMaterialColor(mMaterial, AI_MATKEY_COLOR_AMBIENT,
                aiTextureType_NONE, 0, mAmbientColor) != 0) {
            throw new IllegalStateException(aiGetErrorString());
        }
        mDiffuseColor = AIColor4D.create();
        if (aiGetMaterialColor(mMaterial, AI_MATKEY_COLOR_DIFFUSE,
                aiTextureType_NONE, 0, mDiffuseColor) != 0) {
            throw new IllegalStateException(aiGetErrorString());
        }
        mSpecularColor = AIColor4D.create();
        if (aiGetMaterialColor(mMaterial, AI_MATKEY_COLOR_SPECULAR,
                aiTextureType_NONE, 0, mSpecularColor) != 0) {
            throw new IllegalStateException(aiGetErrorString());
        }
    }
}
