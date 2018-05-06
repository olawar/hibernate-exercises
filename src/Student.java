import java.util.Set;

public class Student implements java.io.Serializable {

	private long id;
	private String name;
	private String surname;
	private long pesel;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public long getPesel() {
		return pesel;
	}

	public void setPesel(long pesel) {
		this.pesel = pesel;
	}
	
	public String toString() {
		return getName() + " " + getSurname() + " (" + getPesel() + ")";
	}
}