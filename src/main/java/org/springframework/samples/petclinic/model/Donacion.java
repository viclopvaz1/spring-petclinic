package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "donacion")
public class Donacion extends BaseEntity {
	
	@Column(name = "cantidad")
	@Min(1)
	@NotNull
	private Integer cantidad;
	
	@ManyToOne
	@JoinColumn(name = "username")
	private User user;
	
	
	@ManyToOne
	@JoinColumn(name = "causa_id")
	private Causa causa;

}
