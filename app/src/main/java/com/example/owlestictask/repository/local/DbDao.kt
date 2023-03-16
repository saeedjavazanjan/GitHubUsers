package com.example.owlestictask.repository.local

import androidx.room.*
import com.example.owlestictask.model.User
import com.example.owlestictask.model.UserDetail

@Dao
interface DbDao {

    @Query("SELECT * FROM UserDetail")
   fun getAll(): List<UserDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insert( user: UserDetail)

    @Query("DELETE FROM UserDetail WHERE userId = :userId")
    fun delete(userId:Int)

    @Query("SELECT * FROM UserDetail WHERE userId=:userId")
    fun isRowIsExist(userId: Int ) : Boolean



}