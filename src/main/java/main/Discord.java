/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package main;

import controller.AtendimentoController;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import model.AtendimentoModel;

/**
 *
 * @author jonathandamasiomedeiros
 */
public class Discord {

    public static void main(String[] args) throws SQLException {
        
        AtendimentoModel atendimentoModel = new AtendimentoModel();
        
        atendimentoModel.setNome("Teste");
        atendimentoModel.setData(new Date());
        atendimentoModel.setStatus(0);
        
        AtendimentoController atController = new AtendimentoController();
        List<AtendimentoModel> atendimentoList = null;
        
        
        try {
            atController.save(atendimentoModel);
            atendimentoList = atController.getAll();

            for (AtendimentoModel atend : atendimentoList){
                System.out.println(atend);
                
            }
            
        } catch (SQLException ex) {
            throw new SQLException("Interface não conseguiu inserir senha: " + ex.getMessage(),ex);
        }
        
        
        
    }
}
