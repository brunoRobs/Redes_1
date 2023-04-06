package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
	//ARGS FORNECE O CONTEÚDO DA MENSAGEM E O HOSTNAME DO SERVIDOR
		public static void main(String[] args) {
			
			DatagramSocket client = null;
			
			try {
				client = new DatagramSocket(); //CRIAÇÃO DO CLIENTE
				
				byte[] message = args[0].getBytes(); //PEGA O CONTEÚDO DA MENSAGEM
				
				System.out.println(message.length); //TAMANHO DA MENSAGEM
				
				InetAddress host = InetAddress.getByName(args[1]); //PEGA O HOSTNAME DO SERVIDOR
				
				System.out.println(host.getHostName()); //NOME DO SERVIDOR
				
				int serverPort = 4791; //PORTA DO SERVIDOR
				
				//CONSTRUÇÃO DO PACOTE DE SOLICITAÇÃO PARA ENVIAR AO SERVIDOR
				DatagramPacket request = new DatagramPacket(message, message.length, host, serverPort);
				
				client.send(request); //ENVIANDO AO SERVIDOR
				
				byte[] buffer = new byte[13];
				
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				
				client.receive(reply); //RECEBIMENTO DO PACOTE DE RESPOSTA
				
				System.out.println("Reply: " + new String(reply.getData()).trim()); //IMPRIMINDO RESPOSTA
				
			} catch(SocketException e) {
				e.printStackTrace();
			} catch(UnknownHostException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				if(client != null) client.close();
			}
		}
}