package com.service.impl;



import com.mapper.TableOfDatabaseMapper;
import com.pojo.TablesOfDatabase;
import com.service.TableOfDatabaseService;
import com.utils.DriverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("all")
public class TableOfDatabaseServiceImpl implements TableOfDatabaseService {

    private final Logger logger = LoggerFactory.getLogger(TableOfDatabaseServiceImpl.class);

    @Autowired
    private TableOfDatabaseMapper tableOfDatabaseMapper;


    @Override
    public void insert(TablesOfDatabase tablesOfDatabase) {
        tableOfDatabaseMapper.insert(tablesOfDatabase);
    }

    @Override
    public List<TablesOfDatabase> findAllTablesNoPage(String databaseName, String hostIp) {
        return tableOfDatabaseMapper.findAllTablesNoPage(databaseName, hostIp);
    }

    @Override
    public List<TablesOfDatabase> findAllTables(Integer page, Integer rows, String databaseName, String hostIp) {
        Integer start = (page - 1) * rows;
        return tableOfDatabaseMapper.findAllTables(start, rows, databaseName, hostIp);
    }

    @Override
    public Long getCount(String databaseName, String hostIp) {
        return tableOfDatabaseMapper.getCount(databaseName, hostIp);
    }

    @Override
    public void deleteBydAndh(String databaseName, String hostIp) {
        tableOfDatabaseMapper.deleteBydAndh(databaseName, hostIp);
    }


    /**
     * @param con          连接对象
     * @param st           {@link PreparedStatement对象}
     * @param databaseName 数据库名
     * @author Fang Ruichuan
     * @date 2021/11/8 12:50
     * @author Fang Ruichuan
     * @date 2021/10/30 10:10
     */
    @Override
    public void saveTableNameAndTableDesc(String hostIp, Integer hostPort, String databaseName, Integer dbVersion,
                                          String username, String password) {
        String url = "jdbc:mysql://" + hostIp + ":" + hostPort + "/" + databaseName +
                "?serverTimezone=UTC&characterEncoding=utf-8&useSSL=true&useUnicode=true";

        logger.info("hostPort = {}, dbVersion = {}", hostPort, dbVersion);


        // 声明Connection对象
        Connection con = null;
        // 声明PreparedStatement对象
        PreparedStatement st = null;
        ResultSet rs = null;
        ResultSet rowRs = null;
        try {
            if (dbVersion.equals(1)) {
                Class.forName(DriverUtil.DRIVER_FOR_8);
            } else if (dbVersion.equals(2)) {
                Class.forName(DriverUtil.DRIVER_FOR_5);
            }
            con = DriverManager.getConnection(url, username, password);
            String sql = "select TABLE_NAME, TABLE_COMMENT\n" +
                    "from information_schema.tables\n" +
                    "where TABLE_TYPE = 'BASE TABLE'\n" +
                    "and TABLE_SCHEMA = ?";
            st = con.prepareStatement(sql);
            st.setString(1, databaseName);
            rs = st.executeQuery();
            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                String table_desc = rs.getString("TABLE_COMMENT");
                sql = "select count(1) from " + table_name;
                st = con.prepareStatement(sql);
                rowRs = st.executeQuery();
                Integer recordNum = 0;
                if (rowRs.next()) {
                    recordNum = rowRs.getInt(1);
                }
                TablesOfDatabase table = new TablesOfDatabase();
                table.setTableName(table_name);
                table.setTableDesc(table_desc);
                table.setRecordNum(recordNum);
                table.setDatabaseName(databaseName);
                table.setHostIp(hostIp);
                this.insert(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rowRs != null) {
                try {
                    rowRs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
