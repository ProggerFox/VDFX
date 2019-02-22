/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.voidjinn.lib.vdfx.asset3d.importer;

import java.util.*;
import javafx.scene.paint.Material;
import net.voidjinn.lib.vdfx.asset3d.asset.AssetArmature;
import net.voidjinn.lib.vdfx.asset3d.asset.AssetMesh;

/**
 *
 * @author CibonTerra
 */
public class ImporterData {

  private final Map<String, AssetMesh> meshes = new HashMap<>();
  private final Map<String, Material> materials = new HashMap<>();
  private final Map<String, AssetArmature> armatures = new HashMap<>();

  public Map<String, AssetMesh> getMeshes() {
    return meshes;
  }

  public Map<String, Material> getMaterials() {
    return materials;
  }

  public Map<String, AssetArmature> getArmatures() {
    return armatures;
  }

  public boolean isWholeScene() {
    return meshes.size() > 1;
  }
}
