package sq.mayv.feature.home.mapper

import sq.mayv.data.model.network.Genre
import sq.mayv.feature.home.ui.state.GenreUI

object GenresModelUIMapper {

    fun mapToUI(list: List<Genre>): List<GenreUI> {
        return list.map { mapToUI(single = it) }
    }

    fun mapToUI(single: Genre): GenreUI {
        return GenreUI(
            id = single.id,
            name = single.name
        )
    }

    fun mapToModel(list: List<GenreUI>): List<Genre> {
        return list.map { mapToModel(single = it) }
    }

    fun mapToModel(single: GenreUI): Genre {
        return Genre(
            id = single.id,
            name = single.name
        )
    }

}