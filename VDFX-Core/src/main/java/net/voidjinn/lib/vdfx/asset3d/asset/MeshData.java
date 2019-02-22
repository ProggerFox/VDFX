/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.voidjinn.lib.vdfx.asset3d.asset;

import javafx.scene.transform.Translate;


/**
 *
 * @author Berger
 */
public class MeshData {

    private float[] points;
    private float[] texCoords;
    private int[][] faces;
    private float[][] weights;
    private int[] faceSmoothingGroups;
    private Translate localTranslate = new Translate(0, 0, 0);
    private Translate globalTranslate = new Translate(0, 0, 0);

    public void setPoints(float[] points) {
        this.points = points;
    }

    public float[] getPoints() {
        return points;
    }

    public void setTexCoords(float[] texCoords) {
        this.texCoords = texCoords;
    }

    public float[] getTexCoords() {
        return texCoords;
    }

    public void setFaces(int[][] faces) {
        this.faces = faces;
    }

    public int[][] getFaces() {
        return faces;
    }

    public void setWeights(float[][] weights) {
        this.weights = weights;
    }

    public float[][] getWeights() {
        return weights;
    }

    public boolean hasWeights() {
        return weights != null && weights.length > 0;
    }

    public void setFaceSmoothingGroups(int[] faceSmoothingGroups) {
        this.faceSmoothingGroups = faceSmoothingGroups;
    }

    public int[] getFaceSmoothingGroups() {
        return faceSmoothingGroups;
    }

    public void setLocalTranslate(Translate localTranslate) {
        this.localTranslate = localTranslate;
    }

    public Translate getLocalTranslate() {
        return localTranslate;
    }

    public void setGlobalTranslate(Translate globalTranslate) {
        this.globalTranslate = globalTranslate;
    }

    public Translate getGlobalTranslate() {
        return globalTranslate;
    }

}
