package com.example.AppManBoard

import com.example.AppManBoard.login.viewmodel.LoginViewMedel
import com.example.AppManBoard.main.ViewModelLogout
import com.example.AppManBoard.main.home.form.viewmodel.AddData
import com.example.AppManBoard.main.home.viewmodel.SearchModel
import com.example.AppManBoard.main.owner.edit.viewmodel.DeletedData
import com.example.AppManBoard.main.owner.edit.viewmodel.EditData
import com.example.AppManBoard.main.owner.viewmodel.SearchModelPageOwner
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {

    private val loginViewMedel: LoginViewMedel by lazy { LoginViewMedel() }
    private val addData: AddData by lazy { AddData() }
    private val editData: EditData by lazy { EditData() }
    private val deletedData: DeletedData by lazy { DeletedData() }
    private val searchDataPageHome : SearchModel by lazy { SearchModel() }
    private val searchDataPageOwner : SearchModelPageOwner by lazy { SearchModelPageOwner() }
    private val logoutViewModel : ViewModelLogout by lazy { ViewModelLogout() }

    @Test
    fun checkLoginFail(){
        assertFalse(loginViewMedel.checkLogin("tanate.chu@gmail.com","1234"))
    }
    @Test
    fun checkLoginTrue(){
        assertFalse(loginViewMedel.checkLogin("tanate.chu@appman.com","1234"))
    }

    @Test
    fun checkAddDataTrue(){
        assertFalse(addData.checkData("hi" ,"tanate chumjai","tanate.chu@appman.com","tanate.chu@appman.co.th 155559562861","https://lh3.googleusercontent.com/a-/AAuE7mA1JtiOfRPbEpfUbGxh3GYmL33Bvd3N38VfkLqctA","hi","18-เม.ย.-2019-10:52:42"))
    }
    @Test
    fun checkAddDataFalse(){
        assertFalse(addData.checkData("hi world ja","tanate chumjai","tanate.chu2@appman.com","tanate.chu@appman.co.th 2155559562861","https://lh3.googleusercontent.com/a-/AAuE7mA1JtiOfRPbEpfUbGxh3GYmL33Bvd3N38VfkLqct2A","hey","13-เม.ย.-2019-10:52:42"))
    }
    @Test
    fun checkEditDataTrue(){
        assertFalse(editData.checkData("hi" ,"tanate chumjai","tanate.chu@appman.com","tanate.chu@appman.co.th 155559562861","https://lh3.googleusercontent.com/a-/AAuE7mA1JtiOfRPbEpfUbGxh3GYmL33Bvd3N38VfkLqctA","hi","18-เม.ย.-2019-10:52:42"))
    }
    @Test
    fun checkEditDataFalse(){
        assertFalse(editData.checkData("null","tanate chumjai","tanate.chu2@appman.com","tanate.chu@appman.co.th 2155559562861","https://lh3.googleusercontent.com/a-/AAuE7mA1JtiOfRPbEpfUbGxh3GYmL33Bvd3N38VfkLqct2A","null","13-เม.ย.-2019-10:52:42"))
    }
    @Test
    fun checkDeletedDataTrue(){
        assertTrue(deletedData.checkData("tanate.chu@appman.co.th 155559562861"))
    }
    @Test
    fun checkDeletedDataFalse(){
        assertFalse(deletedData.checkData("tanate.chu@appman.co.th adlkwkljadadjkl 155559562861"))
    }
    @Test
    fun checkSearchDataHomeTrue(){
        assertTrue(searchDataPageHome.checkData("hi"))
    }
    @Test
    fun checkSearchDataHomeFalse(){
        assertFalse(searchDataPageHome.checkData("axcghjkl;"))
    }
    @Test
    fun checkSearchDataOwnerTrue(){
        assertTrue(searchDataPageOwner.checkData("hi nage"))
    }
    @Test
    fun checkSearchDataOwnerFalse(){
        assertFalse(searchDataPageOwner.checkData("axcghjkl;"))
    }
    @Test
    fun checkLogoutTrue(){
        assertTrue(logoutViewModel.checkEmail("tanate.chu@appman.co.th"))
    }
    @Test
    fun checkLogoutFalse(){
        assertFalse(logoutViewModel.checkEmail("null"))
    }

}
