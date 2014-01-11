package bg.unisofia.fmi.docmag.domain.impl.profile;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Profile {

	private String firstName;
	private String surname;

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	private String lastName;
	private String email;
	private String address;
	private String phone;
	private String faculty;
	private String department;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return this.firstName + " " + this.surname + " " + this.lastName;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	@Override
	public String toString() {
		return "Profile: [firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", address=" + address + ", phone="
				+ phone + ", department=" + department + "]";
	}

}