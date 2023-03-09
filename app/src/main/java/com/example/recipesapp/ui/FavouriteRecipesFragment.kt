package com.example.recipesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.R
import com.example.recipesapp.ui.adapters.RecipesAdapter
import com.example.recipesapp.data.DbRecipes
import com.example.recipesapp.data.Recipe
import com.example.recipesapp.databinding.FragmentFavouriteRecipesBinding
import com.example.recipesapp.viewModels.RecipesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class FavouriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavouriteRecipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipesViewModel by viewModels()

    lateinit var recipesAdapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupNavigation()
        setupSwipeToDeleteRecipe()
        setupObservers()
    }

    private fun setupNavigation() {
        recipesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("recipe", it)
            }
            findNavController().navigate(R.id.action_favouriteRecipes_to_recipeFragment, bundle)
        }
    }

    private fun setupObservers() {
        viewModel.getFavouriteRecipes().observe(viewLifecycleOwner) { recipes ->
            recipesAdapter.differ.submitList(convertDbRecipesToRecipe(recipes))
        }
    }

    private fun setupSwipeToDeleteRecipe() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val recipe = recipesAdapter.differ.currentList[position]
                viewModel.deleteRecipe(recipe)
                view?.let {
                    Snackbar.make(
                        it,
                        "Successfully deleted recipe", Snackbar.LENGTH_LONG
                    )
                }?.apply {
                    setAction("Undo") {
                        viewModel.saveRecipe(recipe)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.favouriteRecipesRecyclerView)
        }
    }

    private fun convertDbRecipesToRecipe(data: List<DbRecipes>): MutableList<Recipe> {
        val result = mutableListOf<Recipe>()
        for (elem in data) {
            result.add(
                Recipe(
                    null, null, null, null, null, null,
                    false, null, null, null, "", false,
                    null, null, elem.image, "", elem.instructions,
                    null, null, null, "", null,
                    null, null, null, "", elem.sourceUrl, "",
                    elem.summary, false, elem.title, false, false,
                    false, false, null
                )
            )
        }
        return result
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