package com.example.flows.main.network

import android.util.Log
import com.example.flows.main.data.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: MainActivityApi) {

    suspend fun fetchRandomDog(): Dog = withContext(Dispatchers.IO) {
        val result = api.getRandomImageByUrl()
        val breedImageUrl = result.message
        extractBreedName(breedImageUrl)?.let { Dog(it, breedImageUrl) }!!
    }

    private fun extractBreedName(message: String): String? {
        val breedName = message.substringAfter("breeds/").substringBefore("/")
        return breedName.replace(Regex("-"), " ").capitalize()
    }


    //Emit all At once
//    suspend fun favoritesSortOrder(): List<String> = withContext(Dispatchers.IO) {
//        delay(10000)
//        listOf<String>(
//            "Setter gordon", "Terrier patterdale", "Germanshepherd", "Ridgeback rhodesian", "Dachshund", "Pomeranian",
//            "Pekinese", "Redbone", "Lhasa", "Chow", "Retriever curly", "Kelpie", "Terrier silky", "Spaniel welsh", "Otterhound"
//        )
//    }


    fun favoritesSortOrder() = flow<List<String>> {

        val allDogList = listOf<String>(
            "Setter gordon","Terrier patterdale","Germanshepherd", "Ridgeback rhodesian", "Dachshund", "Pomeranian",
            "Pekinese", "Redbone", "Lhasa", "Chow","Retriever curly", "Kelpie", "Terrier silky", "Spaniel welsh", "Otterhound",
            "shepherd",
            "basenji",
            "beagle",
            "bluetick",
            "borzoi",
            "bouvier",
            "boxer",
            "brabancon",
            "briard",
            "buhund",
            "norwegian"
        )



        while (true) {
            val list = allDogList.shuffled().subList(0, 20)
            Log.e("Fav Emit SOrt....", list.toString())
            emit(list)
            delay(5000)
        }
    }

    private fun getDogs() = listOf<String>(
        "affenpinscher",
        "african",
        "airedale",
        "akita",
        "appenzeller",
        "australian",
        "shepherd",
        "basenji",
        "beagle",
        "bluetick",
        "borzoi",
        "bouvier",
        "boxer",
        "brabancon",
        "briard",
        "buhund",
        "norwegian",
        "bulldog",
        "boston",
        "english",
        "french",
        "bullterrier",
        "staffordshire",
        "cairn",
        "cattledog",
        "australian",
        "chihuahua",
        "chow",
        "clumber",
        "cockapoo",
        "collie",
        "border",
        "coonhound",
        "corgi",
        "cotondetulear",
        "dachshund",
        "dalmatian",
        "dane",
        "great",
        "deerhound",
        "scottish",
        "dhole",
        "dingo",
        "doberman",
        "entlebucher",
        "eskimo",
        "finnish",
        "lapphund",
        "frise",
        "bichon",
        "germanshepherd",
        "greyhound",
        "italian",
        "groenendael",
        "havanese",
        "husky",
        "keeshond",
        "kelpie",
        "komondor",
        "kuvasz",
        "labrador",
        "leonberg",
        "lhasa",
        "malamute",
        "malinois",
        "maltese",
        "mexicanhairless",
        "mix",
        "newfoundland",
        "otterhound",
        "papillon",
        "pekinese",
        "pembroke",
        "pinscher",
        "miniature",
        "pitbull",
        "pointer",
        "german",
        "germanlonghair",
        "pomeranian",
        "poodle",
        "miniature",
        "standard",
        "toy",
        "pug",
        "schnauzer",
        "giant",
        "miniature",
        "setter",
        "english",
        "gordon",
        "irish",
        "sheepdog",
        "english",
        "shetland",
        "shiba",
        "shihtzu",
        "spaniel",
        "blenheim"
    )
}