<p align="center">
<img src="./previews/logo.png" width="150">
</p>

<p align="center">
An Android Application written purely in Kotlin utilizing all the latest tech in Android. <br> The project uses Material Design guidelines,  MVVM architecture, Hilt for Dependency Injection and uses Room for an offline- first approach.
  </p>

<p align="center">
<img src="./previews/introCompressed.jpg" width="700">
</p>


# Medium Articles 

  | Medium Article  | Github Branch|
| ------ | ------ |
| [What the Flows: Build an Android app using Flows, Live Data, and MVVM architecture](https://proandroiddev.com/what-the-flows-build-an-android-app-using-flows-and-live-data-using-mvvm-architecture-4d3ab807b4dd) | [begin_flows](https://github.com/Shivamdhuria/flows_guide/tree/begin_flows) |
| [Implementing Search Filter using Kotlin Channels and Flows in your Android Application](https://medium.com/@shivamdhuria/implementing-search-filter-using-kotlin-channels-and-flows-in-your-android-application-df7c96e58b19)  | [implementing_search_in_database](https://github.com/Shivamdhuria/flows_guide/tree/implementing_search_in_database)|
| Adding Hilt (No Article ❌) | [migrating_to_hilt](https://github.com/Shivamdhuria/flows_guide/tree/migrating_to_hilt) |
| [Adding animations to your Android application using Lottie library](https://medium.com/@shivamdhuria/adding-animations-to-your-android-application-using-lottie-library-fa2d7197e23b) | [adding_animation](https://github.com/Shivamdhuria/flows_guide/tree/add_animation_final) |
| Adding Stetho (Medium article in progress) | [adding_stetho](https://github.com/Shivamdhuria/flows_guide/tree/adding_stetho) |
| Migrating to Single Activity architecture with Navigation Components (No Article ❌) | [migrate_to_single_activity_and_navigation_component](https://github.com/Shivamdhuria/flows_guide/tree/migrate_to_single_activity_and_navigation_component) |
| [Adding amazing transitions to your Android App easily using Material Motion](https://medium.com/@shivamdhuria/adding-amazing-transitions-to-your-android-app-easily-using-material-motion-f0cd92463b39) | [added_transition_using_material_motion](https://github.com/Shivamdhuria/flows_guide/tree/added_transition_using_material_motion) |


# Tech Stack
<img src="/previews/transition.gif" align="right" width="20%"/>

- [Kotlin](https://kotlinlang.org/)  
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)  
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Dagger-Hilt (alpha) for dependency injection.
- JetPack Components
- Architecture
  - MVVM Architecture 
  - Repository pattern
  - Database Cache as Single Source of Truth
  
  
  # Libraries
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette) - loading images.



## Roadmap (Features to be added )
  - Shared Element Transition from Dog List to Dog Details Fragment
  - Bottom Sheet for all details related to the dog in Dog Details Fragment
  - Using Color Pallette API for setting background color of bottom sheet above
  - Recycler - Selection library with contexual Action mode for setting a Dog as Favorite
  - Using Paging 3 library 
  - Update this Readme for APp screenshots, credits, libraries used and contribution.
