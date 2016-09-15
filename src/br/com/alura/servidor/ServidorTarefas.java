package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
	
	// cada thread copia parametro para sua memoria e cria cache para otimizar o acesso
	//volatile e atomic boolean sao os mesmos 
	private AtomicBoolean estaRodando;
	ServerSocket servidor;
	ExecutorService poolDeThreads;
	private ArrayBlockingQueue<String> filaComandos;

	public ServidorTarefas() throws IOException {

		this.estaRodando =  new AtomicBoolean(true);
		servidor = new ServerSocket(12345);
		poolDeThreads = Executors.newCachedThreadPool(new FabricaDeThreads());
		this.filaComandos = new ArrayBlockingQueue<String>(2);
		iniciarConsumidores();
		
	}

	private void iniciarConsumidores() {
		int qtdConsumidores = 2;
		for (int i =0; i < qtdConsumidores; i++){
			TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
			this.poolDeThreads.execute(tarefa);
		}
	}

	private void rodar() throws IOException {
		System.out.println("--- Iniciando Servidor ---");

		while (this.estaRodando.get()) {
			try{
				Socket socket = servidor.accept();
				System.out.println("aceitando novo cliente na porta: " + socket.getPort());
				poolDeThreads.execute(new DistribuirTarefas(poolDeThreads, filaComandos, socket, this));
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
