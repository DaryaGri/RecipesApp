package com.example.recipesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.R
import com.example.recipesapp.adapters.RecipesAdapter
import com.example.recipesapp.data.Recipe
import com.example.recipesapp.databinding.FragmentFavouriteRecipesBinding
import com.example.recipesapp.databinding.FragmentRecipesListBinding
import com.example.recipesapp.databinding.FragmentSearchRecipesBinding
import com.example.recipesapp.viewModels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavouriteRecipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipesViewModel by viewModels()

    lateinit var recipesAdapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupNavigation()
    }

    private fun setupNavigation() {
        recipesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("recipe", it)
            }
            findNavController().navigate(R.id.action_favouriteRecipes_to_recipeFragment, bundle)
        }
    }


    private fun setupRecyclerView() {
        recipesAdapter = RecipesAdapter(requireContext())
        binding.favouriteRecipesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.favouriteRecipesRecyclerView.adapter = recipesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}