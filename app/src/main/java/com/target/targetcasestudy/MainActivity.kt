package com.target.targetcasestudy

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.target.targetcasestudy.data.DealsViewModel
import com.target.targetcasestudy.data.DealsViewModelFactory
import com.target.targetcasestudy.data.Products
import com.target.targetcasestudy.ui.DealItemFragment
import com.target.targetcasestudy.ui.DealListFragment
import com.target.targetcasestudy.ui.payment.PaymentDialogFragment
import com.target.targetcasestudy.utils.NetworkResponse
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val dealListFragment = DealListFragment()
    @Inject
    lateinit var mViewModelFactory: DealsViewModelFactory

    private val mViewModel: DealsViewModel by lazy {
        ViewModelProvider(
            this,
            mViewModelFactory
        ).get(DealsViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        DaggerCoreComponent.builder().coreModule(CoreModule(this)).build()
            .injectMainActivity(this)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, dealListFragment)
            .commit()

        fetchDeals()
    }

    private fun fetchDeals() {
        val productsList = mViewModel.deals()
        productsList.observe(this@MainActivity, Observer {
            when (it) {
                is NetworkResponse.Success -> {
                    displayDeals(it.data)
                }
                is NetworkResponse.Failure -> {
                    Toast.makeText(this@MainActivity, "API error", Toast.LENGTH_SHORT).show()
                }
            }
            progressBar.visibility = View.GONE
        })
    }

    private fun displayDeals(products: Products) {
        dealListFragment.showDeals(products)
    }

    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.credit_card -> {
                PaymentDialogFragment().show(supportFragmentManager, "CreditCardValidation")
                true
            }
            else -> false
        }
    }
}
