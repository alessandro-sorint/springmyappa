package com.myapp.framework;

public abstract class MyControllerMVC<T> extends MyController{
	protected abstract T getModel();
}
