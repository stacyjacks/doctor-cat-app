package kurmakaeva.anastasia.doctorcat.service

import kurmakaeva.anastasia.doctorcat.retrofit
import retrofit2.http.GET

interface CatFactsApiService {
    @GET("/facts/random?animal_type=cat&amount=1")
    suspend fun getCatFact(): CatFactsApiResponse

    companion object {
        val instance: CatFactsApiService by lazy {
            retrofit.create(CatFactsApiService::class.java)
        }
    }
}