package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JuntaResultadosFutureWsFutureBanco implements Callable<Void> {

	private Future<String> futureWs;
	private Future<String> futureBanco;
	private PrintStream saidaCliente;

	public JuntaResultadosFutureWsFutureBanco(Future<String> futureWs, Future<String> futureBanco,
			PrintStream saidaCliente) {
				this.futureWs = futureWs;
				this.futureBanco = futureBanco;
				this.saidaCliente = saidaCliente;
	}

	@Override
	public Void call() throws Exception {
		
		System.out.println("Aguardando resultados do futureWs e futureBanco");

		try {
		String numeroWs = this.futureWs.get(20, TimeUnit.SECONDS);
		String numeroBanco = this.futureBanco.get(20, TimeUnit.SECONDS);
		
		saidaCliente.println("Resultado do comando C2: " + numeroWs + "," + numeroBanco);
		} catch (InterruptedException | ExecutionException | TimeoutException e){
			System.out.println("Timeout: Cancelando a execucao do c2.");
			this.futureWs.cancel(true);
			this.futureBanco.cancel(true);
		}
		
		System.out.println("finalizou Junta Resultados");
		
		return null; // nao possui retorno
	}

}
