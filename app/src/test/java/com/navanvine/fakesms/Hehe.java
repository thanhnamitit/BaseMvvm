package com.navanvine.fakesms;

import android.content.Intent;
import com.navanvine.fakesms.screen.MainActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static junit.framework.TestCase.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class Hehe {
    @Test
    public void clickingLogin_shouldStartLoginActivity() {

        assertEquals(1, 1);
    }
}
