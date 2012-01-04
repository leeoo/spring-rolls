package com.demo.test;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RMITest {

	public static void main(String[] args) throws Exception{
		new Server().startServer();
		Client client = new Client();
		client.invoke();
	}
	
	static class Client{
		
		public void invoke(){
			try {
				Test test = (Test) Naming.lookup("//192.168.71.98:1099/a");
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
	
	static class Server{
		
		public void startServer(){
			Test test = null;
			try {
				test = new TestImpl("接哈");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			try {
				LocateRegistry.createRegistry(1099);
				Naming.rebind("//192.168.71.98:1099/a", test);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}	
		}
		
	}
	
	static interface Test extends Remote,Serializable{

		public void haha() throws RemoteException ;
		
	}
	
	static abstract class AbTest extends UnicastRemoteObject implements Test{

		protected AbTest() throws RemoteException {
			super();
		}

		private static final long serialVersionUID = -671841927271620452L;
		
	}
	
	static class TestImpl extends AbTest{
		private String name;
		
		protected TestImpl(String name) throws RemoteException {
			this.name=name;
		}

		private static final long serialVersionUID = 7815650281092707812L;



		public synchronized void haha() {
			System.out.println(name+"我运行了，哈哈!!!");
		}

	}

	
}
