package com.example.demo.utils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;



public class ModelMapperUtils {
	private static final ModelMapper MODEL_MAPPER = new ModelMapper();

	private ModelMapperUtils() {
	}

	public static <D> D map(Object source, Class<D> destinationType) {
		return MODEL_MAPPER.map(source, destinationType);
	}

	public static <D> D map(Object source, Class<D> destinationType, String typeMapName) {
		return MODEL_MAPPER.map(source, destinationType, typeMapName);
	}

	public static void map(Object source, Object destination) {
		MODEL_MAPPER.map(source, destination);
	}

	public static void map(Object source, Object destination, String typeMapName) {
		MODEL_MAPPER.map(source, destination, typeMapName);
	}

	public static <D> D map(Object source, Type destinationType) {
		return MODEL_MAPPER.map(source, destinationType);
	}

	public static <D> D map(Object source, Type destinationType, String typeMapName) {
		return MODEL_MAPPER.map(source, destinationType, typeMapName);
	}

	public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
		return source.stream().map(element -> MODEL_MAPPER.map(element, targetClass)).collect(Collectors.toList());
	}
}
