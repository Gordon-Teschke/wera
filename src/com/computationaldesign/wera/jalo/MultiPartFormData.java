package com.computationaldesign.wera.jalo;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



public class MultiPartFormData {

	private HashMap parameters = new HashMap();
	private byte[] fileBytes, bytes;
	private final int mbLimit = 10;
	private final int FILE_SIZE_LIMIT = 1024*1024*mbLimit;
	private String data;


	public MultiPartFormData(HttpServletRequest request) throws IOException {
		
		int contentLength = request.getContentLength();
	    System.out.println("MultiPartFormData.contentLength=" + contentLength);
		
		if(contentLength > FILE_SIZE_LIMIT)
			throw new IOException("File has exceeded size limit.");
		
		ServletInputStream in = request.getInputStream();
		
		bytes = new byte[contentLength];
		byte[] tempByte = new byte[1];
		int paramCount = 0;
		int paramLineCount = 0;	
		int byteCount = 0;
		
		while(in.read(tempByte) > -1) {
			
			bytes[byteCount] = tempByte[0];
			byteCount++;
		}

		String data = new String(bytes, "ISO-8859-1");
		this.data = data;
		String boundary = data.substring(0,data.indexOf('\n'));
		String[] elements = data.split(boundary);

		for(int i = 0; i < elements.length; i++) {

			if(elements[i].length() > 0) {

				String[] descval = elements[i].split("\n");
				
				// take the first line of this element and split it by ";"
				String[] disp = descval[1].split(";");

				// if there's a filename, it's a file				
				System.out.println("descval[1]=" + descval[1]);
				System.out.println("descval[2]=" + descval[2]);
				if(disp.length > 2) {

						String longFileName = disp[2].substring(
							disp[2].indexOf('"')+1,disp[2].length()-2).trim();
						parameters.put("longFileName",longFileName);
						parameters.put("fileName",longFileName.substring(
							longFileName.lastIndexOf("\\")+1,
							longFileName.length()));
						parameters.put("contentType",descval[2].substring(
							descval[2].indexOf(' ')+1,
							descval[2].length()-1));
	
						int pos = 0;
						int lineCount = 0;
						//paramLineCount--;
						while(lineCount != paramLineCount+3) {
						//while(lineCount <  disp.length ) {
	
							System.out.print((char)bytes[pos]);
							if((char)bytes[pos] == '\n') lineCount++;
							pos++;
						}
						//paramLineCount += lineCount;
						System.out.println("disp.length=" + disp.length);
						System.out.println("paramLineCount=" + paramLineCount);
						System.out.println("LineCount=" + lineCount);
						System.out.println("1.pos=" + pos);
	
						fileBytes = new byte[bytes.length - boundary.length() - 4 - pos];
						int fileByteCount = 0;
						
						for(int k = pos; k < (bytes.length - boundary.length() - 4); k++) {
							
							fileBytes[fileByteCount] = bytes[k];
							fileByteCount++;
						}	
						//System.out.println("pos="+pos);
						//System.out.println("boundary.length()="+boundary.length());
						//System.out.println("fileByteCount="+fileByteCount);
						//System.out.println("contentLength="+fileBytes.length);
						//System.out.println("paramLineCount="+paramLineCount);
	
				} else {
					
					paramCount++;
					paramLineCount += 4;
					
					// loop for multi-line params
					String value = "";
					for(int p = 3; p < descval.length; p++) {
						
						if(p != 3) value += "\n";
						value += descval[p].trim();
						paramLineCount++;
					}
					
					parameters.put(
						descval[1].substring(
							descval[1].indexOf('"')+1,
							descval[1].length()-2).trim(),
						value
					);
				}
			}
		}
		
		bytes = null;
		System.gc();
	}

	public byte[] getFile() { return fileBytes; }
	public HashMap getParameters() { return parameters; }
}
