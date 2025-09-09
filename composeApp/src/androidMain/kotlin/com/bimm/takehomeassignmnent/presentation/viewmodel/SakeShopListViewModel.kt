package com.bimm.takehomeassignmnent.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimm.takehomeassignmnent.domain.model.SakeShop
import com.bimm.takehomeassignmnent.domain.usecase.GetSakeShopsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SakeShopListUiState(
    val isLoading: Boolean = true,
    val sakeShops: List<SakeShop> = emptyList(),
    val errorMessage: String? = null
)

class SakeShopListViewModel(
    private val getSakeShopsUseCase: GetSakeShopsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SakeShopListUiState())
    val uiState: StateFlow<SakeShopListUiState> = _uiState.asStateFlow()
    
    init {
        loadSakeShops()
    }
    
    fun loadSakeShops() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            getSakeShopsUseCase()
                .onSuccess { shops ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        sakeShops = shops
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }
}