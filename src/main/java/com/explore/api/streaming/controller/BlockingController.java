package com.explore.api.streaming.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/v2/contents")
public class BlockingController {

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @GetMapping
    public void stream(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/json");
        response.setStatus(200);
        Runnable runnable = () -> {
            try {
                Files.lines(Paths.get("E:\\dummy-datasets\\archive\\random1.json"))
                        .forEach(line -> {
                            try {
                                Thread.sleep(200);
                                outputStream.write(line.getBytes());
                             //   outputStream.flush();
                            } catch (Exception ex) { ex.printStackTrace();}
                            finally {
                            }
                        });

            } catch (IOException e) {
                e.printStackTrace();
            }
//            try {
//               // outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        };
        executorService.submit(runnable);
    }
}
