package ca.warp7.pathplotter.ui

import ca.warp7.pathplotter.util.f
import ca.warp7.pathplotter.util.f2
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.HBox

class ControlBar {

    private val timeSlider = Slider().apply {
        this.value = 0.0
        this.prefWidth = 360.0
        this.max = 1.0
        this.min = 0.0
    }

    private fun textField(): TextField {
        return TextField().apply {
            this.prefWidth = 50.0
        }
    }

    fun setTotalTime(t: Double) {
        timeSlider.max = t
    }

    fun setElapsedTime(t: Double) {
        timeSlider.value = t
    }

    fun getElapsedTime(): Double {
        return timeSlider.value
    }

    private val x = textField()
    private val y = textField()
    private val theta = textField()
    private val mag = textField()

    fun addTimePropertyListener(listener: () -> Unit) {
        timeSlider.valueProperty().addListener { _, _, _ -> listener() }
    }

    fun addEditListener(listener: (newPose: Pose2d, newMag: Double) -> Unit) {
        val handler = EventHandler<KeyEvent> {
            if (it.code == KeyCode.TAB || it.code == KeyCode.ENTER) {
                try {
                    val pose = Pose2d(x.text.toDouble(), y.text.toDouble(),
                            Rotation2d.fromDegrees(theta.text.toDouble()))
                    val m = mag.text.toDouble()
                    listener(pose, m)
                } catch (e: Exception) {
                }
            }
        }
        x.onKeyPressed = handler
        y.onKeyPressed = handler
        theta.onKeyPressed = handler
        mag.onKeyPressed = handler
    }

    private fun fixWidthLabel(t: String): Label {
        return Label(t)
    }

    private val xLabel = fixWidthLabel("X:")
    private val yLabel = fixWidthLabel("Y:")
    private val thetaLabel = fixWidthLabel("θ:")
    private val magLabel = fixWidthLabel("M:")

    val top = HBox().apply {
        spacing = 8.0
        padding = Insets(4.0, 8.0, 4.0, 8.0)
        this.style = "-fx-background-color: #eee"
        alignment = Pos.CENTER
        this.children.addAll(timeSlider,
                xLabel, x,
                yLabel, y,
                thetaLabel, theta,
                magLabel, mag
        )
    }

    fun setPose(pose: Pose2d, pMag: Double = Double.NaN) {
        x.text = pose.translation.x.f
        y.text = pose.translation.y.f
        theta.text = pose.rotation.degrees.f2
        if (pMag.isNaN()) {
            mag.text = ""
        } else {
            mag.text = pMag.f2
        }
    }

    fun clearPose() {
        x.text = ""
        y.text = ""
        theta.text = ""
        mag.text = ""
    }
}