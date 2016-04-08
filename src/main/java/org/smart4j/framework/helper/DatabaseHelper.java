package org.smart4j.framework.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.PropsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手类
 * Created by david.cai on 2016/3/4.
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final QueryRunner QUERY_RUNNER;

    private static final BasicDataSource DATA_SOURCE;

    private static final ThreadLocal<Connection> CONNECTION_HOLDER ;

    static {
        QUERY_RUNNER = new QueryRunner();

        CONNECTION_HOLDER = new ThreadLocal<>();

        Properties props = PropsUtil.loadProps("config.properties");

        String driver = props.getProperty("jdbc.driver");
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection connection = CONNECTION_HOLDER.get();

        try {
            if (connection == null) {
                connection = DATA_SOURCE.getConnection();
            }
        } catch (SQLException e) {
            LOGGER.error("get jdbc connection failure", e);
            throw new RuntimeException(e);
        } finally {
            CONNECTION_HOLDER.set(connection);
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     */
    public static void colseConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close jdbc connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 查询实体列表
     * @param entityClass
     * @param sql
     * @param param
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... param){
        List<T> entityList = null;

        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), param);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
            colseConnection();
        }

        return entityList;
    }

    /**
     * 查询实体
     * @param entityClass
     * @param sql
     * @param param
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... param){
        T entity = null;
        try {
            entity = QUERY_RUNNER.query(getConnection(), sql, new BeanHandler<T>(entityClass), param);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        } finally {
            colseConnection();
        }
        return entity;
    }

    /**
     * 查询
     * @param sql
     * @param param
     * @return
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... param){
        List<Map<String, Object>> result = null;
        try {
            result = QUERY_RUNNER.query(getConnection(), sql, new MapListHandler(), param);
        } catch (SQLException e) {
            LOGGER.error("execute query failure", e);
            throw new RuntimeException(e);
        } finally {
            colseConnection();
        }
        return result;
    }

    /**
     * 执行更新语句
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, Object... params){
        int rows = 0;
        try {
            rows = QUERY_RUNNER.update(getConnection(), sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure", e);
            throw new RuntimeException(e);
        } finally {
            colseConnection();
        }
        return rows;
    }

    /**
     * 插入实体
     * @param entity
     * @param fieldMap
     * @return
     */
    public static <T> boolean insertEntity(Class<T> entity, Map<String, Object> fieldMap){
        if (CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not insert entity: fieldMap is emtpy");
            return false;
        }

        String sql = "insert into " + getTableName(entity);
        StringBuilder column = new StringBuilder(" (");
        StringBuilder value = new StringBuilder(" (");
        for(String field : fieldMap.keySet()) {
            column.append(field).append(",");
            value.append("?,");
        }

        column = column.replace(column.lastIndexOf(",") , column.length() , ") ");
        value = value.replace(value.lastIndexOf(",") , value.length() , ") ");

        sql += column + " values " + value ;
        Object[] param = fieldMap.values().toArray();

        return executeUpdate(sql, param) == 1;
    }

    /**
     * 更新实体
     * @param entity
     * @param id
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity (Class<T> entity, long id, Map<String, Object> fieldMap){
        if (CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not update entity: fieldMap is emtpy");
            return false;
        }

        String sql = "update " + getTableName(entity) + " set ";
        StringBuilder column = new StringBuilder("");
        StringBuilder value = new StringBuilder("");
        for(String field : fieldMap.keySet()) {
            column.append(field).append(" = ? ,");
        }

        sql += column.substring(0, column.lastIndexOf(",")) +  " where id = ? ";
        List<Object> param = new ArrayList<>();
        param.addAll(fieldMap.values());
        param.add(id);
        return executeUpdate(sql, param.toArray()) == 1;
    }

    /**
     * 删除实体
     * @param entity
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean deleteEntity(Class<T> entity, long id){
        String sql = "delete from " + getTableName(entity) + " where id = ?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 执行SQL文件
     * @param fileName
     */
    public static void executeSqlFile(String fileName){
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String sql = "";
        try {
            while ((sql = reader.readLine()) != null){
                DatabaseHelper.executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file failure", e);
        }
    }

    private static <T> String getTableName(Class<T> entity) {
        return entity.getSimpleName().toLowerCase();
    }
}
