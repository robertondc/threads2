package br.com.alura.servidor;

import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTarefas {
	public static void main(String[] args) throws Exception {
		System.out.println("--- Iniciando Servidor ---");

		ServerSocket servidor = new ServerSocket(12345);

		while (true) {
			Socket socket = servidor.accept();
			System.out.println("aceitando novo cliente na porta: " + socket.getPort());
			new Thread(new DistribuirTarefas(socket)).start();
		}

	}
}
