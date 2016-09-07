package br.com.alura.servidor;

import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {

	private Socket socket;

	public DistribuirTarefas(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		System.out.println("Distribuindo taredas para o clitente" + socket.getPort());
		try {
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			
			while(entradaCliente.hasNextLine()){
				String comando = entradaCliente.nextLine();
				System.out.println(comando);
			}
			
			entradaCliente.close();
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}

		System.out.println("Distribuido" + socket.getPort());
	}

}
