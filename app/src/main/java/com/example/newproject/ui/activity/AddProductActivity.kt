package com.example.newproject.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.R
import com.example.newproject.databinding.ActivityAddProductBinding
import com.example.newproject.model.ProductModel
import com.example.newproject.repository.ProductRepositoryImpl
import com.example.newproject.utils.LoadingUtils
import com.example.newproject.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddProductBinding
    lateinit var productViewModel: ProductViewModel
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        loadingUtils = LoadingUtils(this)

        binding.btnAddProduct.setOnClickListener({
            loadingUtils.show()
            var productName : String = binding.editProductName.text.toString()
            var productDesc : String = binding.editProductDescription.text.toString()
            var productPrice : Int = binding.editProductPrice.text.toString().toInt()

            var model = ProductModel("", productName, productDesc, productPrice)

            productViewModel.addProduct(model){ success, message ->
                if (success){
                    loadingUtils.dismiss()
                    Toast.makeText(this@AddProductActivity, message,Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    loadingUtils.dismiss()
                    Toast.makeText(this@AddProductActivity, message, Toast.LENGTH_LONG).show()
                }
            }
        })
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}