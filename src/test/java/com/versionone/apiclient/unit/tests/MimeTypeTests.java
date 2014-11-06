package com.versionone.apiclient.unit.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.MimeType;

public class MimeTypeTests {
	
    private static void testExtension(String extension, String expectedMimeType) {
        TestFilename("blah." + extension, expectedMimeType);
    }

    private static void TestFilename(String filename, String expectedMimeType) {
        Assert.assertEquals(expectedMimeType, MimeType.resolve(filename));
    }

    @Test
    public void TxtIsTextPlain() {
        testExtension("txt", "text/plain");
    }

    @Test
    public void RtfIsTextRtf() {
        testExtension("rtf", "plain/rtf");
    }

    @Test
    public void CaseDoesNotMatter() {
        testExtension("TXT", "text/plain");
    }

    @Test
    public void PathDoesNotMatter() {
        TestFilename("e:/dog/cat/mouse.txt", "text/plain");
    }

    @Test
    public void MSOfficeFileTypes() {
        testExtension("doc", "application/msword");
        testExtension("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        testExtension("dot", "application/msword");
        testExtension("dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");

        testExtension("xls", "application/vnd.ms-excel");
        testExtension("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        testExtension("ppt", "application/vnd.ms-powerpoint");
        testExtension("pps", "application/vnd.ms-powerpoint");
        testExtension("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");

        testExtension("pub", "application/vnd.ms-publisher");
    }

    @Test
    public void DefaultIsOctetStream() {
        testExtension("YouWillNeverFindAFileWithThisExtensionEverInYourEntireLifeOK", "application/octet-stream");
    }
}
