import Foundation
import Shared
import Combine

@MainActor
class SakeShopDetailViewModel: ObservableObject {
    @Published var isLoading = true
    @Published var sakeShop: SakeShop?
    @Published var errorMessage: String?
    
    private let getSakeShopByIdUseCase: GetSakeShopByIdUseCase
    
    init() {
        self.getSakeShopByIdUseCase = DependencyContainer.shared.getSakeShopByIdUseCase()
    }
    
    func loadSakeShop(shopName: String) {
        isLoading = true
        errorMessage = nil
        sakeShop = nil
        
        Task {
            do {
                let shop = try await getSakeShopByIdUseCase.invoke(id: shopName)
                await MainActor.run {
                    if let shop = shop {
                        self.sakeShop = shop
                        self.isLoading = false
                    } else {
                        self.errorMessage = "Shop '\(shopName)' not found"
                        self.isLoading = false
                    }
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