/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;

/**
 * @author ocq
 */
public class DBUtils {

    static org.h2.tools.Server DBserver;
    static Logger log = Logger.getLogger(DBUtils.class.getName());
    public static JdbcConnectionPool cp;

    static DataSource dataSource;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            if (cp == null) {
                cp = JdbcConnectionPool.create("jdbc:h2:KC", "sa", "");
                conn = cp.getConnection();
            } else {
                conn = cp.getConnection();
            }
        } catch (SQLException ex) {
            log.info(ex);
        }
        return conn;
    }

    /**
     * 创建或者更新数据库表
     *
     * @author ocq 添加于2014-7-21
     */
    public static void updateTable() {

        //建表语句，结构“String[0]：表名，String[1]：对应的建表语句”
        String[][] tablesCreateSql = new String[][]{
            //ID INT IDENTITY (1,1)
            {"t_activeEmail", "CREATE TABLE t_activeEmail(siteId  BIGINT(19),siteUrl VARCHAR(500),typeId INTEGER(10),typeName VARCHAR(50),TIME_STAMP timestamp default now(),needGetRegUrlFromEmail INTEGER(1))"},
            {"t_task", "CREATE TABLE t_task(ID INT,data BLOB)"},
            {"t_anonymous", "CREATE TABLE t_anonymous(ID INT IDENTITY (1,1), forms VARCHAR(256))"}

        };

        //表的修改补丁
        String[] tablePatches = new String[]{
            "ALTER TABLE t_activeEmail  ADD COLUMN email varchar(50)",
            "ALTER TABLE t_activeEmail  ADD COLUMN username varchar(50)",
            "ALTER TABLE t_activeEmail  ADD COLUMN  password varchar(50)",
            "ALTER TABLE T_FORUM  ADD COLUMN otherMsg BLOB"
        };

        Connection conn = null;
        Statement pstmt = null;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.createStatement();
            //检查表结构是否需要创建表
            for (int i = 0, len = tablesCreateSql.length; i < len; i++) {

                ResultSet rs = null;
                int tableRowNum = 0;
                try {
                    rs = pstmt.executeQuery("SELECT COUNT(*) FROM ".concat(tablesCreateSql[i][0]));
                    if (rs.next()) {
                        tableRowNum = rs.getInt(1);
                    }
                } catch (Exception e) {
                    //证明表不存在
                    if (e.toString().contains("not found")) {
                        tableRowNum = -1;
                    } else {
                        log.error(e);
                    }
                }

                //如果表不存在就创建
                if (tableRowNum < 0) {
                    try {
                        pstmt.execute(tablesCreateSql[i][1]);
                        log.info(tablesCreateSql[i][0] + "表创建成功");
                    } catch (Exception ex) {

                        if (ex.toString().contains("already exists")) {
                            log.info(tablesCreateSql[i][0] + "表已经存在");
                        } else {
                            log.error(tablesCreateSql[i][1].concat("  执行失败") + ex);
                        }
                    }
                }
            }//建表语句 for end

            for (int i = 0; i < tablePatches.length; i++) {
                try {
                    pstmt.execute(tablePatches[i]);
                } catch (Exception ex) {
                    // 如果报错 Duplicate column name xxx 证明 字段已存在
                    if (ex.toString().contains("Duplicate column name")) {
                        log.info("字段" + tablePatches[i] + "已存在");
                    } else {
                        log.error(ex);
                    }

                }
            }

        } catch (Exception ex) {
            log.error(ex);

        } finally {
            DBUtils.closeStatement(pstmt);
            DBUtils.closeConnection(conn);
        }
    }

    static {
        updateTable();
    }

    /**
     * 移除表
     *
     * @author ocq 添加于2014-7-21
     */
    public static void dropTable() {
        String[][] tables = new String[][]{
            {"t_task", "DROP TABLE t_task"}
        };

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtils.getConnection();
            for (String[] table : tables) {
                for (String elem : table) {
                    if (elem.startsWith("DROP")) {
                        pstmt = conn.prepareStatement(elem);
                        pstmt.execute();
                    }
                }
            }
            log.info("清空数据库成功！");
        } catch (SQLException ex) {
            log.error(ex);
        } finally {
            DBUtils.closeStatement(pstmt);
            DBUtils.closeConnection(conn);
        }
    }

    public static String initDS() {
        String res = "success";
        log.info("初始化数据库连接池");
        try {
            //jdbc:h2:D:/TGB/TGB
            // cp = JdbcConnectionPool.create("jdbc:h2:~/CloudsSaas", "sa", "");
            cp = JdbcConnectionPool.create("jdbc:h2:KC", "sa", "");
            cp.getConnection();
            //  cp = JdbcConnectionPool.create("jdbc:h2:tcp://localhost:9101/CloudsSaas", "sa", "");
        } catch (SQLException ex) {
            log.error(ex);
            res = ex.toString();
        }
        return res;
    }

    public static void initDB() {
//CREATE TABLE t_media(ID INT PRIMARY KEY, keyword VARCHAR(255),url VARCHAR(255),alt VARCHAR(255),status char(5),time_stamp TIMESTAMP default  CURRENT_TIMESTAMP());
        //CREATE TABLE t_keyword_(ID INT PRIMARY KEY, keyword VARCHAR(255),url VARCHAR(255),pubcount int(10),status char(5),time_stamp TIMESTAMP default  CURRENT_TIMESTAMP());
    }

    public static void closeResultset(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception ex) {
            log.error("closeResultset异常" + ex);
        }
    }

    public static void closeStatement(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (Exception ex) {
            log.error("closeStatement异常" + ex);
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
            log.error("closeConnection异常" + ex);
        }
    }

    public static void main(String[] args) {
        new DBUtils();
    }
}
