/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.example.crash_reproducer;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;

import javax.net.ssl.SSLException;

public class App {
    public static void main(String[] args) throws InterruptedException, SSLException {
        System.out.println("Initializing SSL (loading netty-tcnative)...");
        SslContextBuilder.forClient().build();
    }
}
