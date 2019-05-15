package pe.com.redcups.core.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.redcups.core.JuergappDatabase
import pe.com.redcups.core.model.ProductCategory
import pe.com.redcups.core.repository.ProductCategoryRepository

class ProductCategoryViewModel(application: Application): AndroidViewModel(application){

    private val repository: ProductCategoryRepository
    val allProductCategories: LiveData<List<ProductCategory>>


    init {
        val productCategoryDao = JuergappDatabase.getDatabase(application, viewModelScope).productCategoryDao()
        repository = ProductCategoryRepository(productCategoryDao, application.applicationContext)
        allProductCategories = repository.allProductCategories
    }

    fun insert(productCategory: ProductCategory) = viewModelScope.launch(Dispatchers.IO) {
        //for debugging
        Log.d("Inserting", productCategory.id.toString())
        Log.d("Inserting", productCategory.name)
        repository.insert(productCategory)
    }

    fun getProductCategories(){
        repository.fetchProductCategories()
    }
    fun setProductCategories(productCategories: Array<ProductCategory>){
        for (productCategory in productCategories){
            insert(productCategory)
        }

    }


}