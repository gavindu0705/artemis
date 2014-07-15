package com.artemis.mongo.persistence;

import java.io.Serializable;
import java.util.List;

import com.mongodb.DBObject;

/**
 * 查询接口
 * 
 * @author xiaoyu
 * 
 * @param <E>
 * @param <K>
 */
public interface EntityDao<E extends Entity, K extends Serializable> {

	/**
	 * 根据id查找对象
	 * 
	 * @param id
	 * @return
	 */
	E findById(K id);

	E findById(K id, String[] fields);

	/**
	 * 查找所有对象
	 * 
	 * @return
	 */
	List<E> findAll();

	/**
	 * 根据id列表查找所有对象，返回的列表根据传入的idList排序
	 * 
	 * @param idList
	 * @return
	 */
	List<E> findAllByIds(List<K> idList);

	/**
	 * 向数据库插入一条数据
	 * 
	 * @param entity
	 */
	void save(E entity);

	/**
	 * 向数据库插入一批数据
	 * 
	 * @param entities
	 */
	void saveList(List<E> entities);

	/**
	 * 根据id删除一条数据
	 * 
	 * @param id
	 */
	void deleteById(K id);

	/**
	 * 根据id列表删除一批数据
	 * 
	 * @param idList
	 */
	void deleteByIds(List<K> idList);

	/**
	 * 得到对应的实体类
	 * 
	 * @return
	 */
	Class<E> getEntityClass();

	List<E> findAll(DBObject q);

	List<E> findAll(DBObject q, String[] fields);

	List<E> findAll(DBObject q, int maxResult);

	List<E> findAll(DBObject q, int maxResult, String[] fields);

	List<E> findAll(DBObject q, int firstResult, int maxResult);

	List<E> findAll(DBObject q, int firstResult, int maxResult, String[] fields);

	List<E> findAll(DBObject q, DBObject sq);

	List<E> findAll(DBObject q, DBObject sq, String[] fields);

	List<E> findAll(DBObject q, DBObject sq, int maxResult);

	List<E> findAll(DBObject q, DBObject sq, int maxResult, String[] fields);

	List<E> findAll(DBObject q, DBObject sq, int firstResult, int maxResult);

	List<E> findAll(DBObject q, DBObject sq, int firstResult, int maxResult, String[] fields);

	E findOne(DBObject q);

	E findOne(DBObject q, String[] fields);

	long findCount();

	long findCount(DBObject q);

	void updateById(DBObject data, K id);

	void update(DBObject data, DBObject q);

	void delete(DBObject q);
}
