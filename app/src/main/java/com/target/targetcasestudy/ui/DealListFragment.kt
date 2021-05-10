package com.target.targetcasestudy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.DealItem
import com.target.targetcasestudy.data.Products
import kotlinx.android.synthetic.main.fragment_deal_list.*


class DealListFragment : Fragment() {

  private val dealItemFragment = DealItemFragment()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_deal_list, container, false)
  }

  internal fun showDeals(products: Products) {
    recycler_view.layoutManager = LinearLayoutManager(requireContext())
    recycler_view.adapter = DealItemAdapter(products, requireContext(), ::onClick )
  }

  private fun onClick(dealItem: DealItem, imageView: ImageView){
    ViewCompat.setTransitionName(imageView, "deal_list_item_image_view")

    fragmentManager?.let {
      val transaction = it.beginTransaction()
      transaction.add(R.id.container, dealItemFragment)
      transaction.addSharedElement(imageView, "deal_image_view")
      transaction.addToBackStack(null)
      transaction.commit()
    }

    dealItemFragment.showDeal(dealItem)
  }
}
