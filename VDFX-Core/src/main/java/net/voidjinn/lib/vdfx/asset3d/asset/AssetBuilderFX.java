/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.voidjinn.lib.vdfx.asset3d.asset;

import java.util.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Affine;
import net.voidjinn.lib.vdfx.asset3d.importer.ImporterData;
import net.voidjinn.lib.vdfx.asset3d.scene.shape.*;

/**
 *
 * @author Berger
 */
public class AssetBuilderFX {

    public Node build(ImporterData data) {
        Node node = null;
        if (data != null) {
            if (data.isWholeScene()) {
                final Group scene = new Group();
                data.getMeshes().entrySet().forEach((entry) -> {
                    scene.getChildren().add(processAssetMesh(entry.getValue()));
                });
                node = scene;
            } else {
                if (data.getMeshes().size() == 1) {
                    node = processAssetMesh(data.getMeshes().values().iterator().next());
                }
            }
        }
        return node;
    }

    public PolygonMeshView processAssetMesh(AssetMesh assetMesh) {
        switch (assetMesh.getMeshType()) {
            /*
      case STATIC:
        final AssetStaticMesh staticMesh = (AssetStaticMesh) assetMesh;
        return setPolygonMeshView(staticMesh.testBuildPM(), staticMesh);
      //return setMeshView(buildStaticMesh(staticMesh), staticMesh);
             */
            case DYNAMIC:
                final AssetDynamicMesh dynamicMesh = (AssetDynamicMesh) assetMesh;
                return setPolygonMeshView(buildDynamicMesh(dynamicMesh), dynamicMesh);
            case SKINNED:
                final AssetSkinnedMesh skinnedMesh = (AssetSkinnedMesh) assetMesh;
                final PolygonMeshView skinningMeshView = setPolygonMeshView(buildSkinnedMesh(skinnedMesh), skinnedMesh);
                skinningMeshView.setDrawMode(DrawMode.LINE);
                return skinningMeshView;
            default:
                return null;
        }
    }

    public Mesh buildStaticMesh(AssetStaticMesh staticMesh) {
        final TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(staticMesh.getMeshData().getPoints());
        mesh.getTexCoords().addAll(staticMesh.getMeshData().getTexCoords());
        for (int[] face : staticMesh.getMeshData().getFaces()) {
            mesh.getFaces().addAll(face);
        }
        return mesh;
    }

    public PolygonMesh buildDynamicMesh(AssetDynamicMesh dynamicMesh) {
        final PolygonMesh mesh = new PolygonMesh(
                dynamicMesh.getMeshData().getPoints(),
                dynamicMesh.getMeshData().getTexCoords(),
                dynamicMesh.getMeshData().getFaces());
        mesh.getFaceSmoothingGroups().addAll(dynamicMesh.getMeshData().getFaceSmoothingGroups());
        return mesh;
    }

    public SkinningMesh buildSkinnedMesh(AssetSkinnedMesh skinnedMesh) {
        final AssetArmature armature = skinnedMesh.getArmatureAsset();
        final List<Joint> joints = new ArrayList<>(armature.getJoints().values());
        final SkinningMesh skinningMesh = new SkinningMesh(
                buildDynamicMesh(skinnedMesh),
                skinnedMesh.getMeshData().getWeights(),
                (Affine[])armature.getBindTransforms().toArray(),
                armature.getBindGlobalTransform(),
                joints,
                armature.getJointForest());
        return skinningMesh;
    }

    protected MeshView setMeshView(Mesh mesh, AssetStaticMesh asset) {
        final MeshView view = new MeshView(mesh);
        view.setId(asset.getId());
        view.setMaterial(asset.getRoot().getMaterials().get(asset.getMaterial()));
        view.setCullFace(CullFace.BACK);
        return view;
    }

    protected PolygonMeshView setPolygonMeshView(PolygonMesh mesh, AssetMesh asset) {
        final PolygonMeshView view = new PolygonMeshView(mesh);
        view.setId(asset.getId());
        if (asset.hasMaterial()) {
            view.setMaterial(asset.getRoot().getMaterials().get(asset.getMaterial()));
        }
        view.setCullFace(CullFace.BACK);
        return view;
    }
}
