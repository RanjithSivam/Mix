package com.ranjith.mixmatch.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ranjith.mixmatch.util.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="user_profile")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Scope("prototype")
public class UserProfile {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@CreationTimestamp
	private LocalDate createdAt;
	
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private LocalDate dateOfBirth;
	
	private String email;
	
	private String imageUrl;
	
	private String about;
	
	@Enumerated(EnumType.STRING)
	private GenderEnum gender;
	
	@ElementCollection
	@CollectionTable(name="user_following")
	private List<Integer> following = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name="user_follower")
	private List<Integer> follower = new ArrayList<>();
	
	@OneToOne(optional = false,cascade = CascadeType.ALL)
	@JoinColumn(name="user_id",nullable = false)
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "userProfile",cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Post> post = new ArrayList<>();
}
