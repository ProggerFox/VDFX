/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.voidjinn.lib.vdfx.asset3d.asset;

import java.util.*;
import javafx.scene.Parent;
import javafx.scene.transform.Affine;
import net.voidjinn.lib.vdfx.asset3d.importer.ImporterData;

/**
 *
 * @author Berger
 */
public class AssetArmature extends Asset3D {

  private final Affine bindGlobalTransform = new Affine();
  private final Map<String, BoneData> joints = new HashMap<>();
  private final List<Parent> jointForest = new ArrayList<>();
  private final List<Affine> bindTransforms = new ArrayList<>();

  public AssetArmature(ImporterData root) {
    super(root);
  }

  public Affine getBindGlobalTransform() {
    return bindGlobalTransform;
  }

  public Map<String, BoneData> getJoints() {
    return joints;
  }

  public List<Parent> getJointForest() {
    return jointForest;
  }

  public List<Affine> getBindTransforms() {
    return bindTransforms;
  }

}
