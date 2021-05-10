package com.target.targetcasestudy.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.DealItem
import kotlinx.android.synthetic.main.fragment_deal_item.*


class DealItemFragment : Fragment() {

    private lateinit var mDealItem: DealItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.zoom_in_image_transform)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deal_item, container, false)
    }

    override fun onResume() {
        super.onResume()

        ViewCompat.setTransitionName(deal_image_view, "deal_image_view")

        mDealItem.let { dealItem ->
            if (dealItem.sale_price != null) {
                deal_price.text = dealItem.sale_price.display_string
                deal_regular.visibility = View.VISIBLE
                deal_regular_price.visibility = View.VISIBLE
                deal_regular_price.text = mDealItem.regular_price.display_string
                deal_regular_price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                deal_price.text = dealItem.regular_price.display_string
            }

            deal_title.text = dealItem.title
            deal_description.text = dealItem.description

            Glide.with(requireContext())
                .load(dealItem.image_url)
                .fitCenter()
                .into(deal_image_view)
        }
    }

    internal fun showDeal(dealItem: DealItem) {
        mDealItem = dealItem
    }
}
