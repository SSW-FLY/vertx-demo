package com.example.vertxwebdemo.controller;

import com.example.vertxwebdemo.pojo.WorkbenchReport;
import com.example.vertxwebdemo.proto.WorkbenchReportOuterClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class FileCompareController {


    @GetMapping("/api/file")
    public void json() throws Exception {
        long start = System.currentTimeMillis();
        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
        try (FileInputStream fileInputStream = new FileInputStream("/Users/fly/Downloads/1.csv");
             CSVReader reader = new CSVReaderBuilder(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8)).withCSVParser(rfc4180Parser).build()) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                WorkbenchReport report = new WorkbenchReport();
                report.setDate(line[0]);
                report.setDimension(List.of(Objects.requireNonNull(getDimension(line, 3))));
                report.setMetrics(List.of(line[4], line[5]));
            }

            System.out.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/api/file1")
    public void proto() throws Exception {
        long start = System.currentTimeMillis();
        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
        try (FileInputStream fileInputStream = new FileInputStream("/Users/fly/Downloads/1.csv");
             CSVReader reader = new CSVReaderBuilder(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8)).withCSVParser(rfc4180Parser).build();
             FileOutputStream outputStream = new FileOutputStream("2.log")) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                WorkbenchReportOuterClass.WorkbenchReport report = WorkbenchReportOuterClass.WorkbenchReport
                        .newBuilder()
                        .setDate(line[0])
                        .addAllDimensions(List.of(Objects.requireNonNull(getDimension(line, 3))))
                        .addAllMetrics(List.of(line[4], line[5]))
                        .build();
                report.writeDelimitedTo(outputStream);
            }
            System.out.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/api/file2")
    public void read() {
        long start = System.currentTimeMillis();
        try (FileInputStream inputStream = new FileInputStream("2.log")) {
            WorkbenchReportOuterClass.WorkbenchReport report;
            while ((report = WorkbenchReportOuterClass.WorkbenchReport.parseDelimitedFrom(inputStream)) != null) {
                WorkbenchReport converter = converter(report);
            }
            System.out.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    private static String[] getDimension(String[] line, int dimensionSize) {
        StringBuilder str = new StringBuilder();
        String[] temp = new String[dimensionSize];
        for (int i = 1; i < dimensionSize + 1; i++) {
            temp[i - 1] = line[i];
            str.append(line[i]);
        }
        if ("".equals(str.toString())) {
            return null;
        }
        return temp;
    }
}
