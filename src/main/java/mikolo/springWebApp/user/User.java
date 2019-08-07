package mikolo.springWebApp.user;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	//alter table users modify column user_id int NOT NULL AUTO_INCREMENT;
	@Column(name = "user_id")
	private long id; 
	
	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String name;

	@NotNull
	@Column(name = "last_name")
	private String LastName;

	@NotNull
	private int active;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role>roles;
	
	@Transient
	private String operation;
	
	//pole będzie potrzebne do odczytania roli uzytkownika
	@Transient
	private int nrRoli;
	
	@Transient
	private String newPassword;
	
	@Transient
	private String oldPassword;
	
	@Transient
	private String newEmail;
	
}
