package com.example.flows.dogDetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.flows.R
import kotlinx.android.synthetic.main.dog_detail_fragment.*

// Because you added androidx.navigation:navigation-fragment-ktx, you’re now able to set the
// layout for the Fragment directly in its constructor
class DogDetailFragment : Fragment(R.layout.dog_detail_fragment) {

    private val args: DogDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val (imageUrl, breed) = args

        setSharedElementTransitionOnEnter()
        postponeEnterTransition()

        image_view_full_screen_doggo.apply {
            transitionName = imageUrl
            startEnterTransitionAfterLoadingImage(imageUrl, this)
        }
    }

//    private fun loadLayout() {
//        val (imageUrl, breed) = args
//        setSharedElementTransitionOnEnter()
//        postponeEnterTransition()
// //        postponeEnterTransition()
// //        ImageLoader.loadImage(requireContext(), imageUrl, image)
//        Log.e("FRA", id.toString())
//        image.apply {
//            transitionName = imageUrl
//            startEnterTransitionAfterLoadingImage(imageUrl, this)
//        }
//        name.text = breed
//    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition)
    }

    private fun startEnterTransitionAfterLoadingImage(imageAddress: String, imageView: ImageView) {
        Glide.with(this)
                .load(imageAddress)
                .dontAnimate() // 1
                .listener(object : RequestListener<Drawable> { // 2
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: com.bumptech.glide.request.target.Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }
                })
                .into(imageView)
    }
}