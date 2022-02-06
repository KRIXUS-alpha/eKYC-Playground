package com.example.ekyc_playground;
import androidx.annotation.NonNull;
import androidx.core.util.PatternsCompat;
import junit.framework.TestCase;

public class RegisterTest extends TestCase {

    public void testEmail()
    {
        boolean actual =true;
        boolean flag = isEmailValid("name@gmail.com"); //testcase1 - Returns true
        //boolean flag = isEmailValid("name.com"); //testcase2 - Returns False
        assertEquals(actual,flag);

    }

    public boolean isEmailValid(@NonNull String email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }


}
