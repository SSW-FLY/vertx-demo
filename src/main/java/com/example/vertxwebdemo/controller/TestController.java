package com.example.vertxwebdemo.controller;

import com.example.vertxwebdemo.proto.WorkbenchReportOuterClass;
import com.google.protobuf.ByteString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public void test() throws Exception {
        WorkbenchReportOuterClass.WorkbenchReport m1 = WorkbenchReportOuterClass.WorkbenchReport.newBuilder()
                .addMetrics("12")
                .addMetrics("13")
                .addMetrics("14")
                .setDate("20230101")
                .build();

        WorkbenchReportOuterClass.WorkbenchReport m2 = WorkbenchReportOuterClass.WorkbenchReport.newBuilder()
                .addMetrics("12")
                .addMetrics("13")
                .addMetrics("14")
                .setDate("20230102")
                .build();


        WorkbenchReportOuterClass.WorkbenchReport report = WorkbenchReportOuterClass.WorkbenchReport.newBuilder()
                .addDimensions("click")
                .addDimensions("click1")
                .addDimensions("click2")
                .addMetrics("12")
                .addMetrics("13")
                .addMetrics("14")
                .addDetail(m1)
                .addDetail(m2)
                .build();

        ByteString bytes = report.toByteString();
        String str = bytes.toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/fly/Downloads/t1.txt"));
        for (int i = 0; i < 1000; i++) {
            writer.write(str);
            System.out.println(i + "--- " + str);
            writer.newLine();
        }

        System.out.println("结束");

        BufferedReader reader = new BufferedReader(new FileReader("/Users/fly/Downloads/t1.txt"));

        String res = null;
        int i = 0;
        while ((res = reader.readLine()) != null) {
            i++;
        }
        System.out.println("-----------------------------------------------");
        System.out.println(i);
    }
}
