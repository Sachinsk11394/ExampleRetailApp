package com.target.targetcasestudy.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.DealItem
import com.target.targetcasestudy.data.Products

class DealItemAdapter(private val products: Products, private val context: Context, private val onClick: (dealItem: DealItem,imageView: ImageView) -> Unit) :
    RecyclerView.Adapter<DealItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.deal_list_item, parent, false)
        return DealItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.listOfProducts.size
    }

    override fun onBindViewHolder(viewHolder: DealItemViewHolder, position: Int) {
        viewHolder.bindView(products.listOfProducts[position], context, onClick)
    }
}

class DealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(item: DealItem, context: Context, onClick: (dealItem: DealItem,imageView: ImageView) -> Unit) {
        Glide.with(context)
            .load(item.image_url)
            .fitCenter()
            .into(itemView.findViewById(R.id.deal_list_item_image_view))
        itemView.findViewById<TextView>(R.id.deal_list_item_title).text = item.title
        if (item.sale_price != null) {
            itemView.findViewById<TextView>(R.id.deal_list_item_price).text =
                item.sale_price.display_string
        } else {
            itemView.findViewById<TextView>(R.id.deal_list_item_price).text =
                item.regular_price.display_string
        }
        itemView.findViewById<TextView>(R.id.deal_list_item_aisle).text = item.aisle.toUpperCase()

        itemView.setOnClickListener { onClick(item, itemView.findViewById(R.id.deal_list_item_image_view)) }
    }
}