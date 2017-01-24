package com.spacebrains.interfaces;
/**
 * Implements basic methods to handle communication with RESTful API -
 * to put a row into a database
 * to get a row set from database
 * to delete a row from database
 * 
 * @author oleg.chizhov
 *
 */
public interface IRestAware<T> {
	void putRow(T obj);
	T getRowset();
	boolean delete(T obj);
}
