package com.example.englishbala;

public class ServerData {
    public final static String SERVER_ADDRESS = "http://139.196.255.54/New";//做英语单词app时想要把用户数据存到阿里云服务器

    public final static String SERVER_LOGIN_ADDRESS = SERVER_ADDRESS + "/Login.php";
    public final static String SERVER_UPLOAD_RECORD_ADDRESS = SERVER_ADDRESS + "/upload/Record.php";
    public final static String SERVER_UPLOAD_INFO_ADDRESS = SERVER_ADDRESS + "/upload/InforFiles.php";
    public final static String SERVER_RETURN_BOOKS_ADDRESS = SERVER_ADDRESS + "/upload/GetAllFiles.php";

    public final static String LOGIN_SINA_NUM = "sinaNum";
    public final static String LOGIN_SINA_NAME = "sinaName";

}
