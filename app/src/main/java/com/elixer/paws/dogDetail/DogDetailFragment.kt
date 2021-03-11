package com.elixer.paws.dogDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.elixer.paws.R
import com.elixer.paws.extensions.themeColor
import com.elixer.paws.util.ImageLoader
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.dog_detail_fragment.*

// Because you added androidx.navigation:navigation-fragment-ktx, you’re now able to set the
// layout for the Fragment directly in its constructor
class DogDetailFragment : Fragment(R.layout.dog_detail_fragment) {

    private val args: DogDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            isElevationShadowEnabled = true
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val (imageUrl, breed) = args
        ImageLoader.loadImage(requireContext(), imageUrl, image_dog_detail)
        textview_dog_breed.text = breed
        detail_container.transitionName = imageUrl
    }
}
