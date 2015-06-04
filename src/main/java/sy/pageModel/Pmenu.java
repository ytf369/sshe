package sy.pageModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sy.model.Smenu;

public class Pmenu {
	private int id;
	private String text;
	private String url;
	private Smenu smenu;
	private List<Smenu> smenus=new ArrayList<Smenu>();
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
	public Smenu getSmenu() {
		return smenu;
	}
	public void setSmenu(Smenu smenu) {
		this.smenu = smenu;
	}
	public List<Smenu> getSmenus() {
		return smenus;
	}
	public void setSmenus(List<Smenu> smenus) {
		this.smenus = smenus;
	}

	
}
