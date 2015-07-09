/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Adam
 */
public class StandaloneWriter extends XMLWriter {

    public StandaloneWriter(OutputStream out) throws UnsupportedEncodingException {
        super(out);
    }

    @Override
    protected void writeDeclaration() throws IOException {
        OutputFormat format = getOutputFormat();

        String encoding = format.getEncoding();

        if (!format.isSuppressDeclaration()) {
            if (encoding.equals("UTF8")) {
                writer.write("<?xml version=\"1.0\"");

                if (!format.isOmitEncoding()) {
                    writer.write(" encoding=\"UTF-8\"");
                }

                writer.write(" standalone=\"yes\"");
                writer.write("?>");
            } else {
                writer.write("<?xml version=\"1.0\"");

                if (!format.isOmitEncoding()) {
                    writer.write(" encoding=\"" + encoding + "\"");
                }

                writer.write(" standalone=\"yes\"");
                writer.write("?>");
            }

            if (format.isNewLineAfterDeclaration()) {
                println();
            }
        }
    }
}
