package com.sunil.kumar.ui.postlist

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sunil.kumar.R
import com.sunil.kumar.databinding.ActivityPostListBinding
import com.sunil.kumar.ui.UiState
import com.sunil.kumar.ui.adapter.BindingRecyclerAdapter
import com.sunil.kumar.ui.adapter.PostListDividerDecoration
import com.sunil.kumar.ui.postdetail.PostDetailActivity
import com.sunil.kumar.util.itemanimators.AlphaCrossFadeAnimator
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PostListActivity : DaggerAppCompatActivity() {

    companion object {

        const val TAG = "PostListActivity"
        const val ANIMATOR_DURATION = 200L
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var postListActivityViewModel: PostListActivityViewModel

    private lateinit var binding: ActivityPostListBinding

    private val allSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_list)

        postListActivityViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PostListActivityViewModel::class.java)

        binding.vm = postListActivityViewModel
        binding.executePendingBindings()

        subscribeToViewModel()

        setupRecyclerView()

        postListActivityViewModel.setUpViewModel()
    }

    private fun setupRecyclerView() {

        val itemAnimator = AlphaCrossFadeAnimator()

        itemAnimator.addDuration = ANIMATOR_DURATION
        itemAnimator.removeDuration = ANIMATOR_DURATION
        itemAnimator.changeDuration = ANIMATOR_DURATION
        itemAnimator.moveDuration = ANIMATOR_DURATION

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = BindingRecyclerAdapter(
            postListActivityViewModel.dataSet,
            postListActivityViewModel.viewModelLayoutIdMap
        )
        binding.recyclerView.addItemDecoration(
            PostListDividerDecoration(resources.getDimensionPixelSize(R.dimen.divider_size))
        )

        binding.recyclerView.itemAnimator = itemAnimator
        binding.recyclerView.adapter = adapter
    }

    private fun subscribeToViewModel() {

        allSubscriptions.add(
            postListActivityViewModel.openDetailPageSubject.hide()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ postData ->

                    PostDetailActivity.launch(this@PostListActivity, postData)
                }, { e -> Log.e(TAG, "Error : " + e.message) })
        )

        postListActivityViewModel.uiState.observe(this, Observer { uiState ->

            when (uiState) {

                UiState.Loading -> {
                    postListActivityViewModel.showProgress.set(true)
                }

                UiState.DataLoaded -> {
                    postListActivityViewModel.showProgress.set(false)
                }

                UiState.Empty -> {
                    postListActivityViewModel.showProgress.set(true)
                    Snackbar.make(binding.root, getString(R.string.no_results_found), Snackbar.LENGTH_LONG).show()
                }

                UiState.Error -> {
                    postListActivityViewModel.showProgress.set(true)
                    Snackbar.make(binding.root, getString(R.string.general_error), Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        allSubscriptions.dispose()
    }
}
