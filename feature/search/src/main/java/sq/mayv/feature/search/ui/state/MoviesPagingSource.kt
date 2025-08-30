package sq.mayv.feature.search.ui.state

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.first
import sq.mayv.core.common.GenericState
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.repository.MoviesRepository

class MoviesPagingSource(
    private val repository: MoviesRepository,
    private val query: String,
    private val languageCode: String
) : PagingSource<Int, MovieDetails>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDetails> {
        return try {
            val page = params.key ?: 1
            val result = repository.search(query = query, pageIndex = page, languageCode).first()

            when (result) {
                is GenericState.Success -> {
                    LoadResult.Page(
                        data = result.data,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (result.data.isEmpty()) null else page + 1
                    )
                }

                is GenericState.Failure -> {
                    LoadResult.Error(Throwable("Error code: ${result.errorCode}"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDetails>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
