package com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime

data class AnimeArticleData(
    val status:Boolean,
    val data: List<Data>?,
    val errors: KitsuError?
)

sealed class UiState{

    class Error(val throwable:Throwable):UiState(), Ui{
        override fun render() {
            TODO("Not yet implemented")
        }
    }

    class Loading:UiState(), Ui{
        override fun render() {
            TODO("Not yet implemented")
        }
    }
    class Success(val anime:AnimeArticleData):UiState(), Ui{
        override fun render() {
            TODO("Not yet implemented")
        }
    }
}

interface Ui{
    fun render()
}