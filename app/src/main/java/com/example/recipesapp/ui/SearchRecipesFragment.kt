package com.example.recipesapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipesapp.R
import com.example.recipesapp.databinding.FragmentRecipesListBinding
import com.example.recipesapp.databinding.FragmentSearchRecipesBinding
import com.example.recipesapp.viewModels.RecipesViewModel

class SearchRecipesFragment : Fragment(R.layout.fragment_search_recipes) {

    private var _binding: FragmentSearchRecipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipesViewModel by viewModels()




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}