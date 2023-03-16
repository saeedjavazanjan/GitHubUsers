package com.example.owlestictask.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.owlestictask.model.User
import com.example.owlestictask.repository.remote.ApiService
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    val api: ApiService,
    val query: String
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position=params.key ?:1

        return try {
            val response=api.getUserList(
                query,
               params.loadSize,
                 position
                )
            val users=response?.items
            LoadResult.Page(
                data = users!!,
                prevKey = if (position==1)null else position-1,
               nextKey =  if (response?.items?.isEmpty() == true)null else position+1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
             LoadResult.Error(e)
        } catch (e:java.lang.NullPointerException){
            LoadResult.Error(e)

        }



    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}