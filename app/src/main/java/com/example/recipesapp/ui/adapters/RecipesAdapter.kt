package com.example.recipesapp.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipesapp.R
import com.example.recipesapp.data.Recipe
import com.example.recipesapp.databinding.RecipesItemPreviewBinding

class RecipesAdapter(val context: Context) :
    RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    inner class RecipesViewHolder(private val binding: RecipesItemPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recipe) {
            with(binding) {
                Glide.with(context)
                    .load(item.image)
                    .placeholder(R.drawable.baseline_lunch_dining_24)
                    .into(recipeImageImageView)
                titleTextView.text = item.title
                descriptionTextView.text = item.instructions
                ingredientsTextView.text = item.summary

                if(item.instructions.isNullOrEmpty()){
                    descriptionTextView.visibility = View.GONE
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val binding =
            RecipesItemPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Recipe) -> Unit)? = null

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val recipe = differ.currentList[position]
        holder.bind(recipe)
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(recipe) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Recipe) -> Unit) {
        onItemClickListener = listener
    }
}