package com.example.flows.searchDog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.flows.R
import com.example.flows.util.ImageLoader
import kotlinx.android.synthetic.main.dog_detail_fragment.*
import kotlinx.android.synthetic.main.fragment_search_dog_detail.*

class SearchDogDetailFragment : Fragment(R.layout.fragment_search_dog_detail) {

    private val args: SearchDogDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val (imageUrl, breed) = args
        ImageLoader.loadImage(requireContext(), imageUrl, animate_image)
        // textview_dog_breed.text = breed
    }
}