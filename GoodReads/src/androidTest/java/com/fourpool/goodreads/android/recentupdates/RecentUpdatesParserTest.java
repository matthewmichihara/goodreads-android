package com.fourpool.goodreads.android.recentupdates;

import android.test.AndroidTestCase;

import com.fourpool.goodreads.android.model.Update;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class RecentUpdatesParserTest extends AndroidTestCase {
    @InjectMocks private RecentUpdatesParser parser;

    @Override public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }

    public void testParseReturnsExpectedNumberOfUpdates() throws IOException, SAXException, ParserConfigurationException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("sample_updates.xml");
        List<Update> updates = parser.parse(in);
        assertEquals(5, updates.size());
    }
}
