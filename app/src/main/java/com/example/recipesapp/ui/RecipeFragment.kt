package com.example.recipesapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.recipesapp.databinding.FragmentRecipeBinding
import com.example.recipesapp.viewModels.RecipesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipesViewModel by viewModels()
    private val args: RecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebPage()
    }

    private fun setupWebPage() {
        val recipe = args.recipe
        binding.webView.apply {
            webViewClient = WebViewClient()
            recipe.sourceUrl?.let { loadUrl(it) }
        }
        binding.favouriteFloatingButton.setOnClickListener {
            viewModel.saveRecipe(recipe)
            view?.let { view -> Snackbar.make(view, "Recipe saved successfully", Snackbar.LENGTH_SHORT).show() }
        }
        binding.shareFloatingButton.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_SEND);
            myIntent.type = "text/plain"
            myIntent.putExtra(Intent.EXTRA_TEXT, recipe.sourceUrl )
            startActivity(Intent.createChooser(myIntent, "Share with"))

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}