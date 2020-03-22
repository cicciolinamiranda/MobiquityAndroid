package com.example.mobiquity.ui.item

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiquity.R
import com.example.mobiquity.databinding.ActivityItemListBinding
import com.example.mobiquity.injection.ViewListModelFactory
import com.example.mobiquity.repository.model.Item
import com.example.mobiquity.ui.product.ProductListActivity
import com.google.android.material.snackbar.Snackbar

class ItemListActivity : AppCompatActivity(), ItemAdapterViewModel.ItemAdapterViewModelListener {
    private lateinit var binding: ActivityItemListBinding
    private lateinit var viewModel: ItemViewListModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_list)
        binding.itemList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProviders.of(this, ViewListModelFactory(this, false)).get(ItemViewListModel::class.java)
        viewModel.addListener(this)
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.viewModel = viewModel
    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

    override fun onItemClick(item: Item) {
        val intent = Intent(this,ProductListActivity::class.java);
        intent.putExtra("itemId", item.id)
        startActivity(intent)
    }
}
