package com.saitejajanjirala.compose_note.data.repository

import com.saitejajanjirala.compose_note.data.db.ImageDao
import com.saitejajanjirala.compose_note.domain.models.ImageModel
import com.saitejajanjirala.compose_note.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(val imageDao: ImageDao) : ImageRepository {
    override suspend fun insert(imageModel: ImageModel) {
        imageDao.insert(imageModel)
    }

    override suspend fun delete(imageModel: ImageModel) {
        imageDao.delete(imageModel)
    }

    override fun getAllImagesByNoteId(noteId: Int): Flow<List<ImageModel>> {
        return imageDao.getAllImagesByNoteId(noteId)
    }
}