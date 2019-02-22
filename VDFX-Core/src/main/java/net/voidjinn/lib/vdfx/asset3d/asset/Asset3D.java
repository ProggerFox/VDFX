/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.voidjinn.lib.vdfx.asset3d.asset;

import javafx.geometry.Point3D;
import net.voidjinn.lib.vdfx.asset3d.importer.ImporterData;

/**
 *
 * @author Berger
 */
public abstract class Asset3D {

  private final ImporterData root;

  public Asset3D(ImporterData root) {
    this.root = root;
  }

  public ImporterData getRoot() {
    return root;
  }
}
