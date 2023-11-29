package com.example.vertxwebdemo.controller;

import com.example.vertxwebdemo.pojo.WorkbenchReport;
import com.example.vertxwebdemo.proto.WorkbenchReportOuterClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        FileOutputStream outputStream = new FileOutputStream("t1.log");
        for (int i = 0; i < 100000; i++) {
            report.writeDelimitedTo(outputStream);
        }
        outputStream.flush();
        outputStream.close();

        System.out.println("结束");
    }


    @GetMapping("/api/test1")
    public void json() throws Exception {
        WorkbenchReport m1 = new WorkbenchReport();

        List<String> metrics = List.of("12", "13", "14");
        m1.setMetrics(metrics);
        m1.setDate("20230101");

        WorkbenchReport m2 = new WorkbenchReport();
        m2.setMetrics(metrics);
        m2.setDate("20230102");


        WorkbenchReport report = new WorkbenchReport();
        List<String> dimension = List.of("click", "click1", "click2");
        report.setDimension(dimension);
        report.setMetrics(metrics);
        report.setDetail(List.of(m1, m2));


        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(report);

        BufferedWriter writer = new BufferedWriter(new FileWriter("t2.log"));
        for (int i = 0; i < 100000; i++) {
            writer.write(str);
            writer.flush();
            System.out.println(i + "--- " + str);
            writer.newLine();
        }
        writer.flush();
        writer.close();
        System.out.println("结束");

    }


    @GetMapping("/api/test2")
    public void readProto() throws Exception {
        long start = System.currentTimeMillis();
        FileInputStream inputStream = new FileInputStream("t1.long");
        List<WorkbenchReport> reports = new ArrayList<>();
        WorkbenchReportOuterClass.WorkbenchReport report;
        while ((report = WorkbenchReportOuterClass.WorkbenchReport.parseDelimitedFrom(inputStream)) != null) {
            WorkbenchReport converter = converter(report);
            reports.add(converter);

        }
        System.out.println(System.currentTimeMillis() - start);
    }


    @GetMapping("/api/test3")
    public void readJson() throws Exception {
        long start = System.currentTimeMillis();
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader reader = new BufferedReader(new FileReader("t2.log"));
        String res = null;
        List<WorkbenchReport> reports = new ArrayList<>();
        while ((res = reader.readLine()) != null) {
            WorkbenchReport report = mapper.readValue(res, WorkbenchReport.class);
            reports.add(report);
        }

        System.out.println(System.currentTimeMillis() - start);
    }

    public WorkbenchReport converter(WorkbenchReportOuterClass.WorkbenchReport data) {
        WorkbenchReport report = new WorkbenchReport();
        report.setMetrics(data.getMetricsList());
        report.setDimension(data.getDimensionsList());
        report.setDate(data.getDate());
        if (!data.getDetailList().isEmpty()) {
            List<WorkbenchReport> list = data.getDetailList().stream().map(this::converter).collect(Collectors.toList());
            report.setDetail(list);
        }
        return report;
    }

    public static void main(String[] args) throws Exception {
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

        ByteString string = report.toByteString();
        String stringUtf8 = string.toStringUtf8();
        System.out.println(stringUtf8);
    }
}
