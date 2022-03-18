package com.del.testandroiddeveloper.data.local.room

import androidx.room.*
import com.del.testandroiddeveloper.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movieEntities WHERE isComingSoon = 0 ORDER BY popularity DESC")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieEntities WHERE isComingSoon = 0 AND title LIKE '%' || :search || '%'")
    fun getSearchMovies(search: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieEntities WHERE isComingSoon = 1")
    fun getComingSoonMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieEntities WHERE favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieEntities WHERE favorite = 1 AND title LIKE '%' || :search || '%'")
    fun getSearchFavoriteMovies(search: String): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Update
    suspend fun updateFavoriteMovie(movie: MovieEntity)
}