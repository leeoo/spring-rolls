package com.demo.test;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMITest {

	public static void main(String[] args) throws Exception{
		new Thread(new Server()).start();
		//Thread.sleep(100);
		//Client client = new Client();
		//client.invoke();
	}
	
	static class Client{
		
		public void invoke(){
			try {
				Test test = (Test) Naming.lookup("//127.0.0.1:1099/a");
				test.haha();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	static class Server implements Runnable{
		
		static{
			try {
				LocateRegistry.createRegistry(1099);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			Test test = new TestImpl();
			try {
				Naming.rebind("//127.0.0.1:1099/a", test);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}			
		}
		
	}
	
	static interface Test extends Remote{

		public void haha();
		
	}
	
	static class TestImpl implements Test,Serializable{

		private static final long serialVersionUID = 7815650281092707812L;

		public synchronized void haha() {
			System.out.println("我运行了，哈哈!!!");
		}

	}

	
}
