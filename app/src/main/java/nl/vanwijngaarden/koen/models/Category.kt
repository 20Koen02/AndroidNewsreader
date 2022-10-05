package nl.vanwijngaarden.koen.models

import nl.vanwijngaarden.koen.api.responses.GetArticlesResponse

data class Category(
    val id: Int,
    val name: String
) {
    companion object {
        fun fromResponse(categories: List<GetArticlesResponse.Result.Category>): List<Category> {
            return categories.map { c ->
                Category(
                    id = c.id,
                    name = c.name
                )
            }
        }
    }
}