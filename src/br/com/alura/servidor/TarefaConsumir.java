package br.com.alura.servidor;

import java.util.concurrent.ArrayBlockingQueue;

public class TarefaConsumir implements Runnable{

	private ArrayBlockingQueue<String> filaComandos;

	public TarefaConsumir(ArrayBlockingQueue<String> filaComandos) {
		this.filaComandos = filaComandos;
	}

	@Override
	public void run() {
		
		String comando = null;
		
		try {
			while((comando = filaComandos.take()) != null){
				System.out.println("Consumindo comando " + comando + " , " + Thread.currentThread().getName());
				Thread.sleep(10000);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
