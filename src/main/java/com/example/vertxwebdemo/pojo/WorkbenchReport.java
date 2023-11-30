package com.example.vertxwebdemo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class WorkbenchReport {

    private List<String> dimension;

    private List<String> metrics;

    private String date;

    private List<WorkbenchReport> detail;

    @JsonIgnore
    public String getDimensionStr() {
        return Optional.ofNullable(dimension)
                .orElse(Collections.emptyList())
                .stream().reduce((x, y) -> x + "-" + y)
                .orElse("");
    }
}
