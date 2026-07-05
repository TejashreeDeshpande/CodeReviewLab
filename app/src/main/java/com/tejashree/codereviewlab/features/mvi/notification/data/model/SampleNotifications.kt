package com.tejashree.codereviewlab.features.mvi.notification.data.model

val sampleNotifications = listOf(
    SmartNotification(
        1,
        "Payment Reminder",
        "Credit card due tomorrow. Total amount: $1,240.50",
        Priority.HIGH,
        NotificationType.PAYMENT,
        "9:30",
        createdAt = System.currentTimeMillis() - 3600000
    ),
    SmartNotification(
        2,
        "Sprint Planning",
        "Q3 Roadmap discussion starting in 15 minutes in Room 402",
        Priority.HIGH,
        NotificationType.WORK,
        "10:15"
    ),
    SmartNotification(
        3,
        "Amazon Order",
        "Your package with order #12345 has been delivered to your porch",
        Priority.MEDIUM,
        NotificationType.SHOPPING,
        "Yesterday"
    ),
    SmartNotification(
        4,
        "Instagram",
        "Someone liked your photo and 5 others started following you",
        Priority.LOW,
        NotificationType.SOCIAL,
        "Yesterday"
    ),
    SmartNotification(
        5,
        "Server Alert",
        "Production server CPU usage exceeded 95%. Immediate action required",
        Priority.HIGH,
        NotificationType.WORK,
        "Just now"
    ),
    SmartNotification(
        6,
        "Uber",
        "Your ride with John is arriving in 3 minutes. White Tesla Model 3",
        Priority.MEDIUM,
        NotificationType.SOCIAL,
        "11:05"
    ),
    SmartNotification(
        7,
        "Weekly Report",
        "Your weekly productivity report is ready. You were 15% more active",
        Priority.LOW,
        NotificationType.WORK,
        "9:00"
    ),
    SmartNotification(
        8,
        "Starbucks",
        "Happy Hour! Buy one get one free on all handcrafted drinks today",
        Priority.LOW,
        NotificationType.SHOPPING,
        "08:30"
    ),
    SmartNotification(
        9,
        "Electricity Bill",
        "Your bill for March is ready for review. Auto-pay scheduled for April 5th",
        Priority.MEDIUM,
        NotificationType.PAYMENT,
        "Yesterday"
    ),
    SmartNotification(
        10,
        "Slack",
        "Alex mentioned you in #design-system: 'Can we review these icons?'",
        Priority.HIGH,
        NotificationType.WORK,
        "12:00"
    ),
    SmartNotification(
        11,
        "Spotify",
        "Your Release Radar is updated with new music from your favorite artists",
        Priority.LOW,
        NotificationType.SOCIAL,
        "Yesterday"
    ),
    SmartNotification(
        12,
        "Bank Alert",
        "Potential fraudulent transaction of $499.00 at 'ELECTRONICS_STORE'",
        Priority.HIGH,
        NotificationType.PAYMENT,
        "Just now"
    )
)
