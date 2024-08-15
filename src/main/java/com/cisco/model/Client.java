package com.cisco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spinjar.com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = ClientDeserializer.class)
public class Client implements Serializable {
    private String name;
    private Double balance;
}
