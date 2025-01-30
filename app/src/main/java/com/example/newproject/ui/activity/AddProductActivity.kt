package com.example.newproject.ui.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.R
import com.example.newproject.databinding.ActivityAddProductBinding
import com.example.newproject.model.ProductModel
import com.example.newproject.repository.ProductRepositoryImpl
import com.example.newproject.utils.ImageUtils
import com.example.newproject.utils.LoadingUtils
import com.example.newproject.viewmodel.ProductViewModel
import com.squareup.picasso.Picasso

class AddProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddProductBinding
    lateinit var productViewModel: ProductViewModel
    lateinit var loadingUtils: LoadingUtils
    lateinit var imageUtils: ImageUtils
    var imageUri: Uri? =  null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtils(this)

        loadingUtils = LoadingUtils(this)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        imageUtils.registerActivity { url ->
            url.let { it ->
                imageUri = it
                Picasso.get().load(it).into(binding.browseImage)
            }
        }
        binding.browseImage.setOnClickListener {
            imageUtils.launchGallery(this)
        }
        binding.btnAddProduct.setOnClickListener {
            uploadImage()
        }
//
//        binding.btnAddProduct.setOnClickListener({
//            loadingUtils.show()
//            var productName : String = binding.editProductName.text.toString()
//            var productDesc : String = binding.editProductDescription.text.toString()
//            var productPrice : Int = binding.editProductPrice.text.toString().toInt()
//
//            var model = ProductModel("", productName, productDesc, productPrice)
//
//            productViewModel.addProduct(model){ success, message ->
//                if (success){
//                    loadingUtils.dismiss()
//                    Toast.makeText(this@AddProductActivity, message,Toast.LENGTH_LONG).show()
//                    finish()
//                }else{
//                    loadingUtils.dismiss()
//                    Toast.makeText(this@AddProductActivity, message, Toast.LENGTH_LONG).show()
//                }
//            }
//        })
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun uploadImage() {
        imageUri?.let { uri ->
            productViewModel.uploadImage(this, uri) { imageUrl ->
                Log.d("checpoirs", imageUrl.toString())
                if (imageUrl != null) {
                    addProduct(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                }
            }
        }
    }

    private fun addProduct(url: String) {
        var productName = binding.editProductName.text.toString()
        var productPrice = binding.editProductPrice.text.toString().toInt()
        var productDesc = binding.editProductDescription.text.toString()

        var model = ProductModel(
            "",
            productName,
            productDesc, productPrice, url
        )
        productViewModel.addProduct(model) { success, message ->
            if (success) {
                Toast.makeText(
                    this@AddProductActivity,
                    message, Toast.LENGTH_LONG
                ).show()
                finish()
                loadingUtils.dismiss()
            } else {
                Toast.makeText(
                    this@AddProductActivity,
                    message, Toast.LENGTH_LONG
                ).show()

            }
        }
    }

}
