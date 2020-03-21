package com.example.mobiquity.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mobiquity.R
import com.example.mobiquity.databinding.ActivityProductListBinding
import com.example.mobiquity.injection.ViewListModelFactory

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var viewModel: ProductViewListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
//        binding.productList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemId = intent.getLongExtra("itemId", 36802)
        viewModel = ViewModelProviders.of(this, ViewListModelFactory(this)).get(ProductViewListModel::class.java)
        viewModel.loadItems(itemId)
        binding.viewModel = viewModel
        //to change title of activity
        val actionBar = supportActionBar


        viewModel.itemName.observe(this, Observer {
                itemName -> actionBar!!.title = itemName
        })
    }
}
