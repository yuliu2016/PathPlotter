module pathplotter {
    requires kotlin.stdlib;

    requires javafx.controls;
    requires wpilibj.java;
    requires wpiutil.java;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.iconli.core;

    requires ntcore.java;
    requires ntcore.jni;
    requires org.json;

    exports ca.warp7.pathplotter;
    exports edu.wpi.first.hal;
}