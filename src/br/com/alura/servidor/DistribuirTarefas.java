package br.com.alura.servidor;

import java.io.PrintStream;
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
					default: {
						saidaCliente.println("comando nao encontrado");
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
