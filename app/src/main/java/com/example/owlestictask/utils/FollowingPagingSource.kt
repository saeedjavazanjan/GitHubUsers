package com.example.owlestictask.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.owlestictask.model.Following
import com.example.owlestictask.repository.remote.ApiService
import retrofit2.HttpException
import java.io.IOException

class FollowingPagingSource (
    val api: ApiService,
    val query: String
) : PagingSource<Int, Following>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Following> {
        val position=params.key ?:1

        return try {
            val response=api.getUserFollowing(
                query,
                params.loadSize,
                position
            )
            val following=response
            LoadResult.Page(
                data = following!!,
                prevKey = if (position==1)null else position-1,
                nextKey =  if (response.isEmpty())null else position+1
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

    override fun getRefreshKey(state: PagingState<Int, Following>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}