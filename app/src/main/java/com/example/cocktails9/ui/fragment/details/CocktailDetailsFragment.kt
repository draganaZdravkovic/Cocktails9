package com.example.cocktails9.ui.fragment.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cocktails9.R
import com.example.cocktails9.data.model.CocktailDetails
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.data.model.Resource
import com.example.cocktails9.databinding.FragmentCocktailDetailsBinding
import com.example.cocktails9.ui.activity.MainActivity
import com.example.cocktails9.ui.fragment.cocktails.viewmodel.CocktailsViewModel
import com.example.cocktails9.ui.fragment.details.viewmodel.CocktailsDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailDetailsFragment : Fragment(R.layout.fragment_cocktail_details) {
    private var _binding: FragmentCocktailDetailsBinding? = null
    private val binding get() = _binding!!

    private val cocktailsDetailsViewModel by viewModels<CocktailsDetailsViewModel>()
    private val cocktailsViewModel by viewModels<CocktailsViewModel>()

    private val args: CocktailDetailsFragmentArgs by navArgs()
    private lateinit var cocktailID: String
    private var isFavorite: Boolean = false

    private lateinit var userEmail: String
    private lateinit var cocktail: Cocktails

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailDetailsBinding.inflate(inflater, container, false)

        cocktailID = args.drinkID ?: ""
        isFavorite = args.isFavorite

        userEmail = (activity as MainActivity).userEmail

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initClickListener()
    }

    private fun initClickListener() {
        binding.ivFav.setOnClickListener {

            if (isFavorite) {
                cocktailsViewModel.removeFavorite(cocktailID, userEmail)
            } else {
                cocktailsViewModel.addFavorite(cocktail, userEmail)
            }
        }
    }

    private fun initObservers() {
        cocktailsDetailsViewModel.getCocktailDetails.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    showLoading(false)

                    setContentData(resource.data)
                    cocktail = Cocktails(
                        id = resource.data.id,
                        userEmail = userEmail,
                        image = resource.data.image,
                        name = resource.data.name,
                        alcoholic = resource.data.alcoholic,
                        isFavorite = isFavorite
                    )
                }
                is Resource.Loading -> showLoading(resource.isLoading)
                is Resource.Error -> showErrorDialog(resource.message)
            }
        }
        cocktailsDetailsViewModel.getCocktailDetails(cocktailID)

        cocktailsDetailsViewModel.getIsFavorite(cocktailID, userEmail)
        cocktailsDetailsViewModel.isFavorite.observe(viewLifecycleOwner) {
            isFavorite = it
            if (it)
                setImage(binding.ivFav, R.drawable.ic_fav_on)
            else
                setImage(binding.ivFav, R.drawable.ic_fav_off_background)
        }
    }

    private fun setContentData(cocktail: CocktailDetails) {
        Glide.with(binding.ivCocktail.context).load(cocktail.image)
            .into(binding.ivCocktail)

        binding.tvName.text = resources.getString(R.string.text_with_end_space, cocktail.name)
        binding.tvAlcoholic.text =
            resources.getString(R.string.text_with_end_space, cocktail.alcoholic)
        binding.tvSpecificCategory.text =
            resources.getString(R.string.text_with_end_space, cocktail.category)
        binding.tvSpecificGlassType.text =
            resources.getString(R.string.text_with_end_space, cocktail.glass)
        binding.tvSpecificIngredients.text =
            resources.getString(R.string.text_with_end_space, cocktail.ingredientMeasureString)
        binding.tvSpecificInstructions.text =
            resources.getString(R.string.text_with_end_space, cocktail.instructions)
    }

    private fun setImage(ivFavorite: ImageView, imgRes: Int) {
        Glide.with(ivFavorite.context).load(imgRes)
            .into(ivFavorite)
    }

    private fun showErrorDialog(message: String) {
        binding.group.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        AlertDialog.Builder(context)
            .setTitle(resources.getString(R.string.cocktails_error))
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ -> }
            .show()
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.group.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.group.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}