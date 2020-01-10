package io.nirahtech.gepeen.persistence;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.nirahtech.gepeen.property.Identifier;

public class EntityRepository<T> extends CrudRepositoryOrchestrator implements Repository<T> {
	
	private final Class type = ((Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	private final String TABLE = type.getSimpleName().toLowerCase();
	
	
	
	public EntityRepository() {
		super();
	}

	@Override
	public List<T> findAll() {
		Set<T> result = new HashSet<T>();
		final StringBuilder QUERY = new StringBuilder()
				.append("SELECT * ")
				.append("FROM " + TABLE+';');
		try {
			final Statement STATEMENT = getConnection().createStatement();
			final ResultSet RESULT_SET = STATEMENT.executeQuery(QUERY.toString());
			while (RESULT_SET.next()) {
				final T object = (T) createObject(RESULT_SET, type.getClass());
				result.add(object);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public T find(Identifier id) {
		final StringBuilder QUERY = new StringBuilder()
				.append("SELECT * ")
				.append("FROM " + TABLE+' ')
				.append("WEHERE identifier = ?;");
		try {
			final PreparedStatement preparedStatement = getConnection().prepareStatement(QUERY.toString());
			preparedStatement.setString(1, id.getValue());
			final ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				final T object = (T) createObject(resultSet, type.getClass());
				return object;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean persist(T object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(T object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Identifier id) {
		final StringBuilder QUERY = new StringBuilder()
				.append("DELETE FROM " + TABLE+' ')
				.append("WEHERE identifier = ?;");
		try {
			final PreparedStatement preparedStatement = getConnection().prepareStatement(QUERY.toString());
			preparedStatement.setString(1, id.getValue());
			return preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


}
