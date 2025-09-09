import SwiftUI
import Shared

struct SakeShopDetailView: View {
    @StateObject private var viewModel = SakeShopDetailViewModel()
    let shopName: String
    
    var body: some View {
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
                        viewModel.loadSakeShop(shopName: shopName)
                    }
                    .buttonStyle(.borderedProminent)
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            } else if let sakeShop = viewModel.sakeShop {
                SakeShopDetailContent(shop: sakeShop)
            } else {
                Text("Shop not found")
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
        .navigationTitle("Shop Details")
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            viewModel.loadSakeShop(shopName: shopName)
        }
    }
}

struct SakeShopDetailContent: View {
    let shop: SakeShop
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                // Shop Image
                if let pictureUrl = shop.picture {
                    AsyncImage(url: URL(string: pictureUrl)) { image in
                        image
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                    } placeholder: {
                        Rectangle()
                            .fill(Color.gray.opacity(0.3))
                            .overlay {
                                Image(systemName: "photo")
                                    .foregroundColor(.gray)
                                    .font(.largeTitle)
                            }
                    }
                    .frame(height: 200)
                    .clipped()
                    .cornerRadius(12)
                }
                
                // Shop Name
                Text(shop.name)
                    .font(.title2)
                    .fontWeight(.bold)
                
                // Rating
                HStack {
                    Image(systemName: "star.fill")
                        .foregroundColor(.yellow)
                        .font(.title3)
                    
                    Text("\(String(format: "%.1f", shop.rating)) stars")
                        .font(.title3)
                        .fontWeight(.medium)
                }
                
                // Description
                VStack(alignment: .leading, spacing: 8) {
                    Text("Description")
                        .font(.headline)
                        .fontWeight(.bold)
                    
                    Text(shop.description_)
                        .font(.body)
                        .fixedSize(horizontal: false, vertical: true)
                }
                
                // Address
                VStack(alignment: .leading, spacing: 8) {
                    Text("Address")
                        .font(.headline)
                        .fontWeight(.bold)
                    
                    HStack {
                        Image(systemName: "location")
                            .foregroundColor(.blue)
                        
                        Text(shop.address)
                            .font(.body)
                            .fixedSize(horizontal: false, vertical: true)
                    }
                    .padding()
                    .background(Color(.systemGray6))
                    .cornerRadius(8)
                }
                
                // Action Buttons
                VStack(spacing: 12) {
                    Button(action: {
                        openMaps(shop: shop)
                    }) {
                        HStack {
                            Image(systemName: "location")
                            Text("Open in Maps")
                        }
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                    }
                    
                    Button(action: {
                        openWebsite(shop: shop)
                    }) {
                        HStack {
                            Image(systemName: "safari")
                            Text("Visit Website")
                        }
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.green)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                    }
                }
                
                Spacer()
            }
            .padding()
        }
    }
    
    private func openMaps(shop: SakeShop) {
        if let url = URL(string: shop.google_maps_link) {
            UIApplication.shared.open(url)
        }
    }
    
    private func openWebsite(shop: SakeShop) {
        if let url = URL(string: shop.website) {
            UIApplication.shared.open(url)
        }
    }
}

struct SakeShopDetailView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            SakeShopDetailView(shopName: "Test Shop")
        }
    }
}