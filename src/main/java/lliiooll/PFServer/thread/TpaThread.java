package lliiooll.PFServer.thread;

import org.bukkit.Bukkit;

import lliiooll.PFServer.cmd.Tpa;

public class TpaThread implements Runnable{
	
	private Thread t = new Thread(this,"TpaThread");
	public static int s;
	
	public TpaThread(int miao){
		s = miao;
		t.start();
	}
	
	@Override
	public void run() {
		while(true) {
			if(s == 0) {
				Tpa.tof = true;
				break;
			}else {
				try {
					Thread.sleep(1000);
					s--;
				} catch (InterruptedException e) {
					System.out.println("��c������һ���������·�����޷���������");
					e.printStackTrace();
					Bukkit.getServer().shutdown();
				}
			}
		}
		
	}

}
