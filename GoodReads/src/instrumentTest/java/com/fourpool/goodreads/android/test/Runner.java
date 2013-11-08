package com.fourpool.goodreads.android.test;

import android.os.Bundle;
import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;

import com.fourpool.goodreads.android.login.LogInControllerTest;

import junit.framework.TestSuite;

public class Runner extends InstrumentationTestRunner {
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        // Fix for mockito/dexmaker: https://code.google.com/p/dexmaker/issues/detail?id=2
        System.setProperty("dexmaker.dexcache", getTargetContext().getCacheDir().toString());
    }

    @Override
    public TestSuite getAllTests(){
        InstrumentationTestSuite suite = new InstrumentationTestSuite(this);
        suite.addTestSuite(LogInControllerTest.class);
        return suite;
    }

    @Override
    public ClassLoader getLoader() {
        return Runner.class.getClassLoader();
    }
}
