package com.fourpool.goodreads.android.login;

import android.content.Context;
import android.test.AndroidTestCase;

import com.fourpool.goodreads.android.model.SessionStore;
import com.squareup.otto.Bus;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class LogInControllerTest extends AndroidTestCase {
    @Mock private LogInDisplay displayMock;
    @Mock private Context contextMock;
    @Mock private SessionStore sessionStoreMock;
    @Mock private Bus busMock;
    @InjectMocks private LogInController controller;

    @Override public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }

    public void testSomething() {
        doNothing().when(displayMock).inProgress();
        controller.onStart(displayMock);
        controller.dummyMethod();
        verify(displayMock).inProgress();
    }
}
