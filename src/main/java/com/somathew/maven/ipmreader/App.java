package com.somathew.maven.ipmreader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

enum PDS{
	p0001("Mastercard Mapping service account number", 3, 21),
	p0002("GCMS Product Idenitifier", 3,3),
	p0003("Licensed product identifier", 3,3),
	p0005("Message Error indicator", 3, 140),
	p0017("Transaction integrity class",3, 2),
	p0023("Terminal Type",3, 21),
	p0025("Message Reversal Indicator",3 , 7),
	p0043("Program registration ID", 3,3),
	p0044("Program participation indicator",3, 20),
	p0045("Mastercard generated installment identifier",3,1),
	p0046("Japan Common Merchant Code",3, 4),
	p0056("Mastercard electronic card indicator", 3, 1),
	p0052("Electronic Commerce Security Level Indicator", 3, 3),
	p0057("Transaction category indicator",3, 2),
	p0058("Token Assurance Level", 3, 2),
	p0059("Token requestor ID", 3, 11),
	p0071("Chip to Magnetic Stripe Conversion Service Indicator",3, 4),
	p0072("Authentication indicator", 3, 1),
	p0080("Tax amount", 3, 60),
	p0105("File ID",3,25),
	p0122("Processing mode", 3, 1),
	p0140("CardHolder Billing Amounts USD Amount", 3, 24),
	p0141("Customer currency conversion revenue data", 3, 25),
	p0145("Alternate transaction fee amount", 3, 15),
	p0146("Transaction fee amounts", 3, 432),
	p0147("Extended precision amounts", 3, 576),
	p0148("Currency exponents", 3, 60),
	p0149("Original Amounts Currency code", 3, 6),
	p0157("Alternate processor indicator", 3, 4),
	p0158("Business activity", 3, 31),
	p0159("Settlement data", 3, 67),
	p0160("Multiple settlement data", 3, 108),
	p0165("Settlement indicator", 3, 30),
	p0170("Card acceptor inquiry information", 3, 57),
	p0171("Alternate Card acceptor Description Data", 3, 201),
	p0172("Sole Proprietor name", 3, 30),
	p0173("Legal corporate name", 3, 30),
	p0174("Dun and Bradstreet number", 3, 15),
	p0175("Card acceptor URL", 3, 255),
	p0176("Mastercard assigned ID", 3, 6),
	p0177("Cross-border", 3, 2),
	p0178("Alternate card acceptor description data 2", 3, 201),
	p0179("Long running Transaction Indicator", 3, 2),
	p0180("Domestic card acceptor tax ID", 3, 20),
	p0181("Installment payment data", 3, 50),
	p0182("Installment Payment Cardholder Statement Data", 3, 87),
	p0183("Brazil Post-Dated Transaction Data", 3, 39),
	p0188("Proprietary Service Data", 3, 99),
	p0189("POI data", 3, 41),
	p0190("Partner ID Code", 3, 6),
	p0191("Originating Message Format", 3, 1),
	p0192("Payment Transaction Initiator", 3,3),
	p0194("Remote Payments Program Data", 3, 1),
	p0195("Installment Data", 3, 27),
	p0196("Mobile Phone Reload Data", 3, 47),
	p0197("National Use Tax Data", 3, 53),
	p0198("Device Type", 3, 2),
	p0199("Funding transaction information", 3, 199),
	p0200("Fraud notification date", 3, 8),
	p0202("PAN Syntax Error", 3, 19),
	p0204("Amount Syntax Error", 3, 12),
	p0205("Message error indicator syntax return", 3, 14),
	p0206("Late Presentment indicator", 3, 4),
	p0207("Wallet Identifier", 3, 3),
	p0208("Additional Merchant Data", 3, 26),
	p0209("Independent Sales Organization ID",3, 11),
	p0210("Transit Program", 3, 4),
	p0211("Terminal Compliant Indicator", 3, 2),
	p0212("Merchant Data Services", 3, 5),
	p0213("Original Merchant Data", 3, 115),
	p0214("Merchant Data Cleansing Plus", 3, 354),
	p0215("Mastercard Merchant Data Advance", 3, 106),
	p0220("Brazil Merchant Tax ID", 3, 14),
	p0225("Original Message Reason Code Syntax Return", 3, 4),
	p0261("Risk Management Approval Code", 3, 11),
	p0301("Checksum file amount", 3, 16),
	p0306("File Message Counts", 3, 8),
	p0375("Member reconciliation Indicator 1", 3, 50),
	p0446("Transaction fee amount, syntax error", 3, 36),
	p0502("Custom identifier", 3, 82),
	p0520("Travel Date", 3, 6),
	p0596("Card acceptor Tax ID", 3, 21),
	p0597("Total Tax amount", 3, 14),
	p0623("Motor Fuel Information", 3, 35),
	p0629("Odometer reading", 3, 7),
	p0799("Test Case Traceability identifiers", 3, 165)
	;
	
	
	private int lenOfLengthField;
	private int maxLength;
	private String name;
	
