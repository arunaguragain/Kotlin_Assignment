package com.example.newproject.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.newproject.model.ProductModel
import com.example.newproject.repository.ProductRepository

class ProductViewModel(val repository: ProductRepository) {

    fun addProduct(productModel: ProductModel, callback : (Boolean, String) -> Unit){
        repository.addProduct(productModel, callback)
    }

    fun updateProduct(productId:String, data: MutableMap<String,Any>, callback: (Boolean, String) -> Unit){
        repository.updateProduct(productId, data, callback)
    }

    fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit){
        repository.deleteProduct(productId, callback)
    }

    var _products = MutableLiveData<ProductModel?>()
    var products = MutableLiveData<ProductModel?>()
        get() = _products

    var _allProducts = MutableLiveData<List<ProductModel>?>()
    var allProducts = MutableLiveData<List<ProductModel>?>()
        get() = _allProducts

    var _loadingStateSingleProduct = MutableLiveData<Boolean>()
    var loadingStateSingleProduct = MutableLiveData<Boolean>()
        get() = _loadingState

    fun getProductById(productId: String){
        _loadingStateSingleProduct.value = true
        repository.getProductById(productId){
            product, success, message ->
            if (success){
                _products.value = product
                _loadingStateSingleProduct.value = false
            }
        }
    }

    var _loadingState = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()
        get() = _loadingState

    fun getAllProduct( ){
        _loadingState.value = true
        repository.getAllProduct{
            products, success, message ->
            if (success){
                _allProducts.value = products
                _loadingState.value = false
            }
        }
    }
}