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

import com.fasterxml.jackson.databind.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;
import net.voidjinn.lib.vdfx.asset3d.asset.*;
import net.voidjinn.lib.vdfx.asset3d.importer.*;
import net.voidjinn.lib.vdfx.asset3d.scene.shape.SmoothingGroups;

/**
 *
 * @author FalcoTheBold
 */
public class ReaderGLTF extends Importer {

    private final HashMap<String, JsonNode> datas = new HashMap<>(7);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ImporterData load(URL url) throws IOException {
        datas.clear();
        final ImporterDataGltf data = new ImporterDataGltf();
        final JsonNode gltf = mapper.readTree(url.openStream());
        if (gltf != null) {
            final String baseUrl = url.toExternalForm().substring(0, url.toExternalForm().lastIndexOf("/") + 1);
            //final JsonNode asset = gltf.get("asset");
            final JsonNode buffers = gltf.get("buffers");
            if (buffers != null) {
                for (int b = 0; b < buffers.size(); b++) {
                    processBuffers(buffers.get(b), data, baseUrl);
                }
            }

            final JsonNode scenes = gltf.get("scenes");
            final JsonNode nodes = gltf.get("nodes");
            datas.put("bufferViews", gltf.get("bufferViews"));
            datas.put("accessors", gltf.get("accessors"));
            datas.put("materials", gltf.get("materials"));
            datas.put("skins", gltf.get("skins"));
            datas.put("meshes", gltf.get("meshes"));
            datas.put("scenes", scenes);
            datas.put("nodes", nodes);

            if (scenes != null) {
                for (int s = 0; s < scenes.size(); s++) {
                    final JsonNode sceneNodes = scenes.get(s).get("nodes");
                    for (int n = 0; n < sceneNodes.size(); n++) {
                        processNode(nodes.get(sceneNodes.get(n).asInt()), data);
                    }
                }
            }
        }
        return data;
    }

    @Override
    public boolean isSupported(String supportType) {
        return supportType.toLowerCase().equals("gltf");
    }

    //Processing functions
    private void processBuffers(JsonNode buffer, ImporterDataGltf data, String baseUrl) throws MalformedURLException, IOException {
        if (buffer.has("uri")) {
            final int bLen = buffer.get("byteLength").asInt();
            final String uri = buffer.get("uri").asText();
            final String fullUrl = baseUrl + uri;
            int curIdx = data.getBuffers().size();
            data.getBuffers().put(curIdx, fullUrl);
            if (uri.endsWith(".bin")) {
                final URL bufferUrl = new URL(fullUrl);
                if (bufferUrl != null) {
                    final InputStream stream = bufferUrl.openStream();
                    data.getBufferDatas().put(curIdx, ByteBuffer.wrap(stream.readAllBytes()));
                    stream.close();
                }
            }
        }
    }

    private Number[] processAccessor(JsonNode accessor, JsonNode bufferViews, ImporterDataGltf data) {
        final JsonNode bView = bufferViews.get(accessor.get("bufferView").asInt());
        final int accCount = accessor.get("count").asInt();
        final int accOffset = accessor.has("byteOffset") ? accessor.get("byteOffset").asInt(0) : 0;
        final int accType = AccessorType.valueOf(accessor.get("type").asText()).getNumElements();
        final AccessorComponentType accCompType = AccessorComponentType.getById(accessor.get("componentType").asInt());
        final int accCompNum = accCompType.getSize();
        final int stride = accessor.has("byteStride") ? accessor.get("byteStride").asInt() : 0;
        System.out.println("ByteStride: " + stride);

        final ByteBuffer buffer = data.getBufferDatas().get(bView.get("buffer").asInt());
        final int bLen = bView.get("byteLength").asInt();
        final int bOffset = bView.get("byteOffset").asInt(0);

        //final int totalSize = (bLen - accOffset) / (accType * accCompNum);
        final int totalSize = accCount * accType;
        final Number[] elements = new Number[totalSize];
        buffer.rewind();

        for (int e = 0; e < totalSize; e++) {
            final int pos = (stride > 0 ? stride * e : accCompNum * e) + (accOffset + bOffset);
            buffer.position(pos);
            switch (accCompType.getType()) {
                case FLOAT:
                    elements[e] = buffer.getFloat();
                    break;
                case INTEGER:
                    elements[e] = buffer.getInt();
                    break;
                case SHORT:
                    elements[e] = buffer.getShort();
                    break;
                default:
                    elements[e] = buffer.get();
            }
        }
        return elements;
    }

