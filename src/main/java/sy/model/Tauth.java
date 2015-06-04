package sy.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tauth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tauth", catalog = "sy")
public class Tauth implements java.io.Serializable {

	// Fields

	private String cid;
	private Tauth tauth;
	private String cdesc;
	private String name;
	private BigDecimal seq;
	private String url;
	private Set<Tauth> tauths = new HashSet<Tauth>(0);
	private Set<Troletauth> troletauths = new HashSet<Troletauth>(0);

	// Constructors

	/** default constructor */
	public Tauth() {
	}

	/** minimal constructor */
	public Tauth(String cid, String name) {
		this.cid = cid;
		this.name = name;
	}

	/** full constructor */
	public Tauth(String cid, Tauth tauth, String cdesc, String name, BigDecimal seq, String url, Set<Tauth> tauths, Set<Troletauth> troletauths) {
		this.cid = cid;
		this.tauth = tauth;
		this.cdesc = cdesc;
		this.name = name;
		this.seq = seq;
		this.url = url;
		this.tauths = tauths;
		this.troletauths = troletauths;
	}

	// Property accessors
	@Id
	@Column(name = "cid", unique = true, nullable = false, length = 36)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")
	public Tauth getTauth() {
		return this.tauth;
	}

	public void setTauth(Tauth tauth) {
		this.tauth = tauth;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}
	@Column(name = "cdesc", length = 200)
	public String getCdesc() {
		return cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "seq", precision = 22, scale = 0)
	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	@Column(name = "url", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tauth")
	public Set<Tauth> getTauths() {
		return this.tauths;
	}

	public void setTauths(Set<Tauth> tauths) {
		this.tauths = tauths;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tauth")
	public Set<Troletauth> getTroletauths() {
		return this.troletauths;
	}

	public void setTroletauths(Set<Troletauth> troletauths) {
		this.troletauths = troletauths;
	}

}