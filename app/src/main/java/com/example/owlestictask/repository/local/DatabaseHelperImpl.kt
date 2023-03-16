package  com.example.owlestictask.repository.local

import com.example.owlestictask.model.User
import com.example.owlestictask.model.UserDetail


class DatabaseHelper(private val appDatabase: AppDatabase) {
    private val userDao = appDatabase.dbDao()
    fun insert(favUser: UserDetail) = userDao.insert(favUser)

    fun checkExist(userId: Int) = userDao.isRowIsExist(userId)

    fun delete(userId:Int) = userDao.delete(userId)
}