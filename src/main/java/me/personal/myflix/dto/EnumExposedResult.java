package me.personal.myflix.dto;

import java.util.List;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnumExposedResult {
	private List<Map<String, Object>> data;
}

