package com.simplekjl.showsapp.presentation.features.showlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.simplekjl.showsapp.R
import com.simplekjl.showsapp.data.model.Show
import com.simplekjl.showsapp.databinding.ShowListActivityBinding
import com.simplekjl.showsapp.presentation.features.showlist.adapter.ItemClickListener
import com.simplekjl.showsapp.presentation.features.showlist.adapter.ShowCardsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ShowListActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var activityBinding: ShowListActivityBinding
    private val activityViewModel: ShowListActivityViewModel by viewModel()
    private val tag = ShowListActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.show_list_activity)
        getShows(1)
    }

    private fun getShows(page: Int) {
        //use view model and render state
        activityViewModel.getShows(page).observe(this, Observer { state ->
            when (state) {
                is Loading -> {
                    showLoader()
                }
                is ErrorEx -> {
                    showErrorMessage()
                    Timber.d(tag, "message : ${state.msg} , code: ${state.code}")
                }
                is ErrorMessage -> {
                    updateErrorMessage(state.status_message)
                }
                is NotFoundMessage -> {
                    updateErrorMessage(state.status_message)
                }
                is Success -> {
                    showList()
                    renderShows(state.results)
                }
            }
        })
    }

    private fun updateErrorMessage(msg: String) {
        activityBinding.errorMessage.text = msg
    }

    private fun showErrorMessage() {
        activityBinding.errorMessage.visibility = View.VISIBLE
        activityBinding.rvGeneric.visibility = View.INVISIBLE
        activityBinding.progressBar.visibility = View.INVISIBLE
    }

    private fun showLoader() {
        activityBinding.errorMessage.visibility = View.INVISIBLE
        activityBinding.rvGeneric.visibility = View.INVISIBLE
        activityBinding.progressBar.visibility = View.VISIBLE
    }

    private fun showList() {
        activityBinding.errorMessage.visibility = View.INVISIBLE
        activityBinding.rvGeneric.visibility = View.VISIBLE
        activityBinding.progressBar.visibility = View.INVISIBLE
    }

    private fun renderShows(list: List<Show>) {
        with(activityBinding.rvGeneric) {
            layoutManager = LinearLayoutManager(this@ShowListActivity)
            adapter = ShowCardsAdapter(list, this@ShowListActivity)
        }
    }

    override fun onItemClick(position: Int) {
        //TODO item click
    }

    override fun onDestroy() {
        super.onDestroy()
        activityViewModel.clear()
    }
}
