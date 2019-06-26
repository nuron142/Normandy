package com.sunil.kumar.ui.postdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sunil.kumar.R
import com.sunil.kumar.databinding.ActivityPostDetailBinding
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.ui.UiState
import com.sunil.kumar.ui.adapter.BindingRecyclerAdapter
import com.sunil.kumar.ui.adapter.PostListDividerDecoration
import com.sunil.kumar.util.getJson
import com.sunil.kumar.util.itemanimators.AlphaCrossFadeAnimator
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PostDetailActivity : DaggerAppCompatActivity() {

    companion object {

        const val TAG = "PostDetailActivity"

        const val ANIMATOR_DURATION = 200L
        const val POST_DATA = "postData"

        fun launch(context: Context, postData: PostData) {

            val intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra(POST_DATA, postData.getJson())
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var postDetailActivityViewModel: PostDetailActivityViewModel

    private lateinit var binding: ActivityPostDetailBinding

    private val allSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)
        binding.lifecycleOwner = this

        postDetailActivityViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PostDetailActivityViewModel::class.java)

        binding.vm = postDetailActivityViewModel
        binding.executePendingBindings()

        subscribeToViewModel()

        setupRecyclerView()

        postDetailActivityViewModel.setUpViewModel()

    }

    private fun setupRecyclerView() {

        val itemAnimator = AlphaCrossFadeAnimator()

        itemAnimator.addDuration = ANIMATOR_DURATION
        itemAnimator.removeDuration = ANIMATOR_DURATION
        itemAnimator.changeDuration = ANIMATOR_DURATION
        itemAnimator.moveDuration = ANIMATOR_DURATION

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = BindingRecyclerAdapter(
            postDetailActivityViewModel.dataSet,
            postDetailActivityViewModel.viewModelLayoutIdMap
        )
        binding.recyclerView.addItemDecoration(
            PostListDividerDecoration(resources.getDimensionPixelSize(R.dimen.divider_size))
        )

        binding.recyclerView.itemAnimator = itemAnimator
        binding.recyclerView.adapter = adapter
    }

    private fun subscribeToViewModel() {

        allSubscriptions.add(
            postDetailActivityViewModel.closeDetailSubject.hide()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    this@PostDetailActivity.finish()
                }, { e -> Log.e(TAG, "Error : " + e.message) })
        )

        postDetailActivityViewModel.uiState.observe(this, Observer { uiState ->

            when (uiState) {

                UiState.Loading -> {
                    postDetailActivityViewModel.showProgress.set(true)
                }

                UiState.DataLoaded -> {
                    postDetailActivityViewModel.showProgress.set(false)
                }

                UiState.Empty -> {
                    postDetailActivityViewModel.showProgress.set(false)
                    Snackbar.make(binding.root, getString(R.string.no_results_found), Snackbar.LENGTH_LONG).show()
                }

                UiState.Error -> {
                    postDetailActivityViewModel.showProgress.set(false)
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
