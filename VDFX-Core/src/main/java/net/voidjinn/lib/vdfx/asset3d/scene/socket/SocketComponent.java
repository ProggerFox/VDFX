/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.voidjinn.lib.vdfx.asset3d.scene.socket;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.transform.Translate;
import javafx.util.Pair;

/**
 *
 * @author CibonTerra
 */
public class SocketComponent {

  private final Node basis;
  private final Map<String, Pair<Node, Translate>> sockets = new HashMap<>();

  public SocketComponent(Node basis) {
    this.basis = basis;
  }

  public void attach(String socket, Node node, Translate translate) {
    final Pair<Node, Translate> pair = new Pair<>(node, translate);
    sockets.put(socket, pair);
    node.translateXProperty().bind(basis.translateXProperty().add(translate.xProperty()));
    node.translateYProperty().bind(basis.translateYProperty().add(translate.yProperty()));
    node.translateZProperty().bind(basis.translateZProperty().add(translate.zProperty()));
    node.rotateProperty().bind(basis.rotateProperty());
    node.rotationAxisProperty().bind(basis.rotationAxisProperty());
    /*
    node.layoutXProperty().bind(basis.layoutXProperty());
    node.layoutYProperty().bind(basis.layoutYProperty());
     */
  }

  public void detach(String socket) {
    if (sockets.containsKey(socket)
            && sockets.get(socket) != null) {
      final Pair<Node, Translate> p = sockets.get(socket);
      p.getKey().translateXProperty().unbind();
      p.getKey().translateYProperty().unbind();
      p.getKey().translateZProperty().unbind();
      p.getKey().rotateProperty().unbind();
      sockets.remove(socket);
    }
  }

  public Node getBasis() {
    return basis;
  }

  public Map<String, Pair<Node, Translate>> getSockets() {
    return sockets;
  }

}