	PDS(String name, int lenOfLengthField, int maxLength){
		this.lenOfLengthField = lenOfLengthField;
		this.maxLength = maxLength;
		this.name =  name;
	}

	public int getLenOfLengthField() {
		return lenOfLengthField;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public String getName() {
		return name;
	}
	
	
	
	
}

public class App {

	public static void main(String[] args) throws IOException, ISOException {
		//RandomAccessFile file = new RandomAccessFile("C:\\Users\\somathew\\Documents\\Sprint work\\Sprint 124\\FirstPresentment-AllElements-1Txn-1LogicalFile.ipm", "r");
		RandomAccessFile file = new RandomAccessFile("test.ipm", "r");
		GenericPackager packager = new GenericPackager("ISO8583_format.xml");
		ISOMsg msg;
		
		int fileSize = (int)file.length();
		
		int numOfReadBytes = 0;
		
		byte[] byteArray = new byte[fileSize];
		
		while(numOfReadBytes < fileSize) {
			
			System.out.println("File size:"+fileSize + ", read:"+ numOfReadBytes);
			msg = new ISOMsg();
			
			msg.setPackager(packager);
			
			file.seek(numOfReadBytes);
			
			file.read(byteArray);
			
//			for(int i=0; i < byteArray.length; i++) {
//				System.out.print((char)byteArray[i]);
//			}
				
			//byteArray = "0200B2200000001000000000000000800000201234000000010000011072218012345606A5DFGR021ABCDEFGHIJ 1234567890".getBytes();
			
			msg.unpack(byteArray);
			
			
			logISOMsg(msg);
			
			numOfReadBytes += msg.pack().length;
			
			
		}

	}
	private static void logISOMsg(ISOMsg msg) {
				
		System.out.println("----ISO MESSAGE-----");
		
		try {
			System.out.println("MTI : " + msg.getMTI());
			System.out.println("BitMap value: " + msg.getComponent(-1).getValue());
			for (int i=1;i<=msg.getMaxField();i++) {
				if (msg.hasField(i)) {
					//if there are children
					if(msg.getComponent(i).getChildren().size()>0) {
						System.out.println(msg.getPackager().getFieldDescription(msg, i));
						Map<Integer, ISOField> children = msg.getComponent(i).getChildren();
						for(Integer j : children.keySet()) {
							System.out.println("\t Subfield "+children.get(j).getKey()+" : "+ children.get(j).getValue());
						}
					}
					else {
						System.out.println(msg.getPackager().getFieldDescription(msg, i)+" : "+msg.getString(i));
						
						if(i==48 || i==62 || i==123 || i==124 || i==125) {
							readAdditionalData(msg.getString(i));
							
						}
					}
					
				}
			}
		} catch (ISOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("--------------------");
		}
 
	}
	
	private static void readAdditionalData(String data) {
		System.out.println("---ADDITIONAL DATA--");
		
		int read=0;
		
		int len = data.length();
		
		PDS pds;
		
		while(read < len) {

			String tagID = "p"+data.substring(read, read+4);
			
			read = read+4;

			pds = PDS.valueOf(tagID);
			
			if(pds == null) {
				System.out.println("Not found "+ tagID);
				break;
			}
			
			
			String tagLength = data.substring(read, read+pds.getLenOfLengthField());
			
			read = read + pds.getLenOfLengthField();

			int toRead=0;
			
			if(Integer.parseInt(tagLength) < pds.getMaxLength()) {
				toRead = Integer.parseInt(tagLength);
			} else {
				toRead = pds.getMaxLength();
			}
			
			String tagValue = data.substring(read, read + toRead );
			
			read = read + toRead;
			
			
			System.out.println(pds.getName()+ " : "+ tagValue);
			
		}
		
	}

}
