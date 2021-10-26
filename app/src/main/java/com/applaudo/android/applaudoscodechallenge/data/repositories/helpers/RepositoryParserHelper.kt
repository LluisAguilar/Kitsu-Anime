package com.applaudo.android.applaudoscodechallenge.data.repositories.helpers

import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.anime.*
import retrofit2.Response


fun AnimeArticleDataToAnimeArticleDataUseCase(animeArticle: Response<AnimeArticleData>): Response<AnimeArticleDataUseCase> {
    if (animeArticle.isSuccessful){
        return Response.success(AnimeArticleDataUseCase(animeArticle.isSuccessful, dataToDatUseCase(animeArticle.body()?.data), KitsuError(getErrorList(animeArticle.body()?.errors))))
    } else {
        return Response.error(animeArticle.code(), animeArticle.errorBody()!!)
    }
}

private fun dataToDatUseCase(data: List<com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.Data>?): List<Data>{
    val dataList = mutableListOf<Data>()

    data?.let {

        for (x in 0 until it.size){
            dataList.add(Data(it.get(x).status,
                Attributes(
                    it.get(x).attributes?.abbreviatedTitles,
                    it.get(x).attributes?.ageRating,
                    it.get(x).attributes?.ageRatingGuide,
                    it.get(x).attributes?.averageRating,
                    it.get(x).attributes?.canonicalTitle,
                    CoverImage(
                        it.get(x).attributes?.coverImage?.large,
                        Meta(
                            Dimensions(
                                Large(
                                    it.get(x).attributes?.coverImage?.meta?.dimensions?.large?.height,
                                    it.get(x).attributes?.coverImage?.meta?.dimensions?.large?.width),
                                Medium(
                                    it.get(x).attributes?.coverImage?.meta?.dimensions?.medium?.height,
                                    it.get(x).attributes?.coverImage?.meta?.dimensions?.medium?.width),
                                Small(
                                    it.get(x).attributes?.coverImage?.meta?.dimensions?.small?.height,
                                    it.get(x).attributes?.coverImage?.meta?.dimensions?.small?.width),
                                Tiny(
                                    it.get(x).attributes?.coverImage?.meta?.dimensions?.tiny?.height,
                                    it.get(x).attributes?.coverImage?.meta?.dimensions?.tiny?.width)
                                )),
                        it.get(x).attributes?.coverImage?.original,
                        it.get(x).attributes?.coverImage?.small,
                        it.get(x).attributes?.coverImage?.tiny),
                    it.get(x).attributes?.coverImageTopOffset,
                    it.get(x).attributes?.createdAt,
                    it.get(x).attributes?.description,
                    it.get(x).attributes?.endDate,
                    it.get(x).attributes?.episodeCount,
                    it.get(x).attributes?.episodeLength,
                    it.get(x).attributes?.favoritesCount,
                    it.get(x).attributes?.nextRelease,
                    it.get(x).attributes?.nsfw,
                    it.get(x).attributes?.popularityRank,
                    PosterImage(
                        it.get(x).attributes?.posterImage?.large,
                        it.get(x).attributes?.posterImage?.medium,
                        Meta(
                            Dimensions(
                                Large(
                                    it.get(x).attributes?.posterImage?.meta?.dimensions?.large?.height,
                                    it.get(x).attributes?.posterImage?.meta?.dimensions?.large?.width),
                                Medium(
                                    it.get(x).attributes?.posterImage?.meta?.dimensions?.medium?.height,
                                    it.get(x).attributes?.posterImage?.meta?.dimensions?.medium?.width
                                ),
                                Small(
                                    it.get(x).attributes?.posterImage?.meta?.dimensions?.small?.height,
                                    it.get(x).attributes?.posterImage?.meta?.dimensions?.small?.width
                                ),
                                Tiny(
                                    it.get(x).attributes?.posterImage?.meta?.dimensions?.tiny?.height,
                                    it.get(x).attributes?.posterImage?.meta?.dimensions?.tiny?.width
                                )
                            )),it.get(x).attributes?.posterImage?.original,
                        it.get(x).attributes?.posterImage?.small,
                        it.get(x).attributes?.posterImage?.tiny),
                    RatingFrequencies(
                        it.get(x).attributes?.ratingFrequencies?.ten,
                        it.get(x).attributes?.ratingFrequencies?.eleven,
                        it.get(x).attributes?.ratingFrequencies?.twelve,
                        it.get(x).attributes?.ratingFrequencies?.thirteen,
                        it.get(x).attributes?.ratingFrequencies?.fourteen,
                        it.get(x).attributes?.ratingFrequencies?.fifteen,
                        it.get(x).attributes?.ratingFrequencies?.sixteen,
                        it.get(x).attributes?.ratingFrequencies?.seventeen,
                        it.get(x).attributes?.ratingFrequencies?.eighteen,
                        it.get(x).attributes?.ratingFrequencies?.nineteen,
                        it.get(x).attributes?.ratingFrequencies?.two,
                        it.get(x).attributes?.ratingFrequencies?.twenty,
                        it.get(x).attributes?.ratingFrequencies?.three,
                        it.get(x).attributes?.ratingFrequencies?.four,
                        it.get(x).attributes?.ratingFrequencies?.five,
                        it.get(x).attributes?.ratingFrequencies?.six,
                        it.get(x).attributes?.ratingFrequencies?.seven,
                        it.get(x).attributes?.ratingFrequencies?.eight,
                        it.get(x).attributes?.ratingFrequencies?.nine),
                    it.get(x).attributes?.ratingRank,
                    it.get(x).attributes?.showType,
                    it.get(x).attributes?.slug,
                    it.get(x).attributes?.startDate,
                    it.get(x).attributes?.status,
                    it.get(x).attributes?.subtype,
                    it.get(x).attributes?.synopsis,
                    it.get(x).attributes?.tba,
                    Titles(
                        it.get(x).attributes?.titles?.en,
                        it.get(x).attributes?.titles?.en_jp,
                        it.get(x).attributes?.titles?.en_us,
                        it.get(x).attributes?.titles?.ja_jp
                    ),
                    it.get(x).attributes?.totalLength,
                    it.get(x).attributes?.updatedAt,
                    it.get(x).attributes?.userCount,
                    it.get(x).attributes?.youtubeVideoId),
                it.get(x).id,
                LinksData(it.get(x).links?.self),
                Relationships(
                    Characters(
                        Links(
                            it.get(x).relationships?.characters?.links?.self,
                            it.get(x).relationships?.characters?.links?.related)),
                    Productions(
                        Links(
                            it.get(x).relationships?.productions?.links?.self,
                            it.get(x).relationships?.productions?.links?.related)),
                    Staff(
                        Links(
                            it.get(x).relationships?.staff?.links?.self,
                            it.get(x).relationships?.staff?.links?.related
                        )
                    ),
                    null, null, null, null, null, null, null, null, null, null, null, null, null
                ),
                it.get(x).type,
                KitsuError(getErrorList(it.get(x).errors))))
        }

    }

    return dataList
}

private fun getErrorList(errorsList: com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.KitsuError?):List<Error>{
    val errorsListUseCase = mutableListOf<Error>()

    errorsList?.errors?.let {
        for (x in 0 until it.size){
            errorsListUseCase.add(Error(it.get(x).status,it.get(x).detail, it.get(x).code, it.get(x).title))
        }
    }
    return errorsListUseCase
}