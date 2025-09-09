# Sake Shop Locator - KMP

A Kotlin Multiplatform Mobile app that helps users discover local sake shops. Built with KMP to share business logic between Android and iOS while maintaining native UI experiences.

## Architecture Overview

This project follows Clean Architecture principles with a clear separation of concerns across multiple layers. Here's how it's structured:

### Domain Layer (`shared/src/commonMain/kotlin/domain/`)
The core business logic lives here, completely platform-agnostic.

- **Models** (`model/`): Simple data classes representing our business entities
  - `SakeShop.kt` - Core shop data with serialization support
- **Use Cases** (`usecase/`): Single-responsibility business operations
  - `GetSakeShopsUseCase` - Retrieves all available sake shops
  - `GetSakeShopByIdUseCase` - Finds a specific shop by name/ID
- **Repository Interface** (`repository/`): Abstract data access contract

### Data Layer (`shared/src/commonMain/kotlin/data/`)
Handles data persistence and external sources.

- **Repository Implementation** (`repository/`): Concrete data access logic with error handling
- **Data Sources** (`source/`): Abstracts different data origins
  - `SakeShopDataSource` - Interface for shop data providers
  - `LocalSakeShopDataSource` - JSON file-based implementation
  - `ResourceReader` - Platform-specific file reading (expect/actual pattern)

### Dependency Injection (`shared/src/commonMain/kotlin/di/`)
Uses Koin for lightweight DI across platforms.

- `SharedModule.kt` - Core dependencies available to all platforms
- Platform-specific modules for iOS/Android resource handling

## Platform Implementations

### Android (`composeApp/`)
Modern Android app using Jetpack Compose.

- **UI**: Declarative Compose components
- **Navigation**: Built-in Compose navigation
- **DI**: Koin integration with Android context
- **Data**: Asset-based JSON loading

### iOS (`iosApp/`)
Native SwiftUI implementation.

- **UI**: SwiftUI views with MVVM pattern
- **Navigation**: NavigationView and NavigationLink
- **DI**: Manual dependency injection container (bypassed Koin complexity)
- **Data**: Bundle resource loading via Kotlin/Native interop

## Key Design Decisions

### Why Clean Architecture?
We needed a way to share business logic while keeping platform UIs independent. Clean Architecture gives us:
- Clear separation between business rules and framework details
- Easy testing through dependency injection
- Platform flexibility without duplicating domain logic

### Why Manual DI on iOS?
Initially tried Koin for both platforms, but hit issues with Kotlin `Result` types in Swift interop. The manual DI container proved simpler and more reliable:

```swift
class DependencyContainer {
    private let _getSakeShopsUseCase: GetSakeShopsUseCase
    
    private init() {
        resourceReader = ResourceReader()
        dataSource = LocalSakeShopDataSource(resourceReader: resourceReader)
        repository = SakeShopRepositoryImpl(dataSource: dataSource)
        _getSakeShopsUseCase = GetSakeShopsUseCase(repository: repository)
    }
}
```

### Error Handling Strategy
Use cases throw exceptions rather than returning `Result<T>` types. This simplifies Swift interop since Swift's try/catch works naturally with Kotlin exceptions.

Before:
```kotlin
suspend operator fun invoke(): Result<List<SakeShop>>
```

After:
```kotlin
suspend operator fun invoke(): List<SakeShop> // throws on error
```

### Resource Loading
Each platform handles file loading differently:
- **Android**: Assets folder with `AssetManager`
- **iOS**: Main bundle with `NSBundle.pathForResource`

The `expect/actual` mechanism keeps this platform-specific while sharing the data parsing logic.

## Testing Strategy

Focused on unit testing the business logic where bugs are most costly:

- **Use Cases**: Test business rules and error handling
- **Repository**: Test data transformation and error propagation
- **Data Sources**: Test JSON parsing and resource loading

Used fake implementations rather than mocking frameworks to keep tests simple and fast. Each test follows the Arrange-Act-Assert pattern with descriptive names.

## Running the Project

### Prerequisites
- Android Studio or IntelliJ IDEA
- Xcode (for iOS)
- Kotlin 1.9.21+

### Android
```bash
./gradlew :composeApp:installDebug
```

### iOS
1. Build shared framework: `./gradlew :shared:linkDebugFrameworkIosSimulatorArm64`
2. Open `iosApp/iosApp.xcodeproj` in Xcode
3. Ensure `sakeshop.json` is added to the target
4. Build and run

### Tests
```bash
./gradlew :shared:allTests
```

## Data Source

Shop data comes from a curated JSON file (`sakeshop.json`) containing real sake shops and breweries in the Nagano region. Each entry includes:
- Basic shop information (name, description, rating)
- Location details (address, coordinates)
- External links (Google Maps, website)

## Future Improvements

- Add network data source for real-time shop information
- Implement favorites functionality with local persistence  
- Add shop photos and user reviews
- Include turn-by-turn navigation integration
- Support multiple regions beyond Nagano

---

## Original Build Instructions
This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
* `/iosApp` contains iOS applications and SwiftUI code.
* `/shared` is for the code that will be shared between all targets in the project.

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

