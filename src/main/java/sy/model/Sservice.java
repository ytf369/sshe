package sy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
@Entity
@Table(name = "sservice", catalog = "sy")
public class Sservice {
private int id;
private String text;
private Set<Smenu> smens=new HashSet<Smenu>();
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
@ManyToMany(cascade=CascadeType.ALL)
@JoinTable(
        name="service_menu",
        joinColumns=@JoinColumn(name="service_id"),
        inverseJoinColumns=@JoinColumn(name="menu_id")
)
public Set<Smenu> getSmens() {
	return smens;
}
public void setSmens(Set<Smenu> smens) {
	this.smens = smens;
}

}
