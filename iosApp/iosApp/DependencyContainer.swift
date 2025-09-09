import Foundation
import Shared

class DependencyContainer {
    static let shared = DependencyContainer()
    
    private let resourceReader: ResourceReader
    private let dataSource: LocalSakeShopDataSource
    private let repository: SakeShopRepositoryImpl
    private let _getSakeShopsUseCase: GetSakeShopsUseCase
    private let _getSakeShopByIdUseCase: GetSakeShopByIdUseCase
    
    private init() {
        resourceReader = ResourceReader()
        dataSource = LocalSakeShopDataSource(resourceReader: resourceReader)
        repository = SakeShopRepositoryImpl(dataSource: dataSource)
        _getSakeShopsUseCase = GetSakeShopsUseCase(repository: repository)
        _getSakeShopByIdUseCase = GetSakeShopByIdUseCase(repository: repository)
    }
    
    func getSakeShopsUseCase() -> GetSakeShopsUseCase {
        return _getSakeShopsUseCase
    }
    
    func getSakeShopByIdUseCase() -> GetSakeShopByIdUseCase {
        return _getSakeShopByIdUseCase
    }
}
