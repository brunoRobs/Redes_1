package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
	
	public static float soma(String a, String b) {
		return Float.parseFloat(a) + Float.parseFloat(b);
	}
	
	public static float mult(String a, String b) {
		return Float.parseFloat(a) * Float.parseFloat(b);
	}
	
	public static float divi(String a, String b) {
		return Float.parseFloat(a) / Float.parseFloat(b);
	}
	
	public static void main(String[] args) {
		
		DatagramSocket server = null;
		
		try {
			server = new DatagramSocket(Integer.parseInt(args[0])); //CRIAÇÃO DO SERVIDOR
			
			byte[] buffer = new byte[13]; //BUFFER QUE ARMAZENA A SOLICITAÇÃO DO CLIENTE
			
			while(true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				
				server.receive(request); //RECEBIMENTO DA SOLICITAÇÃO POR OUTRO PROCESSO
				
				String operacao = new String(request.getData());
				
				float resultado = 0f;
				
				String[] val = new String[2];
				
				if(operacao.contains("+")) {
					val[0] = operacao.substring(0, operacao.indexOf("+"));
					val[1] = operacao.substring(operacao.indexOf("+") + 1);
					resultado = soma(val[0], val[1]);
				}
				else if(operacao.contains("*")) {
					val[0] = operacao.substring(0, operacao.indexOf("*"));
					val[1] = operacao.substring(operacao.indexOf("*") + 1);
					resultado = mult(val[0], val[1]);
				}
				else {
					val[0] = operacao.substring(0, operacao.indexOf("/"));
					val[1] = operacao.substring(operacao.indexOf("/") + 1);
					resultado = divi(val[0], val[1]);
				}
				
				//DATAGRAMA COM RESULTADO A SER ENVIADO
				DatagramPacket result = new DatagramPacket(String.valueOf(resultado).getBytes(), String.valueOf(resultado).getBytes().length);
				
				//CONSTRUÇÃO DA MENSAGEM DE RESPOSTA
				DatagramPacket reply = new DatagramPacket(result.getData(), result.getLength(), request.getAddress(), request.getPort());
				
				server.send(reply); //ENVIO PARA O PROCESSO CUJO ENDEREÇO E PORTA ESTÃO INDICADOS NO REQUEST
			}
		} catch(SocketException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(server != null) server.close();
		}
	}
}
