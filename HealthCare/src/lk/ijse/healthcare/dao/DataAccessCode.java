package lk.ijse.healthcare.dao;

import lk.ijse.healthcare.db.DatabaseConnection;
import lk.ijse.healthcare.dto.DoctorDto;
import lk.ijse.healthcare.dto.PatientDto;

import java.sql.*;
import java.util.ArrayList;

public class DataAccessCode {
    public boolean saveDoctor(DoctorDto dto)
            throws ClassNotFoundException, SQLException {
        PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement("INSERT INTO doctor VALUES (?,?,?,?)");
        statement.setString(1,dto.getdId());
        statement.setString(2,dto.getName());
        statement.setString(3,dto.getAddress());
        statement.setString(4,dto.getContact());
        return statement.executeUpdate()>0;

    }

    public ArrayList<DoctorDto> searchDoctors(String text) throws ClassNotFoundException, SQLException {
        String searchText ="%"+text+"%";

        PreparedStatement stm =DatabaseConnection.getInstance()
                .getConnection().prepareStatement(
                "SELECT * FROM doctor  WHERE address LIKE ? || name LIKE ? || contact LIKE ? "
        );
        stm.setString(1,searchText);
        stm.setString(2,searchText);
        stm.setString(3,searchText);
            ResultSet set =stm.executeQuery();
        ArrayList<DoctorDto> doctors=new ArrayList();
        while (set.next()){
            doctors.add(new DoctorDto(
                    set.getString(1),set.getString(2),
                    set.getString(3), set.getString(4)));
        }
        return doctors;
    }
    public boolean deleteDoctor(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DatabaseConnection.getInstance().getConnection().prepareStatement(
                "DELETE FROM  doctor WHERE did=?"
        );
        statement.setString(1,id);
        return statement.executeUpdate()>0;
    }
    public boolean updateDoctor(DoctorDto dto) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DatabaseConnection.getInstance().getConnection().prepareStatement("UPDATE doctor SET name =?,address=?,contact=? WHERE did= ? ");
        stm.setString(1, dto.getName());
        stm.setString(2, dto.getAddress());
        stm.setString(3, dto.getContact());
        stm.setString(4, dto.getdId());
        return stm.executeUpdate()>0;


    }
 //=----------------------------------------------------------------------------------------------------------
    public boolean savePatient(PatientDto dto) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement("INSERT INTO patient VALUES (?,?,?,?)");
        statement.setString(1,dto.getId());
        statement.setString(2,dto.getName());
        statement.setString(3, dto.getAddress());
        statement.setString(4, dto.getContact());
        return statement.executeUpdate()>0;
    }
    public boolean deletPatient(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement("DELETE FROM patient WHERE pId=?");
        statement.setString(1,id);
        return statement.executeUpdate()>0;
    }
    public ArrayList<PatientDto> searchPatient(String text) throws SQLException, ClassNotFoundException {
        String searchText ="%"+text+"%";

        PreparedStatement stm =DatabaseConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT * FROM patient  WHERE address LIKE ? || name LIKE ? || contact LIKE ? "
                );
        stm.setString(1,searchText);
        stm.setString(2,searchText);
        stm.setString(3,searchText);
        ResultSet set =stm.executeQuery();
        ArrayList<PatientDto> Patients=new ArrayList();
        while (set.next()){
            Patients.add(new PatientDto(
                    set.getString(1),set.getString(2),
                    set.getString(3), set.getString(4)));
        }
        return Patients;
    }
    public boolean updatePatient(PatientDto dto) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement("UPDATE patient SET name =?,address=?,contact=? WHERE pid= ? ");
        statement.setString(1, dto.getName());
        statement.setString(2, dto.getAddress());
        statement.setString(3, dto.getContact());
        statement.setString(4, dto.getId());
        return statement.executeUpdate()>0;
    }


}
