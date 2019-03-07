module VDFX {
    requires java.logging;
    requires javafx.base;
    requires javafx.baseEmpty;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.graphicsEmpty;
    
    exports net.voidjinn.lib.vdfx.base.util.math.geom;
    exports net.voidjinn.lib.vdfx.asset3d.asset;
    exports net.voidjinn.lib.vdfx.asset3d.importer;
}
