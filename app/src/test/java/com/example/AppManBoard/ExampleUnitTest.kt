package com.example.AppManBoard

import com.example.AppManBoard.login.viewmodel.LoginViewMedel
import com.example.AppManBoard.main.MainActivityViewModel
import com.example.AppManBoard.main.home.form.viewmodel.FormActivityViewModel
import com.example.AppManBoard.main.home.viewmodel.PageMainViewModel
import com.example.AppManBoard.main.owner.edit.viewmodel.EditActivityViewModelDeleted
import com.example.AppManBoard.main.owner.edit.viewmodel.EditActivityViewModelEdit
import com.example.AppManBoard.main.owner.viewmodel.PageOwnerViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {

    private val loginViewMedel: LoginViewMedel by lazy { LoginViewMedel() }
    private val formActivityViewModel: FormActivityViewModel by lazy { FormActivityViewModel() }
    private val editActivityViewModelEdit: EditActivityViewModelEdit by lazy { EditActivityViewModelEdit() }
    private val editActivityViewModelDeleted: EditActivityViewModelDeleted by lazy { EditActivityViewModelDeleted() }
    private val pageMainViewDataPageHome : PageMainViewModel by lazy { PageMainViewModel() }
    private val searchDataPageOwnerViewModel : PageOwnerViewModel by lazy { PageOwnerViewModel() }
    private val logoutMainActivityViewModel : MainActivityViewModel by lazy { MainActivityViewModel() }

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
        assertFalse(formActivityViewModel.checkData("hi" ,"tanate chumjai","tanate.chu@appman.com","tanate.chu@appman.co.th 155559562861","https://lh3.googleusercontent.com/a-/AAuE7mA1JtiOfRPbEpfUbGxh3GYmL33Bvd3N38VfkLqctA","hi","18-เม.ย.-2019-10:52:42"))
    }
    @Test
    fun checkAddDataFalse(){
        assertFalse(formActivityViewModel.checkData("hi world ja","tanate chumjai","tanate.chu2@appman.com","tanate.chu@appman.co.th 2155559562861","https://lh3.googleusercontent.com/a-/AAuE7mA1JtiOfRPbEpfUbGxh3GYmL33Bvd3N38VfkLqct2A","hey","13-เม.ย.-2019-10:52:42"))
    }
    @Test
    fun checkEditDataTrue(){
        assertFalse(editActivityViewModelEdit.checkData("hi" ,"tanate chumjai","tanate.chu@appman.com","tanate.chu@appman.co.th 155559562861","https://lh3.googleusercontent.com/a-/AAuE7mA1JtiOfRPbEpfUbGxh3GYmL33Bvd3N38VfkLqctA","hi","18-เม.ย.-2019-10:52:42"))
    }
    @Test
    fun checkEditDataFalse(){
        assertFalse(editActivityViewModelEdit.checkData("null","tanate chumjai","tanate.chu2@appman.com","tanate.chu@appman.co.th 2155559562861","https://lh3.googleusercontent.com/a-/AAuE7mA1JtiOfRPbEpfUbGxh3GYmL33Bvd3N38VfkLqct2A","null","13-เม.ย.-2019-10:52:42"))
    }
    @Test
    fun checkDeletedDataTrue(){
        assertTrue(editActivityViewModelDeleted.checkData("tanate.chu@appman.co.th 155559562861"))
    }
    @Test
    fun checkDeletedDataFalse(){
        assertFalse(editActivityViewModelDeleted.checkData("tanate.chu@appman.co.th adlkwkljadadjkl 155559562861"))
    }
    @Test
    fun checkSearchDataHomeTrue(){
        assertTrue(pageMainViewDataPageHome.checkData("hi"))
    }
    @Test
    fun checkSearchDataHomeFalse(){
        assertFalse(pageMainViewDataPageHome.checkData("axcghjkl;"))
    }
    @Test
    fun checkSearchDataOwnerTrue(){
        assertTrue(searchDataPageOwnerViewModel.checkData("hi nage"))
    }
    @Test
    fun checkSearchDataOwnerFalse(){
        assertFalse(searchDataPageOwnerViewModel.checkData("axcghjkl;"))
    }
    @Test
    fun checkLogoutTrue(){
        assertTrue(logoutMainActivityViewModel.checkEmail("tanate.chu@appman.co.th"))
    }
    @Test
    fun checkLogoutFalse(){
        assertFalse(logoutMainActivityViewModel.checkEmail("null"))
    }

}
