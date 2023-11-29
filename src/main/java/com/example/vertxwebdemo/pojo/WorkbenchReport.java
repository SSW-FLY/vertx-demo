package com.example.vertxwebdemo.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkbenchReport {

    private List<String> dimension;

    private List<String> metrics;

    private String date;

    private List<WorkbenchReport> detail;
}
