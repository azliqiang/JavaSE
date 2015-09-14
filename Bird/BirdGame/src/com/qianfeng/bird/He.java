package com.qianfeng.bird;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.omg.IOP.IORHelper;

public class He {

	/**
	 * @param args
	 */
	
	private AudioFormat format;
	private byte [] samples;
	//static String  love = "520";
	int age ;
	static String name;
	
	public static void main(String[] args) throws Exception {
     He he = new He("timewarning.wav");
    InputStream stream = new ByteArrayInputStream(he.getSamples());
     
     he.play(stream);
    System.exit(0);
		
		
	}
	
	
	public byte[] getSamples(){
		
		return samples ;
	}
	
	public He(String musicName) throws Exception {
		// TODO Auto-generated constructor stub
	AudioInputStream stream = AudioSystem.getAudioInputStream(new File(musicName));
	format = stream.getFormat(); 
	samples = getSamples(stream);
	}
	
	private byte [] getSamples(AudioInputStream stream){
		
		int length = (int)(stream.getFrameLength()*format.getFrameSize());
		
		byte [] sample=  new byte [length];
		
		DataInputStream dataInputStream = new DataInputStream(stream);
		try {
			dataInputStream.readFully(sample);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sample;
	}
	
	
	public void play(InputStream stream){
	
		int  bufferSize = format.getFrameSize();
		Math.round(format.getFrameRate()/10);
		
		byte [] buffer = new byte[bufferSize];
		
		//
		SourceDataLine line ;
		
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format, bufferSize);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		line.start();
		//copy date to the line
		
		int numBytesRead = 0;
		
		while (numBytesRead != -1) {
			try {
				numBytesRead = stream.read(buffer, 0, buffer.length);
			    if(numBytesRead != -1){
			    	
			    	line.write(buffer, 0, numBytesRead);
			    }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			line.drain();
			line.close();
			
		}
	}
}
