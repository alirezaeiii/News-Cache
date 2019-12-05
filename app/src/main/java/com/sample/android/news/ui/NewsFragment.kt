package com.sample.android.news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.sample.android.news.BR
import com.sample.android.news.R
import com.sample.android.news.databinding.FragmentNewsBinding
import com.sample.android.news.domain.Article
import com.sample.android.news.util.setupActionBar
import com.sample.android.news.viewmodels.NewsViewModel


class NewsFragment : BaseFragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: NewsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, NewsViewModel.Factory(activity.application))
            .get(NewsViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of Articles to cards.
     */
    private lateinit var viewModelAdapter: NewsAdapter

    private lateinit var binding: FragmentNewsBinding

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.news.observe(viewLifecycleOwner, Observer<List<Article>> { articles ->
            articles.let {
                viewModelAdapter.submitList(articles)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false).apply {
            setVariable(BR.vm, viewModel)
            // Set the lifecycleOwner so DataBinding can observe LiveData
            lifecycleOwner = viewLifecycleOwner
        }

        viewModelAdapter =
            NewsAdapter(NewsAdapter.OnClickListener { article, imageView, titleTextView, descriptionTextView ->
                val extras = FragmentNavigatorExtras(
                    imageView to article.url,
                    titleTextView to article.title,
                    descriptionTextView to getString(R.string.transition_description, article.url)
                )
                findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToDetailFragment(article), extras)
            })

        with(binding) {
            recyclerView.apply {
                setHasFixedSize(true)
                adapter = viewModelAdapter
                postponeEnterTransition()
                viewTreeObserver
                    .addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
            }

            (activity as AppCompatActivity).setupActionBar(toolbar) {
                setTitle(R.string.app_name)
            }
        }

        return binding.root
    }
}