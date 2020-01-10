package io.nirahtech.gepeen.persistence;

import java.util.List;

import io.nirahtech.gepeen.property.Identifier;

public class EntityService<T> implements Service<T> {
	
	private final Repository<T> repository = new EntityRepository<T>();
	
	public EntityService() {
		
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public T find(Identifier id) {
		return repository.find(id);
	}

	@Override
	public boolean persist(T object) {
		return repository.persist(object);
	}

	@Override
	public boolean update(T object) {
		return repository.update(object);
	}

	@Override
	public boolean delete(Identifier id) {
		return repository.delete(id);
	}
	
	

}
