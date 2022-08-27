package com.service.impl;


import com.mapper.TableStructureMapper;
import com.pojo.TableStructure;
import com.service.TableStructureService;
import com.utils.DriverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@SuppressWarnings("all")
@Service
@Transactional
public class TableStructureServiceImpl implements TableStructureService {

    @Autowired
    private TableStructureMapper tableStructureMapper;


    @Override
    public void deleteOld(String hostIp, String databaseName, String tableName) {
        tableStructureMapper.deleteOld(hostIp, databaseName, tableName);
    }

    @Override
    public void saveTableStructure(String hostIp, String hostPort, String databaseName, Integer dbVersion, String tableName, String username, String password) {
        String url = "jdbc:mysql://" + hostIp + ":"+ hostPort + "/" + databaseName +
                "?serverTimezone=Asia/Shanghai&characterEncoding=utf-8&useSSL=true&useUnicode=true";
        // 声明Connection对象
        Connection con = null;
        // 声明PreparedStatement对象
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            if (dbVersion.equals(1)) {
                Class.forName(DriverUtil.DRIVER_FOR_8);
            } else if (dbVersion.equals(2)) {
                Class.forName(DriverUtil.DRIVER_FOR_5);
            }
            con = DriverManager.getConnection(url, username, password);
            String sql = "select COLUMN_NAME, COLUMN_TYPE, COLUMN_COMMENT " +
                    "from information_schema.COLUMNS where TABLE_NAME = ?\n" +
                    "and TABLE_SCHEMA = ? order by ORDINAL_POSITION";
            st = con.prepareStatement(sql);
            st.setString(1, tableName);
            st.setString(2, databaseName);
            rs = st.executeQuery();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String columnType = rs.getString("COLUMN_TYPE");
                String columnComment = rs.getString("COLUMN_COMMENT");
                TableStructure structure = new TableStructure();
                structure.setHostIp(hostIp);
                structure.setDatabaseName(databaseName);
                structure.setTableName(tableName);
                structure.setColumnName(columnName);
                structure.setColumnType(columnType);
                structure.setColumnComment(columnComment);
                tableStructureMapper.insert(structure);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    @Override
    public List<TableStructure> findStructureInfo(Integer page, Integer limit, String hostIp, String databaseName, String tableName) {
        Integer start = (page - 1) * limit;
        return tableStructureMapper.findStructureInfo(start, limit, hostIp, databaseName, tableName);
    }

    @Override
    public Long structureCount(String hostIp, String databaseName, String tableName) {
        return tableStructureMapper.structureCount(hostIp, databaseName, tableName);
    }
}
