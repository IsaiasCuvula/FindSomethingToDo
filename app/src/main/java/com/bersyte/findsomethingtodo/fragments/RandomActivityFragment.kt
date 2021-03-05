package com.bersyte.findsomethingtodo.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bersyte.findsomethingtodo.MainActivity
import com.bersyte.findsomethingtodo.R
import com.bersyte.findsomethingtodo.databinding.FragmentRandomActivityBinding
import com.bersyte.findsomethingtodo.models.RandomActivity
import com.bersyte.findsomethingtodo.viewmodel.FavoriteActivityViewModel
import com.bersyte.findsomethingtodo.viewmodel.RandomActivityViewModel
import java.util.*


class RandomActivityFragment :
    Fragment(R.layout.fragment_random_activity) {


    private var _binding: FragmentRandomActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var mRandomActivityViewModel: RandomActivityViewModel
    private var mProgressDialog: Dialog? = null
    private lateinit var viewModel: FavoriteActivityViewModel
    private lateinit var currActivity: RandomActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomActivityBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        mRandomActivityViewModel = ViewModelProvider(this)
            .get(RandomActivityViewModel::class.java)

        mRandomActivityViewModel.getRandomActivityFromAPI()
        randomActivityViewModelObserver()

        binding.swipeRefresh.setOnRefreshListener {
            mRandomActivityViewModel.getRandomActivityFromAPI()
        }

        saveFavAct()
    }

    private fun saveFavAct() {

        binding.ivFavoriteAct.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_favorite_unselected
            )
        )
        var addedToFavorites = false

        binding.ivFavoriteAct.setOnClickListener {

            if (addedToFavorites) {
                Toast.makeText(
                    activity,
                    " You already added it to favorite",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.insertFAvActivity(currActivity)
                addedToFavorites = true

                binding.ivFavoriteAct.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_favorite
                    )
                )
                Toast.makeText(
                    activity,
                    "Activity Added to Favorite",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun randomActivityViewModelObserver() {
        mRandomActivityViewModel.randomActivityResponse.observe(
            viewLifecycleOwner, { randomActivity ->
                randomActivity?.let {

                    currActivity = it

                    if (binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    binding.tvActivity.text = it.activity
                    binding.tvType.text = it.type
                    binding.tvAccessibility.text = it.accessibility.toString()
                    binding.tvParticipants.text = it.participants.toString()
                    binding.tvPrice.text = it.price.toString()
                    color()

                    if (it.link.isNullOrBlank()) {
                        binding.btnSeeMoreDetails.visibility = View.GONE
                    } else {
                        binding.btnSeeMoreDetails.visibility = View.VISIBLE
                        showActivityDetails(it)
                    }
                }

            }
        )

        mRandomActivityViewModel.randomActivityLoadingError.observe(
            viewLifecycleOwner,
            { dataError ->
                dataError?.let {
                    Log.e(
                        "aaa", "randomDishViewModelObserver: $dataError"
                    )
                    if (binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    }
                }
            })

        mRandomActivityViewModel.loadRandomActivity.observe(viewLifecycleOwner, { loadRandomDish ->
            loadRandomDish.let {
                if (loadRandomDish && !binding.swipeRefresh.isRefreshing) {
                    showProgressDialog()
                } else {
                    hideProgressDialog()
                }
            }
        })

    }

    private fun showProgressDialog() {
        mProgressDialog = Dialog(requireActivity())
        mProgressDialog?.let {
            it.setContentView(R.layout.dialog_custom_progress)
            it.show()
        }
    }

    private fun hideProgressDialog() {
        mProgressDialog?.dismiss()
    }

    private fun showActivityDetails(activity: RandomActivity) {
        binding.btnSeeMoreDetails.setOnClickListener { mView ->
            val direction = RandomActivityFragmentDirections
                .actionRandomActivityFragmentToActivityDetailsFragment(activity)
            mView.findNavController().navigate(direction)
        }
    }


    private fun color() {
        val random = Random()
        val color =
            Color.argb(
                255,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            )

        binding.ivActImage.setBackgroundColor(color)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}