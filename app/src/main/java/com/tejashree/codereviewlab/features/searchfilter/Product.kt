package com.tejashree.codereviewlab.features.searchfilter

import kotlinx.collections.immutable.persistentListOf

enum class ProductCategory(val title: String) {
    ELECTRONICS("Electronics"),
    HOME("Home"),
    FITNESS("Fitness"),
    SPORTS("Sports"),
    FURNITURE("Furniture"),
    ACCESSORIES("Accessories"),
    STORAGE("Storage"),
    WEARABLES("Wearables"),
    HEALTH("Health");
}

data class Product(
    val id: String,
    val name: String,
    val desc: String,
    val category: ProductCategory
)

val mockProducts = persistentListOf(
    Product(
        id = "1",
        name = "Wireless Headphones",
        desc = "Noise-cancelling Bluetooth headphones with 30-hour battery life.",
        category = ProductCategory.ELECTRONICS
    ),
    Product(
        id = "2",
        name = "Mechanical Keyboard",
        desc = "RGB mechanical keyboard with tactile switches.",
        category = ProductCategory.ELECTRONICS
    ),
    Product(
        id = "3",
        name = "Smart Water Bottle",
        desc = "Tracks hydration and reminds you to drink water.",
        category = ProductCategory.HEALTH
    ),
    Product(
        id = "4",
        name = "Yoga Mat",
        desc = "Eco-friendly, non-slip yoga mat.",
        category = ProductCategory.FITNESS
    ),
    Product(
        id = "5",
        name = "Running Shoes",
        desc = "Lightweight shoes for everyday running.",
        category = ProductCategory.SPORTS
    ),
    Product(
        id = "6",
        name = "Coffee Maker",
        desc = "12-cup programmable coffee maker.",
        category = ProductCategory.HOME
    ),
    Product(
        id = "7",
        name = "Air Purifier",
        desc = "HEPA air purifier for cleaner indoor air.",
        category = ProductCategory.HOME
    ),
    Product(
        id = "8",
        name = "Office Chair",
        desc = "Ergonomic chair with adjustable lumbar support.",
        category = ProductCategory.FURNITURE
    ),
    Product(
        id = "9",
        name = "Laptop Backpack",
        desc = "Water-resistant backpack with laptop compartment.",
        category = ProductCategory.ACCESSORIES
    ),
    Product(
        id = "10",
        name = "Portable SSD",
        desc = "1TB USB-C portable solid state drive.",
        category = ProductCategory.STORAGE
    ),
    Product(
        id = "11",
        name = "Smart Watch",
        desc = "Heart-rate monitor with GPS tracking.",
        category = ProductCategory.WEARABLES
    ),
    Product(
        id = "12",
        name = "Bluetooth Speaker",
        desc = "Portable waterproof speaker with deep bass.",
        category = ProductCategory.ELECTRONICS
    )
)