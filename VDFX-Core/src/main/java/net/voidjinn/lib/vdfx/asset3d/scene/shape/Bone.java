/**
 *
 * Bone.java
 *
 * Copyright (c) 2013-2016, F(X)yz
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of F(X)yz, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL F(X)yz BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.voidjinn.lib.vdfx.asset3d.scene.shape;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;

/**
 *
 * @author jpereda
 */
public class Bone extends Group {

    public Bone() {
        this(1, Point3D.ZERO);
    }

    public Bone(double scale, Point3D posJoint) {
        Box origin = new Box(10, 10, 10);
        origin.setMaterial(new PhongMaterial(Color.ORANGE));

        Cylinder bone = new Cylinder(5, posJoint.magnitude() / scale);
        double angle = Math.toDegrees(Math.acos((new Point3D(0, 1, 0)).dotProduct(posJoint) / posJoint.magnitude()));
        Point3D axis = (new Point3D(0, 1, 0)).crossProduct(posJoint);
        bone.getTransforms().addAll(new Rotate(angle, 0, 0, 0, axis), new Translate(0, posJoint.magnitude() / 2d / scale, 0));
        bone.setMaterial(new PhongMaterial(Color.CADETBLUE));

        Sphere end = new Sphere(6);
        end.getTransforms().addAll(new Translate(posJoint.getX() / scale, posJoint.getY() / scale, posJoint.getZ() / scale));
        end.setMaterial(new PhongMaterial(Color.YELLOW));

        getChildren().addAll(origin, bone, end);
        getTransforms().add(new Scale(scale, scale, scale));
    }

}
