package io.nirahtech.gepeen.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.nirahtech.jsongeco.JsonParser;

public abstract class CrudRepositoryOrchestrator {
	private static Database database;

	public CrudRepositoryOrchestrator() {
		database = new Database();
		database.connect();
	}

	protected final Connection getConnection() {
		return database.getConnection();
	}

	protected final <T> T createObject(final ResultSet queryResult, final Class<T> classToBuild) {
		final String json = createJson(queryResult, classToBuild);
		final JsonParser jsonParser = new JsonParser();
		return jsonParser.toObject(json, classToBuild);
	}

	private final <T> String createJson(final ResultSet queryResult, final Class<T> classToBuild) {
		List<String> properties = Arrays.asList(classToBuild.getFields()).stream().map(propertie -> propertie.getName()).collect(Collectors.toList());
		String[] objectProperties = new String[properties.size()];
		objectProperties = properties.toArray(objectProperties);
        
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			ResultSetMetaData metadata = queryResult.getMetaData();
			int totalColumns = queryResult.getMetaData().getColumnCount();
			if (totalColumns % 2 == 0) {
				int realTotalColumns = totalColumns / 2;
				for (int index = 1; index <= realTotalColumns; index++) {
					String columnName = metadata.getColumnName(index);
					boolean isSucceded = false;
					for (String property : objectProperties) {
						if (property.equalsIgnoreCase(columnName)) {
							columnName = property;
							int columnType = metadata.getColumnType(index);
							Object columnValue;
							switch (columnType) {
							case Types.ARRAY:
								columnValue = queryResult.getArray(index);
								break;
							case Types.BIGINT:
								columnValue = queryResult.getLong(index);
								break;
							case Types.BINARY:
								columnValue = queryResult.getBinaryStream(index);
								break;
							case Types.BIT:
								columnValue = queryResult.getByte(index);
								break;
							case Types.BLOB:
								columnValue = queryResult.getBlob(index);
								break;
							case Types.BOOLEAN:
								columnValue = queryResult.getBoolean(index);
								break;
							case Types.CHAR:
								columnValue = queryResult.getCharacterStream(index);
								break;
							case Types.CLOB:

								columnValue = queryResult.getClob(index);
								break;
							case Types.DATALINK:

								columnValue = queryResult.getURL(index);
								break;
							case Types.DATE:

								columnValue = queryResult.getDate(index);
								break;
							case Types.DECIMAL:

								columnValue = queryResult.getFloat(index);
								break;
							case Types.DOUBLE:

								columnValue = queryResult.getDouble(index);
								break;
							case Types.FLOAT:

								columnValue = queryResult.getFloat(index);
								break;
							case Types.INTEGER:

								columnValue = queryResult.getInt(index);
								break;
							case Types.JAVA_OBJECT:

								columnValue = queryResult.getObject(index);
								break;
							case Types.LONGNVARCHAR:

								columnValue = queryResult.getString(index);
								break;
							case Types.LONGVARBINARY:

								columnValue = queryResult.getBinaryStream(index);
								break;
							case Types.LONGVARCHAR:

								columnValue = queryResult.getString(index);
								break;
							case Types.NCHAR:

								columnValue = queryResult.getNCharacterStream(index);
								break;
							case Types.NCLOB:

								columnValue = queryResult.getNClob(index);
								break;
							case Types.NULL:
								columnValue = null;
								break;
							case Types.NUMERIC:

								columnValue = queryResult.getInt(index);
								break;
							case Types.NVARCHAR:

								columnValue = queryResult.getNString(index);
								break;
							case Types.OTHER:

								columnValue = queryResult.getObject(index);
								break;
							case Types.REF:

								columnValue = queryResult.getRef(index);
								break;
							case Types.ROWID:

								columnValue = queryResult.getRowId(index);
								break;
							case Types.SMALLINT:

								columnValue = queryResult.getShort(index);
								break;
							case Types.SQLXML:

								columnValue = queryResult.getSQLXML(index);
								break;
							case Types.STRUCT:

								columnValue = queryResult.getObject(index);
								break;
							case Types.TIME:

								columnValue = queryResult.getTime(index);
								break;
							case Types.TIME_WITH_TIMEZONE:

								columnValue = queryResult.getTime(index);
								break;
							case Types.TIMESTAMP:

								columnValue = queryResult.getTimestamp(index);
								break;
							case Types.TIMESTAMP_WITH_TIMEZONE:

								columnValue = queryResult.getTimestamp(index);
								break;
							case Types.TINYINT:

								columnValue = queryResult.getByte(index);
								break;
							case Types.VARBINARY:

								columnValue = queryResult.getBinaryStream(index);
								break;
							case Types.VARCHAR:

								columnValue = queryResult.getString(index);
								break;
							
							default:
								columnValue = null;
								break;
							}
							model.put(columnName, columnValue);
							isSucceded = true;
							break;
						}
					}
					if (!isSucceded) {
						
					}
				}
				StringBuilder json = new StringBuilder();
				json.append('{');
				int currentColumnIndex = 0;
				for (String key : model.keySet()) {
					json.append('"').append(key).append('"').append(':').append(model.get(key));
					if (currentColumnIndex < objectProperties.length-1) {
						json.append(',');
					}
				}
				json.append('}');
				return json.toString();

			} else {

			}
		} catch (SQLException e) {
			// TODO: handle exception
		}

		return null;
	}
}
