package com.bimm.takehomeassignmnent.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimm.takehomeassignmnent.domain.model.SakeShop
import com.bimm.takehomeassignmnent.domain.usecase.GetSakeShopByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SakeShopDetailUiState(
    val isLoading: Boolean = true,
    val sakeShop: SakeShop? = null,
    val errorMessage: String? = null
)

class SakeShopDetailViewModel(
    private val getSakeShopByIdUseCase: GetSakeShopByIdUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SakeShopDetailUiState())
    val uiState: StateFlow<SakeShopDetailUiState> = _uiState.asStateFlow()
    
    fun loadSakeShop(shopId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            getSakeShopByIdUseCase(shopId)
                .onSuccess { shop ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        sakeShop = shop
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