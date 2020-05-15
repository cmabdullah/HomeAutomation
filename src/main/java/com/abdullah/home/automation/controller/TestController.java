package com.abdullah.home.automation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/socket")
    public String socket(){
        return "10";
    }

    @ResponseBody
    @GetMapping("/socket2")
    public String socket2(){


        String result = "";
        try {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            System.out.println(serverAddress.getHostAddress());
            Socket socket = new Socket(serverAddress , 2017);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("30");
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            result = input.readLine();
            System.out.println(input.readLine());

            out.close();
            //input.close();
            socket.close();

        }catch(UnknownHostException e) {
            System.out.println(e.toString());
        }catch(IOException e) {
            System.out.println(e.toString());
        }catch(IllegalArgumentException e) {
            System.out.println(e.toString());
        }catch(Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }
}
