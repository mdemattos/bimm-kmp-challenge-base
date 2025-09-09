import Foundation
import Shared
import Combine

@MainActor
class SakeShopListViewModel: ObservableObject {
    @Published var isLoading = true
    @Published var sakeShops: [SakeShop] = []
    @Published var errorMessage: String?
    
    private let getSakeShopsUseCase: GetSakeShopsUseCase
    
    init() {
        self.getSakeShopsUseCase = DependencyContainer.shared.getSakeShopsUseCase()
        loadSakeShops()
    }
    
    func loadSakeShops() {
        isLoading = true
        errorMessage = nil
        
        Task {
            do {
                let shops = try await getSakeShopsUseCase.invoke()
                await MainActor.run {
                    self.sakeShops = shops
                    self.isLoading = false
                }
            } catch {
                await MainActor.run {
                    self.errorMessage = error.localizedDescription
                    self.isLoading = false
                }
            }
        }
    }
}