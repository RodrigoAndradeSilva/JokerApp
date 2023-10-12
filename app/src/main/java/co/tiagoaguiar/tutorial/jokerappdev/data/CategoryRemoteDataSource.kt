package co.tiagoaguiar.tutorial.jokerappdev.data

import android.os.Handler
import android.os.Looper
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class CategoryRemoteDataSource {

    fun findAllCategories(callback: ListCategoryCallback) {
        HTTPClient.retrofit() // aqui tem a instancia retrofit
            .create(ChuckNorrisAPI::class.java)
            .findAllCategories()
            .enqueue(object : Callback<List<String>> {
                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    if (response.isSuccessful) {
                        val categories = response.body()
                        callback.onSuccess(categories ?: emptyList())
                    } else {
                        // quando o servidor devolve status de error < 500
                        val error = response.errorBody()?.string()
                        callback.onError(error ?: "Erro desconhecido")
                    }
                    callback.onComplete()
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    callback.onError(t.message ?: "Erro interno")
                    callback.onComplete()
                }
            })
    }
}
