package com.tejashree.codereviewlab.features.vehicle.dashboard

sealed interface AutonomousEvent {
    val timestamp: Long
    val confidence: Confidence

    data class Cruising(
        override val timestamp: Long,
        override val confidence: Confidence,
        val speedKph: Int
    ) : AutonomousEvent
    data class VehicleDetected(
        override val timestamp: Long,
        override val confidence: Confidence,
        val distanceMeters: Distance
    ) : AutonomousEvent
    data class SlowingDown(
        override val timestamp: Long,
        override val confidence: Confidence,
        val reason: String
    ) : AutonomousEvent
    data class EmergencyStop(
        override val timestamp: Long,
        override val confidence: Confidence,
        val obstacle: ObstacleType
    ) : AutonomousEvent
    data class DegradedMode(
        override val timestamp: Long,
        override val confidence: Confidence,
        val unavailableSensors: Set<SensorType>
    ) : AutonomousEvent
}
@JvmInline
value class Confidence private constructor(val percent: Int) {
    companion object {
        fun of(percent: Int): Confidence {
            require(percent in 0..100) {
                "Confidence must be between 0 and 100"
            }
            return Confidence(percent)
        }
    }
}
@JvmInline
value class Distance(val value: Float) {
    init { require(value >= 0f) }
}

enum class ObstacleType(
    val label: String,
    val severity: Severity
) {
    VEHICLE(
        label = "Vehicle",
        severity = Severity.MEDIUM
    ),

    PEDESTRIAN(
        label = "Pedestrian",
        severity = Severity.CRITICAL
    ),

    CYCLIST(
        label = "Cyclist",
        severity = Severity.CRITICAL
    ),

    MOTORCYCLE(
        label = "Motorcycle",
        severity = Severity.HIGH
    ),

    ANIMAL(
        label = "Animal",
        severity = Severity.HIGH
    ),

    DEBRIS(
        label = "Road Debris",
        severity = Severity.MEDIUM
    ),

    TRAFFIC_CONE(
        label = "Traffic Cone",
        severity = Severity.LOW
    ),

    UNKNOWN(
        label = "Unknown Object",
        severity = Severity.HIGH
    )
}

fun AutonomousEvent.toHeadline(): String =
    when (this) {
        is AutonomousEvent.Cruising ->
            "Cruising at $speedKph km/h"

        is AutonomousEvent.VehicleDetected ->
            "Vehicle detected ${distanceMeters.value} m ahead"

        is AutonomousEvent.SlowingDown ->
            "Slowing down: $reason"

        is AutonomousEvent.EmergencyStop ->
            "Emergency stop: ${obstacle.label}"

        is AutonomousEvent.DegradedMode ->
            "Operating with limited sensors"
    }

enum class SensorType(
    val displayName: String,
    val icon: String
) {
    CAMERA("Camera", "📷"),
    LIDAR("LiDAR", "📡"),
    RADAR("Radar", "📶"),
    ULTRASONIC("Ultrasonic", "🔊"),
    GPS("GPS", "🛰️"),
    IMU("IMU", "📐"),          // Inertial Measurement Unit
    WHEEL_ENCODER("Wheel Encoder", "⚙️"),
    V2X("V2X", "📡")           // Vehicle-to-Everything communication
}

enum class Severity {
    LOW, MEDIUM, HIGH, CRITICAL
}