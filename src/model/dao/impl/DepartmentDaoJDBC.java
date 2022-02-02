package model.dao.impl;

import DB.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
    private Connection conn;
    
    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        try(PreparedStatement st = conn.prepareStatement(
                "INSERT INTO department "
                + "(Name) "
                + "VALUES "
                + "(?)",
                Statement.RETURN_GENERATED_KEYS)){
            st.setString(1, obj.getName());
            
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            } else {
                throw new DbException("Unexpected error! No rows Affected! ");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Department obj) {
        try(PreparedStatement st = conn.prepareStatement(
                "UPDATE department "
                + "SET Name = ? "
                + "WHERE Id = ?")){
            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());
            
            st.executeUpdate();
        }
        catch (SQLException e) { 
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try(PreparedStatement st = conn.prepareStatement(
                "DELETE FROM department "
                + "WHERE Id = ?")){
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Department findById(Integer id) {
        try(PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM department WHERE Id = ?")){
            st.setInt(1, id);
            try(ResultSet rs = st.executeQuery()){
                if(rs.next()){
                    Department dep = new Department();
                    dep.setId(rs.getInt("Id"));
                    dep.setName(rs.getString("Name"));
                    return dep;
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 

    @Override
    public List<Department> findAll() {
        try(PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM department")){
            
            try(ResultSet rs = st.executeQuery()){
                List<Department> list = new ArrayList<>();
                while(rs.next()){
                    Department obj = new Department();
                    obj.setId(rs.getInt("Id"));
                    obj.setName(rs.getString("Name"));
                    list.add(obj);
                }
                return list;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
