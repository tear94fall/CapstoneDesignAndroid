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
    // 에뮬레이터에서 사용하는 아이피
    private String emulator_ip_addr = "10.0.2.2";

    /* 서버의 아이피를 입력함 */
    //private String server_target_ip = "172.17.211.160";
    private String server_target_ip = "192.168.0.54";

    // 실제 사용하는 아이피와 포트
    private String server_ip_addr = server_target_ip;
    private int server_port = 8888;

    public void setLoginInfo(String id, String passwd){
        this.userid = id;
        this.passwd =passwd;
    }

    public String getResponse_data(){
        return this.response_data;
    }

    // 2번 요청
    // 에코 요청으로 네트워크 연결상태가 양호한지 체크하는 메소드
    // 보낸 데이터를 그대로 받아야한다.
    public void EchoRequest(final String echo_test_value) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            //msg.SetProtocol(MessageProtocol.CHAT);
            // 위의 코드를 실행하면 처음에 프로토콜을 전송데이터의 처음에 삽입함

            String packet;
            String request_number="2";

            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("echo_test_key", "echo_test_value");
            packet = DataBuf.get_data();
            System.out.println(packet);

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
            byte[] bytes = new byte[buf.position()];
            buf.flip();
            buf.get(bytes);
            String recv_message = new String(bytes);


            /* 반드시 닫아줄것 */
            client.close();

            /* 에러 처리 로직 추가 할것 */

            response_data = recv_message;

        }catch (UnknownHostException ex) {
            response_data = "false";
        }catch (IOException ex) {
            response_data = "false";
        }
    }


    // 4번 요청
    // 로그인을 위한 메서드
    public void loginCheck() throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

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

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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

    // 6번 요청
    //회원 가입을 위한 아이디 중복검사를 실행하는 메서드
    public void IdCheck(String _id) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

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

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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

    // 8번 요청
    //회원 가입을 실행하는 메서드
    public void CreateAccount(String _id, String _passwd, String _name, String _tel) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="8";
            String user_id = _id;
            String user_pw = _passwd;
            String user_name = _name;
            String user_tel = _tel;

            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("user_id", user_id);
            DataBuf.set_data("user_pw", user_pw);
            DataBuf.set_data("user_name", user_name);
            DataBuf.set_data("user_tel", user_tel);

            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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

    // 10번 요청
    //캡챠의 문제를 가져옴
    public void getCaptchatTestSet() throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="10";

            DataBuf.set_data("request_number", request_number);
            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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

    // 10-2 번 요청 사용하지 않는다.
    // 새로운 계정을 만드는 메소드
    public void CreateAccount(String _captcha_ans_num, String _capthca_ans) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="10";

            String captcha_ans_num = _captcha_ans_num;
            String captcha_ans = _capthca_ans;

            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("ans_num", captcha_ans_num);
            DataBuf.set_data("ans", captcha_ans);

            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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

    // 12번 요청
    // 마지막 운전 날짜를 가져오는 메소드
    public void getLastDriveDate(String _id) throws UnknownHostException,IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);

        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            //msg.SetProtocol(MessageProtocol.CHAT);
            // 위의 코드를 실행하면 처음에 프로토콜을 전송데이터의 처음에 삽입함

            String packet;
            String request_number="12";
            String user_id=_id;

            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("user_id", user_id);

            packet = DataBuf.get_data();
            System.out.println(packet);

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

    // 14번 요청
    // 이미지에 쓰여진 글자를 통해 테스트를 진행하는 캡차
    // 입력된 정답과 문제를 보냄 테스트가 통과했는지 아닌지에대한 값만을 가져옴
    public void getCaptchatTestSet2(String captcha_test_number, String captcha_test_answer) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="14";

            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("captcha2_number", captcha_test_number);
            DataBuf.set_data("captcha2_answer", captcha_test_answer);
            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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


    // 16번 요청
    // 사용자의 정보를 가져오는 클래스
    public void getUserInfo(String userid) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="16";

            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("userid", userid);
            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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


    // 18번 요청
    // 사용자의 모든 정보를 가져오는 클래스
    public void getAllUserInfoClass(String userid) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="18";

            /* 필요한 파라미터 추가 할것 */
            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("userid", userid);
            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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

    // 20번 요청
    // 사용자의 정보를 업데이트 하는 클래스
    public void UpdateUserInfo(String userid, String userpassword, String username, String usertel) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="20";

            /* 필요한 파라미터 추가 할것 */
            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("userid", userid);
            DataBuf.set_data("userpassword", userpassword);
            DataBuf.set_data("username", username);
            DataBuf.set_data("usertel", usertel);
            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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


    // 22번 요청
    // 마지막 운전 날짜를 업데이트 하는 함수
    public void UpdateLastDriveDate(String userid) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="22";

            /* 필요한 파라미터 추가 할것 */
            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("userid", userid);
            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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

    // 24번 요청
    // 음주운전 카운트를 증가 시키는 함수
    public void UpdateAlcoholCount(String userid) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="24";

            /* 필요한 파라미터 추가 할것 */
            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("userid", userid);
            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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

    // 26번 요청
    // 이름과 전화버호로 아이디를 찾는 함수
    public void FindUserId(String username, String usertel) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="26";

            /* 필요한 파라미터 추가 할것 */
            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("username", username);
            DataBuf.set_data("usertel", usertel);
            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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

    // 28번 요청
    // 아이디와 이름으로 비밀번호를 찾아내는 함수
    public void FindUserPw(String userid, String username) throws UnknownHostException,IOException, InterruptedException {
        try {
            InetSocketAddress hostAddress = new InetSocketAddress(server_ip_addr, server_port);
            SocketChannel client = SocketChannel.open(hostAddress);

            MessagePacker msg = new MessagePacker();
            DataBuffer DataBuf = new DataBuffer();

            String packet;
            String request_number="28";

            /* 필요한 파라미터 추가 할것 */
            DataBuf.set_data("request_number", request_number);
            DataBuf.set_data("userid", userid);
            DataBuf.set_data("username", username);
            packet = DataBuf.get_data();

            msg.add(packet);
            msg.Finish();

            /* 서버에게 데이터를 보냄 */
            client.write(msg.getBuffer());

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            /* 서버에서 데이터를 받아옴 */
            client.read(buf);

            /* ByteBuffer 를 String 으로 저장 함*/
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
