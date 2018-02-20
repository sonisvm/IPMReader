package com.somathew.maven.ipmreader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOMsgFieldPackager;
import org.jpos.iso.packager.GenericPackager;

public class WriteApp {

	public static void main(String[] args) throws ISOException, IOException {
		GenericPackager packager = new GenericPackager("ISO8583_format.xml");
		
		ISOMsg msg = new ISOMsg();
		
		System.out.println(((ISOMsgFieldPackager)packager.getFieldPackager(48)).getISOMsgPackager());
		System.out.println(((ISOMsgFieldPackager)packager.getFieldPackager(48)).getISOFieldPackager());
		System.out.println(packager.getFieldPackager(48));
		
		msg.setPackager(packager);
		msg.setMTI("1442");
		msg.set("3.1", "22");
		msg.set("3.2", "22");
		msg.set("3.3", "22");
		msg.set("23", "123");
		msg.set("30.1", "10000");
		msg.set("30.2", "10000");
		msg.set("93", "abdeflf");
		
		ISOMsg subfieldsContainer = new ISOMsg(48);
		ISOField tlvField = new ISOField(105);
		tlvField.setValue("12345");
		subfieldsContainer.set(tlvField);
		
		msg.set(subfieldsContainer);
		
		
		
		FileOutputStream out = new FileOutputStream("./test.ipm");
		
		out.write(msg.pack());
		
		out.close();
	}

}
