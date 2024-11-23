package com.dsj.rpc.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer {
    @Override
    public void doStart(int port) {
        //创建Vert.x实例
        Vertx vertx = Vertx.vertx();

        //创建HttpServer服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        //设置请求处理器
        server.requestHandler(new HttpServerHandler());

        //启动HTTP服务器并监听指定端口
        server.listen(port, request -> {
            if (request.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            } else {
                System.out.println("Failed to start server: " + request.cause());
            }
        });
    }
}
