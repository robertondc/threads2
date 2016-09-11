package br.com.alura.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidor;
	private ExecutorService poolDeThreads;

	public DistribuirTarefas(ExecutorService poolDeThreads, Socket socket, ServidorTarefas servidor) {
		this.poolDeThreads = poolDeThreads;
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
						
						ComandoC1 c1 = new ComandoC1(saidaCliente);
						poolDeThreads.execute(c1);						
						break;
					}
					case "c2": {
						ComandoC2 c2 = new ComandoC2(saidaCliente);
						poolDeThreads.execute(c2);
						
						break;
					}
					case "fim" :{
						saidaCliente.println("desligando o servidor");
						servidor.parar();
						break;
					}
					default: {
						saidaCliente.println("comando nao encontrado");
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
