package br.com.alura.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 12345);
		System.out.println("Conexao estabelecida");

		Thread threadEnviaComandos = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Pode enviar comandos");
				try {
					PrintStream saida = new PrintStream(socket.getOutputStream());
					Scanner teclado = new Scanner(System.in);
					while (teclado.hasNextLine()) {

						String linha = teclado.nextLine();
						if (linha.trim().equals("")) {
							break;
						}
						saida.println(linha);
					}
					saida.close();
					teclado.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		Thread threadRecebeResposta = new Thread(new Runnable() {
			@Override
			public void run() {
				
				System.out.println("Recebendo dados do servidor");
				try {
					Scanner respostaServidor = new Scanner(socket.getInputStream());
					while(respostaServidor.hasNextLine()){
						String linha = respostaServidor.nextLine();
						System.out.println(linha);
					}
					
					respostaServidor.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				
			}

		});

		threadEnviaComandos.start();
		threadRecebeResposta.start();
		
		//thread main vai aguardar threadEnviaComandos terminar
		threadEnviaComandos.join();
		
		
		System.out.println("fechando socket do cliente");
		socket.close();

	}
}
