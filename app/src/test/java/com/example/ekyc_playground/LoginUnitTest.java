package com.example.ekyc_playground;


import androidx.annotation.NonNull;
import androidx.core.util.PatternsCompat;


public class LoginUnitTest extends Testcase{
    public void testEmail(){
        boolean actual = true;
        boolean flag = isEmailValid("name@gmail.com");
        assertEquals(actual,flag);
    }


    public boolean isEmailValid(@NonNull String email){
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }
}
