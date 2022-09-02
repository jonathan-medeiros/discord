/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    public void save(AtendimentoModel atendimentoModel) throws SQLException{
        
        String sql = "INSERT INTO ATENDIMENTO "
                + "(NOME, DATA, STATUS) "
                + "VALUES (?, ?, ?)";
        
        
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            
            statement.setString(1, atendimentoModel.getNome());
            statement.setTimestamp(2, new Timestamp(atendimentoModel.getData().getTime()));
            statement.setInt(3, atendimentoModel.getStatus());
            
            statement.execute();
            
        } catch (SQLException ex) {            
            throw new SQLException("Erro ao inserir a senha: " + ex.getMessage(),ex);
        }finally{
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
            throw new SQLException("Erro ao inserir a senha: " + ex.getMessage(),ex);
        }finally{
            ConnectionFactory.closeConnection(conn, statement, resultSet);
        }
        
        return atendimentoList;
        
        
    }
    
    
    
    
    
}
