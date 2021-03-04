package com.bersyte.findsomethingtodo.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bersyte.findsomethingtodo.MainActivity
import com.bersyte.findsomethingtodo.R
import com.bersyte.findsomethingtodo.adapter.FavActivityAdapter
import com.bersyte.findsomethingtodo.databinding.FragmentFavoriteBinding
import com.bersyte.findsomethingtodo.models.RandomActivity
import com.bersyte.findsomethingtodo.viewmodel.FavoriteActivityViewModel


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteActivityViewModel
    private lateinit var mFavActivityAdapter: FavActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        mFavActivityAdapter = FavActivityAdapter(this)

        binding.rvFavActivity.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mFavActivityAdapter
        }

        viewModel.allFavActivities.observe(viewLifecycleOwner, { list ->

            list?.let {
                mFavActivityAdapter.differ.submitList(it)
                updateUI(it)
            }
        })

    }

    fun deleteFavActivity(favActivity: RandomActivity) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Delete Activity")

        builder.setMessage("Are you sure wants to delete this activity?")

        builder.setIcon(
           android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton(
           "YES"
        ) { dialogInterface, _ ->
            viewModel.deleteFAvActivity(favActivity)
            Toast.makeText(
                activity,
                " You delete your activity",
                Toast.LENGTH_SHORT
            ).show()
            dialogInterface.dismiss()
        }

        builder.setNegativeButton(
            "NO"
        ) { dialogInterface, _ ->

            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    private fun updateUI(favAct: List<RandomActivity>) {
        if (favAct.isNotEmpty()) {
            binding.rvFavActivity.visibility = View.VISIBLE
            binding.tvNoFavActAddedYet.visibility = View.GONE
        } else {
            binding.rvFavActivity.visibility = View.GONE
            binding.tvNoFavActAddedYet.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}