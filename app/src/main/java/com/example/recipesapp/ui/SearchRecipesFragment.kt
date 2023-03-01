package com.example.recipesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.R
import com.example.recipesapp.adapters.RecipesAdapter
import com.example.recipesapp.databinding.FragmentSearchRecipesBinding
import com.example.recipesapp.util.Constants.SEARCH_RECIPES_TIME_DELAY
import com.example.recipesapp.util.Resource
import com.example.recipesapp.viewModels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchRecipesFragment : Fragment() {

    private var _binding: FragmentSearchRecipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipesViewModel by viewModels()

    lateinit var recipesAdapter: RecipesAdapter
    val TAG = "SearchRecipesFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        searchTimeDelay()
        setupObservers()
        setupNavigation()
    }

    private fun setupNavigation() {
        recipesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("recipe", it)
            }
            findNavController().navigate(R.id.action_searchRecipesFragment_to_recipeFragment, bundle)
        }
    }

    private fun searchTimeDelay() {
        var job: Job? = null
        binding.searchEditText.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_RECIPES_TIME_DELAY)
                editable?.let {
                   if(editable.toString().isNotEmpty()){
                      viewModel.searchRecipes(editable.toString())
                   }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recipesAdapter = RecipesAdapter(requireContext())
        binding.searchRecipeRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchRecipeRecyclerview.adapter = recipesAdapter
    }


    private fun setupObservers() {
        viewModel.searchData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { recipesResponse ->
                        recipesAdapter.differ.submitList(recipesResponse.recipes)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occurred $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}