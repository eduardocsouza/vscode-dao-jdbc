package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import db.DB;
import db.DbException;
import models.dao.SellerDao;
import models.entities.Department;
import models.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

    private Connection conn;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj){

        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(
                "INSERT INTO seller (name, email, birthDate, basesalary, departmentId) "
                + "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, obj.getName());
                ps.setString(2, obj.getEmail());
                ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
                ps.setDouble(4, obj.getBaseSalary());
                ps.setInt(5, obj.getDepartment().getId());

                int rows = ps.executeUpdate();

                if(rows >0){
                    ResultSet rs = ps.getGeneratedKeys();
                    if(rs.next()){
                        int id = rs.getInt(1);
                        obj.setId(id);
                    }

                    DB.closeResultSet(rs);
                }
                else{
                    throw new DbException("erro");
                }

        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }

        finally{
            DB.closeStatement(ps);
        }
        
    }

    @Override
    public void update(Seller obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = conn.prepareStatement("SELECT s.*, d.name as DpName FROM seller s "
                                    + "INNER JOIN department d "
                                    + "ON s.departmentId = d.Id "
                                    + "WHERE s.Id = ?");
            
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                Department dep = new Department();
                
                dep.setId(rs.getInt("departmentId"));
                dep.setName(rs.getString("dpname"));

                Seller seller = new Seller();
                seller.setId(rs.getInt("Id"));
                seller.setName(rs.getString("name"));
                seller.setEmail(rs.getString("email"));
                seller.setBirthDate(rs.getDate("birthDate"));
                seller.setBaseSalary(rs.getDouble("basesalary"));
                seller.setDepartment(dep);

                return seller;
            }

            return null;
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
        
    }

    @Override
    public List<Seller> findAll() {
        // TODO Auto-generated method stub
        return null;
    }


}