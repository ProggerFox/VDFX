/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.voidjinn.lib.vdfx.asset3d.asset;

import net.voidjinn.lib.vdfx.asset3d.importer.ImporterData;



/**
 *
 * @author Berger
 */
public class AssetSkinnedMesh extends AssetDynamicMesh {

    private final AssetArmature armatureAsset;

    public AssetSkinnedMesh(AssetArmature armature, ImporterData data) {
        super(data, MeshType.SKINNED);
        this.armatureAsset = armature;
    }

    public AssetArmature getArmatureAsset() {
        return armatureAsset;
    }
}
