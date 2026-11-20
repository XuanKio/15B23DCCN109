package model;

/** Nhân viên của một rạp. */
public class Employee9 extends Account9 {
    private String employeeCode;
    private String fullName;
    private Cinema9 cinema;

    public Employee9() {
        super();
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Cinema9 getCinema() {
        return cinema;
    }

    public void setCinema(Cinema9 cinema) {
        this.cinema = cinema;
    }
}