    private void processNode(JsonNode node, ImporterDataGltf data) {
        if (node.has("skin")) {
            final JsonNode skin = datas.get("skins").get(node.get("skin").asInt());
            final JsonNode skinOwner = datas.get("nodes").get(skin.get("skeleton").asInt());
            if (!data.getArmatures().containsKey(skinOwner.get("name").asText())) {

            }
        }
        if (node.has("mesh")) {
            System.out.println("Has mesh, proccess.");
            final JsonNode mesh = datas.get("meshes").get(node.get("mesh").asInt());
            final String meshName = mesh.get("name").asText();
            final JsonNode primitives = mesh.get("primitives");
            for (int p = 0; p < primitives.size(); p++) {
                final JsonNode primitive = primitives.get(p);
                final JsonNode attributes = primitive.get("attributes");
                final JsonNode accessors = datas.get("accessors");
                final JsonNode bufferViews = datas.get("bufferViews");

                final JsonNode indices = accessors.get(primitive.get("indices").asInt());
                final JsonNode material = accessors.get(primitive.get("material").asInt());
                final JsonNode positions = accessors.get(attributes.get("POSITION").asInt());
                final JsonNode normals = accessors.get(attributes.get("NORMAL").asInt());
                final JsonNode texcoords0 = accessors.get(attributes.get("TEXCOORD_0").asInt());
                System.out.println("JsonNode: " + indices.toString());
                System.out.println("JsonNode: " + indices.toString());
                System.out.println("JsonNode: " + indices.toString());
                System.out.println("JsonNode: " + indices.toString());
                System.out.println("JsonNode: " + indices.toString());

                final Number[] vertices = processAccessor(positions, bufferViews, data);
                //final Number[] pnormals = processAccessor(normals, bufferViews, data);

                System.out.println("Vertices: " + Arrays.deepToString(vertices));
                //Preset counts
                //final int MINMAXLEN = /*accPosition != null ? accPosition.getCount() / accPosition.getElementType().getNumComponents() : */ 3;/*vertices.size() / nPoints;*/ // 3
                float[] min = new float[3];
                float[] max = new float[3];
                Arrays.fill(min, Integer.MAX_VALUE);
                Arrays.fill(max, Integer.MIN_VALUE);
                final boolean uvsThere = texcoords0 != null && texcoords0.get("count").asInt() > 0; //for uvs != null*/;

//      Use indices-accessor if provided, else create indices based on vertices sequentally
                int facesNumber = 1;
                Number[] faces = null;
                if (indices != null) {
                    facesNumber = indices.get("count").asInt() / 3;
                    faces = processAccessor(indices, bufferViews, data);
                } else {
                    /*
            facesNumber = accPosition.getCount() / accPosition.getElementType().getNumComponents();
            indices = new int[facesNumber];
            int lastIndex = 0;
            for (int i = 0; i < facesNumber; i++) {

              indices[i] = i;
            }
                     */
                    throw new IllegalStateException("Non-indexed meshes not supported yet.");
                }

                //Set accessor datas, else default to 0.0f
                float[] fnormals;
                if (normals != null) {
                    final Number[] anormals = processAccessor(normals, bufferViews, data);
                    fnormals = new float[anormals.length];
                    for (int n = 0; n < anormals.length; n++) {
                        fnormals[n] = anormals[n].floatValue();
                    }
                } else {
                    fnormals = new float[vertices.length];
                    for (int n = 0; n < fnormals.length; n++) {
                        fnormals[n] = 0.0f;
                    }
                }
                int txcoordsNumber = 2;
                if (texcoords0 != null) {
                    txcoordsNumber = texcoords0.get("count").asInt();
                }

                //Preparing the asset
                final AssetMesh assetMesh;
                if (node.has("skin")) {
                    final JsonNode skin = datas.get("skins").get(node.get("skin").asInt());
                    final JsonNode skinOwner = datas.get("nodes").get(skin.get("skeleton").asInt());
                    if (!data.getArmatures().containsKey(skinOwner.get("name").asText())) {
                        AssetArmature armature = null;
                    } else {
                        AssetArmature armature = data.getArmatures().get(skinOwner.get("name").asText());
                    }
                    assetMesh = new AssetDynamicMesh(data);
                    //applyArmatureDatas(assetMesh.getMeshData(), skinModel, meshPrimitiveModel, vertices.length);
                    //assetMesh = new AssetSkinnedMesh(armature, data);
                } else {
                    assetMesh = new AssetDynamicMesh(data);
                }

                //Processing vertices
                int vNr = 0;
                final float[] points = new float[vertices.length];
                for (int i = 0; i < vertices.length; i++) {
                    float c = scale * ((vNr > 0 ? -1 : 1) * vertices[i].floatValue());
                    points[i] = c;
                    int j = i % 3;
                    min[j] = Math.min(min[j], c);
                    max[j] = Math.max(max[j], c);
                    vNr++;
                    vNr = vNr > 2 ? 0 : vNr++;
                }
                assetMesh.getMeshData().setPoints(points);

                //Processing TexCoords
                final float[] uvs = new float[txcoordsNumber];
                if (uvsThere) {
                    final Number[] texCoords = processAccessor(texcoords0, bufferViews, data);
                    for (int i = 0; i < texCoords.length; i++) {
                        uvs[i] = texCoords[i].floatValue();
                    }
                } else {
                    for (int i = 0; i < txcoordsNumber; i++) {
                        uvs[i] = 0f; // create at least 2 coordinates
                    }
                }
                assetMesh.getMeshData().setTexCoords(uvs);

                //Processing faces/normals
                final int[][] pfaces = new int[facesNumber][];
                final int[][] pnormals = new int[facesNumber][];
                for (int f = 0; f < pfaces.length; f++) {
                    int curPos = f * 3;
                    pfaces[f] = new int[]{
                        faces[curPos].intValue(), 0,
                        faces[curPos + 1].intValue(), 0,
                        faces[curPos + 2].intValue(), 0
                    /*Regular way: 
            indices[fPos + V1], (uvsThere ? indices[fPos + UV1] : 0),
                     */
                    };
                    pnormals[f] = new int[]{
                        faces[curPos].intValue(), faces[curPos + 1].intValue(), faces[curPos + 2].intValue()
                    };
                }
                assetMesh.getMeshData().setFaces(pfaces);

                //proccess smoothing groups
                boolean useNormals = true;
                if (useNormals) {
                    int[] smGroups = SmoothingGroups.calcSmoothGroups(pfaces, pnormals, fnormals);
                    assetMesh.getMeshData().setFaceSmoothingGroups(smGroups);
                    //mesh.getFaceSmoothingGroups().setAll(smGroups);
                } else {
                    //mesh.getFaceSmoothingGroups().setAll(((IntegerArrayList) smoothingGroups.subList(smoothingGroupsStart, smoothingGroups.size())).toIntArray());
                }
                assetMesh.setId(meshName);
                /*
        assetMesh.setMaterial(meshPrimitiveModel.getMaterialModel().getName());
        assetMesh.setTestView(mesh);
        if (nodeModel.getTranslation() != null) {
          assetMesh.getMeshData().setGlobalTranslate(new Translate(
                  nodeModel.getTranslation()[0],
                  nodeModel.getTranslation()[1],
                  nodeModel.getTranslation()[2]
          ));
        }
                 */
                data.getMeshes().put(meshName, assetMesh);
            }
        }
        if (node.has("children")) {
            final JsonNode children = node.get("children");
            for (int c = 0; c < children.size(); c++) {
                processNode(datas.get("nodes").get(children.get(c).asInt()), data);
            }
        }
    }

    //Represent-class for datas to gather
    private class ImporterDataGltf extends ImporterData {

        private final Map<Integer, String> buffers = new HashMap<Integer, String>();
        private final Map<Integer, ByteBuffer> bufferDatas = new HashMap<>();

        public Map<Integer, String> getBuffers() {
            return buffers;
        }

        public Map<Integer, ByteBuffer> getBufferDatas() {
            return bufferDatas;
        }

    }
}
