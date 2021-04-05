package factor.re.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@NamedQueries({@NamedQuery(name = "getAllReimbursement", query = "from Reimbursement")})
@Entity
@Table(name = "ers_reimbursement")
public class Reimbursement {
	@GeneratedValue
	@Id
	@Column(name = "reimb_id")
	private int id;
	@Column(name = "reimb_amount")
	private float amount;
	@Column(name = "reimb_submitted")
	@CreationTimestamp
	private Timestamp submitted;
	@Column(name = "reimb_resolved")
	private Timestamp resolved;
	@Column(name = "reimb_description")
	private String description;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "reimb_author", referencedColumnName = "user_id")
	private User author;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "reimb_resolver", referencedColumnName = "user_id")
	private User resolver;
	@Column(name = "reimb_status_id")
	private int statusId;
	@Column(name = "reimb_type_id")
	private int typeId;
	
	public Reimbursement() {
		//No-arg constructor
	}
	
	public Reimbursement(int id, float amount, Timestamp submitted, Timestamp resolved, String description, User author,
						 User resolver, int statusId, int typeId) {
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.statusId = statusId;
		this.typeId = typeId;
	}

	public Reimbursement(float amount, Timestamp submitted, Timestamp resolved, String description, User author, User resolver, int statusId, int typeId) {
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.statusId = statusId;
		this.typeId = typeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getResolver() {
		return resolver;
	}

	public void setResolver(User resolver) {
		this.resolver = resolver;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Reimbursement that = (Reimbursement) o;
		return id == that.id && Float.compare(that.amount, amount) == 0 && statusId == that.statusId && typeId == that.typeId && Objects.equals(submitted, that.submitted) && Objects.equals(resolved, that.resolved) && Objects.equals(description, that.description) && Objects.equals(author, that.author) && Objects.equals(resolver, that.resolver);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, amount, submitted, resolved, description, author, resolver, statusId, typeId);
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", description=" + description + ", author=" + author + ", resolver=" + resolver + ", status_id="
				+ statusId + ", type_id=" + typeId + "]";
	}
}
