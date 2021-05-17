/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.mysql.cj.xdevapi.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author samuel
 */
public class TestMySqlJDBC {
    public static void main(String[] args){
        
        CRUD crud = new CRUD();
        crud.showMenu();
        crud.inputOption();
        
         
    }
}
    
class CRUD{
    private String url = "jdbc:mysql://localhost:3307/test?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true"; //Cadena de conversion
    public void showMenu(){
    System.out.println("Seleccione operacion: ");
    System.out.println("1. Listar personas");
    System.out.println("2. Agregar persona");
    System.out.println("3. Eliminar persona");
    System.out.println("0. Salir");
    }

    public void inputOption(){
        Scanner sc = new Scanner(System.in);
        int opt = sc.nextInt();
        switch(opt){
            case 1: listarPersonas(); break;
            case 2: agregarPersona(); break;
            case 3: eliminarPersona(); break;
            default: System.out.println("Opcion no valida");
        }
    }

    public void listarPersonas(){
        try {
            
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, "root", "[aquí iría tu contraseña del admin de la base de datos]"); //Conexion a schema
            PreparedStatement pst = conexion.prepareStatement( //Instruccion SQL
                "SELECT id_persona, nombre FROM persona");

            ResultSet resultado = pst.executeQuery(); //Ejecución de la consulta
            while(resultado.next()){ //Sacar datos
                
                System.out.print("Nombre Persona: " + resultado.getString("nombre"));
                System.out.println("Id Persona: " + resultado.getInt("id_persona"));
                
                
            }
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void agregarPersona(){
        Scanner sc = new Scanner(System.in);
        String nombre, apellido, email, telefono;
        
        System.out.println("Introduce nombre: ");
        nombre = sc.nextLine();
        
        System.out.println("Introduce apellido: ");
        apellido = sc.nextLine();
        
        System.out.println("Introduce email: ");
        email = sc.nextLine();
        
        System.out.println("Introduce telefono: ");
        telefono = sc.nextLine();
        
        try {
                Connection conexion = DriverManager.getConnection(url, "root", "Codigo1998");
                PreparedStatement pst = conexion.prepareStatement(
                    "INSERT into persona values (?, ?, ?, ?, ?)");
                pst.setInt(1, 0);
                pst.setString(2, nombre);
                pst.setString(3, apellido);
                pst.setString(4, email);
                pst.setString(5, telefono);
                pst.executeUpdate();
                conexion.close();
               

                JOptionPane.showMessageDialog(null, "Registro realizado con éxito");
                conexion.close();
               
            } catch (Exception e) {
                System.err.println("ERROR al registrar cliente. " + e);
                JOptionPane.showMessageDialog(null, "ERROR al registrar persona.\nContacte con el administrador del sistema");
            }
    }
    
    public void eliminarPersona(){
        try {
            Scanner sc = new Scanner(System.in);
            String email;
            System.out.print("Introduce email de la perosna que deseas eliminar: ");
            email = sc.nextLine();
            
            Connection conexion = DriverManager.getConnection(url, "root", "Codigo1998");
            PreparedStatement pst = conexion.prepareStatement(
                    "DELETE FROM persona WHERE email = '" + email + "'");
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    
