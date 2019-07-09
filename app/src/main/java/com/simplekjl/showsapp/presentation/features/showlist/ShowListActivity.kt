package com.simplekjl.showsapp.presentation.features.showlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    val adapter: ShowCardsAdapter = ShowCardsAdapter(mutableListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.show_list_activity)
        //started point for the results
        getShows(1)
        // subscriptions to have an infinite scroll
        suscribeToMoreShows()
    }

    private fun suscribeToMoreShows() {
        activityViewModel.shows.observe(
            this, Observer { state ->
                when (state) {
                    is Loading -> {
                        Timber.d("Loading more ...")
                    }
                    is ErrorEx -> {
                        Timber.d(tag, "message : ${state.msg} , code: ${state.code}")
                    }
                    is ErrorMessage -> {
                        Timber.d(state.status_message)
                    }
                    is NotFoundMessage -> {
                        Timber.d(state.status_message)
                    }
                    is Success -> {
                        activityViewModel.nextPage = state.page + 1
                        activityViewModel.totalPages = state.total_pages
                        activityViewModel.loadingData = false
                        adapter.updateItems(state.results)
                    }
                }
            })
    }

    private fun getShows(page: Int) {
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
                    activityViewModel.nextPage = state.page + 1
                    activityViewModel.totalPages = state.total_pages
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
        //setting the adapter
        activityBinding.rvGeneric.layoutManager = LinearLayoutManager(this@ShowListActivity)
        activityBinding.rvGeneric.adapter = adapter
        adapter.updateItems(list)
        with(activityBinding.rvGeneric) {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager
                    val totalItemCount = layoutManager!!.itemCount
                    val lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                    Timber.d("item Count $totalItemCount")
                    if (lastVisibleItemPosition == totalItemCount.minus(2)) {
                        activityViewModel.loadNextPage()
                        Timber.d("Loading next page $lastVisibleItemPosition")
                    }

                }
            })
        }
    }

    override fun onItemClick(position: Int) {
        //Implement click listener to the items
    }

    override fun onDestroy() {
        super.onDestroy()
        activityViewModel.clear()
    }
}
