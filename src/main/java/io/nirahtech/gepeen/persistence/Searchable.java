package io.nirahtech.gepeen.persistence;

import java.util.List;

import io.nirahtech.gepeen.property.Identifier;

interface Searchable<T> {
	public List<T> findAll();
	public T find(final Identifier id);
	public boolean persist(final T object);
	public boolean update(final T object);
	public boolean delete(final Identifier id);
}
