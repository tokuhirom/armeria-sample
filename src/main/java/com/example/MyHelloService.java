package com.example;

import com.example.thrift.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

public class MyHelloService implements HelloService.AsyncIface {
    @Override
    public void hello(String name, AsyncMethodCallback resultHandler) throws TException {
        resultHandler.onComplete("Hello, " + name + "!");
    }
}
