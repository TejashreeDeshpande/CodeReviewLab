# CodeReviewLab

A Jetpack Compose practice repository featuring hands-on UI challenges, state management, animations, and architecture exercises focused on Android development.

## 🚀 Features

### 1. Simple Counter
A basic state management exercise demonstrating:
- **`rememberSaveable`**: Persisting state across configuration changes.
- **Custom Theming**: Use of gradients and custom button styles.
- **Interactive UI**: Increment and decrement functionality.
- 
### 2. Search & Filter Screen
A robust implementation of a product listing screen that includes:
- **Search Functionality**: Real-time filtering of products by name using a search query.
- **Category Filtering**: Dynamic filtering using `FilterChip` and `FlowRow`.
- **UI State Management**: Handling of different UI states: `Loading`, `Error`, and `Success`.
- **Animations**: Uses `animateFloatAsState` for smooth transitions (e.g., refresh icon rotation).
- **Material 3 UI**: Built using Material 3 components like `Scaffold`, `Card`, `ListItem`, `OutlinedTextField`, and `LargeFloatingActionButton`.

## 🛠️ Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Design System**: Material 3
- **Architecture**: MVI/State-based UI patterns
- **Data Structures**: `kotlinx.collections.immutable` for performant state handling.

## 📁 Project Structure
- `com.tejashree.codereviewlab.features.searchfilter`: Logic and UI for the Search & Filter feature.
- `com.tejashree.codereviewlab.features.counter`: Simple counter state management exercise.
- `com.tejashree.codereviewlab.features.common`: Reusable UI components like `AppTopBar`.
- `com.tejashree.codereviewlab.ui.theme`: App-wide Material 3 theme and styling.

## 📸 Previews
The project makes extensive use of Compose Previews, supporting both **Light Mode** and **Dark Mode** for all major screens and components.

## 🛠️ Getting Started
1. Clone the repository.
2. Open in Android Studio (Ladybug or newer).
3. Sync Gradle and run the `:app` module.
