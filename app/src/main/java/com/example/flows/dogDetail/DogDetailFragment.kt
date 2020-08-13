package com.example.flows.dogDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.flows.R
import com.example.flows.util.ImageLoader
import kotlinx.android.synthetic.main.dog_detail_fragment.*

// Because you added androidx.navigation:navigation-fragment-ktx, you’re now able to set the
// layout for the Fragment directly in its constructor
class DogDetailFragment : Fragment(R.layout.dog_detail_fragment) {

    private val args: DogDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadLayout()
    }

    private fun loadLayout() {
        val (imageUrl, breed) = args
        ImageLoader.loadImageWithCircularCrop(requireContext(), imageUrl, image)
        name.text = breed
    }
}