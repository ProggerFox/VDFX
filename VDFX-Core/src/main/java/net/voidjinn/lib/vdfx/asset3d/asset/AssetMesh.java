/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.voidjinn.lib.vdfx.asset3d.asset;

import javafx.scene.shape.*;
import net.voidjinn.lib.vdfx.asset3d.importer.ImporterData;
import net.voidjinn.lib.vdfx.asset3d.scene.shape.*;

/**
 *
 * @author CibonTerra
 */
public abstract class AssetMesh extends Asset3D {

    private final MeshData meshData = new MeshData();
    private String id = "default";
    private String material = null;
    private String armature = null;
    private final MeshType meshType;
    private VertexFormat format = VertexFormat.POINT_TEXCOORD;

    public AssetMesh(ImporterData data, MeshType meshType) {
        super(data);
        this.meshType = meshType;
    }

    public MeshData getMeshData() {
        return meshData;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFormat(VertexFormat format) {
        this.format = format;
    }

    public VertexFormat getFormat() {
        return format;
    }

    public String getId() {
        return id;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public boolean hasMaterial() {
        return material != null;
    }

    public void setArmature(String armature) {
        this.armature = armature;
    }

    public String getArmature() {
        return armature;
    }

    public boolean hasArmature() {
        return armature != null;
    }

    public MeshType getMeshType() {
        return meshType;
    }

    private PolygonMeshView testView;

    public void setTestView(PolygonMesh mesh) {
        this.testView = new PolygonMeshView(mesh);
        testView.setCullFace(CullFace.BACK);
    }

    public PolygonMeshView getTestView() {
        return testView;
    }

    public enum MeshType {
        STATIC,
        DYNAMIC,
        SKINNED
    }
}
