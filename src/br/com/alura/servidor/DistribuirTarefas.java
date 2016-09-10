package br.com.alura.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidor;

	public DistribuirTarefas(Socket socket, ServidorTarefas servidor) {
		this.socket = socket;
		this.servidor = servidor;
	}

	@Override
	public void run() {

		System.out.println("Distribuindo taredas para o clitente" + socket.getPort());
		try {
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());

			while (entradaCliente.hasNextLine()) {
				String comando = entradaCliente.nextLine();
				System.out.println("comando recebido " + comando);

				switch (comando) {
					case "c1": {
						saidaCliente.println("confirmacao do comando c1");
						break;
					}
					case "c2": {
						saidaCliente.println("confirmacao do comando c2");
						break;
					}
					case "fim" :{
						saidaCliente.println("desligando o servidor");
					}
					default: {
						saidaCliente.println("comando nao encontrado");
						servidor.parar();
						return;
					}
				}
				
			}

			entradaCliente.close();
			saidaCliente.close();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		System.out.println("Distribuido" + socket.getPort());
	}

}
