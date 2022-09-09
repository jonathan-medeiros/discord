/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AtendimentoModel;
import util.ConnectionFactory;

/**
 *
 * @author jonathandamasiomedeiros
 */
public class AtendimentoController {
    
    public int save(AtendimentoModel atendimentoModel) throws SQLException{
        
        String sql = "INSERT INTO ATENDIMENTO "
                + "(NOME, DATA, STATUS) "
                + "VALUES (?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, atendimentoModel.getNome());
            statement.setTimestamp(2, new Timestamp(atendimentoModel.getData().getTime()));
            statement.setInt(3, atendimentoModel.getStatus());
            
            statement.execute();
            
            rs = statement.getGeneratedKeys();
            
            if (rs.next()){
                return rs.getInt(1);
            }
            
        } catch (SQLException ex) {            
            throw new SQLException("Erro ao inserir a senha no banco de dados: " + ex.getMessage(),ex);
        }finally{
            ConnectionFactory.closeConnection(conn, statement);
        }
        
        return 0;
        
    }
    
    public void updateJaAtendido() throws SQLException {
        String sql = "UPDATE ATENDIMENTO SET STATUS = 2 "
                + "WHERE STATUS = 1";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            statement.execute();

        } catch (SQLException e) {
            throw new SQLException("Erro ao tentar atualizar para clientes já atendidos" + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }
    
    public AtendimentoModel getFirst() throws SQLException {

        String sql = "SELECT * FROM ATENDIMENTO WHERE STATUS = 0 order by id asc limit 1";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                AtendimentoModel atendimentoModel = new AtendimentoModel();

                atendimentoModel.setId(resultSet.getInt("ID"));
                atendimentoModel.setNome(resultSet.getString("NOME"));
                atendimentoModel.setData(resultSet.getDate("DATA"));
                atendimentoModel.setStatus(resultSet.getInt("STATUS"));
                return atendimentoModel;
            }
            return null;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao tentar fazer o select que obtém a pessoa a ser atendida no banco de dados " 
                    + e.getMessage(), e);

        } finally {
            ConnectionFactory.closeConnection(conn, statement, resultSet);
        }
    }
    
    public void update(AtendimentoModel atendimentoModel) throws SQLException {
        String sql = "UPDATE ATENDIMENTO SET STATUS = ?, ATENDIMENTO = ?"
                + "WHERE ID = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);

            Integer i = 1;

            statement.setInt(i++, 1);
            statement.setTimestamp(i++, new java.sql.Timestamp(new Date().getTime()));
            statement.setInt(i++, atendimentoModel.getId());

            statement.execute();

        } catch (SQLException e) {
            throw new SQLException("Erro ao tentar atualizar o registro no banco de dados" + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }
    
    
    public List<AtendimentoModel> getAll() throws SQLException{
        
        String sql = "SELECT * FROM ATENDIMENTO";
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List<AtendimentoModel> atendimentoList = new ArrayList();
        
        
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                AtendimentoModel atendimentoModel = new AtendimentoModel();
        
                atendimentoModel.setId(resultSet.getInt("ID"));
                atendimentoModel.setNome(resultSet.getString("NOME"));
                atendimentoModel.setData(resultSet.getTimestamp("DATA"));
                atendimentoModel.setAtendimento(resultSet.getTimestamp("ATENDIMENTO"));
                atendimentoModel.setStatus(resultSet.getInt("STATUS"));
                
                atendimentoList.add(atendimentoModel);
            }
        } catch (SQLException ex) {
            throw new SQLException("Erro ao obter as senhas do banco de dados: " + ex.getMessage(),ex);
        }finally{
            ConnectionFactory.closeConnection(conn, statement, resultSet);
        }
        
        return atendimentoList;
    }
}
