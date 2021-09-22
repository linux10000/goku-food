package com.akiratoriyama.gokufoodapi.model;

public interface TotalRecordsHolder<T> {

	Integer getTotalRecords();
	
	T getData();
}
