/*
Copyright (c) 2018-2019, FalcoTheBold
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.voidjinn.lib.gltf4fx;

/*
import de.javagl.jgltf.model.*;
import de.javagl.jgltf.model.io.*;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.logging.*;
import javafx.geometry.Point3D;
import javafx.scene.paint.*;
import javafx.scene.transform.*;
import net.voidjinn.asset.AssetArmature;
import net.voidjinn.importer.Importer;
import net.voidjinn.importer.ImporterData;
import net.voidjinn.lib.vdfx.scene.shape.*;
 */
/**
 *
 * @author FalcoTheBold
 */
public class ImporterGltf /*extends Importer*/ {
//
//    //Modes
//    //private boolean skeletal = false;//hide or show bones(joints)
//    //Reader-Tools
//    private final GltfAssetReader reader;
//
//    public ImporterGltf() {
//        this.reader = new GltfAssetReader();
//    }
//
//    @Override
//    public ImporterData load(URL url) throws IOException {
//        try {
//            //Get asset-informations
//            final GltfAsset asset = reader.read(url.toURI());
//            final GltfModel model = GltfModels.create(asset);
//            final ImporterData data = new ImporterData();
//            for (MaterialModel materialModel : model.getMaterialModels()) {
//                processMaterial(materialModel, data);
//            }
//            for (int s = 0; s < model.getSceneModels().size(); s++) {
//                processScene(data, model.getSceneModels().get(s), s);
//            }
//            return data;
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(ImporterGltf.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
//
//    @Override
//    public boolean isSupported(String supportType) {
//        return true;
//        //return extension != null && extension.equals("obj");
//    }
//
//    private void processMaterial(MaterialModel model, ImporterData data) {
//        //System.out.println("Material-Attributes(" + model.getName() + "): " + model.getValues());
//        final PhongMaterial material = new PhongMaterial(Color.GREY);
//        if (model.getValues().containsKey("baseColorFactor")) {
//            final float[] baseColor = (float[]) model.getValues().get("baseColorFactor");
//            material.setDiffuseColor(new Color(baseColor[0], baseColor[1], baseColor[2], baseColor[3]));
//        }
//        if (model.getValues().containsKey("hasBaseColorTexture")
//                && (int) model.getValues().get("hasBaseColorTexture") == 1) {
//        }
//        data.getMaterials().put(model.getName(), material);
//    }
//
//    private void processScene(ImporterData data, SceneModel sceneModel, int sceneNr) {
//        final String sceneName = sceneModel.getName() != null ? sceneModel.getName() : "scene" + sceneNr;
//        for (int n = 0; n < sceneModel.getNodeModels().size(); n++) {
//            processNode(data, sceneName, sceneModel.getNodeModels().get(n), n);
//        }
//    }
//
//    private void processNode(ImporterData data, String path, NodeModel nodeModel, int nodeNr) {
//        final String pathNow = path + "." + (nodeModel.getName() != null ? nodeModel.getName() : "node" + nodeNr);
//        //Check if skin is available and process armature
//        if (nodeModel.getSkinModel() != null) {
//            processSkin(data, nodeModel.getSkinModel());
//        }
//        //Check if mesh-datas are available
//        if (nodeModel.getMeshModels().size() > 0) {
//            for (int m = 0; m < nodeModel.getMeshModels().size(); m++) {
//                final MeshModel meshModel = nodeModel.getMeshModels().get(m);
//                final String meshName = (meshModel.getName() != null ? meshModel.getName() : "mesh" + m++);
//                final String pathMesh = pathNow + "." + meshName;
//                for (int mp = 0; mp < meshModel.getMeshPrimitiveModels().size(); mp++) {
//                    processMeshPrimitive(data, pathMesh, meshName, nodeModel, meshModel, meshModel.getMeshPrimitiveModels().get(mp), mp);
//                }
//            }
//        }
//        for (int c = 0; c < nodeModel.getChildren().size(); c++) {
//            processNode(data, pathNow, nodeModel.getChildren().get(c), c);
//        }
//    }
//
//    private String processSkin(ImporterData data, SkinModel model) {
//        //Assume the first node of the skin-list is the root joint
//        final NodeModel rootJoint = model.getJoints().get(0);
//        //Check if root joint has parent (armature object), if not -> assume it is the parent
//        NodeModel parentNode = rootJoint.getParent() != null
//                ? rootJoint.getParent()
//                : rootJoint;
//        if (!data.getArmatures().containsKey(parentNode.getName())) {
//            final AssetArmature armature = new AssetArmature(data);
//            data.getArmatures().put(parentNode.getName(), armature);
//            //set the root joint
//            System.out.println("Process root-joint: " + rootJoint.getName());
//            final Joint joint = buildJoint(rootJoint);
//            final Affine affine = new Affine();
//            armature.getBindTransforms().add(affine);
//            armature.getJointForest().add(joint);
//            armature.getJoints().put(rootJoint.getName(), joint);
//            armature.getBindTransforms().set(
//                    armature.getBindTransforms().indexOf(affine),
//                    new Affine(new Translate(
//                            joint.t.getX(),
//                            joint.t.getY(),
//                            joint.t.getZ()))
//            );
//            //Process the children node of parentNode as joints
//            rootJoint.getChildren().forEach((jointNode) -> {
//                processChildJoint(armature, jointNode);
//            });
//        }
//        return parentNode.getName();
//    }
//
//    private void processChildJoint(AssetArmature armature, NodeModel jointModel) {
//        System.out.println("Processing joint - " + jointModel.getName());
//        final Joint joint = buildJoint(jointModel);
//        if (jointModel.getParent() != null) {
//            final Affine affine = new Affine();
//            armature.getBindTransforms().add(affine);
//            final Joint parent = armature.getJoints().get(jointModel.getParent().getName());
//            System.out.println("Parent-joint - " + (parent != null ? parent.getId() : "None"));
//            Joint parentJoint = parent;
//            parentJoint.getChildren().add(joint);
//            parentJoint.getChildren().add(new Bone(0.002, new Point3D(
//                    joint.t.getX(),
//                    joint.t.getY(),
//                    joint.t.getZ()))
//            );
//            try {
//                armature.getBindTransforms().set(
//                        armature.getBindTransforms().indexOf(affine),
//                        new Affine(joint.getLocalToSceneTransform().createInverse())
//                );
//            } catch (NonInvertibleTransformException ex) {
//                System.out.println("Error: " + ex);
//            }
//        }
//        armature.getJoints().put(jointModel.getName(), joint);
//        //Process further joint nodes
//        jointModel.getChildren().forEach((childJoint) -> {
//            processChildJoint(armature, childJoint);
//        });
//    }
//
//    private Joint buildJoint(NodeModel jointModel) {
//        final Joint joint = new Joint();
//        joint.setId(jointModel.getName());
//        final float jointScalePos = 1f;
//        final float jointScaleRot = 100f;
//        if (jointModel.getTranslation() != null) {
//            double x = jointScalePos * jointModel.getTranslation()[0];
//            double y = jointScalePos * jointModel.getTranslation()[1];
//            double z = jointScalePos * jointModel.getTranslation()[2];
//            System.out.println("Joint-Pos: " + x + ", " + y + ", " + z);
//            joint.t.setX(x);
//            joint.t.setY(-y);
//            joint.t.setZ(z);
//        }
//        if (jointModel.getRotation() != null) {
//            double rotX = jointScaleRot * jointModel.getRotation()[0];
//            double rotY = jointScaleRot * jointModel.getRotation()[1];
//            double rotZ = jointScaleRot * jointModel.getRotation()[2];
//            /*
//            Varianten probiert:
//            - rotX,   rotY,   rotZ  =>  Fast richtig
//            - -rotX,  -rotY,  -rotZ =>  falsch
//            - -rotX,  -rotY,  rotZ  =>  falsch
//            - -rotX,  rotY,  rotZ   =>  falsch
//            - rotX,  -rotY,  rotZ   =>  falsch
//            - rotX,  rotY,  -rotZ   =>  falsch
//             */
//            joint.rx.setAngle(rotX);
//            joint.ry.setAngle(rotY);
//            joint.rz.setAngle(rotZ);
//            System.out.println("Joint-Rot: " + rotX + ", " + rotY + ", " + rotZ);
//        }
//        if (jointModel.getScale() != null) {
//            double scaX = jointModel.getScale()[0];
//            double scaY = jointModel.getScale()[1];
//            double scaZ = jointModel.getScale()[2];
//            joint.s.setX(scaX);
//            joint.s.setY(scaY);
//            joint.s.setZ(scaZ);
//            System.out.println("Joint-Scale: " + scaX + ", " + scaY + ", " + scaZ);
//        }
//        return joint;
//    }
//
//    private void processMeshPrimitive(ImporterData data, String path, String meshName, NodeModel nodeModel, MeshModel meshModel, MeshPrimitiveModel meshPrimitiveModel, int primitiveNr) {
//        final AccessorModel accPosition = meshPrimitiveModel.getAttributes().get("POSITION");
//        if (accPosition != null && accPosition.getCount() > 0) {
//            if (meshPrimitiveModel.getMode() == 4) {
//                final String key = meshName + "." + primitiveNr;
//                final String primitivePath = path + ".primitive" + primitiveNr;
//                //Getting the accessors
//                final AccessorModel accIndices = meshPrimitiveModel.getIndices();
//                final AccessorModel accNormal = meshPrimitiveModel.getAttributes().get("NORMAL");
//                final AccessorModel accTexCoords0 = meshPrimitiveModel.getAttributes().get("TEXCOORDS_0");
//                final AccessorModel accColors0 = meshPrimitiveModel.getAttributes().get("COLOR_0");
//
//                if (isDebug()) {
//                    System.out.println("Counts:");
//                    System.out.println("Positions - " + (accPosition != null ? accPosition.getCount() : 0));
//                    System.out.println("Normals - " + (accNormal != null ? accNormal.getCount() : 0));
//                    System.out.println("Textcoords0 - " + (accTexCoords0 != null ? accTexCoords0.getCount() : 0));
//                    System.out.println("Indices - " + (accIndices != null ? accIndices.getCount() : 0));
//                }
//
//                //Preset counts
//                //final int MINMAXLEN = /*accPosition != null ? accPosition.getCount() / accPosition.getElementType().getNumComponents() : */ 3;/*vertices.size() / nPoints;*/ // 3
//                float[] min = new float[3];
//                float[] max = new float[3];
//                Arrays.fill(min, Integer.MAX_VALUE);
//                Arrays.fill(max, Integer.MIN_VALUE);
//
//                //Process Mesh based on indices or not
//                int[] indices;
//                float[] vertices = processFloatBuffer(accPosition);
//                float[] fnormals;
//                final boolean uvsThere = accTexCoords0 != null && accTexCoords0.getCount() > 0; //for uvs != null
//                //Use indices-accessor if provided, else create indices based on vertices sequentally
//                int facesNumber = 1;
//                if (accIndices != null) {
//                    facesNumber = accIndices.getCount() / 3;
//                    indices = processIndexBuffer(accIndices);
//                } else {
//                    /*
//                    facesNumber = accPosition.getCount() / accPosition.getElementType().getNumComponents();
//                    indices = new int[facesNumber];
//                    int lastIndex = 0;
//                    for (int i = 0; i < facesNumber; i++) {
//
//                      indices[i] = i;
//                    }
//                     */
//                    throw new IllegalStateException("Non-indexed meshes not supported yet.");
//                }
//                //Set accessor datas, else default to 0.0f
//                if (accNormal != null) {
//                    fnormals = processFloatBuffer(accNormal);
//                } else {
//                    fnormals = new float[vertices.length];
//                    for (int n = 0; n < fnormals.length; n++) {
//                        fnormals[n] = 0.0f;
//                    }
//                }
//                int txcoordsNumber = 2;
//                if (accTexCoords0 != null) {
//                    txcoordsNumber = accTexCoords0.getCount();
//                }
//
//                if (isDebug()) {
//                    System.out.println("");
//                    System.out.println("Vertices-Array: " + vertices.length);
//                    System.out.println("Normals-Array: " + fnormals.length);
//                    System.out.println("MinMaxLen: " + 3);
//                    System.out.println("FacesNumber: " + facesNumber);
//                    System.out.println("TexCoordsNumber: " + txcoordsNumber);
//                }
//
//                //Preparing the asset
//                final AssetMesh assetMesh;
//                final SkinModel skinModel = nodeModel.getSkinModel();
//                if (skinModel != null && skinModel.getJoints().size() > 0) {
//                    final String armId = processSkin(data, skinModel);
//                    AssetArmature armature = data.getArmatures().get(armId);
//                    assetMesh = new AssetDynamicMesh(data);
//                    applyArmatureDatas(assetMesh.getMeshData(), skinModel, meshPrimitiveModel, vertices.length);
//                    //assetMesh = new AssetSkinnedMesh(armature, data);
//                } else {
//                    assetMesh = new AssetDynamicMesh(data);
//                }
//
//                //Processing vertices
//                int vNr = 0;
//                final FloatArrayList lVertices = new FloatArrayList(accPosition.getCount());
//                for (int i = 0; i < vertices.length; i++) {
//                    float c = scale * ((vNr > 0 ? -1 : 1) * vertices[i]);
//                    lVertices.add(c);
//                    int j = i % 3;
//                    min[j] = Math.min(min[j], c);
//                    max[j] = Math.max(max[j], c);
//                    vNr++;
//                    vNr = vNr > 2 ? 0 : vNr++;
//                }
//                assetMesh.getMeshData().setPoints(lVertices.toFloatArray());
//
//                //Processing TexCoords
//                final FloatArrayList lUVs = new FloatArrayList(txcoordsNumber);
//                if (uvsThere) {
//                    final float[] texCoords = processFloatBuffer(accTexCoords0);
//                    for (int i = 0; i < texCoords.length; i++) {
//                        lUVs.add(texCoords[i]);
//                    }
//                } else {
//                    for (int i = 0; i < txcoordsNumber; i++) {
//                        lUVs.add(0f); // create at least 2 coordinates
//                    }
//                }
//                assetMesh.getMeshData().setTexCoords(lUVs.toFloatArray());
//
//                //Processing faces/normals
//                int[][] pfaces = new int[facesNumber][];
//                int[][] pnormals = new int[facesNumber][];
//                for (int f = 0; f < pfaces.length; f++) {
//                    int curPos = f * 3;
//                    pfaces[f] = new int[]{
//                        indices[curPos], 0,
//                        indices[curPos + 1], 0,
//                        indices[curPos + 2], 0
//                    //Regular way: indices[fPos + V1], (uvsThere ? indices[fPos + UV1] : 0),
//                    };
//                    pnormals[f] = new int[]{
//                        indices[curPos], indices[curPos + 1], indices[curPos + 2]
//                    };
//                }
//                assetMesh.getMeshData().setFaces(pfaces);
//
//                if (debug) {
//                    debugIndexArrays("Indices", indices);
//                    debugFoatArrays("Vertexes", vertices);
//                    debugFoatArrays("UVs", lUVs.toFloatArray());
//                    debugIntegerArrayArrays("VertexNormals", pnormals);
//                    debugIntegerArrayArrays("PFaces", pfaces);
//                    debugFoatArrays("FaceNormals", fnormals);
//                    System.out.println("");
//                }
//
//                final PolygonMesh mesh = new PolygonMesh(lVertices.toFloatArray(), lUVs.toFloatArray(), pfaces);
//
//                //proccess smoothing groups
//                boolean useNormals = true;
//                if (useNormals) {
//                    int[] smGroups = SmoothingGroups.calcSmoothGroups(pfaces, pnormals, fnormals);
//                    assetMesh.getMeshData().setFaceSmoothingGroups(smGroups);
//                    mesh.getFaceSmoothingGroups().setAll(smGroups);
//                } else {
//                    //mesh.getFaceSmoothingGroups().setAll(((IntegerArrayList) smoothingGroups.subList(smoothingGroupsStart, smoothingGroups.size())).toIntArray());
//                }
//                assetMesh.setId(key);
//                assetMesh.setMaterial(meshPrimitiveModel.getMaterialModel().getName());
//                assetMesh.setTestView(mesh);
//                if (nodeModel.getTranslation() != null) {
//                    assetMesh.getMeshData().setGlobalTranslate(new Translate(
//                            nodeModel.getTranslation()[0],
//                            nodeModel.getTranslation()[1],
//                            nodeModel.getTranslation()[2]
//                    ));
//                }
//                data.getMeshes().put(primitivePath, assetMesh);
//            } else {
//                throw new IllegalArgumentException("Draw-mode of the gltf-model has to be 4 (TRIANGLES).");
//            }
//        } else {
//            throw new IllegalArgumentException("Mesh has no vertices-data.");
//        }
//    }
//
//    private void applyArmatureDatas(MeshData meshData, SkinModel skin, MeshPrimitiveModel model, int nPoints) {
//        final int[] skinIndices = processIndexBuffer(model.getAttributes().get("JOINTS_0"));
//        final float[] skinWeights = processFloatBuffer(model.getAttributes().get("WEIGHTS_0"));
//        System.out.println("Calculate: " + skinIndices.length + " SkinIndices for " + nPoints + " points.");
//        final float[][] weights = new float[skin.getJoints().size()][nPoints];
//        for (int i = 0; i < skinIndices.length; i += 2) {
//            int pIndex = i / 2;
//            int jIndex1 = skinIndices[i];
//            int jIndex2 = skinIndices[i + 1];
//            float weight1 = skinWeights[i];
//            float weight2 = skinWeights[i + 1];
//            float total = weight1 + weight2;
//            weight1 /= total;
//            weight2 /= total;
//            if (weights[jIndex1][pIndex] == 0) {
//                weights[jIndex1][pIndex] = weight1;
//            }
//            if (weights[jIndex2][pIndex] == 0) {
//                weights[jIndex2][pIndex] = weight2;
//            }
//        }
//        meshData.setWeights(weights);
//        if (isDebug()) {
//            System.out.println("Weights");
//            System.out.println("" + meshData.getWeights());
//        }
//    }
}
