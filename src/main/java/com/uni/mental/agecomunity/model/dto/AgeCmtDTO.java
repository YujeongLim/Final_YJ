package com.uni.mental.agecomunity.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AgeCmtDTO {
    private int ageCmtNo;
    private int ageComNo;
    private String memberNick;
    private String ageCmtDetail;
    private Date ageCmtDate;
}
