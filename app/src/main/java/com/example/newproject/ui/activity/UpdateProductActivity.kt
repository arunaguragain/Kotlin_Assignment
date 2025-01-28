package com.example.newproject.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.R
import com.example.newproject.databinding.ActivityUpdateProductBinding
import com.example.newproject.repository.ProductRepositoryImpl
import com.example.newproject.viewmodel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateProductBinding
    lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        var productId : String? = intent.getStringExtra("productId")
        productViewModel.getProductById(productId.toString())

        productViewModel.products.observe(this){
            binding.updateProductName.setText((it?.productName.toString()))
            binding.updateProductPrice.setText((it?.price.toString()))
            binding.updateProductDescription.setText((it?.productDesc.toString()))
        }

        binding.btnUpdateProduct.setOnClickListener{
            val productName = binding.updateProductName.text.toString()
            val price = binding.updateProductPrice.text.toString().toInt()
            val desc = binding.updateProductDescription.text.toString()

            var updatedMap = mutableMapOf<String,Any>()

            updatedMap["productName"] = productName
            updatedMap["price"] = price
            updatedMap["productDesc"] = desc

            productViewModel.updateProduct(productId.toString(), updatedMap){
                success,message->
                if (success){
                    Toast.makeText(this@UpdateProductActivity, message, Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this@UpdateProductActivity,message, Toast.LENGTH_LONG).show()
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}