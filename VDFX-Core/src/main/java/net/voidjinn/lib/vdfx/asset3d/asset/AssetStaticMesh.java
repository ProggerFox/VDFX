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
public class AssetStaticMesh extends AssetMesh {

  public AssetStaticMesh(ImporterData data) {
    super(data, MeshType.STATIC);
  }

  @Override
  public String getArmature() {
    return null;
  }

  @Override
  public boolean hasArmature() {
    return false;
  }
}
