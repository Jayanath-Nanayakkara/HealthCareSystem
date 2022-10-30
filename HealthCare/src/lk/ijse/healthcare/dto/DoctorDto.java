package lk.ijse.healthcare.dto;

public class DoctorDto {
    private String dId;
    private String name;
    private String address;
    private String contact;

    public DoctorDto(String dId, String name, String address, String contact) {
        this.dId = dId;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
