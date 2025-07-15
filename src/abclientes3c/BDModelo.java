package abclientes3c;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class BDModelo {
Connection conexion=null;
public void manejador(String driverbd){

}

public void conectar(){
    try {
       Class.forName("com.mysql.cj.jdbc.Driver");
        conexion=DriverManager.getConnection(
                "jdbc:mysql://localhost/inrd3a", 
                "root", 
                "conpassword");
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(BDModelo.class.getName()).log(Level.SEVERE, null, ex);
    }
     catch (SQLException ex) {
        Logger.getLogger(BDModelo.class.getName()).log(Level.SEVERE, null, ex);
    }
   
}

public String altas(String u,String p,String c,String n,String a){
    String movimiento="";
    Statement comando;         
    try {
        comando = conexion.createStatement();
            ResultSet registro=
            comando.executeQuery("select * from tclientes where usuario='"+
                    u+"'");
            if (registro.next()==false){
            comando.executeUpdate("insert into tclientes values('"+
                    u+"','"+
                    p+"','"+
                    c+"','"+
                    n+"','"+
                    a+"')");
            movimiento="Cliente agregado";
            }
            else{
                movimiento="Cliente duplicado";
            }
            } catch (SQLException ex) {
        Logger.getLogger(BDModelo.class.getName()).log(Level.SEVERE, null, ex);
    
            }
            return movimiento;
}

public String bajas(String u){
    String movimiento="";
    Statement comando;         
    try {
        comando = conexion.createStatement();
            ResultSet registro=
            comando.executeQuery("select * from tclientes where usuario='"+
                    u+"'");
            if (registro.next()==true){
            comando.executeUpdate("delete from tclientes where usuario='"+
                    u+"'");
            movimiento="Cliente eliminado";
            }
            else{
                movimiento="Cliente No encontrado";
            }
            } catch (SQLException ex) {
        Logger.getLogger(BDModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
            return movimiento;
}

public String cambios(String u,String p,String c,String n,String a){
    String movimiento="";
    Statement comando;         
    try{
       comando = conexion.createStatement();
       ResultSet registro=
       comando.executeQuery("select * from tclientes where usuario='"+u+"'");
            if (registro.next()==true){
            comando.executeUpdate("update tclientes set password='"+
                    p+"',correo='"+c+"',nombre='"+
                    n+"',apellido='"+a+"' where usuario='"+u+"'");
            movimiento="Cliente modificado";
            }else{
                movimiento="Cliente no encontrado";
            }
            } catch (SQLException ex) {
        Logger.getLogger(BDModelo.class.getName()).log(Level.SEVERE, null, ex);   }
            return movimiento;
}

public String[] consultas(String u){
    String movimiento="";
    Statement comando;
    String p,c,n,a;
    String v[]={"","","","",""};
    try{
       comando = conexion.createStatement();
       ResultSet registro=
       comando.executeQuery("select * from tclientes where usuario='"+u+"'");
       if (registro.next()==true){
               p=registro.getString("password");
               c=registro.getString("correo");
               n=registro.getString("nombre");
               a=registro.getString("apellido");
               v[0]=p;  v[1]=c;  v[2]=n;   v[3]=a;
               movimiento="Cliente consultado";
               v[4]=movimiento;
            }else{
                movimiento="Cliente no encontrado";
                v[4]=movimiento;
            }
        } catch (SQLException ex) {
        Logger.getLogger(BDModelo.class.getName()).log(Level.SEVERE, null, ex);   }
        return v;
}

public DefaultTableModel listar(){
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("usuario");
        modeloTabla.addColumn("password");
        modeloTabla.addColumn("correo");
        modeloTabla.addColumn("nombre");
        modeloTabla.addColumn("apellido");
        String u,p,c,n,a;
        
        try {
            // TODO add your handling code here:
            Statement comando=conexion.createStatement();
            ResultSet registro=
            comando.executeQuery("select * from tclientes");
            while (registro.next()){
                u=registro.getString("usuario");
                p=registro.getString("password");
                c=registro.getString("correo");
                n=registro.getString("nombre");
                a=registro.getString("apellido");
                modeloTabla.addRow(
                new Object[]{u,p,c,n,a});
            }
            //.setModel(modeloTabla);
            conexion.close();
            //lbResultado.setText("Listado de Artículos");         
            } catch (SQLException ex) {
            Logger.getLogger(Formulario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return modeloTabla;
}


}
