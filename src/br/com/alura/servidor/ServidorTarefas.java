package br.com.alura.servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {
	public static void main(String[] args) throws Exception {
		System.out.println("--- Iniciando Servidor ---");

		ServerSocket servidor = new ServerSocket(12345);
		
		ExecutorService poolDeThreads = Executors.newCachedThreadPool();
		
		while (true) {
			Socket socket = servidor.accept();
			System.out.println("aceitando novo cliente na porta: " + socket.getPort());
			poolDeThreads.execute(new DistribuirTarefas(socket));
		}

	}
}
