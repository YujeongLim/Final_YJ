package com.uni.mental.ageComunity.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AgeCmtDTO {
    private Long ageCmtNo;
    private Long ageComNo;
    private String memberNick;
    private String ageCmtDetail;
    private Date ageCmtDate;

}
