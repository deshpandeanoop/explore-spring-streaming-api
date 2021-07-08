package com.explore.api.streaming.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequestMapping("/v1/contents")
@RestController
public class StreamingController {

    @GetMapping
    public ResponseEntity<StreamingResponseBody> streamContent(HttpServletResponse httpServletResponse) {
        System.out.println("Received streaming request");
       //httpServletResponse.setContentType("application/octet-stream");
       return new ResponseEntity<>(buildStreamingResponseBody(), HttpStatus.OK);
    }

    private StreamingResponseBody buildStreamingResponseBody() {
        try {
            return httpResponseStream -> {
                Files.lines(Paths.get("E:\\dummy-datasets\\archive\\bitstampUSD_1-min_data.csv"))
                .forEach(line -> {
                    try {
                        //Thread.sleep(200);
                        httpResponseStream.write(line.getBytes());
                        httpResponseStream.flush();
                    } catch (Exception ex) { }
                });
            };
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
