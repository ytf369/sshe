package sy.pageModel;

import java.util.List;
import java.util.Set;

import sy.model.Smenu;

public class Service {
private int id;
private String text;
private List<Pmenu> children;
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
public List<Pmenu> getChildren() {
	return children;
}
public void setChildren(List<Pmenu> children) {
	this.children = children;
}

}
