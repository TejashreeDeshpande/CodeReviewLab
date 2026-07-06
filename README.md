# CodeReviewLab

A Jetpack Compose practice repository featuring hands-on UI challenges, state management, animations, and architecture exercises focused on modern Android development.

## 🚀 Features

### 1. 🚗 Park & Go (UI & Canvas)
A complex parking management UI demonstrating:
- **Custom Canvas Drawing**: Realistic parking map with grid systems and glow effects.
- **State-Driven UI**: Interactive checklist with "Select All" functionality.
- **Modern State Handling**: Integration with `ViewModel` and `collectAsStateWithLifecycle`.

### 2. 📝 Notes App (MVVM)
A full-stack feature exercise implementing:
- **Clean Architecture**: Separation of concerns into `data`, `domain`, and `presentation` layers.
- **Navigation 3**: Experimenting with the latest `androidx.navigation3` for type-safe routing.
- **DI with Koin**: Dependency injection for ViewModels and repositories.

### 3. 🔔 Smart Notification (MVI)
A robust notification handler using the **MVI (Intent-State-Effect)** pattern:
- **Uni-directional Data Flow**: Managing UI state through explicit Intents.
- **Local Notifications**: Helper classes for system-level interactions.

### 4. 🎨 Canvas Art (Galaxy & Kaleidoscope)
Experimental UI components focused on high-performance graphics:
- **Geometric Pathing**: Drawing complex shapes and patterns.
- **Animations**: Fluid motion using Compose animation APIs.

### 5. 🔍 Search & Filter
A product listing implementation featuring:
- **Real-time Filtering**: Dynamic search and category-based chips.
- **Loading/Error States**: Graceful handling of asynchronous data states.
- **Material 3**: Use of `FlowRow`, `FilterChip`, and `Scaffold`.

### 6. 🏆 Leaderboard
Dynamic list management focused on:
- **Performance**: Efficient rendering of lists with custom animations.
- **UI Consistency**: Material 3 design patterns.

### 7. 🔢 Simple Counter
A foundation exercise for state management:
- **`rememberSaveable`**: Persisting state across configuration changes.
- **Interactive UI**: Basic state mutation and event handling.

### 8. 🧩 Leetcode Prep
A collection of common algorithmic challenges implemented in Kotlin, used for practicing logic alongside UI development.

## 🛠️ Tech Stack
- **Language**: Kotlin 2.x
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVI, MVVM, Clean Architecture
- **Dependency Injection**: Koin
- **Navigation**: Navigation Compose & Navigation 3
- **Data Handling**: `kotlinx.collections.immutable`, `kotlinx.serialization`
- **State Management**: StateFlow, `collectAsStateWithLifecycle`

## 📁 Project Structure
The project is organized by feature to allow for clear separation of logic:
- `features/vehicle/parkandgo`: UI and Canvas experiments.
- `features/mvvm/notes`: Full MVVM implementation.
- `features/mvi/notification`: MVI architectural pattern.
- `features/canvas`: Custom drawing and animations.
- `features/leetcode`: Logical and algorithmic exercises.

## 🛠️ Getting Started
1. Clone the repository.
2. Open in Android Studio (Ladybug or newer).
3. Sync Gradle and run the `:app` module.
4. Check `MainActivity.kt` to switch between different feature entry points.
