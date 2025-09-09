import SwiftUI
import Shared

@main
struct iOSApp: App {
    
    init() {
        _ = DependencyContainer.shared
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
