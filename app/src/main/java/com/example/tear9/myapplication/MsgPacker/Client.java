package com.example.tear9.myapplication.MsgPacker;

import java.nio.ByteBuffer;
import java.util.Scanner;
import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

public class Client {
    int request_num;
    private String userid;
    private String passwd;
    private String response_data;

    public void setLoginInfo(String id, String passwd){
        this.userid = id;
        this.passwd =passwd;
    }

    public String getResponse_data(){
        return this.response_data;
    }


    // 로그인을 위한 메서드
    public void loginCheck() throws UnknownHostException,IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);

        try {
            InetSocketAddress hostAddress = new InetSocketAddress("10.0.2.2", 8888);
            System.out.println("Client Started!");
            SocketChannel client = SocketChannel.open(hostAddress);
            System.out.println("Client Started!");

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            //msg.SetProtocol(MessageProtocol.CHAT);
            // 위의 코드를 실행하면 처음에 프로토콜을 전송데이터의 처음에 삽입함

            String packet;
            String request_number="4";
            String user_id=this.userid;
            String user_pw=this.passwd;

            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("user_id", user_id);
            DataBuf.set_data("user_pw", user_pw);

            packet = DataBuf.get_data();
            System.out.println(packet);

            /* 서버로 보낼 데이터의 형식을 정할것 현재는 앞뒤로 || 추가만함 */
            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String으로 저장 함*/
            byte[] bytes = new byte[buf.position()];
            buf.flip();
            buf.get(bytes);
            String recv_message = new String(bytes);


            /* 반드시 닫아줄것 */
            client.close();

            /* 에러 처리 로직 추가 할것 */

            response_data = recv_message;

        }catch (UnknownHostException ex) {
            System.err.println(ex);
        }catch (IOException ex) {
            System.err.println(ex);
        }
    }

    //회원 가입을 위한 아이디 중복검사를 실행하는 메서드
    public void IdCheck(String _id) throws UnknownHostException,IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);

        try {
            InetSocketAddress hostAddress = new InetSocketAddress("10.0.2.2", 8888);
            System.out.println("Client Started!");
            SocketChannel client = SocketChannel.open(hostAddress);
            System.out.println("Client Started!");

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            //msg.SetProtocol(MessageProtocol.CHAT);
            // 위의 코드를 실행하면 처음에 프로토콜을 전송데이터의 처음에 삽입함

            String packet;
            String request_number="6";
            String user_id= _id;

            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("user_id", user_id);

            packet = DataBuf.get_data();
            System.out.println(packet);

            /* 서버로 보낼 데이터의 형식을 정할것 현재는 앞뒤로 || 추가만함 */
            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String으로 저장 함*/
            byte[] bytes = new byte[buf.position()];
            buf.flip();
            buf.get(bytes);
            String recv_message = new String(bytes);


            /* 반드시 닫아줄것 */
            client.close();

            /* 에러 처리 로직 추가 할것 */

            response_data = recv_message;

        }catch (UnknownHostException ex) {
            System.err.println(ex);
        }catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
