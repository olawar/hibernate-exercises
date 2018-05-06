import java.util.Set;

public class Teacher {
	
	private long id;
	private String name;
	private String surname;
	private String topic;
	private Set<SchoolClass> schoolClasses;
	
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
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Set<SchoolClass> getSchoolClasses() {
		return schoolClasses;
	}
	public void setSchoolClasses(Set<SchoolClass> schoolClasses) {
		this.schoolClasses = schoolClasses;
	}
	
	public String toString() {
		return "Teacher: " + getName() + " " + getSurname() + " (" + getTopic() + ")";
	}

}
