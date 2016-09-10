package br.com.alura.servidor;

public class ServidorDeTestesVolatile {
	// threads possuem cache de variaveis.
	// e com isso um valor alterado pode nao ser lido corretamente dentro das threads
	// volatile faz com que a variavel sempre seja lido diret na memoria principal
	// a classe AtomicBoolean eh uma alternativa ao volatile 
	private volatile boolean estaRodando = false;
	
	public static void main(String[] args) throws InterruptedException {
		ServidorDeTestesVolatile servidor = new ServidorDeTestesVolatile();
		servidor.rodar();
		servidor.alterandoAtributo();
	}

	private void alterandoAtributo() throws InterruptedException {
		
		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando = true");
		this.estaRodando = true;
		
		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando = false");
		this.estaRodando = false;
		
	}

	private void rodar() {
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("servidor comecando, estaRodando = " + estaRodando);
				
				while(!estaRodando){}
				
				System.out.println("servidor rodando, estaRodando = " + estaRodando);
				
				while(estaRodando){}
				
				System.out.println("servidor terminado, estaRodando " + estaRodando);
				
			}
			
		}).start();
		
	}

}
