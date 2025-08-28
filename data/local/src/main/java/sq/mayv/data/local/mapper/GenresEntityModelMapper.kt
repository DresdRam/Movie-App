package sq.mayv.data.local.mapper

import sq.mayv.data.local.entity.GenreEntity
import sq.mayv.data.model.network.Genre

object GenresEntityModelMapper {

    fun mapToModel(list: List<GenreEntity>): List<Genre> {
        return list.map { mapToModel(single = it) }
    }

    fun mapToModel(single: GenreEntity): Genre {
        return Genre(
            id = single.id,
            name = single.name
        )
    }

    fun mapToEntity(list: List<Genre>): List<GenreEntity> {
        return list.map { mapToEntity(single = it) }
    }

    fun mapToEntity(single: Genre): GenreEntity {
        return GenreEntity(
            id = single.id,
            name = single.name
        )
    }

}