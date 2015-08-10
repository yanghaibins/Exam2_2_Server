package com.hand.Exam2_2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ChatSocket extends Thread {
	
	Socket socket;
	
	public ChatSocket(Socket s){
		this.socket = s;
	}
	
	public void out(String out) {
		try {
			
			FileInputStream fis = new FileInputStream("SampleChapter1.pdf");
			BufferedInputStream bis = new BufferedInputStream(fis,1000000);
			byte input[] = new byte[100000];
			
			while (bis.read(input) != -1) {
				socket.getOutputStream().write(input);
			}
			
			socket.getOutputStream().write((out+"接收完毕").getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("断开了一个客户端链接");
			ChatManager.getChatManager().remove(this);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		out("你已经连接到本服务器了");
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream(),"UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				ChatManager.getChatManager().publish(this, line);
				
				
				
			}
			br.close();
			System.out.println("断开了一个客户端链接");
			ChatManager.getChatManager().remove(this);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("断开了一个客户端链接");
			ChatManager.getChatManager().remove(this);
			e.printStackTrace();
		}
		
	}
}
