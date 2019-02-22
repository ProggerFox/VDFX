/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.voidjinn.lib.vdfx.asset3d.asset;

import net.voidjinn.lib.vdfx.asset3d.importer.ImporterData;
import net.voidjinn.lib.vdfx.asset3d.scene.shape.PolygonMesh;

/**
 *
 * @author Berger
 */
public class AssetDynamicMesh extends AssetMesh {
  
    public AssetDynamicMesh(ImporterData data) {
        this(data, MeshType.DYNAMIC);
    }

    protected AssetDynamicMesh(ImporterData data, MeshType meshType) {
        this(data, meshType, new PolygonMesh());
    }

    protected AssetDynamicMesh(ImporterData data, MeshType meshType, PolygonMesh mesh) {
        super(data, meshType);
    }

}
