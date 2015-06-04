package sy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "smenu", catalog = "sy")
public class Smenu {
private int id;
private String text;
private String url;
private Smenu smenu;
private Set<Smenu> smenus=new HashSet<Smenu>();
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
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "pid")
public Smenu getSmenu() {
	return smenu;
}
public void setSmenu(Smenu smenu) {
	this.smenu = smenu;
}
@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "smenu")
public Set<Smenu> getSmenus() {
	return smenus;
}
public void setSmenus(Set<Smenu> smenus) {
	this.smenus = smenus;
}
@Override
public String toString() {
	return "Smenu [id=" + id + ", text=" + text + ", url=" + url + ", smenu="
			+ smenu + ", smenus=" + smenus + "]";
}

}
