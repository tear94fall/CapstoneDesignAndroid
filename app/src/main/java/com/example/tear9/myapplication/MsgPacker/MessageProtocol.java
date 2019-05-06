package com.example.tear9.myapplication.MsgPacker;

public class MessageProtocol{

    // 프토로콜들의 정의
    // 프로토콜 숫자는 2의 배수

    /*
    1. 에코기능
    2. 요청 시작
    3. 요청 끝
    4. 응답 시작
    5. 응답 끝
     */

    public static final byte ECHO = 0;
    public static final byte REQUEST_START = 1;
    public static final byte REQEUST_END = 2;
    public static final byte RESPONSE_START = 3;
    public static final byte RESPONSE_END = 4;

    /* 추가 프로토콜은 순번대로 정의 할것 */
}