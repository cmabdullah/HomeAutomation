package com.abdullah.home.automation.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@Service
public class RegulatorServiceImpl implements RegulatorService{
    @Override
    public int postSocketRequest(int regulatorParam) {

        String result = "";
        try {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            System.out.println(serverAddress.getHostAddress());
            Socket socket = new Socket(serverAddress , 2017);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(regulatorParam);
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
        return 0;
    }
}
