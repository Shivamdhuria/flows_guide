package com.example.flows.main.network

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

    fun favoritesSortOrder() = flow {
        while (true) {
            val list = getDogs().map { it.capitalize() }.shuffled().subList(0, 50)
            emit(list)
            delay(2000)
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