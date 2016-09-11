package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
	
	// cada thread copia parametro para sua memoria e cria cache para otimizar o acesso
	//volatile e atomic boolean sao os mesmos 
	private AtomicBoolean estaRodando;
	ServerSocket servidor;
	ExecutorService poolDeThreads;

	public ServidorTarefas() throws IOException {

		this.estaRodando =  new AtomicBoolean(true);
		servidor = new ServerSocket(12345);
		poolDeThreads = Executors.newFixedThreadPool(4, new FabricaDeThreads());
	}

	private void rodar() throws IOException {
		System.out.println("--- Iniciando Servidor ---");

		while (this.estaRodando.get()) {
			try{
				Socket socket = servidor.accept();
				System.out.println("aceitando novo cliente na porta: " + socket.getPort());
				poolDeThreads.execute(new DistribuirTarefas(poolDeThreads,socket, this));
			} catch (SocketException e){
				System.out.println("Socket Exception, esta rodando? " + this.estaRodando.get());
			}
		}
	}

	public void parar() throws IOException {
		this.estaRodando.set(false);
		this.servidor.close();
		this.poolDeThreads.shutdown();
	}

	public static void main(String[] args) throws Exception {
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();

	}
}
