package com.example;

import com.example.grpc.HiService;
import com.example.thrift.HelloService;
import com.linecorp.armeria.common.SerializationFormat;
import com.linecorp.armeria.common.SessionProtocol;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.grpc.GrpcServiceBuilder;
import com.linecorp.armeria.server.http.file.HttpFileService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.thrift.THttpService;

public class MyApp {
    public static void main(String[] args) {
        HelloService.AsyncIface helloHandler = new MyHelloService();

        ServerBuilder sb = new ServerBuilder();
        sb.port(8080, SessionProtocol.HTTP);
        GrpcService grpcService = new GrpcServiceBuilder()
                .addService(new HiService())
                .build();

        // Mapping /hello to thrift service
        sb.serviceAt(
                "/hello",
                THttpService.of(helloHandler, SerializationFormat.THRIFT_BINARY)
                        .decorate(LoggingService::new));

        // Mapping /static-cp/ to static content(classpath)
        sb.serviceUnder("/static-cp/",
                HttpFileService.forClassPath("/static/"));

        // Mapping /static-fs/ to static content(file system)
        sb.serviceUnder("/static-fs/",
                HttpFileService.forFileSystem("src/main/resources/static/"));

        // Mapping /docs/** to documentation
        sb.serviceUnder("/docs/", new DocService());

        // Mapping /** to grpc
        sb.serviceUnder(
                "/",
                grpcService.decorate(LoggingService::new));

        Server server = sb.build();
        server.start();
    }
}
