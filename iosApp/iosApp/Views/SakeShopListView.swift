import SwiftUI
import Shared

struct SakeShopListView: View {
    @StateObject private var viewModel = SakeShopListViewModel()
    
    var body: some View {
        NavigationView {
            Group {
                if viewModel.isLoading {
                    ProgressView()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                } else if let errorMessage = viewModel.errorMessage {
                    VStack(spacing: 16) {
                        Text("Error: \(errorMessage)")
                            .foregroundColor(.red)
                            .multilineTextAlignment(.center)
                        
                        Button("Retry") {
                            viewModel.loadSakeShops()
                        }
                        .buttonStyle(.borderedProminent)
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                } else {
                    List(viewModel.sakeShops, id: \.name) { shop in
                        NavigationLink(destination: SakeShopDetailView(shopName: shop.name)) {
                            SakeShopRowView(shop: shop)
                        }
                    }
                    .listStyle(PlainListStyle())
                }
            }
            .navigationTitle("Local Sake Shops")
            .navigationBarTitleDisplayMode(.large)
        }
    }
}

struct SakeShopRowView: View {
    let shop: SakeShop
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(shop.name)
                .font(.headline)
                .fontWeight(.bold)
                .lineLimit(2)
            
            HStack {
                Image(systemName: "star.fill")
                    .foregroundColor(.yellow)
                    .font(.caption)
                
                Text(String(format: "%.1f", shop.rating))
                    .font(.subheadline)
                    .fontWeight(.medium)
            }
            
            Text(shop.address)
                .font(.caption)
                .foregroundColor(.secondary)
                .lineLimit(2)
        }
        .padding(.vertical, 4)
    }
}

struct SakeShopListView_Previews: PreviewProvider {
    static var previews: some View {
        SakeShopListView()
    }
}