/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package cc.suitalk.arbitrarygen.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 *
 * @author AlbieLiang
 */
public class XmlUtils {

    private static final String TAG = "AG.XmlUtils";

    /**
     * The method will parse the XML InputStream and return a notes list.
     * At the end, it will close the InputStream.
     * If you do not want it close the stream, you can invoke
     * {@link #getXmlNotes(InputStream, SaxXmlParserHandler, boolean)} instead.
     *
     * @param ins the input stream
     * @param handler the {@link SaxXmlParserHandler} for XML parse
     * @return XML notes information list
     */
    public static List<RawTemplate> getXmlNotes(InputStream ins, SaxXmlParserHandler handler) {
        return getXmlNotes(ins, handler, true);
    }

    /**
     * The method will parse the XML InputStream and return a notes list.
     *
     * @param ins the input stream
     * @param handler the {@link SaxXmlParserHandler} for XML parse
     * @param close   true : close the InputStream at the end of the method, false : otherwise
     * @return XML notes information list
     *
     * @see #getXmlNotes(InputStream, SaxXmlParserHandler)
     */
    public static List<RawTemplate> getXmlNotes(InputStream ins, SaxXmlParserHandler handler, boolean close) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            parser.parse(ins, handler);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
        } catch (ParserConfigurationException e) {
            Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
        } catch (SAXException e) {
            Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
        } finally {
            if (ins != null && close) {
                try {
                    ins.close();
                } catch (IOException e) {
                    Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
                }
            }
        }
        return handler.getXmlNotes();
    }

    /**
     * The method will parse the XML InputStream and return a notes list. At the end, it will close the InputStream.
     *
     * @param file the input {@link File}
     * @param handler the {@link SaxXmlParserHandler} for XML parse
     * @return XML notes information list
     *
     * @see #getXmlNotes(InputStream, SaxXmlParserHandler)
     */
    public static List<RawTemplate> getXmlNotes(File file, SaxXmlParserHandler handler) {
        InputStream ins = null;
        try {
            ins = new FileInputStream(file);
            return getXmlNotes(ins, handler);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
                }
            }
        }
        return null;
    }

    /**
     * {@link #getXmlNotes(File, SaxXmlParserHandler)}
     *
     * @param file the input {@link File}
     * @return XML notes information list
     */
    public static List<RawTemplate> getXmlNotes(File file) {
        return getXmlNotes(file, new SaxXmlParserHandler());
    }
}
