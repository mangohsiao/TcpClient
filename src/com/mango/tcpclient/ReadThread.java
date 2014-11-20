package com.mango.tcpclient;

import java.io.IOException;
import java.io.InputStream;

public class ReadThread implements Runnable {

	InputStream is;

	public ReadThread(InputStream is) {
		super();
		this.is = is;
	}

	@Override
	public void run() {
		byte[] rBuf = new byte[8192];
		int rtvl = -1;
		int len = 0;
		String strIn;
		boolean err = false;
		try {
			while (!err) {
				rtvl = is.read(rBuf, 0, 2);
				if (rtvl < 0) {
					err = true;
				}
				if (rBuf[0] == 0x02) {
					System.out.println("HB 0x02");
					continue;
				}
				rtvl = is.read(rBuf, 2, 2);
				if (rtvl < 0) {
					err = true;
				}
				len = (rBuf[2] & 0xff) << 8 | rBuf[3] & 0xff;
				rtvl = is.read(rBuf, 4, len);
				if (rtvl < 0) {
					err = true;
				}
				System.out.println("len=" + len);
				strIn = new String(rBuf, 4, len);
				System.out.println("content: " + strIn);
			}
			System.out.println(" closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
